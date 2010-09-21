package com.mvwsolutions.classwriter;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class LineNumberTableAttribute implements Attribute {
	public final static String typeString = "LineNumberTable";

	private ArrayList lineNumberEntries;

	LineNumberTableAttribute(DataInputStream classStream) throws IOException {
		int lineNumberEntryCount = classStream.readUnsignedShort();
		lineNumberEntries = new ArrayList(lineNumberEntryCount);
		for (int i = 0; i < lineNumberEntryCount; i++) {
			lineNumberEntries.add(new LineNumberEntry(classStream));
		}
	}

	/**
	 * Update the line number table for inserted/replaced code. Entries pointing
	 * to code that has been replaced are removed.
	 */
	void fixOffsets(int start, int oldPostEnd, int newPostEnd) {
		for (Iterator i = lineNumberEntries.iterator(); i.hasNext();) {
			LineNumberEntry entry = (LineNumberEntry) i.next();
			if (entry.start_pc > start) {
				if (entry.start_pc < oldPostEnd)
					i.remove();
				else
					entry.start_pc += newPostEnd - oldPostEnd;
			}
		}
	}

	public String getTypeString() {
		return typeString;
	}

	public void write(DataOutputStream classStream) throws IOException {
		classStream.writeShort(lineNumberEntries.size());
		for (Iterator i = lineNumberEntries.iterator(); i.hasNext();) {
			LineNumberEntry entry = (LineNumberEntry) i.next();
			classStream.writeShort(entry.start_pc);
			classStream.writeShort(entry.line_number);
		}
	}

	public int getLineNumber(int position) {
		int lineNumber = 0;
		for (Iterator i = lineNumberEntries.iterator(); i.hasNext();) {
			LineNumberEntry lne = (LineNumberEntry) i.next();
			if (lne.start_pc <= position) {
				lineNumber = lne.line_number;
			}
		}
		return lineNumber;
	}

	class LineNumberEntry {
		int start_pc;
		int line_number;

		LineNumberEntry(DataInputStream classStream) throws IOException {
			start_pc = classStream.readUnsignedShort();
			line_number = classStream.readUnsignedShort();
		}
	}
}
