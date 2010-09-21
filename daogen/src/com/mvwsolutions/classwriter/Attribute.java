package com.mvwsolutions.classwriter;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Attribute extends Cloneable {
	public void write(DataOutputStream classStream) throws IOException;

	public String getTypeString();
}