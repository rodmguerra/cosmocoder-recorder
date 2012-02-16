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
package com.cosmocoder.recorder.guava;

import com.cosmocoder.recorder.core.Invocation;
import com.google.common.base.Function;

public class InvocationFunction<Root,ReturnType> implements Function<Root,ReturnType> {

    private Invocation<Root,ReturnType> invocation;

    public InvocationFunction(Invocation<Root,ReturnType> invocation) {
        this.invocation = invocation;
    }

    @Override
    public ReturnType apply(Root root) {
        return invocation.invokeFrom(root);
    }

    @Override
    public String toString() {
        return "InvocationFunction{" +
                "invocation=" + invocation +
                '}';
    }
}
