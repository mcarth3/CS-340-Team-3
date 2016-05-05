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
	 * @param PollingInterval: the polling interval
	 * @param ThisServerProxy: the ServerProxy to be polled
	 * @pre none
	 * @post poller is set up to poll ThisServerProxy on the polling interval
	 * @throws InvalidServerProxyException 
	 */
	//private ServerPoller(ServerProxy newServerProxy) throws InvalidProxyException {
	//}
	
	/**
	 * requests an up-to-date model from the server, also resets the polling interval
	 * sends the model number in the request using the api '/game/model?version=N' (which returns an updated model if there is one, and the current model if there is no version number given)
	 * @pre the Catan server is running and serverproxy is not null 
	 * @post polling interval resets and gives controller a updated model
	 * @throws PollException if polling fails for any reason
	 */
	//CatanModel poll() throws PollException {
	//	return null;
	//}

	/**
	 * makes sure the ServerPoller follows the singleton pattern
	 * @pre none
	 * @post returns single static instance of the ServerPoller
	 * @throws InvalidServerProxyException 
	 */
	public static ServerPoller getSingleton() throws InvalidProxyException {
		return singleton;
	}
	

	
	private class ServerPollerTask extends TimerTask {	
		/**
		 *  Calls the ServerPoller.poll() when timer has expired.
		 */
		@Override
		public void run() {
		}

	}
}
