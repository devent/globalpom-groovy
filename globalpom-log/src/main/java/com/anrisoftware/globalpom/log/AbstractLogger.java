package com.anrisoftware.globalpom.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for a {@link Logger}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.13
 */
public abstract class AbstractLogger {

	protected transient Logger log;

	/**
	 * Creates a new {@link Logger} for the given context {@link Class}.
	 */
	protected AbstractLogger(@SuppressWarnings("rawtypes") Class contextClass) {
		this.log = LoggerFactory.getLogger(contextClass);
	}

}
