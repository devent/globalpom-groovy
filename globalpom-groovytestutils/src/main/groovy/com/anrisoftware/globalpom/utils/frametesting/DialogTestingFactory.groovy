/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.utils.frametesting;


/**
 * Factory to create dialog testing.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.21
 */
interface DialogTestingFactory {

	/**
	 * Creates a new dialog testing with the specified arguments.
	 *
	 * @param title
	 *            the title of the dialog;
	 *
	 * @param size
	 *            the {@link Dimension} size of the dialog;
	 *
	 * @param createDialog
	 *            callback to create the {@link JDialog}. Have no arguments,
	 *            must return a {@link JDialog} object.
	 *
	 * @param createComponent
	 *            callback to create the {@link Component} for the frame;
	 *            the {@link JFrame} is passed as the first argument, must
	 *            return a {@link Component} object.
	 *
	 * @param setupDialog
	 *            callback to setups the {@link JDialog};
	 *            the {@link JDialog} is passed as the first argument,
	 *            the {@link Component} is passed as the second argument,
	 *            nothing is returned.
	 *
	 * @param createFixture
	 *            callback to create the {@link FrameFixture};
	 *            the {@link JDialog} is passed as the first argument, must
	 *            return a {@link FrameFixture} object.
	 *
	 * @return the {@link DialogTesting}.
	 */
	DialogTesting create(Map args)
}
