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
	 * @return the {@link DialogTesting}.
	 */
	DialogTesting create(Map args)
}
