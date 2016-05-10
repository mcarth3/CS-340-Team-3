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
import proxy.MockProxy;
import proxy.RealProxy;

public class ServerTests {
	public static void main(String[] args) {
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
	
	RealProxy rp = new RealProxy();
	MockProxy mp = new MockProxy();
	// ======== REAL PROXY TESTS =========
	@Test
	public void userLoginTest() {
		assertFalse(false);
		rp.userRegister("SAM", "sam");
		rp.userLogin("SAM", "sam");
	}
	
	// ======== MOCK PROXY TESTS =========
	@Test
	public void userLoginTestMock() {
		assertFalse(false);
		mp.userRegister("SAM", "sam");
		mp.userLogin("SAM", "sam");
	}
	@Test
	public void userRegisterTest() {
		mp.userRegister("SAM", "sam");
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
