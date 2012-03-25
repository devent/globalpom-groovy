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
		def internalFrame = new JInternalFrame(title, true, false, true, true)
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
}
