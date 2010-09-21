package com.mvwsolutions.classwriter;

import java.util.Collection;
import java.util.Stack;

import com.mvwsolutions.util.NetByte;

public class TableSwitch extends SwitchOpCode {
	TableSwitch(int v, String m) {
		super(v, m);
	}

	Stack stackUpdate(Instruction instruction, Stack old_stack,
			CodeAttribute attribute) throws CodeCheckException {
		Stack new_stack = (Stack) old_stack.clone();
		if (new_stack.size() < 1) {
			throw new CodeCheckException("Stack too small in tableswitch");
		}
		if (new_stack.pop() != ProcessStack.CAT1) {
			throw new CodeCheckException(
					"tableswitch: stack operand is wrong size");
		}
		return new_stack;
	}

	void traverse(Instruction instruction, Collection next,
			CodeAttribute attribute) throws CodeCheckException {
		int offset = instruction.operands.length % 4;
		next.add(new InstructionPointer(NetByte.quadToInt(instruction.operands,
				offset)
				+ instruction.instructionStart));
		offset += 4;
		int lowend = NetByte.quadToInt(instruction.operands, offset);
		offset += 4;
		int highend = NetByte.quadToInt(instruction.operands, offset);
		offset += 4;
		for (int i = lowend; i <= highend; i++) {
			next.add(new InstructionPointer(NetByte.quadToInt(
					instruction.operands, offset + 4 * (i - lowend))
					+ instruction.instructionStart));
		}
	}

	void fixDestinationAddress(Instruction instruction, int start,
			int oldPostEnd, int newPostEnd) throws CodeCheckException {
		int offset = instruction.operands.length % 4;
		offset += 4;
		int lowend = NetByte.quadToInt(instruction.operands, offset);
		offset += 4;
		int highend = NetByte.quadToInt(instruction.operands, offset);
		offset += 4;
		for (int i = lowend; i <= highend; i++) {
			fixSwitchDestination(instruction, offset + (i - lowend) * 4,
					oldPostEnd, newPostEnd);
		}
		throw new CodeCheckException("Unsupported address fix-up- tableswitch");
	}

	Instruction read(InstructionPointer cr, byte[] code)
			throws CodeCheckException {
		cr.currentPos++;
		int operandStart = cr.currentPos;
		cr.currentPos += (4 - (cr.currentPos % 4)) % 4;
		cr.currentPos += 4;
		int lowend = (NetByte.mU(code[cr.currentPos++]) << 24)
				| (NetByte.mU(code[cr.currentPos++]) << 16)
				| (NetByte.mU(code[cr.currentPos++]) << 8)
				| NetByte.mU(code[cr.currentPos++]);
		int highend = (NetByte.mU(code[cr.currentPos++]) << 24)
				| (NetByte.mU(code[cr.currentPos++]) << 16)
				| (NetByte.mU(code[cr.currentPos++]) << 8)
				| NetByte.mU(code[cr.currentPos++]);
		cr.currentPos += 4 * (highend - lowend + 1);
		return new Instruction(this, operandStart - 1, getSubArray(code,
				operandStart, cr.currentPos - operandStart), false);
	}
}
