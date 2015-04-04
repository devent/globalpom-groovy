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
package com.anrisoftware.globalpom.utils

import groovy.util.logging.Slf4j

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecuteResultHandler
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.ExecuteResultHandler
import org.apache.commons.exec.ExecuteWatchdog
import org.apache.commons.exec.Executor
import org.apache.commons.exec.PumpStreamHandler
import org.joda.time.Duration

/**
 * Creates a process for tests.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.12
 */
@Slf4j
class ProcessContext {

	/**
	 * Sets the {@link Duration} timeout of the process in milliseconds.
	 * <p>
	 * Defaults to 10 seconds.
	 */
	public static Duration timeout = Duration.parse("PT10S")

	private final CommandLine cmdLine

	private final ExecuteResultHandler processResult

	private final ExecuteWatchdog watchdog

	private final Executor executor

	private final ByteArrayOutputStream processStream

	private Exception exception

	/**
	 * Sets the process command to execute.
	 *
	 * @param process
	 * 				the process command with parameter.
	 */
	ProcessContext(String process) {
		cmdLine = CommandLine.parse(process)
		processResult = new DefaultExecuteResultHandler()
		watchdog = new ExecuteWatchdog(timeout.millis)
		executor = new DefaultExecutor()
		processStream = new ByteArrayOutputStream()
		executor.streamHandler = new PumpStreamHandler(processStream)
		executor.watchdog = watchdog
	}

	/**
	 * Creates a process and runs a test in the context of the process.
	 *
	 * @param test
	 * 				the test callback to run after the process was started.
	 *
	 * @return this {@link ProcessContextTestUtils}
	 */
	ProcessContext inProcessContext(def test) {
		startProcess()
		try {
			test()
		} catch (e) {
			exception = e
		} finally {
			waitForProcessToTerminate()
		}
		if (exception != null) {
			watchdog.destroyProcess()
			throw exception
		}
		this
	}

	private startProcess() throws IOException {
		executor.execute(cmdLine, processResult)
		exception = processResult.hasResult() ? processResult.exception : null
		if (exception != null) {
			log.error "Error execute the process '$cmdLine'"
			throw exception
		}
		Thread.sleep 500
	}

	private waitForProcessToTerminate() {
		waitFor { exception != null || processResult.hasResult() }
	}

	private waitFor(def callback) {
		while (!callback()) {
			Thread.sleep 500
		}
	}

	/**
	 * Returns the process output as a byte array.
	 *
	 * @return the byte array of the output.
	 */
	public byte[] getProcessByteArray() {
		processStream.toByteArray()
	}
}
