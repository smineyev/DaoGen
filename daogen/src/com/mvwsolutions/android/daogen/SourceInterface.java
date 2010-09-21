package com.mvwsolutions.android.daogen;

import java.io.IOException;
import java.io.PrintWriter;

public interface SourceInterface {
	
	/**
	 * 
	 * @param packageName
	 * @param className
	 * @return
	 * @throws IOException
	 * @throws SIException
	 */
	public PrintWriter getWriterForClass( String packageName, String className)
		throws IOException, SIException;
	
	public void doneWithWriter(PrintWriter w) throws IOException, SIException;

	/**
	 * Exception indicating a non-IO problem in the SourceInterface implementation
	 */
	public class SIException extends Exception {

		/**
		 * Default serialization id to suppress warnings
		 */
		private static final long serialVersionUID = -8992118680928506335L;

		/**
		 * @param message
		 */
		public SIException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param cause
		 */
		public SIException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @param cause
		 */
		public SIException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

	}

}
