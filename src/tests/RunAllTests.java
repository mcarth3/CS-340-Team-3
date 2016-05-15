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
				//"tests.ServerTests",
				"tests.ModelJSONInitializationTest",//gson is throwing malformedjson exception for some reason, but conversion from model to JSON and back works
				//"tests.ServerPollerTest",
				//"tests.CanDoTest"
		};
		org.junit.runner.JUnitCore.main(testClasses);
	}
}
