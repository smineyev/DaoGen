package com.mvwsolutions.classwriter;

import java.util.Stack;

class ComboStack implements ProcessStack {
	private ProcessStack first;
	private ProcessStack second;

	ComboStack(ProcessStack ps1, ProcessStack ps2) {
		first = ps1;
		second = ps2;
	}

	public Stack stackUpdate(Stack old_stack) throws CodeCheckException {
		return second.stackUpdate(first.stackUpdate(old_stack));
	}
}