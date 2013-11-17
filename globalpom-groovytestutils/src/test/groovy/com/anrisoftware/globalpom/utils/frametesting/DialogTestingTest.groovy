/*
 * Copyright 2011-2013 Erwin Müller <erwin.mueller@deventm.org>
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

import org.fest.swing.fixture.DialogFixture
import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see DialogTesting
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class DialogTestingTest {

	@Test
	void "show dialog"() {
		def title = "$NAME/show dialog"
		def testing = factory.create([title: title])()
		testing.withFixture { DialogFixture it ->
			assert it != null
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
