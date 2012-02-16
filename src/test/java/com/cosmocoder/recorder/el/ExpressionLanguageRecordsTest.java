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

import com.cosmocoder.recorder.test.object.Person;
import org.junit.Test;

import static com.cosmocoder.recorder.core.InvocationRecords.on;
import static com.cosmocoder.recorder.el.ExpressionLanguageRecords.newPropertyExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExpressionLanguageRecordsTest {
    
    @Test
    public void testPropertyEL() {
        String streetNameEL = newPropertyExpression(
                "person", on(Person.class).getAddress().getStreetName()
        );
        assertThat(streetNameEL, is("person.address.streetName"));
    }
}
