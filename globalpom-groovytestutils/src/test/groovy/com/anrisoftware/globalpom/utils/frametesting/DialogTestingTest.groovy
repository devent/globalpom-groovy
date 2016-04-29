/*
 * Copyright 2011-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.utils.frametesting

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic

import org.fest.swing.fixture.DialogFixture
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.utils.frametesting.DialogTesting.DialogTestingFactory
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see DialogTesting
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@CompileStatic
class DialogTestingTest {

    @Test
    void "show dialog"() {
        def title = "$NAME/show dialog"
        def testing = factory.create([title: title])()
        testing.withFixture { DialogFixture fix ->
            assert fix != null
        }
    }

    @Test
    void "exception dialog"() {
        def title = "$NAME/exception dialog"
        def testing = factory.create([title: title])()
        shouldFailWith NullPointerException, {
            testing.withFixture { DialogFixture fix ->
                throw new NullPointerException()
            }
        }
    }

    static Injector injector

    static DialogTestingFactory factory

    static NAME = DialogTestingTest.class.simpleName

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new FrameTestingModule())
        factory = injector.getInstance DialogTestingFactory
    }
}
