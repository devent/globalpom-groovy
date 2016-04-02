/*
 * Copyright 2011-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-groovytestutils.
 *
 * globalpom-groovytestutils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-groovytestutils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-groovytestutils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.utils.frametesting

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic

import org.fest.swing.fixture.FrameFixture
import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see FrameTesting
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@CompileStatic
class FrameTestingTest {

    @Test
    void "show frame"() {
        def title = "$NAME/show frame"
        def testing = factory.create([title: title])()
        testing.withFixture { FrameFixture fix ->
            assert fix != null
        }
    }

    @Test
    void "exception frame"() {
        def title = "$NAME/exception frame"
        def testing = factory.create([title: title])()
        shouldFailWith NullPointerException, {
            testing.withFixture { FrameFixture fix ->
                throw new NullPointerException()
            }
        }
    }

    static Injector injector

    static FrameTestingFactory factory

    static NAME = FrameTestingTest.class.simpleName

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new FrameTestingModule())
        factory = injector.getInstance FrameTestingFactory
    }
}