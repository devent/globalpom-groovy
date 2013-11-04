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
	 * 			  callback to create the {@link JFrame}.
	 *
	 * @param createComponent
	 * 			  callback to create the {@link Component} for the frame;
	 * 			  the {@link JFrame} is passed as the first argument.
	 *
	 * @param setupFrame
	 * 			  callback to setups the {@link JFrame};
	 * 			  the {@link JFrame} is passed as the first argument;
	 * 			  the {@link Component} is passed as the second argument;
	 *
	 * @param createFixture
	 * 			  callback to create the {@link FrameFixture};
	 * 			  the {@link JFrame} is passed as the first argument;
	 *
	 * @return the {@link FrameTesting}.
	 */
	FrameTesting create(Map args)
}
