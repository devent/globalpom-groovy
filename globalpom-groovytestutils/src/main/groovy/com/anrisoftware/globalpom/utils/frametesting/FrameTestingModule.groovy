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
package com.anrisoftware.globalpom.utils.frametesting

import groovy.transform.CompileStatic

import com.anrisoftware.globalpom.utils.frametesting.DialogTesting.DialogTestingFactory
import com.anrisoftware.globalpom.utils.frametesting.FrameTesting.FrameTestingFactory
import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder

/**
 * Binds the frame testing factory.
 *
 * @see FrameTestingFactory
 * @see DialogTestingFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.21
 */
@CompileStatic
class FrameTestingModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(FrameTesting.class,
                FrameTesting.class).build(FrameTestingFactory.class))
        install(new FactoryModuleBuilder().implement(DialogTesting.class,
                DialogTesting.class).build(DialogTestingFactory.class))
    }
}
