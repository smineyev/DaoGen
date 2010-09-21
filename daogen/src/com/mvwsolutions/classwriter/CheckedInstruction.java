package com.mvwsolutions.classwriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class CheckedInstruction {
	public Instruction instruction;
	public Stack stack;
	public ArrayList previousCheckedInstructions;

	CheckedInstruction(Instruction i, Stack s) {
		instruction = i;
		stack = s;
		previousCheckedInstructions = new ArrayList();
	}

	int getStackDepth() {
		int depth = 0;
		for (Iterator i = stack.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o == ProcessStack.CAT1)
				depth++;
			if (o == ProcessStack.CAT2)
				depth += 2;
		}
		return depth;
	}
}