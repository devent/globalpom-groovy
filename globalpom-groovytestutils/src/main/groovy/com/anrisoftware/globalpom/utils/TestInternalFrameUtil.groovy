/*
 * Copyright 2011-2012 Erwin Müller <erwin.mueller@deventm.org>
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

import java.awt.BorderLayout
import java.awt.Dimension

import javax.swing.JDesktopPane
import javax.swing.JFrame
import javax.swing.JInternalFrame

class TestInternalFrameUtil extends TestFrameUtil {

	/**
	 * The default size of the main frame, set to 600x400.
	 */
	def frameSize = new Dimension(600, 400)

	/**
	 * The default size of the internal frame, set to 300x200.
	 */
	def internalFrameSize = new Dimension(300, 200)

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
	 * Sets the title, the test component and optional the Look&Feel to use.
	 *
	 * @param title
	 * 					the title of the frame.
	 *
	 * @param component
	 * 					the {@link Component} component to test.
	 *
	 * @param lookAndFeel
	 * 					optional the Look&Feel to use. Defaults to the system
	 * 					Look&Feel.
	 *
	 * @since 1.13
	 */
	TestInternalFrameUtil(String title, def component, def lookAndFeel = TestFrameUtil.SYSTEM_LOOK_AND_FEEL) {
		super(title, component, lookAndFeel)
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
