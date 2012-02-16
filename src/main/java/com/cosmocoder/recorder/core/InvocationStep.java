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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singleton;

public class InvocationStep<Root,ReturnType> implements Invocation<Root,ReturnType> {
    private Method method;
    private Object[] params;

    public InvocationStep(Method method, Object... params) {
        this.method = method;
        this.params = params;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    public ReturnType invokeFrom(Root root) {
        try {
            return (ReturnType) method.invoke(root, params);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Could not invoke method", e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Could not invoke method", e);
        }
    }

    @Override
    public List<InvocationStep> getSteps() {
        return new ArrayList<InvocationStep>(singleton(this));
    }
}
