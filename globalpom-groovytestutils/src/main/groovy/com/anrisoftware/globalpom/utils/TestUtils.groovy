package com.anrisoftware.globalpom.utils

import java.io.File
import java.io.InputStream
import java.net.URL
import java.nio.charset.Charset

import name.fraser.neil.plaintext.diff_match_patch

import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.lang.builder.ToStringStyle

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
	 * Opens the {@link InputStream} of the resource with the given name, 
	 * relative to the context class.
	 * 
	 * @param contextClass
	 * 		the context {@link Class} to which the resource is search at.
	 * 
	 * @param resourceName
	 * 		the name of the resource.
	 * 
	 * @since 1.7
	 */
	static InputStream openResourceStream(Class contextClass, String resourceName) {
		resourceURL contextClass, resourceName openStream()
	}

	/**
	 * Returns the resource {@link URL} with the given name, relative to the
	 * context class.
	 * 
	 * @param contextClass
	 * 		the context {@link Class} to which the resource is search at.
	 * 
	 * @param resourceName
	 * 		the name of the resource.
	 * 
	 * @since 1.7
	 */
	static URL resourceURL(Class contextClass, String resourceName) {
		Resources.getResource contextClass, resourceName
	}

	/**
	 * The default {@link Charset} for the tests.
	 */
	Charset charset = Charsets.UTF_8

	/**
	 * Create a temp file with an optional content.
	 */
	File createTempFile(String text="") {
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
	 */
	File createTempDirectory(def files= {}) {
		def dir = Files.createTempDir()
		files(dir)
		return dir
	}

	/**
	 * Returns the URL from the parent directory of the file.
	 */
	URL parentFileToURL(File file) {
		fileToURL file.parentFile
	}

	/**
	 * Returns the URL from the file.
	 */
	URL fileToURL(File file) {
		file.toURI().toURL()
	}

	/**
	 * Assert that the file content equals the given string. If not we create
	 * a difference patch of the string and the file content.
	 */
	void assertFileContent(File file, String string) {
		String fileString = fileToString(file)
		if (fileString != string) {
			def diffMatch = new diff_match_patch()
			def patch = diffMatch.patch_make string, fileString
			def diff = diffMatch.patch_toText patch
			assert false : "The contents of $file and the string differs: ``$diff''"
		}
	}

	/**
	 * Assert that one string equals a different string. If not we create
	 * a difference patch of the string and the file content.
	 * 
	 * @since 1.7
	 */
	void assertStringContent(String stringA, String stringB) {
		if (stringA != stringB) {
			def diffMatch = new diff_match_patch()
			def patch = diffMatch.patch_make stringA, stringB
			def diff = diffMatch.patch_toText patch
			assert false : "The contents of string A and the string B differs: ``$diff''"
		}
	}

	/**
	 * Assert that the file is a directory.
	 */
	void assertFileIsDirectory(File file) {
		assert file.isDirectory()
	}

	/**
	 * Read the content of the file.
	 */
	String fileToString(File file) {
		assert file.isFile() : "File $file does not exists"
		Files.toString(file, charset)
	}

	/**
	 * Loads the resource with the given name as a string.
	 */
	String resourceToString(String resourceName, Class contextClass=this.class) {
		Resources.toString Resources.getResource(contextClass, resourceName), charset
	}

	/**
	 * Opens the stream of the resource with the given name.
	 */
	InputStream openResourceStream(String resourceName) {
		resourceURL(resourceName).openStream()
	}

	/**
	 * Returns the resource URL with the given name.
	 */
	URL resourceURL(String resourceName) {
		Resources.getResource(this.class, resourceName)
	}

	/**
	 * Test if a closure is throwing the expected exception class.
	 */
	void shouldFailWith(Class exceptionClass, Closure closure) {
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
	 */
	void shouldFailWithCause(Class exceptionClass, Closure closure) {
		try {
			closure()
			assert false : "The expected cause was not thrown: $exceptionClass"
		} catch (Throwable t) {
			assert t.cause.class == exceptionClass
		}
	}

	/**
	 * Serializes and deserializes the specified object.
	 */
	def reserialize(def object) {
		SerializableTester.reserialize(object)
	}

	/**
	 * Copy the resource to a target file.
	 */
	void copyResourceToFile(String resourceName, File target) {
		def source = resourceToString(resourceName)
		Files.write source, target, charset
	}

	/**
	 * Copy the resource to a target file and make the file executable.
	 */
	void copyResourceToCommand(String resourceName, File target) {
		copyResourceToFile(resourceName, target)
		target.setExecutable true, false
	}

	/**
	 * Copy a binary resource to a target file.
	 */
	void copyBinResourceToFile(String resourceName, File target) {
		def source = Resources.toByteArray Resources.getResource(this.class, resourceName)
		Files.write source, target
	}

	/**
	 * Create a new directory. It creates the parent directories automatically.
	 */
	void makeDirectory(File directory) {
		directory.mkdirs()
	}

	/**
	 * Assert that two decimal  values are equals. The two values are equals 
	 * if the difference is smaller than epsilon.
	 */
	def assertDecimalEquals(Number a, Number b) {
		assert (a - b).abs() < epsilon : "The difference between $a and $b is greater than $epsilon"
	}

	/**
	 * Assert that two decimal values in an array are equals. The two values 
	 * are equals if the difference is smaller than epsilon.
	 */
	def assertDecimalArrayEquals(def a, def b) {
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
	 * Example with only the initial delay:
	 * </p>
	 * <pre>
	 * sequencedActions([{ }])
	 * </pre>
	 * <p>
	 * Example to execute different GUI related actions:
	 * </p>
	 * <pre>
	 * sequencedActions([
	 * 		{ childrenPanel.name = name },
	 * 		{ model.addElement "Ddd" },
	 * 		{ model.addElement "Eee" },
	 * 		{ model.addElement "Fff" }
	 * ])
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
	 * @param startDelay
	 * 		the initial delay before the first action, in milliseconds.
	 * 
	 * @param endDelay
	 * 		the last delay after the actions are executed, in milliseconds.
	 * 
	 * @param actionDelay
	 * 		the delay before each action is executed, in milliseconds.
	 * 
	 * @since 1.8
	 */
	def sequencedActions(def actions, long startDelay=2000, long endDelay=2000, long actionDelay=1000) {
		Thread.sleep startDelay
		actions.first()()
		actions.drop(1).each {
			Thread.sleep actionDelay
			it()
		}
		Thread.sleep endDelay
	}
}
