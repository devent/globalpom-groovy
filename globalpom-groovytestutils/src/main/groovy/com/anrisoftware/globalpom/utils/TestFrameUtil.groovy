package com.anrisoftware.globalpom.utils

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
	def frameSize = new Dimension(300, 200)

	/**
	 * The name of the look and feel used, default to 
	 * "javax.swing.plaf.metal.MetalLookAndFeel".
	 */
	def lookAndFeel = SYSTEM_LOOK_AND_FEEL

	FrameFixture fixture

	JFrame frame

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
	 */
	void beginPanelFrame(def title, def component, Object... tests) {
		UIManager.setLookAndFeel(lookAndFeel)
		frame = createFrame(title, component)
		beginFixture()
		sequencedActions tests
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
	 * @return the created {@link JFrame}.
	 */
	def createFrame(def title, def component) {
		new SwingBuilder().frame(title: title, pack: true, preferredSize: frameSize) {
			borderLayout()
			widget(component, constraints: BorderLayout.CENTER)
		}
	}

	/**
	 * Creates and show the {@link FrameFixture}. 
	 */
	void beginFixture() {
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
	void endFixture() {
		fixture.cleanUp()
		fixture = null
	}

	/**
	 * Returns the {@link JFrame} of the fixture.
	 */
	JFrame getFrame() {
		frame
	}

	/**
	 * Returns the current {@link FrameFixture}.
	 */
	FrameFixture getFixture() {
		fixture
	}
}
