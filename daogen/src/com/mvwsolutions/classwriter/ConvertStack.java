package com.mvwsolutions.classwriter;

import java.util.Stack;

class ConvertStack implements ProcessStack {
	private Object from_type;
	private Object to_type;

	ConvertStack(Object from, Object to) {
		from_type = from;
		to_type = to;
	}

	public Stack stackUpdate(Stack old_stack) throws CodeCheckException {
		if (old_stack.size() < 1) {
			throw new CodeCheckException(
					"One entry on the stack required to convert");
		}
		Stack new_stack = (Stack) old_stack.clone();
		if (new_stack.pop() != from_type)
			throw new CodeCheckException("Invalid stack entry type to convert");

		new_stack.push(to_type);
		return new_stack;
	}
}