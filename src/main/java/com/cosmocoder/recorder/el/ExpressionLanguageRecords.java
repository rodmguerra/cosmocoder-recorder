/*
 * Copyright (C) 2012 Cosmocoder Recorder Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cosmocoder.recorder.el;

import com.cosmocoder.recorder.core.Invocation;
import com.cosmocoder.recorder.core.InvocationStep;

import java.lang.reflect.Method;
import java.util.List;

import static com.cosmocoder.recorder.core.InvocationRecords.newInvocation;

public final class ExpressionLanguageRecords {

    public static String newPropertyExpression(String rootName, Object methodCall) {
        return newPropertyExpressionLanguage(rootName, newInvocation(methodCall));
    }

    public static String newPropertyExpressionLanguage(String rootName, Invocation invocation) {
        StringBuilder sb = new StringBuilder(rootName);
        List<InvocationStep> steps = invocation.getSteps();
        for (InvocationStep step : steps) {
            sb.append(".").append(propertyNameFromGetter(step.getMethod()));
        }
        return sb.toString();
    }

    private static String propertyNameFromGetter(Method getter) {
        String methodName = getter.getName();
        if (getter.getParameterTypes().length == 0 && methodName.startsWith("get") && methodName.length() > 3) {
            return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
        }
        throw new IllegalArgumentException(getter + " is not a getter.");
    }
}
