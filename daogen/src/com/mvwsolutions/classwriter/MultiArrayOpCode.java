package com.mvwsolutions.classwriter;

import java.util.Stack;

import com.mvwsolutions.util.NetByte;

class MultiArrayOpCode extends SimpleOpCode {
	MultiArrayOpCode(int v, int l, String m) {
		super(v, l, m, null);
	}

	Stack stackUpdate(Instruction instruction, Stack old_stack,
			CodeAttribute attribute) throws CodeCheckException {
		int dimensions = NetByte.mU(instruction.operands[2]);
		Stack new_stack = (Stack) old_stack.clone();
		if (new_stack.size() < dimensions)
			throw new CodeCheckException("Not enough array dimensions on stack");
		for (int i = 0; i < dimensions; i++) {
			if (new_stack.pop() != ProcessStack.CAT1)
				throw new CodeCheckException("Bad array dimension type");
		}
		new_stack.push(ProcessStack.CAT1);
		return new_stack;
	}
}