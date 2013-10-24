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
