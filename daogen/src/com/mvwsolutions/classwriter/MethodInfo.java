package com.mvwsolutions.classwriter;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodInfo extends FieldInfo {
	MethodInfo(DataInputStream dis, ClassWriter containing) throws IOException {
		super(dis, containing);
	}

	MethodInfo(int flags, String name, String descriptor, ClassWriter contains) {
		super(flags, name, descriptor, contains);
		attributes.addAttribute(new CodeAttribute(contains));
	}

	public CodeAttribute getCodeAttribute() {
		return (CodeAttribute) attributes
				.getAttributeByType(CodeAttribute.typeString);
	}
}