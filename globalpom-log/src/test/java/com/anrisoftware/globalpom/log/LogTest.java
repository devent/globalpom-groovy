package com.anrisoftware.globalpom.log;

import org.junit.Test;

import com.google.common.testing.SerializableTester;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class LogTest {

	private static Injector injector = Guice.createInjector(new LoggerModule());

	private static SerializedLoggerFactory serializedLoggerFactory = injector
			.getInstance(SerializedLoggerFactory.class);

	private static LoggerFactory loggerFactory = injector
			.getInstance(LoggerFactory.class);

	@Test
	public void serialize_and_deserialize_logger() {
		SerializedLoggerFactory.Logger log = serializedLoggerFactory
				.create(LogTest.class);
		log = SerializableTester.reserialize(log);
		log.logInfo();
	}

	@Test
	public void not_serializable_logger() {
		LoggerFactory.Logger log = loggerFactory.create(LogTest.class);
		log.logInfo();
	}
}
