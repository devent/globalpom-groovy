/*
 * Copyright 2011-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic

import java.awt.Dimension

import javax.imageio.ImageIO
import javax.inject.Inject

import org.junit.Before
import org.junit.Test

import com.anrisoftware.globalpom.utils.frametesting.FrameTestingModule
import com.anrisoftware.globalpom.utils.imagetesting.ShowImagesFrame.ShowImagesFrameFactory
import com.google.inject.Guice

/**
 * @see ShowImagesFrame
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.2
 */
@CompileStatic
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

    @Before
    void setup() {
        Guice.createInjector(new FrameTestingModule(), new ImageTestingModule()).injectMembers(this)
    }
}
