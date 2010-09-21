package com.mvwsolutions.classwriter;

import java.util.Collection;

class GotoOpCode extends SimpleOpCode {
	GotoOpCode(int v, int l, String m) {
		super(v, l, m, new Cat1Stack(0, 0));
	}

	void fixDestinationAddress(Instruction instruction, int start,
			int oldPostEnd, int newPostEnd) throws CodeCheckException {
		instruction.fixDestinationAddress(start, oldPostEnd, newPostEnd);
	}

	void traverse(Instruction instruction, Collection next,
			CodeAttribute attribute) throws CodeCheckException {
		next.add(new InstructionPointer(instruction.getOffsetDestination()));
	}
}