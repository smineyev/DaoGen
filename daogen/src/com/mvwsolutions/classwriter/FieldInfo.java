package com.mvwsolutions.classwriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FieldInfo {
	int accessFlags;
	int nameIndex;
	int descriptorIndex;
	AttributeList attributes;

	FieldInfo(DataInputStream classStream, ClassWriter contains)
			throws IOException {
		accessFlags = classStream.readUnsignedShort();
		nameIndex = classStream.readUnsignedShort();
		descriptorIndex = classStream.readUnsignedShort();
		attributes = new AttributeList(contains);
		attributes.read(classStream);
	}

	FieldInfo(int flags, String name, String descriptor, ClassWriter contains) {
		nameIndex = contains.getStringIndex(name);
		descriptorIndex = contains.getStringIndex(descriptor);
		accessFlags = flags;
		attributes = new AttributeList(contains);
	}

	public AttributeList getAttributeList() {
		return attributes;
	}

	public String getName() {
		return attributes.getCurrentClass().getString(nameIndex);
	}

	public String getType() {
		return attributes.getCurrentClass().getString(descriptorIndex);
	}

	public int getFlags() {
		return accessFlags;
	}

	public void setType(String newType) {
		descriptorIndex = attributes.getCurrentClass().getStringIndex(newType);
	}

	public boolean isDeprecated() {
		return attributes.getAttributeByType(DeprecatedAttribute.typeString) != null;
	}

	void write(DataOutputStream classStream) throws IOException {
		classStream.writeShort(accessFlags);
		classStream.writeShort(nameIndex);
		classStream.writeShort(descriptorIndex);
		attributes.write(classStream);
	}
}