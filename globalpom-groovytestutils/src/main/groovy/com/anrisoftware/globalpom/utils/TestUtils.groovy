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

import java.nio.charset.Charset

import name.fraser.neil.plaintext.diff_match_patch

import org.apache.commons.io.Charsets
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.SerializationUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.joda.time.Duration
import org.joda.time.ReadableDuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Various utilities to simplify the groovy tests.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class TestUtils {

	private final static Logger LOG = LoggerFactory.getLogger(TestUtils)

	/**
	 * Set default to-string style.
	 */
	static toStringStyle = ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE)

	/**
	 * Default epsilon for the equality of two values.
	 */
	static epsilon = 10**-16

	/**
	 * The initial delay before the first action, in milliseconds.
	 *
	 * @see #sequencedActions(Object)
	 */
	static long startDelay = 2000

	/**
	 * The last delay after the actions are executed, in milliseconds.
	 *
	 * @see #sequencedActions(Object)
	 */
	static long endDelay = 2000

	/**
	 * The delay before each action is executed, in milliseconds.
	 *
	 * @see #sequencedActions(Object)
	 */
	static long actionDelay = 1000

	/**
	 * The default {@link Charset} for the tests.
	 *
	 * @since 1.10
	 */
	static Charset charset = Charsets.UTF_8

	/**
	 * Flag if the strings should be trimmed before comparison.
	 * Default is set to {@code false} which
	 * means no trimming is done. See {@link String#trim()}.
	 *
	 * @since 1.11
	 */
	static boolean trimStrings = false

	/**
	 * Flag if the line ending should be ignored before comparison of
	 * strings. Default is set to {@code true} which
	 * means the line ending is ignored.
	 * <p>
	 * On different systems the line ending can be different: Windows is using
	 * {@code \n\r} and Linux is using {@code \n}. If this flag is set to
	 * {@code true} the line ending will be treated the same.
	 *
	 * @since 1.11
	 */
	static boolean normalizeLineEnding = true

	/**
	 * Reads the resource with the specified name and specified character set,
	 * relative to the context class.
	 *
	 * @param resource
	 * 				the URL of the resource.
	 *
	 * @return the content of the resource.
	 *
	 * @since 1.10
	 */
	static String resourceToString(URL resource) {
		IOUtils.toString resource, charset
	}

	/**
	 * Copy the resource to a target file and make the file executable.
	 * Create any parent directories of the target.
	 *
	 * @since 1.10
	 */
	static void copyResourceToCommand(URL resource, File target) {
		FileUtils.copyURLToFile resource, target
		target.setExecutable true, false
	}

	/**
	 * Create a temporary file with an optional content.
	 *
	 * @param text
	 * 			  the text context of the file. Default is empty string.
	 *
	 * @return the temporary {@link File}.
	 *
	 * @since 1.10
	 */
	static File createTempFile(String text="") {
		def tmpFile = File.createTempFile(this.getClass().name, null)
		FileUtils.write tmpFile, text, charset
		tmpFile.deleteOnExit()
		return tmpFile
	}

	/**
	 * Assert that the file content equals the given string. If not we create
	 * a difference patch of the string and the file content.
	 *
	 * @param file
	 * 				the {@link File} that content is read.
	 *
	 * @param expected
	 * 				the expected resource {@link URL}, {@link File} or object.
	 * 				The resource URL or file is read. If it's neither a
	 * 				resource URL nor a file then the object is interpreted
	 * 				as a string.
	 *
	 * @since 1.11
	 */
	static void assertFileContent(File file, def expected) {
		String fileString = fileToString(file)
		String string
		if (expected instanceof URL) {
			string = resourceToString((URL) expected)
		} else if (expected instanceof File) {
			string = fileToString((File) expected)
		} else {
			string = expected.toString()
		}
		assertStringContent(fileString, string)
	}

	/**
	 * Assert that one string equals a different string. If not we create
	 * a difference patch of the string and the file content. The file content is trimmed before
	 * comparison, according to {@link #trimStrings}.
	 *
	 * @param string
	 * 				the test string.
	 *
	 * @param expected
	 * 				the expected string.
	 *
	 * @since 1.11
	 */
	static void assertStringContent(String string, String expected) {
		if (trimStrings) {
			expected = expected.trim()
			string = string.trim()
		}
		if (normalizeLineEnding) {
			string = StringUtils.replace(string, "\r\n", "\n")
			string = StringUtils.replace(string, "\r", "\n")
			expected = StringUtils.replace(expected, "\r\n", "\n")
			expected = StringUtils.replace(expected, "\r", "\n")
		}
		if (string != expected) {
			def log = LoggerFactory.getLogger(TestUtils)
			def diffMatch = new diff_match_patch()
			def patch = diffMatch.patch_make string, expected
			def diff = diffMatch.patch_toText patch
			log.error "String A: \n>>>\n{}<<<EOL', string B: \n>>>\n{}<<<EOL.", string, expected
			assert false : "The contents of string A and the string B differs: ``$diff''"
		}
	}

	/**
	 * Assert that the file is a directory.
	 *
	 * @since 1.10
	 */
	static void assertFileIsDirectory(File file) {
		assert file.isDirectory()
	}

	/**
	 * Read the content of the file.
	 *
	 * @since 1.10
	 */
	static String fileToString(File file) {
		assert file.isFile() : "File $file does not exists"
		FileUtils.readFileToString(file, charset)
	}

	/**
	 * Test if a closure is throwing the expected exception class.
	 *
	 * @since 1.10
	 */
	static void shouldFailWith(Class exceptionClass, Closure closure) {
		try {
			closure()
			assert false : "Expected exception: $exceptionClass"
		} catch (t) {
			LOG.info "Expected exception thrown: {}", t.message, t
			assert t.class == exceptionClass
		}
	}

	/**
	 * Test if a closure is throwing the expected exception class as the
	 * cause of the current exception.
	 *
	 * @since 1.10
	 */
	static void shouldFailWithCause(Class exceptionClass, Closure closure) {
		try {
			closure()
			assert false : "Expected cause: $exceptionClass"
		} catch (t) {
			LOG.info "Expected exception with cause thrown: {}", t.cause
			assert t.cause.class == exceptionClass
		}
	}

	/**
	 * Serializes and deserializes the specified object.
	 *
	 * @return the deserialized object.
	 *
	 * @since 1.15.1
	 */
	static def reserialize(def object) {
		def bytes = SerializationUtils.serialize(object)
		def obj = SerializationUtils.deserialize(bytes)
		LOG.info "Serialized object {}", obj
		return obj
	}

	/**
	 * Assert that two decimal  values are equals. The two values are equals
	 * if the difference is smaller than epsilon.
	 *
	 * @since 1.10
	 */
	static void assertDecimalEquals(Number a, Number b) {
		assert (a - b).abs() < epsilon : "The difference between $a and $b is greater than $epsilon"
	}

	/**
	 * Assert that two decimal values in an array are equals. The two values
	 * are equals if the difference is smaller than epsilon.
	 *
	 * @since 1.10
	 */
	static void assertDecimalArrayEquals(def a, def b) {
		assert a.size() == b.size()
		a.eachWithIndex { it, idx ->
			assert (it - b[idx]).abs() < epsilon : "The difference between $a and $b is greater than $epsilon"
		}
	}

	/**
	 * Starts the executions of actions after an initial delay. The actions are
	 * delayed for a fixed amount of time. At the end we have again a delay.
	 * <p>
	 * The method is good for actions that the user have to see to verify, like
	 * GUI actions.
	 * <p>
	 * Example with only the initial and end delay:
	 * <pre>
	 * sequencedActions { }
	 * </pre>
	 * Example with only one action:
	 * <pre>
	 * sequencedActions { model.addElement "Ddd" }
	 * </pre>
	 * Example to execute different GUI related actions:
	 * <pre>
	 * sequencedActions(
	 * 		{ childrenPanel.name = name },
	 * 		{ model.addElement "Ddd" },
	 * 		{ model.addElement "Eee" },
	 * 		{ model.addElement "Fff" }
	 * )
	 * </pre>
	 *
	 *
	 * @param actions
	 * 		a list of actions. The first actions is the initial action and is
	 * 		executed after the <code>startDelay</code> delay. Subsequent actions
	 * 		are executed after the fixed delay specified in
	 * 		<code>actionDelay</code>. After the last action the delay
	 * 		<code>endDelay</code> is waited.
	 *
	 * @param arg
	 * 					the argument that is passed to each action.
	 *
	 * @see #startDelay
	 * @see #endDelay
	 * @see #actionDelay
	 *
	 * @since 1.10
	 */
	static void sequencedActions(Object... actions) {
		sequencedActionsWith(null, actions)
	}


	/**
	 * Starts the executions of actions after an initial delay. The actions are
	 * delayed for a fixed amount of time. At the end we have again a delay.
	 * <p>
	 * The method is good for actions that the user have to see to verify, like
	 * GUI actions.
	 * <p>
	 * Example with only the initial and end delay:
	 * <pre>
	 * sequencedActionsWith foo, { }
	 * </pre>
	 * Example with only one action:
	 * <pre>
	 * sequencedActions foo, { model.addElement "Ddd" }
	 * </pre>
	 * Example to execute different GUI related actions:
	 * <pre>
	 * sequencedActions(foo,
	 * 		{ childrenPanel.name = name },
	 * 		{ model.addElement "Ddd" },
	 * 		{ model.addElement "Eee" },
	 * 		{ model.addElement "Fff" }
	 * )
	 * </pre>
	 *
	 *
	 * @param actions
	 * 		a list of actions. The first actions is the initial action and is
	 * 		executed after the <code>startDelay</code> delay. Subsequent actions
	 * 		are executed after the fixed delay specified in
	 * 		<code>actionDelay</code>. After the last action the delay
	 * 		<code>endDelay</code> is waited.
	 *
	 * @param arg
	 * 					the argument that is passed to each action.
	 *
	 * @see #startDelay
	 * @see #endDelay
	 * @see #actionDelay
	 *
	 * @since 1.13
	 */
	static void sequencedActionsWith(def arg, Object... actions) {
		Thread.sleep startDelay
		actions.first()(arg)
		actions.drop(1).each {
			Thread.sleep actionDelay
			it(arg)
		}
		Thread.sleep endDelay
	}

	/**
	 * Waits for a condition to be true and asserts that the time it took is
	 * less then the specified timeout duration.
	 *
	 * <pre>
	 * waitFor { condition == true }
	 * </pre>
	 *
	 * @param condition
	 * 				the condition for that we wait for.
	 *
	 * @param timeout
	 * 				the {@link ReadableDuration} duration for the timeout.
	 * 				Defaults to 25 seconds.
	 *
	 * @since 1.13
	 */
	static void waitFor(def condition, ReadableDuration timeout = Duration.parse("PT25S")) {
		long time = System.currentTimeMillis()
		long timeNow = time
		while (!condition() && timeNow - time < timeout.millis) {
			Thread.sleep 100
			timeNow = System.currentTimeMillis()
		}
		assert timeNow - time <= timeout.millis
	}
}
