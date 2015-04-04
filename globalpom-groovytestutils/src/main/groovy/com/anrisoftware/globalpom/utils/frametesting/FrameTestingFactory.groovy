/*
 * Copyright 2011-2015 Erwin Müller <erwin.mueller@deventm.org>
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




/**
 * Factory to create frame testing.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.21
 */
interface FrameTestingFactory {

    /**
     * Creates a new frame testing with the specified arguments.
     *
     * @param title
     * 			  the title of the frame;
     *
     * @param size
     * 			  the {@link Dimension} size of the frame;
     *
     * @param createFrame
     * 			  callback to create the {@link JFrame}. Have no arguments,
     *            must return a {@link JFrame} object.
     *
     * @param createComponent
     * 			  callback to create the {@link Component} for the frame;
     * 			  the {@link JFrame} is passed as the first argument, must
     *            return a {@link Component} object.
     *
     * @param setupFrame
     * 			  callback to setups the {@link JFrame};
     * 			  the {@link JFrame} is passed as the first argument,
     * 			  the {@link Component} is passed as the second argument,
     *            nothing is returned.
     *
     * @param createFixture
     * 			  callback to create the {@link FrameFixture};
     * 			  the {@link JFrame} is passed as the first argument, must
     *            return a {@link FrameFixture} object.
     *
     * @return the {@link FrameTesting}.
     */
    FrameTesting create(Map args)
}
