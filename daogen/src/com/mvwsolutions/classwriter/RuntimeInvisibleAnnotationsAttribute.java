package com.mvwsolutions.classwriter;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisibleAnnotationsAttribute extends
		RuntimeVisibleAnnotationsAttribute {
	public final static String typeString = "RuntimeInvisibleAnnotations";

	/**
	 * @param classStream
	 * @throws IOException
	 */
	public RuntimeInvisibleAnnotationsAttribute(DataInputStream classStream)
			throws IOException {
		super(classStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvwsolutions.classwriter.RuntimeVisibleAnnotationsAttribute#getTypeString
	 * ()
	 */
	@Override
	public String getTypeString() {
		return typeString;
	}
}
