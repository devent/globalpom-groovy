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
