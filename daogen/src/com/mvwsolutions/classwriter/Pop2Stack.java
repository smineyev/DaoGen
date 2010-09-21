package com.mvwsolutions.classwriter;

import java.util.EmptyStackException;
import java.util.Stack;

public class Pop2Stack implements ProcessStack {

	public Pop2Stack() {
	}

	public Stack stackUpdate(Stack old_stack) throws CodeCheckException {
		try {
			Stack new_stack = (Stack) old_stack.clone();
			if (new_stack.pop() == ProcessStack.CAT1) {
				if (new_stack.pop() != ProcessStack.CAT1) {
					throw new CodeCheckException("pop2: second object not CAT1");
				}
			}
			return new_stack;
		} catch (EmptyStackException ese) {
			throw new CodeCheckException("pop2: stack not deep enough");
		}
	}
}