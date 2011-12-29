package com.anrisoftware.globalpom.utils

import java.io.File
import java.net.URL
import java.nio.charset.Charset

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
	 * Assert that the file content equals the given string.
	 */
	void assertFileContent(File file, String string) {
		assert fileToString(file) == string
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
		Files.toString(file, charset)
	}

	/**
	 * Loads the resource with the given name as a string.
	 */
	String resourceToString(String resourceName) {
		Resources.toString Resources.getResource(this.class, resourceName), charset
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
	 * Create a new directory. It creates the parent directories automatically.
	 */
	void makeDirectory(File directory) {
		directory.mkdirs()
	}
}
