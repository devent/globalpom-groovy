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
