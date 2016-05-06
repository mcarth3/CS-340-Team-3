package poller;

import java.util.Timer;
import java.util.TimerTask;

public class ServerPoller {
	@SuppressWarnings("unused")
//	private static ServerProxy mockServer;
	private static final int PollingInterval = 1000;
	private ServerPollerTask pollingTask;
	private Timer timer;
	private static ServerPoller singleton = null;
	private int modelversion;
	
	/**
	 * creates a new ServerPoller which uses the given serverproxy
	 * @param ThisServerProxy: the ServerProxy to be polled
	 * @pre none
	 * @post poller is set up to poll ThisServerProxy and the polling interval
	 * @throws InvalidServerProxyException 
	 */
	//private ServerPoller(ServerProxy newServerProxy) throws InvalidProxyException {
	//}
	
	/**
	 * requests an up-to-date model from the server, also resets the polling interval
	 * sends the model number in the request using the api '/game/model?version=N' (which returns an updated model if there is one, and the current model if there is no version number given)
	 * modelparser takes the returned JSON and makes a model from it.
	 * the model number gets updated as well
	 * @pre the Catan server is running and serverproxy is not null 
	 * @post polling interval resets and gives returns an updated model, the model number gets updated to the recieved model
	 * @throws PollException if polling fails for any reason
	 */
	//CatanModel poll() throws PollException {
	//	return null;
	//}

	/**
	 * makes sure the ServerPoller follows the singleton pattern
	 * @pre none
	 * @post returns single static instance of the ServerPoller
	 */
	public static ServerPoller getSingleton() {
		return singleton;
	}
	

	
	private class ServerPollerTask extends TimerTask {	
		/**
		 * Calls the ServerPoller.poll() when timer has expired.
		 * @pre none
		 * @post poll is called again and gives the model (created by parsing the JSON) to the controller, which in turn simply replaces the model
		 */
		@Override
		public void run() {
		}

	}
}
