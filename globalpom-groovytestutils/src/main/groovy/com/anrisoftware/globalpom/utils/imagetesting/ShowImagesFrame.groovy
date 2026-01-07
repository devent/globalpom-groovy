/*
 * Copyright 2011-2026 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.utils.imagetesting;

import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridLayout
import java.awt.Image

import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

import org.fest.swing.fixture.FrameFixture

import com.anrisoftware.globalpom.utils.frametesting.FrameTestingFactory
import com.google.inject.assistedinject.Assisted
import com.google.inject.assistedinject.AssistedInject

import groovy.transform.CompileStatic
import jakarta.inject.Inject


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
@CompileStatic
class ShowImagesFrame {

    @Inject
    private FrameTestingFactory frameTestingFactory

    private final List<Image> images

    private final Map args

    @AssistedInject
    ShowImagesFrame(@Assisted Map args) {
        if (args.containsKey('image')) {
            this.images = [args.image] as List<Image>
        } else if (args.containsKey('images')) {
            this.images = args.images as List<Image>
        } else {
            throw new IllegalArgumentException('Missing image/images argument')
        }
        this.args = args
    }

    def call() {
        def testingArgs = new HashMap(args)
        List<JLabel> imageLabels = []
        testingArgs.createComponent = { JFrame frame ->
            def count = Math.ceil((double)images.size() / 2) as int
            def panel = new JPanel(new GridLayout(count, count, 4, 4))
            images.eachWithIndex { Image it, int i ->
                def label = new JLabel(new ImageIcon(it))
                label.setName "image_$i"
                imageLabels << label
                panel.add label
            }
            return panel
        }
        testingArgs.setupFrame = { JFrame frame, Component component ->
            frame.layout = new BorderLayout()
            frame.add component, BorderLayout.CENTER
        }
        def testing = frameTestingFactory.create testingArgs
        testing().withFixture({ FrameFixture fix ->
            imageLabels.each { JLabel it ->
                assert fix.label(it.name).target.icon != null
            }
            fix.close()
        })
    }
}
