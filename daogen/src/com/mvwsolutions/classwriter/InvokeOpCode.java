package com.mvwsolutions.classwriter;

import java.util.Stack;
import java.util.Iterator;
import java.util.ArrayList;

public class InvokeOpCode extends SimpleOpCode {
	InvokeOpCode(int v, int l, String m) {
		super(v, l, m, null);
	}

	Stack stackUpdate(Instruction instruction, Stack old_stack,
			CodeAttribute attribute) throws CodeCheckException {
		Stack new_stack = (Stack) old_stack.clone();
		ClassWriter.CPTypeRef typeRef = instruction
				.getSymbolicReference(attribute.getCurrentClass());
		ArrayList arg_list = TypeParse.parseMethodType(typeRef.getSymbolType());
		Iterator i = arg_list.iterator();
		Object return_stack = TypeParse.stackCategory((String) i.next());
		while (i.hasNext()) {
			Object popped = new_stack.pop();
			if (popped != TypeParse.stackCategory((String) i.next()))
				throw new CodeCheckException("Bad invocation arguments");
		}
		if (!getMnemonic().equals("invokestatic")) {
			Object popped = new_stack.pop();
			if (popped != ProcessStack.CAT1)
				throw new CodeCheckException("Bad invocation object reference");
		}
		if (return_stack != null)
			new_stack.push(return_stack);
		return new_stack;
	}
}
