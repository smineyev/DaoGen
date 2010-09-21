package com.mvwsolutions.android.daogen;

import java.util.StringTokenizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.mvwsolutions.classwriter.ClassWriter;

public class FileSourceInterface implements SourceInterface {
	
	private File base;
	/**
	 * 
	 * @param sourceBase Directory defining top of source tree, where top-level source files or packages dwell
	 */
	public FileSourceInterface( File sourceBase)
	{
		base=sourceBase;
	}

	/* (non-Javadoc)
	 * @see com.mvwsolutions.android.daogen.SourceInterface#doneWithWriter()
	 */
	public void doneWithWriter( PrintWriter w) throws IOException, SIException {
		w.close();
	}

	/* (non-Javadoc)
	 * @see com.mvwsolutions.android.daogen.SourceInterface#getWriterForClass(java.lang.String, java.lang.String)
	 */
	public PrintWriter getWriterForClass(String packageName, String className)
			throws IOException, SIException {
		File packageDir=base;
		for ( StringTokenizer st=new StringTokenizer(packageName,"."); st.hasMoreTokens();)
		{
			packageDir=new File( packageDir, st.nextToken());
		}
		packageDir.mkdirs();
		return new PrintWriter( new FileWriter( new File( packageDir, className+".java")));
	}
	
	public static void main( String[] argv) throws Exception
	{
		File base=new File(argv[0]);
		ClassWriter cw=new ClassWriter();
		cw.readClass(new java.io.FileInputStream(argv[1]));
		new SourceFileGenerator( new FileSourceInterface(base)).generate(cw);
	}
}
