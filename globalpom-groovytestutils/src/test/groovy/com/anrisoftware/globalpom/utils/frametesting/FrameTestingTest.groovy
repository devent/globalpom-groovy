/*
 * Copyright 2011-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import org.fest.swing.fixture.FrameFixture
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty

import com.google.inject.Guice
import com.google.inject.Injector

import groovy.transform.CompileStatic

/**
 * @see FrameTesting
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@CompileStatic
@EnabledIfSystemProperty(named = "project.custom.tests.gui", matches = "true")
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

	@BeforeAll
	static void createFactory() {
		injector = Guice.createInjector(new FrameTestingModule())
		factory = injector.getInstance FrameTestingFactory
	}
}
