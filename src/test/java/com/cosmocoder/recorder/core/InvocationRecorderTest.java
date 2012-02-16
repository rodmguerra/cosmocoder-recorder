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

import com.cosmocoder.recorder.test.object.Address;
import com.cosmocoder.recorder.test.object.Person;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InvocationRecorderTest {

    @Test
    public void testInvocation () {
        Person person = new Person("Alex");
        Address address = new Address();
        String streetName = "My Street";
        person.setAddress(address);
        address.setStreetName(streetName);
        
        InvocationRecorder<Person,String> getAddressRec = new SimpleInvocationRecorder<Person,String>(Person.class);
        getAddressRec.start().getAddress().getStreetName();
        assertThat(getAddressRec.end().invokeFrom(person), is(streetName));
    }

}
