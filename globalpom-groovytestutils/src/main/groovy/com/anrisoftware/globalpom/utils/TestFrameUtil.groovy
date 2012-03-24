package com.anrisoftware.globalpom.utils

import groovy.swing.SwingBuilder

import java.awt.BorderLayout

import javax.swing.JFrame

import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture

/**
 * Creates a {@link FrameFixture} to test components in a {@link JFrame}.
 * 
 * The method {@link TestFrameUtil#beginPanelFrame()} can be used to create
 * a new {@link FrameFixture}, run the tests for one component and end the 
 * fixture all in one test method:
 * 
 * <pre>
 * beginPanelFrame "Test Frame", component, {
 * 		fixture.label("test").requireText "test label"
 * }
 * </pre>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
class TestFrameUtil extends TestUtils {

	private FrameFixture fixture

	private JFrame frame

	/**
	 * Creates a new {@link FrameFixture} with a {@link JFrame}, runs the test
	 * and end the fixture after the test.
	 * 
	 * @param title
	 * 		the title of the {@link JFrame}.
	 * 
	 * @param component
	 * 		the {@link Component} we test.
	 * 
	 * @param runTest
	 * 		the tests to run.
	 * 
	 * @param frameSize
	 * 		optional the {@link JFrame} size, default is 480x360.
	 */
	void beginPanelFrame(def title, def component, def runTest, def frameSize=[480, 360]) {
		createFrame(title, component, frameSize)
		beginFixture()
		runTest()
		endFixture()
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
	 * @param frameSize
	 * 		optional the {@link JFrame} size, default is 480x360.
	 */
	void createFrame(def title, def component, def frameSize=[480, 360]) {
		frame = new SwingBuilder().frame(title: title, pack: true, preferredSize: frameSize) {
			borderLayout()
			widget(component, constraints: BorderLayout.CENTER)
		}
	}

	/**
	 * Creates and show the {@link FrameFixture}. 
	 */
	void beginFixture() {
		fixture = createFrameFixture()
		fixture.show();
	}

	private createFrameFixture() {
		def result = GuiActionRunner.execute([executeInEDT: { frame } ] as GuiQuery);
		new FrameFixture(result);
	}

	/**
	 * End the {@link FrameFixture}.
	 */
	void endFixture() {
		fixture.cleanUp()
		fixture = null
	}

	/**
	 * Returns the current {@link FrameFixture}.
	 */
	FrameFixture getFixture() {
		fixture
	}
}
