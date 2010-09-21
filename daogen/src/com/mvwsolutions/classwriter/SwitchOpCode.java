package com.mvwsolutions.classwriter;

import com.mvwsolutions.util.NetByte;

public abstract class SwitchOpCode extends OpCode {
	SwitchOpCode(int v, String m) {
		super(v, m);
	}

	Instruction repadInstruction(Instruction toRepad) throws CodeCheckException {
		int offset = toRepad.operands.length % 4;
		int new_offset = 3 - (toRepad.getInstructionStart() % 4);
		if (offset != new_offset) {
			byte[] new_operands = new byte[toRepad.operands.length + new_offset
					- offset];
			System.arraycopy(toRepad.operands, offset, new_operands,
					new_offset, toRepad.operands.length - offset);
			NetByte.intToQuad(toRepad.operands.length, toRepad.operands,
					new_offset);
			Instruction result = new Instruction(this, toRepad
					.getInstructionStart(), new_operands, toRepad.wideFlag);
			fixDestinationAddress(result, toRepad.getInstructionStart()
					+ toRepad.operands.length, toRepad.getInstructionStart()
					+ toRepad.operands.length, toRepad.getInstructionStart()
					+ toRepad.operands.length + new_offset - offset);
			return result;
		} else
			return null;
	}

	/**
	 * Change one switch destination to match added or removed code
	 */
	void fixSwitchDestination(Instruction instruction, int offset,
			int oldPostEnd, int newPostEnd) {
		int old_dest = NetByte.quadToInt(instruction.operands, offset);
		if (instruction.getInstructionStart() + old_dest >= oldPostEnd) {
			NetByte.intToQuad(old_dest + oldPostEnd - newPostEnd,
					instruction.operands, offset);
		}
	}
}
