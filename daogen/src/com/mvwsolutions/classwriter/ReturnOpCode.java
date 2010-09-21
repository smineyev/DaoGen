package com.mvwsolutions.classwriter;

import java.util.Collection;

class ReturnOpCode extends SimpleOpCode {
	ReturnOpCode(int v, int l, String m, ProcessStack stack) {
		super(v, l, m, stack);
	}

	void traverse(Instruction instruction, Collection next,
			CodeAttribute attribute) throws CodeCheckException {
	}
}