package com.anrisoftware.globalpom.utils

import static org.apache.commons.lang3.Validate.*

import org.apache.commons.lang3.ArrayUtils

import com.anrisoftware.propertiesutils.ContextProperties
import com.anrisoftware.propertiesutils.ContextPropertiesFactory

/**
 * Print an 2D array as a table.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @author Lyubomyr Shaydariv, http://stackoverflow.com/users/166589/lyubomyr-shaydariv
 * @author http://stackoverflow.com/a/11384393
 * @since 1.14
 */
class TableArrayPrinter {

	private static final URL PROPERTIES_RESOURCE = TableArrayPrinter.class.getResource("/table_array_printer.properties")

	/**
	 * Returns the printer with default properties.
	 *
	 * @param values
	 * 					the 2D array. Can be a list or an array.
	 *
	 * @return the {@link TableArrayPrinter}.
	 */
	public static TableArrayPrinter withDefaults(def values) {
		def p = new ContextPropertiesFactory(TableArrayPrinter).fromResource(PROPERTIES_RESOURCE)
		def table = values.inject([]) { list, v ->
			if (v.getClass().isArray()) {
				if (v.getClass().getComponentType().isPrimitive()) {
					v = ArrayUtils.toObject(v)
				}
				list << Arrays.asList(v)
			} else {
				list << v
			}
		}
		new TableArrayPrinter(p, table)
	}

	private final char borderKnot

	private final char horizontalBorder

	private final char verticalBorder

	private final String nullString

	private final List table

	TableArrayPrinter(ContextProperties p, List table) {
		notNull p, "Printer properties cannot be null."
		notNull table, "Tabular data cannot be null."
		isTrue table.size() > 0, "Tabular data cannot be empty."
		this.borderKnot = p.getCharProperty("border_know")
		this.horizontalBorder = p.getCharProperty("horizontal_border")
		this.verticalBorder = p.getCharProperty("vertical_border")
		this.nullString = p.getProperty("null_string")
		this.table = table
	}

	/**
	 * Returns the 2D array printed as a table.
	 */
	@Override
	String toString() {
		def buffer = new StringWriter()
		def writer = new PrintWriter(buffer)
		print writer
		buffer.buffer
	}

	/**
	 * Print the 2D array in the specified writer.
	 *
	 * @param writer
	 * 					the {@link PrintWriter} for the output.
	 */
	void print(PrintWriter writer) {
		int[] widths = new int[maxColumns]
		adjustColumnWidths(widths)
		String border = getHorizontalBorder(widths)
		printPreparedTable(writer, widths, border)
	}

	private int getMaxColumns() {
		int max = 0
		table.each {
			if (it != null && it.size() > max) {
				max = it.size()
			}
		}
		return max
	}

	private void adjustColumnWidths(int[] widths) {
		table.findAll { it != null }.each {
			for ( int c = 0; c < widths.length; c++ ) {
				String cv = getCellValue(safeGet(it, c, nullString))
				int l = cv.length()
				if (widths[c] < l) {
					widths[c] = l
				}
			}
		}
	}

	private String getCellValue(Object value) {
		value == null ? nullString : value.toString()
	}

	private String getHorizontalBorder(int[] widths) {
		StringBuilder builder = new StringBuilder(256)
		builder.append borderKnot
		for ( final int w : widths ) {
			for ( int i = 0; i < w; i++ ) {
				builder.append horizontalBorder
			}
			builder.append borderKnot
		}
		builder.toString()
	}

	private void printPreparedTable(PrintWriter writer, int[] widths, String horizontalBorder) {
		int lineLength = horizontalBorder.length()
		writer.println horizontalBorder
		table.findAll { it != null }.each { row ->
			writer.println getRow(row, widths, lineLength)
			writer.println horizontalBorder
		}
	}

	private String getRow(List row, int[] widths, int lineLength) {
		StringBuilder builder = new StringBuilder(lineLength)
		builder.append verticalBorder
		int maxWidths = widths.length
		for (int i = 0; i < maxWidths; i++) {
			builder.append padRight(getCellValue(safeGet(row, i, null)), widths[i])
			builder.append verticalBorder
		}
		builder.toString()
	}

	private static String padRight(String s, int n) {
		String.format("%1\$-${n}s", s)
	}

	private static String safeGet(def list, int index, String defaultValue) {
		index < list.size() ? list[index] : defaultValue
	}
}