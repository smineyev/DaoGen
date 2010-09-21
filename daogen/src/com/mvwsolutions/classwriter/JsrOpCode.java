package com.mvwsolutions.classwriter;

class JsrOpCode extends SimpleOpCode {
	JsrOpCode(int v, int l, String m) {
		super(v, l, m, new Cat1Stack(0, 0));
	}

	void fixDestinationAddress(Instruction instruction, int start,
			int oldPostEnd, int newPostEnd) throws CodeCheckException {
		instruction.fixDestinationAddress(start, oldPostEnd, newPostEnd);
	}
}