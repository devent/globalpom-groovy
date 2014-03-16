/*
 * Copyright 2011-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.utils.frametesting

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
class FrameTestingModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(FrameTesting.class,
				FrameTesting.class).build(FrameTestingFactory.class))
		install(new FactoryModuleBuilder().implement(DialogTesting.class,
				DialogTesting.class).build(DialogTestingFactory.class))
	}
}
