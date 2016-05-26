package poller;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import client.GameManager.GameManager;
import model.Game;
import poller.modeljsonparser.ModelParser;
import proxy.RealProxy;

public class ServerPoller {
	@SuppressWarnings("unused")
	private static final int PollingInterval = 1000;
	private ServerPollerTask pollingTask;
	private Timer timer;
	private static ServerPoller singleton = null;
	private int modelversion;
	private static GameManager manager;
	boolean didaction;

	/**
	 * creates a new ServerPoller which uses the given MockProxy
	 * 
	 * @param NewMockproxy:
	 *            the MockProxy to be polled
	 * @pre none
	 * @post poller is set up to poll the MockProxy with the polling interval
	 * @throws InvalidMockProxyException
	 */
	private ServerPoller() throws InvalidMockProxyException {
		pollingTask = new ServerPollerTask();
		timer = new Timer();
		timer.schedule(pollingTask, 0, PollingInterval);
		Random rand = new Random();
		modelversion = 0;
		didaction = false;

		manager = GameManager.getSingleton();
	}

	private void doOnce() {
		if (!didaction) {
			if (RealProxy.getSingleton().gameModel(modelversion) != null) {
				if (RealProxy.getSingleton().gameModel(modelversion).equals("\"true\"")) {
					modelversion = 1;
				}
				didaction = true;
			}
		}
	}

	/**
	 * requests an up-to-date model from the server, also resets the polling
	 * interval, sends the model number in the request using the api
	 * '/game/model?version=N' (which returns an updated model if there is one,
	 * and the current model if there is no version number given), ModelParser
	 * takes the returned JSON and makes a model from it. the model number gets
	 * updated as well
	 * 
	 * @pre the Catan server is running and serverproxy is not null
	 * @post polling interval resets and gives returns an updated model, the
	 *       model number gets updated to the recieved model
	 * @throws PollException
	 *             - if polling fails for any reason
	 */
	public Game poll() throws PollException {

		Game model = null;
		String modeljson = "";
		doOnce();
		try {
			if (modelversion == -1) {
				modeljson = RealProxy.getSingleton().gameModel();
			} else {
				modeljson = RealProxy.getSingleton().gameModel(modelversion);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PollException("Could not communicate with server");
		}

		// System.out.println(modeljson);

		if (modeljson == null) {
			System.out.println("modeljson is null-returning no changes");
		} else if (modeljson.equals("\"true\"")) {
			System.out.println("no changes to model number, thus no new model");
			return GameManager.getSingleton().getModel();
		} else {

			System.out.println("NEW MODEL");
			model = ModelParser.parse2(modeljson);
		}
		return model;
	}

	/**
	 * makes sure the ServerPoller follows the singleton pattern
	 * 
	 * @pre none
	 * @post returns single static instance of the ServerPoller
	 * @throws InvalidMockProxyException
	 */
	public static ServerPoller getSingleton() throws InvalidMockProxyException {
		if (singleton == null) {
			singleton = new ServerPoller();
		}
		return singleton;
	}

	public void setGameManager(GameManager newmanager) {
		manager = newmanager;
	}

	public void setmodelversion(int newmodelversion) {
		modelversion = newmodelversion;
	}

	private class ServerPollerTask extends TimerTask {
		/**
		 * Calls the ServerPoller.poll() when timer has expired.
		 * 
		 * @pre none
		 * @post poll is called again and gives the model (created by parsing
		 *       the JSON) to the controller, which in turn simply replaces the
		 *       model
		 */
		@Override
		public void run() {
			try {
				Game model = poll();
				if (model != null) {
					if (model.getversion() != modelversion) {
						manager.update(model);
						modelversion = model.getversion();
					}
				}
			} catch (PollException e) {
				e.printStackTrace();
			}
		}
	}

}
