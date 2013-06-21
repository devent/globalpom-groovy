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
package com.anrisoftware.globalpom.utils

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Dimension

import javax.swing.JFrame
import javax.swing.UIManager

import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture

/**
 * Creates a {@link FrameFixture} to test components in a {@link JFrame}.
 * <p>
 * The method {@link TestFrameUtil#withFixture(Object...)} can be used to run
 * tests in the fixture. Multiple tests can be specified and they will be
 * run in a sequence. See {@link TestUtils#sequencedActions(Object...)}. *
 *
 * <pre>
 * new TestFrameUtil("Test Frame", component).withFixture {
 * 		fixture.button("test_button").click()
 * }, {
 * 		fixture.label("test").requireText "test label"
 * }
 * </pre>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
class TestFrameUtil {

	/**
	 * Name of the system look&feel.
	 */
	public static SYSTEM_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel"

	/**
	 * Name of the GTK look&feel.
	 */
	public static GTK_LOOK_AND_FEEL = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"

	/**
	 * Name of the nimbus look&feel.
	 */
	public static NIMBUS_LOOK_AND_FEEL = "javax.swing.plaf.nimbus.NimbusLookAndFeel"

	/**
	 * The size of the main frame, default set to 300x200.
	 */
	Dimension frameSize = new Dimension(300, 200)

	private final String title

	private final def component

	/**
	 * The current {@link FrameFixture}.
	 */
	FrameFixture fixture

	/**
	 * The {@link JFrame} of the fixture.
	 */
	JFrame frame

	/**
	 * Sets the title, the test component and optional the Look&Feel to use.
	 *
	 * @param title
	 * 			  the title of the frame.
	 *
	 * @param component
	 * 			  the {@link Component} component to test.
	 * 
	 * @param frameSize
	 * 			  the size of the main frame, default set to 300x200.
	 *
	 * @param lookAndFeel
	 * 			  optional the Look&Feel to use. Defaults to the system
	 * 			  Look&Feel.
	 *
	 * @since 1.19
	 */
	TestFrameUtil(String title, def component, Dimension frameSize = new Dimension(300, 200), def lookAndFeel = SYSTEM_LOOK_AND_FEEL) {
		UIManager.setLookAndFeel(lookAndFeel)
		this.title = title
		this.component = component
		this.frameSize = frameSize
	}

	/**
	 * Creates a new {@link FrameFixture} with a {@link JFrame}, runs the test
	 * and end the fixture after the test. The {@link FrameFixture} is passed
	 * to each specified test.
	 *
	 * @param tests
	 * 					the tests to run.
	 *
	 * @return this {@link TestFrameUtil}
	 *
	 * @since 1.13
	 */
	TestFrameUtil withFixture(Object... tests) {
		frame = createFrame(title, component)
		beginFixture()
		sequencedActionsWith fixture, tests
		endFixture()
		this
	}

	/**
	 * Creates the {@link JFrame} for the fixture.
	 *
	 * @param title
	 * 		the title of the {@link JFrame}.
	 *
	 * @param component
	 * 		the {@link Component} we test.
	 *
	 * @return the created {@link JFrame}.
	 */
	protected createFrame(String title, def component) {
		new SwingBuilder().frame(title: title, pack: true, preferredSize: frameSize) {
			borderLayout()
			widget(component, constraints: BorderLayout.CENTER)
		}
	}

	/**
	 * Creates and show the {@link FrameFixture}.
	 */
	private beginFixture() {
		fixture = createFrameFixture()
		fixture.show()
	}

	private createFrameFixture() {
		def result = GuiActionRunner.execute([executeInEDT: { frame } ] as GuiQuery)
		new FrameFixture(result)
	}

	/**
	 * End the {@link FrameFixture}.
	 */
	private endFixture() {
		fixture.cleanUp()
		fixture = null
	}

	private void setFixture(FrameFixture fixture) {
	}
}
