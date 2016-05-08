package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import poller.InvalidMockProxyException;
import poller.ServerPoller;

public class serverpollertest {
	@Test
	public void test() {
		try {
			System.out.println("Running ServerPoller Test:");
			ServerPoller s = ServerPoller.getSingleton();	
			if (s == null) {
				throw new Exception();
			}
			Thread.sleep(2500);		// polls 10 times
		} catch (InvalidMockProxyException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			fail("ServerPoller getSingleton was null");
		}
	}

}