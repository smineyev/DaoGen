package com.mvwsolutions.android.daogen.plugin;

import java.io.IOException;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.mvwsolutions.android.daogen.SourceFileGenerator;
import com.mvwsolutions.android.daogen.SourceInterface;

import com.mvwsolutions.classwriter.ClassWriter;
import com.mvwsolutions.classwriter.CodeCheckException;

public class DaoGenBuilder extends IncrementalProjectBuilder {

	class DeltaVisitor implements IResourceDeltaVisitor {
		
		private SourceFileGenerator generator;
		
		DeltaVisitor(SourceFileGenerator gen) {
			generator=gen;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// handle added resource
				checkSQLiteAnnotations(generator, resource);
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				checkSQLiteAnnotations(generator, resource);
				break;
			}
			//return true to continue visiting children.
			return true;
		}
	}

	class ResourceVisitor implements IResourceVisitor {
		private SourceFileGenerator generator;
		
		ResourceVisitor(SourceFileGenerator gen) {
			generator=gen;
		}
		
		public boolean visit(IResource resource) throws CoreException {
			checkSQLiteAnnotations(generator, resource);
			//return true to continue visiting children.
			return true;
		}
	}
	
	SourceFileGenerator getGenerator(IProgressMonitor monitor)
	{
		IJavaProject javaProject=JavaCore.create(getProject());
		if (javaProject!=null) {
			try
			{
				// Get the fragment list associated with the project; that's
				// where we'll look for places to put the generated source code
				return new SourceFileGenerator(new PluginSourceInterface(javaProject.getPackageFragmentRoots(), monitor));
			}
			catch (JavaModelException jme)
			{
				// Project wasn't really a Java project?
			}
		}
		return null;
	}
		
	void checkSQLiteAnnotations(SourceFileGenerator generator, IResource resource)
	{
		if (generator != null) {
			if (resource instanceof IFile) {
				IFile resourceFile=(IFile)resource;
				if (resourceFile.getName().endsWith(".class"))
				{
					ClassWriter cw=new ClassWriter();
					try
					{
						cw.readClass(resourceFile.getContents());
						generator.generate(cw);
					}
					catch (CoreException ce) {
						Activator.getDefault().asyncLog(ce);
					}
					catch ( IOException ioe)
					{
						Activator.getDefault().asyncLog(ioe);
					}
					catch ( SourceInterface.SIException sie)
					{
						Activator.getDefault().asyncLog(sie);						
					}
					catch ( CodeCheckException cce)
					{
						Activator.getDefault().asyncLog(cce);												
					}
				}
			}
		}
	}

	public static final String BUILDER_ID = "com.mvwsolutions.daogen.DaoGenBuilder";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			getProject().accept(new ResourceVisitor(getGenerator(monitor)));
		} catch (CoreException e) {
		}
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new DeltaVisitor(getGenerator(monitor)));
	}
}
