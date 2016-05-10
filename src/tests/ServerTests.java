package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import poller.InvalidMockProxyException;
import poller.ServerPoller;

public class ServerTests {
	public static void main(String[] args) {
		System.out.println("inside the main tester");
		
		String[] testClasses = new String[] {
				"tests.ServerTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test1() {
		assertFalse(false);
	}
	@Test
	public void test2() {
		assertEquals("OK", "OK");	
	}
	@Test
	public void test3() {
		assertTrue(true);
	}
	@Test
	public void test4() {
		System.out.println("THE TESTS ARE RUNNING AND WORKING!!!");
	}


	
}
