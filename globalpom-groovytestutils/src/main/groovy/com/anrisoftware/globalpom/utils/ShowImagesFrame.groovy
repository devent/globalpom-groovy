package com.anrisoftware.globalpom.utils;

import static javax.swing.SwingUtilities.invokeLater

import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.event.ActionListener

import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


/**
 * <p>
 * Shows images in a frame. It can show one image or multiple images.
 * </p>
 * <p>
 * Example with one image:
 * </p>
 * <pre>
 * imageA = new Image()
 * new ShowImagesFrame(images=imageA)()
 * </pre>
 * <p>
 * Example with multiple images:
 * </p>
 * <pre>
 * imageA = new Image()
 * imageB = new Image()
 * imageC = new Image()
 * imageD = new Image()
 * new ShowImagesFrame(images=[imageA, imageB, imageC, imageD])()
 * </pre>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
class ShowImagesFrame {

	/**
	 * The image or images to show.
	 */
	def images

	/**
	 * How long the frame is visible before it is closed.
	 */
	long timeout = 60000

	def synchronized call() {
		invokeLater {
			def frame = new JFrame("Icon Show")
			frame.layout = new BorderLayout()

			def count = Math.ceil(getImages().size() / 2) as int
			def panel = new JPanel(new GridLayout(count, count, 4, 4))
			getImages().each {
				def label = new JLabel(new ImageIcon(it))
				panel.add label
			}

			def button = new JButton("Close")
			button.addActionListener({e ->
				frame.visible = false
				synchronized (owner.owner) {
					owner.owner.notifyAll()
				}
			}as ActionListener)

			frame.add panel, BorderLayout.CENTER
			frame.add button, BorderLayout.SOUTH

			frame.setSize 300, 300
			frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
			frame.rootPane.defaultButton = button
			frame.visible = true
		}
		wait timeout
	}

	List getImages() {
		if (images instanceof List) {
			return images
		} else {
			images = [images]
			return images
		}
	}
}