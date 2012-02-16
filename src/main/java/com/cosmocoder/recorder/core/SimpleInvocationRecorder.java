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


import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.mock;

public class SimpleInvocationRecorder<Root, ReturnType> implements InvocationRecorder<Root, ReturnType> {

    public Class<Root> rootClass;
    private ComposedInvocation<Root, ReturnType> invocation = new ComposedInvocation<Root, ReturnType>();
    private boolean stop = false;

    public SimpleInvocationRecorder(Class<Root> rootClass) {
        this.rootClass = rootClass;
    }

    @Override
    public Root start() {
        if(stop) {
            return new SimpleInvocationRecorder<Root,ReturnType>(rootClass).start();
        }
        else {
            return mock(rootClass, ANSWER);
        }
    }
    
    private Answer<Object> ANSWER = new Answer<Object>() {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
            if(stop) {
                throw new IllegalStateException("Recorder is stoped.");
            }
            Method method = invocationOnMock.getMethod();
            if(mustIgnore(method)) {
                invocation.add(new InvocationStep(method, invocationOnMock.getArguments()));
            }
            if(Modifier.isFinal(method.getReturnType().getModifiers())) {
                return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
            }
            return mock(method.getReturnType(), ANSWER);
        }
    };

    private boolean mustIgnore(Method method) {
        return !method.getName().equals("finalize") && method.getParameterTypes().length == 0;
    }

    @Override
    public Invocation<Root, ReturnType> end() {
        stop = true;
        return invocation;
    }

    @Override
    public <T> Invocation<Root, T> current() {
        return (Invocation<Root, T>) invocation;
    }
}
