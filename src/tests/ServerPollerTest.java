package tests;
/**
 * @author Mike Towne
 */
import static org.junit.Assert.*;
import org.junit.Test;

import client.GameManager.GameManager;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import proxy.MockProxy;

public class ServerPollerTest  {
	@Test
	public void test() {
		try {
			System.out.println("Running ServerPoller Test:");
			MockProxy NewMockProxy= MockProxy.getSingleton();
			ServerPoller s = ServerPoller.getSingleton();
			GameManager gm = new GameManager();
			if (s == null) {
				throw new Exception();
			}

			s.setGameManager(gm);
			s.setmodelversion(1);
			Thread.sleep(2500);		// polls 10 times, technically returning the JSON each time
			
		} catch (InvalidMockProxyException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			fail("ServerPoller getSingleton was null");
		}
	}

}