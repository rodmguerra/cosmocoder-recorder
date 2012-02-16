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

import com.cosmocoder.recorder.test.object.Address;
import com.cosmocoder.recorder.test.object.Person;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static com.cosmocoder.recorder.core.InvocationRecords.on;
import static com.cosmocoder.recorder.guava.FunctionRecords.newFunction;
import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Ranges.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FunctionRecordsTest {

    @Test
    public void testNewFunction() {

        Person person = new Person("Alex");
        Address address = new Address();
        String streetName = "My Street";
        person.setAddress(address);
        address.setStreetName(streetName);
        Function<Person, String> personToStreetName = newFunction(on(Person.class).getAddress().getStreetName());
        assertThat(personToStreetName.apply(person), is("My Street"));

        List<Person> people = newArrayList();

        transform(people, newFunction(on(Person.class).getAddress().getStreetName()));
    }

    @Test
    public void testFilter() {
        Person john = new Person("John");
        Person joe = new Person("Joe");
        Person ann = new Person("Ann");
        Person sarah = new Person("Sarah");
        Person sally = new Person("Sally");

        john.setAge(40);
        joe.setAge(50);
        ann.setAge(20);
        sarah.setAge(31);
        sally.setAge(25);

        Collection<Person> people = ImmutableSet.of(
                john, joe, ann, sarah, sally
        );

        Collection<Person> over30s = filter(people,
                compose(
                        greaterThan(30),
                        newFunction(on(Person.class).getAge())
                )
        );

        Collection<Person> upperCaseJs = filter(people,
                compose(
                        containsPattern("J"),
                        newFunction(on(Person.class).getName())
                )
        );

        assertThat(
                ImmutableSet.copyOf(over30s),
                is(ImmutableSet.of(john, joe, sarah))
        );

        assertThat(
                ImmutableSet.copyOf(upperCaseJs),
                is(ImmutableSet.of(john, joe))
        );
    }
}
