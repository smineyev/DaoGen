package com.mvwsolutions.classwriter;

import java.util.Stack;

class HomogStack implements ProcessStack {
	private Object entry_type;
	private int pops;
	private int pushes;

	HomogStack(Object type, int pop, int push) {
		entry_type = type;
		pops = pop;
		pushes = push;
	}

	public Stack stackUpdate(Stack old_stack) throws CodeCheckException {
		if (old_stack.size() < pops) {
			throw new CodeCheckException("Not enough entries on the stack");
		}
		Stack new_stack = (Stack) old_stack.clone();
		int i;
		for (i = 0; i < pops; i++) {
			if (new_stack.pop() != entry_type) {
				throw new CodeCheckException("Invalid stack entry type");
			}
		}
		for (i = 0; i < pushes; i++) {
			new_stack.push(entry_type);
		}

		return new_stack;
	}
}