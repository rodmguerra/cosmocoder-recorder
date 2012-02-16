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
package com.cosmocoder.recorder.core;

import java.util.ArrayList;
import java.util.List;

public class ComposedInvocation<Root, ReturnType> implements Invocation<Root,ReturnType> {

    private List<InvocationStep> steps =
            new ArrayList<InvocationStep>();

    public void add(Invocation invocation) {
        steps.addAll(invocation.getSteps());
    }

    public ReturnType invokeFrom(Root object) {
        Object current = object;
        for (InvocationStep invocation : steps) {
            current = invocation.invokeFrom(current);
        }
        return (ReturnType) current;
    }

    @Override
    public List<InvocationStep> getSteps() {
        return new ArrayList<InvocationStep>(steps);
    }

    @Override
    public String toString() {
        if (steps.isEmpty()) {
            return "Empty Invocation";
        }

        StringBuilder sb =
                new StringBuilder()
                        .append("on(")
                        .append(steps.get(0).getMethod().getDeclaringClass().getCanonicalName())
                        .append(")");

        for (InvocationStep invocation : steps) {
            sb.append(".").append(invocation.getMethod().getName()).append("(");
            for (Object param : invocation.getParams()) {
                sb.append(param);
            }
            sb.append(")");
        }
        return "Invocation{" + sb.toString() + '}';
    }
}
