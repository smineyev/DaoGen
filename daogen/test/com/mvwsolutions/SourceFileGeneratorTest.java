package com.mvwsolutions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import com.mvwsolutions.android.daogen.FileSourceInterface;
import com.mvwsolutions.android.daogen.SourceFileGenerator;
import com.mvwsolutions.classwriter.ClassWriter;

public class SourceFileGeneratorTest {

	@Test
	public void test()  throws Exception {
		String laucnherPrjRoot = "../Launcher2-userapp";
		File base = new File(laucnherPrjRoot+"/src/");
		File classfileToParse = new File(laucnherPrjRoot +"/target/com/android/launcher2/CategoryInfo.class");
		if (!classfileToParse.exists()) {
			throw new FileNotFoundException(classfileToParse.getAbsolutePath());
		}
		
		ClassWriter cw = new ClassWriter();
		cw
				.readClass(new FileInputStream(
						classfileToParse));
		new SourceFileGenerator(new FileSourceInterface(base)).generate(cw);
		classfileToParse = new File("../Launcher2-userapp/target/com/android/launcher2/CategoryApp.class");
		if (!classfileToParse.exists()) {
			throw new FileNotFoundException(classfileToParse.getAbsolutePath());
		}
		cw
		.readClass(new FileInputStream(
				classfileToParse));
		new SourceFileGenerator(new FileSourceInterface(base)).generate(cw);
	}
}
