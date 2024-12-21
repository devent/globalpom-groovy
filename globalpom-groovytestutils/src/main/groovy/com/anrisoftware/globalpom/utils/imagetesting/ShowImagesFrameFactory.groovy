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
package com.anrisoftware.globalpom.utils.imagetesting;


import com.google.inject.assistedinject.Assisted

/**
 * Factory to create the images frame.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.2
 */
interface ShowImagesFrameFactory {

	/**
	 * Creates a new images frame.
	 * @param args
	 * <ul>
	 * <li>@{code image} or @{code images}, the image or images to show.
	 * <li>optional, the arguments from {@link FrameTestingFactory#create(Map)}
	 * </ul>
	 */
	ShowImagesFrame create(@Assisted Map args)
}

