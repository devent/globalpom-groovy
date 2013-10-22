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
	 * @return the {@link FrameTesting}.
	 */
	FrameTesting create(Map args)
}
