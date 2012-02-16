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

import java.util.LinkedList;
import java.util.Queue;

/**
 * Static utility methods to record Invocations
 *
 * @author Rodrigo Mallmann Guerra
 */
public final class InvocationRecords {

    private InvocationRecords() {
    }

    private static ThreadLocal<Queue<InvocationRecorder>> records = new ThreadLocal<Queue<InvocationRecorder>>();

    static {
        records.set(new LinkedList<InvocationRecorder>());
    }

    /**
     * Creates a template object from which is possible
     * to record a sequence of method invocations
     * @param rootClass the root class of the invocation sequence
     * @param <Root> root class for strong typing record
     * @return the recordable template object
     */
    public static <Root> Root on(Class<Root> rootClass) {
        InvocationRecorder<Root, Object> recorder = new SimpleInvocationRecorder<Root, Object>(rootClass);
        records.get().offer(recorder);
        return recorder.start();
    }

    private static <Root, ReturnType> InvocationRecorder<Root, ReturnType> newRecorder(ReturnType methodReturn) {
        return records.get().poll();
    }

    /**
     * From a sequence of method calls creates a {@code Invocation} object
     * @param methodCalls a sequence of method calls recorded like "on(Root.class).firstMethodCall().secondMethodCall()"}
     * @param <Root> the class of root object of the method call sequence
     * @param <ReturnType> the method call sequence return type
     * @return an object representing the sequence of method calls
     */
    public static <Root, ReturnType> Invocation<Root, ReturnType> newInvocation(ReturnType methodCalls) {
        return InvocationRecords.<Root, ReturnType>newRecorder(methodCalls).end();
    }
}
