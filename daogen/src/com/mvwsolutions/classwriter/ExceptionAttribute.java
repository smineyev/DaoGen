package com.mvwsolutions.classwriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class ExceptionsAttribute implements Attribute {
	public final static String typeString = "Exceptions";

	private ArrayList exceptions;

	ExceptionsAttribute(DataInputStream classStream) throws IOException {
		int exceptionCount = classStream.readUnsignedShort();
		exceptions = new ArrayList(exceptionCount);
		for (int i = 0; i < exceptionCount; i++) {
			exceptions.add(new Integer(classStream.readUnsignedShort()));
		}
	}

	public ExceptionsAttribute(Collection integerCollection) {
		exceptions = new ArrayList(integerCollection.size());
		exceptions.addAll(integerCollection);
	}

	public String getTypeString() {
		return typeString;
	}

	public void write(DataOutputStream classStream) throws IOException {
		classStream.writeShort(exceptions.size());
		for (Iterator i = exceptions.iterator(); i.hasNext();) {
			classStream.writeShort(((Integer) i.next()).intValue());
		}
	}
}
