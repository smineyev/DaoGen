package com.mvwsolutions.classwriter;

import java.util.Collection;

public class Wide extends SimpleOpCode {
	Wide(int v, String m) {
		super(v, 1, m, new Cat1Stack(0, 0));
	}

	void traverse(Instruction instruction, Collection next)
			throws CodeCheckException {
		InstructionPointer ip = new InstructionPointer(
				instruction.instructionStart + instruction.getLength());
		ip.wide = true;
		next.add(ip);
	}
}