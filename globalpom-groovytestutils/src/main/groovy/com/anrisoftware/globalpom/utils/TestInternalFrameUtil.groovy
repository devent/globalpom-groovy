package com.anrisoftware.globalpom.utils

import java.awt.BorderLayout
import java.awt.Dimension

import javax.swing.JDesktopPane
import javax.swing.JFrame
import javax.swing.JInternalFrame

class TestInternalFrameUtil extends TestFrameUtil {

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
	 * @return the created {@link JFrame}.
	 */
	def createFrame(def title, def component, def frameSize=[1024, 768]) {
		def internalFrame = new JInternalFrame(title, true, false, true, true)
		internalFrame.setSize frameSize[0]/2 as int, frameSize[1]/2 as int
		internalFrame.setVisible true
		internalFrame.setSelected true
		internalFrame.setLayout new BorderLayout()
		internalFrame.add component
		internalFrame.setOpaque true

		def frame = new JFrame(title)
		frame.contentPane = new JDesktopPane()
		frame.contentPane.add internalFrame
		frame.setPreferredSize new Dimension(frameSize[0] as int, frameSize[1] as int)
		frame
	}
}
