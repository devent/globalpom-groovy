/*
 * Copyright 2011-2014 Erwin Müller <erwin.mueller@deventm.org>
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
import static javax.swing.SwingUtilities.*

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension

import javax.inject.Inject
import javax.swing.JFrame
import javax.swing.JPanel

import org.fest.swing.annotation.RunsInEDT
import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture

import com.google.inject.assistedinject.Assisted

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

	private createFrameCallback

	private createComponentCallback

	private setupFrameCallback

	private createFixtureCallback

	/**
	 * @see FrameTestingFactory#create(Map)
	 */
	@Inject
	FrameTesting(@Assisted Map args) {
		args = defaultArgs(args)
		this.title = args.title
		this.size = args.size
		this.createFrameCallback = args.createFrame
		this.createComponentCallback = args.createComponent
		this.setupFrameCallback = args.setupFrame
		this.createFixtureCallback = args.createFixture
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
			new FrameFixture(result)
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
		sequencedActionsWith(fixture, tests)
		endFixture()
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
