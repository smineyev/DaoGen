package com.mvwsolutions.classwriter;

import java.util.Stack;

interface ProcessStack {
	final static Object CAT1 = new Object();
	final static Object CAT2 = new Object();

	public Stack stackUpdate(Stack old_stack) throws CodeCheckException;
}