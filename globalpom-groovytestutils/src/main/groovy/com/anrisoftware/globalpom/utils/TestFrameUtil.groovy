/*
 * Copyright 2011-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.utils

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.swing.SwingUtilities.*

import java.awt.BorderLayout
import java.awt.Dimension

import javax.swing.JFrame
import javax.swing.UIManager

import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture

import groovy.swing.SwingBuilder

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
 * @deprecated use FrameTesting
 *
 * @see TestUtils#sequencedActions(Object...)
 * @see JInternalFrame
 * @see FrameFixture
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
@Deprecated
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
