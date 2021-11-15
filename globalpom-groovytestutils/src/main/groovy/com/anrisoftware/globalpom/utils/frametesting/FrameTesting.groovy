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
package com.anrisoftware.globalpom.utils.frametesting

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.swing.SwingUtilities.*

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Frame

import javax.inject.Inject
import javax.swing.JFrame
import javax.swing.JPanel

import org.fest.swing.annotation.RunsInEDT
import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture

import com.google.inject.assistedinject.Assisted

import groovy.transform.CompileStatic

/**
 * Creates a frame fixture to test components in a frame.
 * <p>
 * The method {@link FrameTesting#withFixture(Object...)} can be used to run
 * tests in the fixture. Multiple tests can be specified and they will be
 * run in a sequence.
 *
 * Dock testing.
 * <p>
 * <h2>Example:</h2>
 * <p>
 * <pre>
 * def injector = Guice.createInjector(new FrameTestingModule())
 * def testingFactory = injector.getInstance(FrameTestingFactory.class)
 * def title = "Frame Test"
 * def testing = testingFactory.create([title: title])()
 * testing.withFixture({
 * 	// tests
 * })
 * </pre>
 *
 * @see TestUtils#sequencedActions(Object...)
 * @see FrameFixture
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.21
 */
@CompileStatic
class FrameTesting {

	/**
	 * The current title.
	 */
	final String title

	/**
	 * Returns the frame size.
	 */
	final Dimension size

	/**
	 * The frame.
	 */
	JFrame frame

	/**
	 * The component.
	 */
	Component component

	/**
	 * The current {@link FrameFixture}.
	 */
	FrameFixture fixture

	private Closure createFrameCallback

	private Closure createComponentCallback

	private Closure setupFrameCallback

	private Closure createFixtureCallback

	/**
	 * @see FrameTestingFactory#create(Map)
	 */
	@Inject
	FrameTesting(@Assisted Map args) {
		args = defaultArgs(args)
		this.title = args.title
		this.size = args.size as Dimension
		this.createFrameCallback = args.createFrame as Closure
		this.createComponentCallback = args.createComponent as Closure
		this.setupFrameCallback = args.setupFrame as Closure
		this.createFixtureCallback = args.createFixture as Closure
	}

	private Map defaultArgs(Map args) {
		[
			title: "Frame Test",
			size: new Dimension(680, 480),
			createFrame: null,
			createComponent: null,
			setupFrame: null,
			createFixture: null
		] << args
	}

	/**
	 * Creates the frame, dock and frame fixture.
	 *
	 * @return this {@link FrameTesting}.
	 */
	FrameTesting call() {
		invokeAndWait {
			frame = createFrame()
			component = createComponent(frame)
			setupFrame(frame, component)
			fixture = createFixture(frame)
		}
		this
	}

	/**
	 * Creates the frame.
	 *
	 * @return the {@link JFrame}.
	 */
	@RunsInEDT
	JFrame createFrame() {
		if (createFrameCallback != null) {
			createFrameCallback()
		} else {
			def frame = new JFrame(title)
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
			frame.setSize size
			frame.setPreferredSize size
			frame
		}
	}

	/**
	 * Creates the frame component.
	 *
	 * @param frame
	 * 			  the {@link JFrame}.
	 *
	 * @return the {@link Component}.
	 */
	@RunsInEDT
	Component createComponent(JFrame frame) {
		if (createComponentCallback != null) {
			createComponentCallback(frame)
		} else {
			new JPanel()
		}
	}

	/**
	 * Setups the frame.
	 *
	 * @param frame
	 * 			  the {@link JFrame}.
	 *
	 * @param component
	 * 			  the {@link Component} of the frame.
	 */
	@RunsInEDT
	void setupFrame(JFrame frame, Component component) {
		if (setupFrameCallback != null) {
			setupFrameCallback(frame, component)
		} else {
			frame.add component, BorderLayout.CENTER
		}
	}

	/**
	 * Creates the frame fixture.
	 *
	 * @param frame
	 * 			  the {@link JFrame}.
	 *
	 * @return the {@link FrameFixture}.
	 */
	@RunsInEDT
	FrameFixture createFixture(JFrame frame) {
		if (createFixtureCallback != null) {
			createFixtureCallback(frame)
		} else {
			def result = GuiActionRunner.execute([executeInEDT: { frame } ] as GuiQuery)
			new FrameFixture(result as Frame)
		}
	}

	/**
	 * Runs the tests. The {@link FrameFixture} is passed
	 * to each specified test as the first argument.
	 *
	 * @param tests
	 * 			  the tests to run.
	 *
	 * @return this {@link FrameTesting}
	 */
	FrameTesting withFixture(Object... tests) {
		beginFixture()
		try {
			sequencedActionsWith(fixture, tests)
		} finally {
			endFixture()
		}
		this
	}

	/**
	 * Creates and show the {@link FrameFixture}.
	 */
	void beginFixture() {
		fixture.show()
	}

	/**
	 * End the {@link FrameFixture}.
	 */
	void endFixture() {
		fixture.cleanUp()
		fixture = null
	}
}
