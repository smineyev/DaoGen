package com.mvwsolutions.classwriter;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

class UnknownAttribute implements Attribute {
	private byte[] _bytes;
	private String _type;

	UnknownAttribute(int length, String type, DataInputStream classStream)
			throws IOException {
		_bytes = new byte[length];
		_type = type;
		classStream.readFully(_bytes);
	}

	public void write(DataOutputStream classStream) throws IOException {
		classStream.write(_bytes);
	}

	public String getTypeString() {
		return _type;
	}
}