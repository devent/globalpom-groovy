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
package com.anrisoftware.globalpom.utils

import java.awt.BorderLayout
import java.awt.Dimension

import javax.swing.JDesktopPane
import javax.swing.JFrame
import javax.swing.JInternalFrame

import com.anrisoftware.globalpom.utils.frametesting.FrameTesting

/**
 * Creates a frame fixture to test components in an internal frame.
 * <p>
 * The method {@link #withFixture(Object...)} can be used to run
 * tests in the fixture. Multiple tests can be specified and they will be
 * run in a sequence.
 *
 * <pre>
 * new TestInternalFrameUtil("Test Frame", component).withFixture(
 * { FrameFixture fixture ->
 * 		fixture.button("test_button").click()
 * }, { FrameFixture fixture ->
 * 		fixture.label("test").requireText "test label"
 * })
 * </pre>
 *
 * @deprecated use FrameTesting
 *
 * @see TestUtils#sequencedActions(Object...)
 * @see JInternalFrame
 * @see FrameFixture
 * @see TestFrameUtil
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
@Deprecated
class TestInternalFrameUtil extends FrameTesting {

	/**
	 * The default size of the internal frame, set to 300x200.
	 */
	Dimension internalFrameSize = new Dimension(300, 200)

	/**
	 * If the internal frame can be resized, default is <code>true</code>.
	 */
	boolean resizable = true

	/**
	 * If the internal frame can be closed, booleanault is <code>false</code>.
	 */
	boolean closable = false

	/**
	 * If the internal frame is maximized, booleanault is <code>true</code>.
	 */
	boolean maximizable = true

	/**
	 * If the internal frame is iconified, booleanault is <code>true</code>.
	 */
	boolean iconifiable = true

	/**
	 * {@link Icon} for the internal frame.
	 */
	def frameIcon = null

	/**
	 * The {@link JInternalFrame} for the test.
	 */
	final JInternalFrame internalFrame

	/**
	 * @see TestFrameUtil#TestFrameUtil(String, Object, Dimension, Object)
	 *
	 * @since 1.19
	 */
	TestInternalFrameUtil(String title, def component, Dimension frameSize = new Dimension(600, 400), def lookAndFeel = TestFrameUtil.SYSTEM_LOOK_AND_FEEL) {
		super(title, component, frameSize, lookAndFeel)
		this.internalFrame = new JInternalFrame(title)
	}


	/**
	 * Creates a new {@link JFrame} with a {@link JInternalFrame} for the
	 * fixture. The component is added to the internal frame.
	 */
	protected createFrame(String title, def component) {
		internalFrame.resizable = resizable
		internalFrame.closable = closable
		internalFrame.maximizable = maximizable
		internalFrame.iconifiable = iconifiable
		internalFrame.frameIcon = frameIcon
		internalFrame.size = internalFrameSize
		internalFrame.visible = true
		internalFrame.layout = new BorderLayout()
		internalFrame.add component
		def frame = new JFrame(title)
		frame.contentPane = new JDesktopPane()
		frame.contentPane.add internalFrame
		internalFrame.setSelected true
		frame.setPreferredSize frameSize as Dimension
		frame
	}
}
