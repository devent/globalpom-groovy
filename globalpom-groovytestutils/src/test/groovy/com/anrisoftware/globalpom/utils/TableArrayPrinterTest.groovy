/*
 * Copyright 2011-2013 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.Test

class TableArrayPrinterTest {

	@Test
	void "3-2 table"() {
		def table = [
			["A1", "A2", "A2"],
			["B1", "B2", "B2"]
		]
		def output = TableArrayPrinter.withDefaults(table).toString()
		assertStringContent output, """+--+--+--+
|A1|A2|A2|
+--+--+--+
|B1|B2|B2|
+--+--+--+
"""
	}

	@Test
	void "2-3 table"() {
		def table = [
			["A1", "A2"],
			["B1", "B2"],
			["C1", "C2"],
		]
		def output = TableArrayPrinter.withDefaults(table).toString()
		assertStringContent output, """+--+--+
|A1|A2|
+--+--+
|B1|B2|
+--+--+
|C1|C2|
+--+--+
"""
	}

	@Test
	void "primitive values table"() {
		int[][] table = [
			[10, 20],
			[11, 21],
			[12, 22],
		]
		def output = TableArrayPrinter.withDefaults(table).toString()
		assertStringContent output, """+--+--+
|10|20|
+--+--+
|11|21|
+--+--+
|12|22|
+--+--+
"""
	}
}
