/*
 * Copyright 2011-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.utils

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

/**
 *
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
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
