package com.anrisoftware.globalpom.log;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Binds the logger classes.
 * 
 * @author Erwin Müller, erwin.mueller@deventm.org
 * @since 1.13
 */
class LoggerModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(
				SerializedLoggerFactory.Logger.class,
				SerializedLoggerFactory.Logger.class).build(
				SerializedLoggerFactory.class));
		install(new FactoryModuleBuilder().implement(
				LoggerFactory.Logger.class, LoggerFactory.Logger.class).build(
				LoggerFactory.class));
	}

}
