package com.mvwsolutions.classwriter;

public class InstructionPointer {
	int currentPos;
	boolean wide;

	public InstructionPointer(int p) {
		currentPos = p;
		wide = false;
	}
}