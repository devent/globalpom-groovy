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
package com.anrisoftware.globalpom.utils

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.swing.SwingUtilities.*
import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Dimension

import javax.swing.JFrame
import javax.swing.UIManager

import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture

/**
 * Creates a frame fixture to test components in a frame.
 * <p>
 * The method {@link TestFrameUtil#withFixture(Object...)} can be used to run
 * tests in the fixture. Multiple tests can be specified and they will be
 * run in a sequence.
 *
 * <pre>
 * new TestFrameUtil("Test Frame", component).withFixture(
 * {  FrameFixture fixture ->
 * 		fixture.button("test_button").click()
 * }, {  FrameFixture fixture ->
 * 		fixture.label("test").requireText "test label"
 * })
 * </pre>
 *
 * @see TestUtils#sequencedActions(Object...)
 * @see JInternalFrame
 * @see FrameFixture
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
class TestFrameUtil {

	/**
	 * Name of the system look&feel.
	 */
	public static String SYSTEM_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel"

	/**
	 * Name of the GTK look&feel.
	 */
	public static String GTK_LOOK_AND_FEEL = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"

	/**
	 * Name of the nimbus look&feel.
	 */
	public static String NIMBUS_LOOK_AND_FEEL = "javax.swing.plaf.nimbus.NimbusLookAndFeel"

	/**
	 * Name of the substance business look&feel.
	 */
	public static String SUBSTANCE_BUSINESS_LOOK_AND_FEEL = "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel"

	/**
	 * Sets the specified Look&Feel.
	 */
	public static setLookAndFeel(String lookAndFeel) {
		invokeAndWait { UIManager.setLookAndFeel(lookAndFeel) }
	}

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
	 * @since 1.20
	 *
	 * @deprecated it is preferable to use the constructor with named arguments.
	 *
	 * @see #TestFrameUtil(Map)
	 */
	@Deprecated
	TestFrameUtil(String title, def component, Dimension frameSize = new Dimension(300, 200)) {
		this(title: title, component: component, frameSize: frameSize)
	}

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
	 * @since 1.20
	 */
	TestFrameUtil(Map args) {
		args = defaultArgs(args)
		this.title = args.title
		this.component = args.component
		this.frameSize = args.frameSize
	}

	private static defaultArgs(Map args) {
		Map defaultArgs = [:]
		defaultArgs.title = "Test Frame"
		defaultArgs.component = null
		defaultArgs.frameSize = new Dimension(300, 200)
		defaultArgs.putAll(args)
		return defaultArgs
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
		def frame
		invokeAndWait {
			frame = new SwingBuilder().frame(title: title, pack: true, preferredSize: frameSize) {
				borderLayout()
				widget(component, constraints: BorderLayout.CENTER)
			}
		}
		return frame
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
