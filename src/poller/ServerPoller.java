package poller;
/**
 * @author Mike Towne
 */
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import client.GameManager.GameManager;
import client.base.Controller;
import model.Game;
import poller.modeljsonparser.ModelParser;
import proxy.MockProxy;


public class ServerPoller {
	@SuppressWarnings("unused")
	private static MockProxy mockServer;
	private static final int PollingInterval = 1000;
	private ServerPollerTask pollingTask;
	private Timer timer;
	private static ServerPoller singleton = null;
	private int modelversion;
	private static GameManager manager;
	
	/**
	 * creates a new ServerPoller which uses the given MockProxy
	 * @param NewMockproxy: the MockProxy to be polled
	 * @pre none
	 * @post poller is set up to poll the MockProxy with the polling interval
	 * @throws InvalidMockProxyException 
	 */
	private ServerPoller(MockProxy NewMockProxy) throws InvalidMockProxyException {
	//	clientFacade = ClientFacade.getSingleton();
		pollingTask = new ServerPollerTask();
		mockServer = NewMockProxy;
		timer = new Timer();
		timer.schedule(pollingTask, 0, PollingInterval);
		modelversion=0;
	}
	
	
	/**
	 * requests an up-to-date model from the server, also resets the polling interval, sends the model number in the request using the api '/game/model?version=N' (which returns an updated model if there is one, and the current model if there is no version number given), ModelParser takes the returned JSON and makes a model from it. the model number gets updated as well
	 * @pre the Catan server is running and serverproxy is not null 
	 * @post polling interval resets and gives returns an updated model, the model number gets updated to the recieved model
	 * @throws PollException - if polling fails for any reason
	 */
	private Game poll() throws PollException {
		if (modelversion != 0){
			
		}
		Game model = null;
		String modeljson="";
		try {
			modeljson = mockServer.gameModel(modelversion);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PollException("Could not communicate with server");
		}
		
		try {
			model = ModelParser.parse(modeljson, Game.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * makes sure the ServerPoller follows the singleton pattern
	 * @pre none
	 * @post returns single static instance of the ServerPoller
	 * @throws InvalidMockProxyException 
	 */
	public static ServerPoller getSingleton() throws InvalidMockProxyException {
		if(singleton == null) {
			singleton = new ServerPoller(MockProxy.getSingleton());
		}
		return singleton;
		
	}
	
	
	
	public void setGameManager(GameManager newmanager){
		manager = newmanager;
	}
	public void setmodelversion(int newmodelversion){
		modelversion = newmodelversion;
	}
	

	
	private class ServerPollerTask extends TimerTask {
		/**
		 * Calls the ServerPoller.poll() when timer has expired.
		 * @pre none
		 * @post poll is called again and gives the model (created by parsing the JSON) to the controller, which in turn simply replaces the model
		 */
		@Override
		public void run() {
			try {
				Game model = poll();
				if(model != null) {
					manager.update(model);	
				}
			} catch (PollException e) {
				e.printStackTrace();
			}
		}
	}

}
