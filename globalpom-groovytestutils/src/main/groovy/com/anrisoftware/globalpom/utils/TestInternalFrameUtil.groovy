/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
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
	def resizable = true

	/**
	 * If the internal frame can be closed, default is <code>false</code>.
	 */
	def closable = false

	/**
	 * If the internal frame is maximized, default is <code>true</code>.
	 */
	def maximizable = true

	/**
	 * If the internal frame is iconified, default is <code>true</code>.
	 */
	def iconifiable = true

	/**
	 * {@link Icon} for the internal frame.
	 */
	def frameIcon = null

	/**
	 * The {@link JInternalFrame} for the test.
	 */
	JInternalFrame internalFrame

	/**
	 * Creates a new {@link JFrame} with a {@link JInternalFrame} for the 
	 * fixture. The component is added to the internal frame.
	 *
	 * @param title
	 * 		the title of the {@link JFrame}.
	 *
	 * @param component
	 * 		the {@link Component} we test.
	 *
	 * @param frameSize
	 * 		optional the {@link JFrame} size, default is 1024x768.
	 * 
	 * @param internalFrameSize
	 * 		optional the size of the {@link JInternalFrame}, default is the
	 * 		half of the {@link JFrame} size.
	 * 
	 * @return the created {@link JFrame}.
	 */
	def createFrame(def title, def component) {
		this.internalFrame = new JInternalFrame(title, resizable, closable, maximizable, iconifiable)
		internalFrame.frameIcon = frameIcon
		internalFrame.setSize internalFrameSize
		internalFrame.setVisible true
		internalFrame.setSelected true
		internalFrame.setLayout new BorderLayout()
		internalFrame.add component

		def frame = new JFrame(title)
		frame.contentPane = new JDesktopPane()
		frame.contentPane.add internalFrame
		frame.setPreferredSize frameSize as Dimension
		frame
	}

	/**
	 * The {@link JInternalFrame} for the test.
	 */
	JInternalFrame getInternalFrame() {
		internalFrame
	}
}
