package poller;

import java.util.Timer;
import java.util.TimerTask;

public class ServerPoller {
	@SuppressWarnings("unused")
//	private static ServerProxy mockServer;
	private static final int pollInterval = 1000;
	private static ServerPoller singleton = null;
	private ServerPollerTask pollingTask;
	private Timer timer;
	
	/**
	 * makes sure the ServerPoller follows the singleton pattern
	 * @pre none
	 * @post single static instance of the ServerProxy
	 * @throws InvalidServerProxyException 
	 * @return a singleton to the ServerPoller, containing a singleton to the ServerProxy
	 */
	public static ServerPoller getSingleton() throws InvalidServerProxyException {
		return singleton;
	}
	
	/**a
	 * Create a new ServerPoller. 
	 * @param newServerProxy: the ServerProxy that is going to be polled
	 * @param pollInterval: the polling interval
	 * @throws InvalidServerProxyException 
	 * @pre none
	 * @post new instance of Server
	 */
	//private ServerPoller(ServerProxy newServerProxy) throws InvalidServerProxyException {
	//}
	
	/**
	 * tells ServerProxy to update the model. also resets the polling interval
	 * the ServerProxy calls the client's [getModel] method
	 * @pre the Catan server is running and the serverproxy is not null 
	 * @post polling interval resets
	 * @throws PollException any problem that occurs while polling
	 * @return  CatanModel, the new Catan Model to replace the client model
	 */
	//CatanModel poll() throws PollException {
	//	return null;
	//}

	
	private class ServerPollerTask extends TimerTask {	
		/**
		 *  Calls the ServerPoller.poll() when timer has expired.
		 */
		@Override
		public void run() {
		}

	}
}
