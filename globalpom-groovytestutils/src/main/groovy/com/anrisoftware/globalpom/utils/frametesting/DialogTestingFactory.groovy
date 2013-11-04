package com.anrisoftware.globalpom.utils.frametesting

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
	 * 			  the title of the dialog;
	 *
	 * @param size
	 * 			  the {@link Dimension} size of the dialog;
	 *
	 * @param createDialog
	 * 			  callback to create the {@link JDialog}.
	 *
	 * @param createComponent
	 * 			  callback to create the {@link Component} for the frame;
	 * 			  the {@link JFrame} is passed as the first argument.
	 *
	 * @param setupDialog
	 * 			  callback to setups the {@link JDialog};
	 * 			  the {@link JDialog} is passed as the first argument;
	 * 			  the {@link Component} is passed as the second argument;
	 *
	 * @param createFixture
	 * 			  callback to create the {@link FrameFixture};
	 * 			  the {@link JDialog} is passed as the first argument;
	 *
	 * @return the {@link DialogTesting}.
	 */
	DialogTesting create(Map args)
}
