/*
 * Copyright 2011-2012 Erwin Müller <erwin.mueller@deventm.org>
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

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.google.common.io.Resources
import com.google.common.testing.SerializableTester

/**
 * Various utilities to simplify the groovy tests.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class TestUtils {

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
	 * Opens the resource with the specified name, relative to the
	 * context class.
	 *
	 * @param resourceName
	 * 		the name of the resource.
	 *
	 * @param contextClass
	 * 		the context {@link Class} to which the resource is search at.
	 * 		If {@code null} the resource will be located with
	 * 		{@code Resources.class.getClassLoader()}.
	 *
	 * @return the opened {@link InputStream} of the resource.
	 *
	 * @since 1.8
	 */
	static InputStream openResourceStream(String resourceName, Class contextClass=null) {
		resourceURL contextClass, resourceName openStream()
	}

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
		Resources.toString resource, charset
	}

	/**
	 * Returns the resource URL with the specified name, relative to the
	 * context class.
	 *
	 * @param resourceName
	 * 		the name of the resource.
	 *
	 * @param contextClass
	 * 		the context {@link Class} to which the resource is search at.
	 * 		If {@code null} the resource will be located with
	 * 		{@code Resources.class.getClassLoader()}.
	 *
	 * @return the {@link URL} of the resource.
	 *
	 * @since 1.8
	 */
	static URL resourceURL(String resourceName, Class contextClass=null) {
		if (contextClass == null) {
			Resources.getResource resourceName
		} else {
			Resources.getResource contextClass, resourceName
		}
	}

	/**
	 * Copy the resource to a target file.
	 * Create any parent directories of the target.
	 *
	 * @since 1.10
	 */
	static void copyResourceToFile(URL resource, File target) {
		def source = resourceToString(resource)
		target.parentFile.mkdirs()
		Files.write source, target, charset
	}

	/**
	 * Copy the resource to a target file and make the file executable.
	 * Create any parent directories of the target.
	 *
	 * @since 1.10
	 */
	static void copyResourceToCommand(URL resource, File target) {
		copyResourceToFile(resource, target)
		target.setExecutable true, false
	}

	/**
	 * Copy a binary resource to a target file.
	 *
	 * @since 1.10
	 */
	static void copyBinResourceToFile(URL resource, File target) {
		def source = Resources.toByteArray resource
		Files.write source, target
	}

	/**
	 * Create a temp file with an optional content.
	 *
	 * @since 1.10
	 */
	static File createTempFile(String text="") {
		def tmpFile = File.createTempFile(this.getClass().name, null)
		Files.write text.toString(), tmpFile, charset
		tmpFile.deleteOnExit()
		return tmpFile
	}

	/**
	 * Create a temporary directory.
	 *
	 * @param files
	 * 		closure that is called with the created directory.
	 *
	 * @since 1.10
	 */
	static File createTempDirectory(def files= {}) {
		def dir = Files.createTempDir()
		files(dir)
		return dir
	}

	/**
	 * Returns the URL from the parent directory of the file.
	 *
	 * @since 1.10
	 */
	static URL parentFileToURL(File file) {
		fileToURL file.parentFile
	}

	/**
	 * Returns the URL from the file.
	 *
	 * @since 1.10
	 */
	static URL fileToURL(File file) {
		file.toURI().toURL()
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
	 * @param trim
	 * 				if the file content and the expected should be trimmed
	 * 				before compared. Default is set to {@code false} which
	 * 				means no trimming is done. See {@link String#trim()}.
	 *
	 * @since 1.11
	 */
	static void assertFileContent(File file, def expected, boolean trim = false) {
		String fileString = fileToString(file)
		String string
		if (expected instanceof URL) {
			string = resourceToString((URL) expected)
		} else if (expected instanceof File) {
			string = fileToString((File) expected)
		} else {
			string = expected.toString()
		}
		if (trim) {
			fileString = fileString.trim()
			string = string.trim()
		}
		if (fileString != string) {
			def diffMatch = new diff_match_patch()
			def patch = diffMatch.patch_make string, fileString
			def diff = diffMatch.patch_toText patch
			assert false : "The contents of $file and the expected differs: ``$diff''"
		}
	}

	/**
	 * Assert that one string equals a different string. If not we create
	 * a difference patch of the string and the file content.
	 *
	 * @since 1.10
	 */
	static void assertStringContent(String stringA, String stringB) {
		if (stringA != stringB) {
			def diffMatch = new diff_match_patch()
			def patch = diffMatch.patch_make stringA, stringB
			def diff = diffMatch.patch_toText patch
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
		Files.toString(file, charset)
	}

	/**
	 * Reads the resource with the specified name and the current
	 * object as the context.
	 *
	 * @param resourceName
	 * 			the name of the resource.
	 *
	 * @return the content of the resource.
	 *
	 * @since 1.8
	 */
	String resourceToStringFromObject(String resourceName) {
		Resources.toString Resources.getResource(this.class, resourceName), charset
	}

	/**
	 * Opens the stream of the resource with the specified name and the current
	 * object as the context.
	 *
	 * @param resourceName
	 * 			the name of the resource.
	 *
	 * @return the opened {@link InputStream} from the resource.
	 *
	 * @since 1.8
	 */
	InputStream openResourceStreamFromObject(String resourceName) {
		resourceURLFromObject(resourceName).openStream()
	}

	/**
	 * Returns the resource with the specified name and the current
	 * object as the context.
	 *
	 * @param resourceName
	 * 			the name of the resource.
	 *
	 * @return the {@link URL} of the resource.
	 *
	 * @since 1.8
	 */
	URL resourceURLFromObject(String resourceName) {
		Resources.getResource(this.class, resourceName)
	}

	/**
	 * Test if a closure is throwing the expected exception class.
	 *
	 * @since 1.10
	 */
	static void shouldFailWith(Class exceptionClass, Closure closure) {
		try {
			closure()
			assert false : "An expected exception was not thrown: $exceptionClass"
		} catch (Throwable t) {
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
			assert false : "The expected cause was not thrown: $exceptionClass"
		} catch (Throwable t) {
			assert t.cause.class == exceptionClass
		}
	}

	/**
	 * Serializes and deserializes the specified object.
	 *
	 * @since 1.10
	 */
	static def reserialize(def object) {
		SerializableTester.reserialize(object)
	}

	/**
	 * Create a new directory. It creates the parent directories automatically.
	 *
	 * @since 1.10
	 */
	static void makeDirectory(File directory) {
		directory.mkdirs()
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
	 * <p>
	 * Starts the executions of actions after an initial delay. The actions are
	 * delayed for a fixed amount of time. At the end we have again a delay.
	 * </p>
	 * <p>
	 * The method is good for actions that the user have to see to verify, like
	 * GUI actions.
	 * </p>
	 * <p>
	 * Example with only the initial and end delay:
	 * </p>
	 * <pre>
	 * sequencedActions { }
	 * </pre>
	 * <p>
	 * Example with only one action:
	 * </p>
	 * <pre>
	 * sequencedActions { model.addElement "Ddd" }
	 * </pre>
	 * <p>
	 * Example to execute different GUI related actions:
	 * </p>
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
	 * @see #startDelay
	 * @see #endDelay
	 * @see #actionDelay
	 *
	 * @since 1.10
	 */
	static void sequencedActions(Object... actions) {
		Thread.sleep startDelay
		actions.first()()
		actions.drop(1).each {
			Thread.sleep actionDelay
			it()
		}
		Thread.sleep endDelay
	}

	/**
	 * Run tests with created files in a temporary directory.
	 *
	 * @param name
	 * 				the prefix name of the empty files.
	 *
	 * @param callback
	 * 				the callback that will run the tests. The first parameter
	 * 				is a map with the temporary directory and the created
	 * 				files: {@code [dir: <tmpDirectory>, files: <files>]}
	 *
	 * @param copy
	 * 				the callback that is used to create files in the temporary
	 * 				directory. The first parameter
	 * 				is a map with the temporary directory and the created
	 * 				files: {@code [dir: <tmpDirectory>, files: <files>]}
	 *
	 * @param count
	 * 				how many empty files should be created in
	 * 				the temporary directory. Default to zero.
	 *
	 * @param keepFiles
	 * 				set to {@code true} to not delete the temporary directory
	 * 				and the created files after the tests are run. Default to
	 * 				{@code false}.
	 *
	 * @since 1.10
	 */
	static void withFiles(String name, def callback, def copy = { }, int count = 0, boolean keepFiles = false) {
		def files = createFiles count, name
		try {
			copy files
			callback files
		} finally {
			keepFiles ? null : deleteFiles(files)
		}
	}

	private static def createFiles(int count, def name) {
		def files = []
		def file
		def dir = createTempDirectory({ dir ->
			(1..count).each {
				file = new File(dir, "${name}_$it")
				file.createNewFile()
				files << file
			}
		})
		[dir: dir, files: files]
	}

	private static deleteFiles(def files) {
		files.dir.deleteDir()
	}
}
