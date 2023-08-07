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
package com.anrisoftware.globalpom.utils.imagetesting

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder

import groovy.transform.CompileStatic

/**
 * Binds the image testing factory.
 *
 * @see ShowImagesFrameFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.2
 */
@CompileStatic
class ImageTestingModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(ShowImagesFrame.class,
				ShowImagesFrame.class).build(ShowImagesFrameFactory.class))
	}
}
