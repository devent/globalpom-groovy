/**
 * Copyright © 2011 Erwin Müller (erwin.mueller@anrisoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.utils.frametesting

import static javax.swing.SwingUtilities.*

import javax.swing.UIManager

/**
 * Sets the Look&Feel.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class LookAndFeel {

	/**
	 * Name of the system look&feel.
	 */
	public static String SYSTEM_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel"

	/**
	 * Name of the GTK look&feel.
	 */
	public static String GTK_LOOK_AND_FEEL = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"

	/**
	 * Name of the nimbus look&feel.
	 */
	public static String NIMBUS_LOOK_AND_FEEL = "javax.swing.plaf.nimbus.NimbusLookAndFeel"

	/**
	 * Name of the substance business look&feel.
	 */
	public static String SUBSTANCE_BUSINESS_LOOK_AND_FEEL = "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel"

	/**
	 * Sets the specified Look&Feel.
	 */
	public static setLookAndFeel(String lookAndFeel) {
		invokeAndWait { UIManager.setLookAndFeel(lookAndFeel) }
	}
}
