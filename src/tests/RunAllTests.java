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
				"tests.ModelJSONInitializationTest",
				"tests.ServerPollerTest",
				"tests.CanDoTest",
				"ModelJSONInitializationTest"
		};
		org.junit.runner.JUnitCore.main(testClasses);
	}
}
