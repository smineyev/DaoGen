package com.mvwsolutions.classwriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

public class RuntimeVisibleAnnotationsAttribute implements Attribute,
		AnnotationInfo {
	public final static String typeString = "RuntimeVisibleAnnotations";

	private int numberOfAnnotations;
	private Annotation[] annotations;

	RuntimeVisibleAnnotationsAttribute(DataInputStream classStream)
			throws IOException {
		numberOfAnnotations = classStream.readUnsignedShort();
		annotations = new Annotation[numberOfAnnotations];
		for (int i = 0; i < numberOfAnnotations; ++i) {
			annotations[i] = new Annotation(classStream);
		}
	}

	public Annotation[] getAnnotations() {
		return annotations.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvwsolutions.classwriter.Attribute#getTypeString()
	 */
	public String getTypeString() {
		return typeString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvwsolutions.classwriter.Attribute#write(java.io.DataOutputStream)
	 */
	public void write(DataOutputStream classStream) throws IOException {
		classStream.writeShort(numberOfAnnotations);
		for (int i = 0; i < numberOfAnnotations; ++i) {
			annotations[i].write(classStream);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvwsolutions.classwriter.AnnotationInfo#gatherAnnotationInfo(com.
	 * mvwsolutions.classwriter.ClassWriter, java.util.Collection,
	 * java.util.Collection)
	 */
	public void gatherAnnotationInfo(ClassWriter container,
			Collection<Annotation> annotations, Collection<String> strings) {
		for (Annotation a : this.annotations) {
			annotations.add(a);
			a.gatherAnnotationInfo(container, annotations, strings);
		}
	}
}
