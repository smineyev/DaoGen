package com.mvwsolutions.classwriter;

import java.util.Stack;

public class GetOpCode extends SimpleOpCode {
	GetOpCode(int v, int l, String m) {
		super(v, l, m, null);
	}

	Stack stackUpdate(Instruction instruction, Stack old_stack,
			CodeAttribute attribute) throws CodeCheckException {
		Object stack_type = TypeParse.stackCategory(TypeParse
				.parseFieldType(instruction.getSymbolicReference(
						attribute.getCurrentClass()).getSymbolType()));
		if (stack_type == null)
			throw new CodeCheckException("GetOpCode-- bad operand type");
		Stack new_stack = (Stack) old_stack.clone();

		if (getMnemonic().startsWith("put")) {
			if (new_stack.pop() != stack_type)
				throw new CodeCheckException("put value wrong size");
		}

		if (!getMnemonic().substring(3).equals("static")) {
			if (new_stack.size() < 1) {
				throw new CodeCheckException("getfield: stack not big enough");
			}
			if (new_stack.pop() != ProcessStack.CAT1)
				throw new CodeCheckException("getfield: CAT2 on stack");
		}

		if (getMnemonic().startsWith("get")) {
			new_stack.push(stack_type);
		}
		return new_stack;
	}
}
