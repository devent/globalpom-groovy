package com.anrisoftware.globalpom.utils

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
 * @since 1.12
 */
class TestUtils {

	/**
	 * Set default to-string style.
	 */
	static toStringStyle = ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE)

	/**
	 * The default {@link Charset} for the tests.
	 */
	def charset = Charsets.UTF_8

	/**
	 * Create a temp file with an optional content.
	 */
	def createTempFile(def text="") {
		def tmpFile = File.createTempFile(this.getClass().name, null)
		Files.write text.toString(), tmpFile, charset
		tmpFile.deleteOnExit()
		return tmpFile
	}

	/**
	 * Assert that the file content equals the given string.
	 */
	def assertFileContent(File file, def string) {
		assert fileToString(file) == string
	}

	/**
	 * Read the content of the file.
	 */
	def fileToString(File file) {
		Files.toString(file, charset)
	}

	/**
	 * Loads the resource with the given name as a string.
	 */
	def resourceToString(def resourceName) {
		Resources.toString Resources.getResource(this.class, resourceName), charset
	}

	/**
	 * Opens the stream of the resource with the given name.
	 */
	def openResourceStream(def resourceName) {
		Resources.getResource(this.class, resourceName).openStream()
	}

	/**
	 * Test if a closure is throwing the expected exception class.
	 */
	def shouldFailWith(Class exceptionClass, Closure closure) {
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
	def shouldFailWithCause(Class exceptionClass, Closure closure) {
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
	def copyResourceToFile(String resourceName, File target) {
		def source = resourceToString(resourceName)
		Files.write source, target, charset
	}

	/**
	 * Copy the resource to a target file and make the file executable.
	 */
	def copyResourceToCommand(String resourceName, File target) {
		copyResourceToFile(resourceName, target)
		target.setExecutable true, false
	}

	/**
	 * Create a new directory. It creates the parent directories automatically.
	 */
	def makeDirectory(File directory) {
		new File(it).mkdirs()
	}
}
