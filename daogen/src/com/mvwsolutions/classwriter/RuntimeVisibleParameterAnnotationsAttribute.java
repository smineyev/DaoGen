package com.mvwsolutions.classwriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

public class RuntimeVisibleParameterAnnotationsAttribute implements Attribute {

	public final static String typeString = "RuntimeVisibleParameterAnnotations";

	private int numParameters;
	private ParameterAnnotation[] parameterAnnotations;

	RuntimeVisibleParameterAnnotationsAttribute(DataInputStream classStream)
			throws IOException {
		numParameters = classStream.readUnsignedShort();
		parameterAnnotations = new ParameterAnnotation[numParameters];
		for (int i = 0; i < numParameters; ++i) {
			parameterAnnotations[i] = new ParameterAnnotation(classStream);
		}
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
		classStream.writeShort(numParameters);
		for (int i = 0; i < numParameters; ++i) {
			parameterAnnotations[i].write(classStream);
		}
	}

	/**
	 * 
	 * @return clone of the attributes array of annotations for the parameters
	 *         of the function
	 */
	public ParameterAnnotation[] getParameterAnnotations() {
		return parameterAnnotations.clone();
	}

	/**
	 * Annotations for a single parameter
	 */
	public static class ParameterAnnotation implements AnnotationInfo {
		private int numAnnotations;
		private Annotation[] annotations;

		ParameterAnnotation(DataInputStream classStream) throws IOException {
			numAnnotations = classStream.readUnsignedShort();
			annotations = new Annotation[numAnnotations];
			for (int i = 0; i < numAnnotations; ++i) {
				annotations[i] = new Annotation(classStream);
			}
		}

		void write(DataOutputStream classStream) throws IOException {
			classStream.writeShort(numAnnotations);
			for (int i = 0; i < numAnnotations; ++i) {
				annotations[i].write(classStream);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mvwsolutions.classwriter.AnnotationInfo#gatherAnnotationInfo(
		 * com.mvwsolutions.classwriter.ClassWriter, java.util.Collection,
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
}
