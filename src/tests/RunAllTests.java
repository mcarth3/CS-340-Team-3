package tests;

import static org.junit.Assert.*;
import java.util.*; 
import javax.annotation.*; 
import org.junit.*;
import model.*;
import poller.*; 
import proxy.*;
import shared.locations.*;


public class RunAllTests {

	public static void main(String[] args) {
		String[] testClasses = new String[] {
				"tests.ServerTests",
				"tests.ModelJSONInitializationTest", //if it wasn't for a GSON MalformedJsonException, the program could convert classes into json and back
				"tests.ServerPollerTest",
				"tests.CanDoTest"
		};
		org.junit.runner.JUnitCore.main(testClasses);
	}
}
