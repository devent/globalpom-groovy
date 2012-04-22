package com.anrisoftware.globalpom.utils

import java.io.File;

import org.junit.Before

import com.google.common.io.Files;
import com.google.inject.Guice
import com.google.inject.Injector


/**
 * Load service script and create a test environment in which to test the
 * sscontrol service scripts.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
abstract class SscontrolEnvironmentUtils extends TestUtils {

	Iterable modules = []

	Injector injector

	/**
	 * Creates the Guice {@link Injector} from the modules.
	 */
	@Before
	void beforeTest() {
		injector = Guice.createInjector(modules)
	}

	/**
	 * Load the service script. First it copies the script resource in a
	 * temporary directory. After the tests are finished we delete the 
	 * directory. You are supposed to implement the
	 * method by using the SscontrolEnvironmentUtils#loadScript(Class, String, URL, Object, Object, boolean)
	 * helper method.
	 * 
	 * @param scriptName
	 * 		the name of the script to load.
	 * 
	 * @param tests
	 * 		the tests to run.
	 * 
	 * @param delete
	 * 		whether to delete the environment after the tests are run.
	 * 
	 * @return
	 * 		the service that is loaded from the script.
	 * 
	 * @see SscontrolEnvironmentUtils#loadScript(Class, String, URL, Object, Object, boolean)
	 */
	abstract loadScript(String scriptName, def tests, boolean delete=true)

	/**
	 * Load the service script. First it copies the script resource in a
	 * temporary directory. After the tests are finished we delete the 
	 * directory.
	 * 
	 * @param handlerClass 
	 * 		the service handler that will load the script.
	 * 
	 * @param scriptName
	 * 		the name of the script to load.
	 * 
	 * @param services
	 * 		the services.
	 * 
	 * @param tests
	 * 		the tests to run. We pass the variable <code>service</code> for the
	 * loaded script service.
	 * 
	 * @param delete
	 * 		whether to delete the environment after the tests are run.
	 */
	def loadScript(Class handlerClass, String scriptName, def services, def tests, boolean delete=true) {
		def scriptsPath = copyScript scriptName
		try {
			def service = loadScriptWithHandler(handlerClass, scriptName, fileToURL(scriptsPath), services, tests)
			tests(service)
		} finally {
			delete ? Files.deleteRecursively(scriptsPath) : false
		}
	}

	private loadScriptWithHandler(Class handlerClass, String scriptName, URL scriptsPath, def services, def tests) {
		def handler = injector.getInstance handlerClass
		handler.loadScript(scriptsPath, scriptName, services)
		return services.getService(handler.serviceName)
	}

	/**
	 * Create here the new service environment, load the service script and
	 * run the script workers. See example:
	 * 
	 * <pre>
	 * createEnvironment().runScript "Database.groovy", profile, {
	 *     assertFileContent "$_/etc/mysql/conf.d/custom.cnf" as File, customCnf
	 * }
	 * </pre>
	 */
	def createEnvironment() {
		return this
	}

	/**
	 * Delete the created database service environment.
	 */
	abstract deleteEnvironment()

	/**
	 * Loads the service script and runs the workers. After the tests are 
	 * finished the environment is deleted. You are supposed to implement the
	 * method by using the SscontrolEnvironmentUtils#runScript(Class, String, URL, Object, Object, Object, boolean)
	 * helper method.
	 * 
	 * @param scriptName
	 * 		the name of the script to load.
	 * 
	 * @param profile
	 * 		the profile for the service.
	 * 
	 * @param tests
	 * 		the tests to run.
	 * 
	 * @param delete
	 * 		whether to delete the environment after the tests are run.
	 * 
	 * @see SscontrolEnvironmentUtils#runScript(Class, String, URL, Object, Object, Object, boolean)
	 */
	abstract runScript(String scriptName, def profile, def tests, boolean delete=true)

	/**
	 * Loads the service script and runs the workers. After the tests are 
	 * finished the environment is deleted.
	 * 
	 * @param handlerClass 
	 * 		the service handler that will load the script.
	 * 
	 * @param scriptName
	 * 		the name of the script to load.
	 * 
	 * @param scriptsPath
	 * 		the URL of the scripts path.
	 * 
	 * @param services
	 * 		the services.
	 * 
	 * @param profile
	 * 		the profile for the service.
	 * 
	 * @param tests
	 * 		the tests to run.
	 * 
	 * @param delete
	 * 		whether to delete the environment after the tests are run.
	 */
	void runScript(Class handlerClass, String scriptName, URL scriptsPath, def services, def profile, def tests, boolean delete=true) {
		try {
			runScriptWithHandler handlerClass, scriptName, scriptsPath, services, profile
			tests()
		} finally {
			delete ? deleteEnvironment() : false
		}
	}

	private runScriptWithHandler(Class handlerClass, String scriptName, URL scriptsPath, def services, def profile) {
		def handler = injector.getInstance handlerClass
		handler.loadScript(scriptsPath, scriptName, services)
		handler.getWorkers(profile, services).each { it.call() }
	}

	/**
	 * Copy the given script resources to a temporary directory.
	 * 
	 * @param scriptName
	 * 		the name of the script resource.
	 * 
	 * @return
	 * 		the temporary directory {@link File}
	 */
	File copyScript(String scriptName) {
		createTempDirectory { dir ->
			copyResourceToFile scriptName, new File(dir, scriptName)
		}
	}
}
