package com.anrisoftware.globalpom.log;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create a new {@link Logger}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.13
 */
interface SerializedLoggerFactory {

	/**
	 * create a new {@link Logger} for the given context {@link Class}.
	 */
	Logger create(@Assisted Class<?> clazz);

	/**
	 * Contains log messages for the template worker.
	 * 
	 * @author Erwin Mueller, erwin.mueller@deventm.org
	 * @since 1.13
	 */
	static class Logger extends AbstractSerializedLogger {

		/**
		 * For serialization.
		 */
		@Deprecated
		public Logger() {
		}

		@Inject
		Logger(@Assisted Class<?> contextClass) {
			super(contextClass);
		}

		void logInfo() {
			log.info("text");
		}

	}
}
