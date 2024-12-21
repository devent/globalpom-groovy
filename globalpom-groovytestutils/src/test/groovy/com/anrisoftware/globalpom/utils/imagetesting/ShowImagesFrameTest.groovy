/*
 * Copyright 2011-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.utils.imagetesting

import java.awt.Dimension

import javax.imageio.ImageIO

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty

import com.anrisoftware.globalpom.utils.frametesting.FrameTestingModule
import com.google.inject.Guice

import groovy.transform.CompileStatic
import jakarta.inject.Inject

/**
 * @see ShowImagesFrame
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.2
 */
@CompileStatic
@EnabledIfSystemProperty(named = "project.custom.tests.gui", matches = "true")
class ShowImagesFrameTest {

    @Test
    void "show image"() {
        def title = "$NAME/show image"
        def image = ImageIO.read(imageURL)
        def testing = factory.create image: image, title: title, size: frameSize
        testing()
    }

    @Test
    void "show images"() {
        def title = "$NAME/show images"
        def image = ImageIO.read(imageURL)
        def testing = factory.create images: [image, image, image, image], title: title, size: frameSize
        testing()
    }

    @Inject
    ShowImagesFrameFactory factory

    static NAME = ShowImagesFrameTest.class.simpleName

    static Dimension frameSize = new Dimension(171, 171)

    static URL imageURL = ShowImagesFrameTest.class.getResource("x-mail-distribution-list.png")

    @BeforeEach
    void setup() {
        Guice.createInjector(new FrameTestingModule(), new ImageTestingModule()).injectMembers(this)
    }
}
