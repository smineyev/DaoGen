package com.mvwsolutions.classwriter;

import java.util.Collection;
import java.util.Stack;

public class SimpleOpCode extends OpCode {
	private int length;
	private ProcessStack stack_update;

	SimpleOpCode(int v, int l, String m, ProcessStack stacker) {
		super(v, m);
		length = l;
		stack_update = stacker;
	}

	SimpleOpCode(int v, int l, String m) {
		this(v, l, m, new Cat1Stack(1, 1));
	}

	Stack stackUpdate(Instruction instruction, Stack current,
			CodeAttribute attribute) throws CodeCheckException {
		return stack_update.stackUpdate(current);
	}

	boolean isValidOperandLength(int len, boolean wide) {
		return len == length - 1;
	}

	final int getDefaultLength() {
		return length;
	}

	void traverse(Instruction instruction, Collection next,
			CodeAttribute attribute) throws CodeCheckException {
		next.add(new InstructionPointer(instruction.instructionStart
				+ instruction.getLength()));
	}

	Instruction read(InstructionPointer cr, byte[] code)
			throws CodeCheckException {
		if (cr.wide)
			throw new CodeCheckException(getMnemonic() + " can not be wide");

		Instruction result = new Instruction(this, cr.currentPos, getSubArray(
				code, cr.currentPos + 1, length - 1), false);
		cr.currentPos += length;
		return result;
	}
}