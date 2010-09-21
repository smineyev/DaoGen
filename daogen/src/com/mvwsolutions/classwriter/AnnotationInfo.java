package com.mvwsolutions.classwriter;

import java.util.Collection;

public interface AnnotationInfo {
	/**
	 * Collect information about the annotations and strings referenced within
	 * this object
	 * 
	 * @param container
	 *            Class that contains this object
	 * @param annotations
	 *            Annotations nested within this object are added to this
	 *            collection
	 * @param strings
	 *            Strings referenced within this object are added to this
	 *            collection
	 */
	public void gatherAnnotationInfo(ClassWriter container,
			Collection<Annotation> annotations, Collection<String> strings);
}
