package org.example.driverScript;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class BaseRun {

	public static void main(String[] args) {
		
		TestNG runner = new TestNG();
		
		List<String> suitefile= new ArrayList<String>();
		
		String path = System.getProperty("user.dir")+"/testng.xml";
		suitefile.add(path);
		runner.setTestSuites(suitefile);
		runner.run();
		
	}

}
