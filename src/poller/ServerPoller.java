package poller;

import java.util.Timer;
import java.util.TimerTask;

import client.GameManager.GameManager;
import client.join.PlayerWaitingController;
import model.Facade;
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
	private int previousplayers = 0;
	PlayerWaitingController thisPlayerWaitingController;

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
		//Random rand = new Random();
		modelversion = 1;
		didaction = false;
		thisPlayerWaitingController = null;
		manager = GameManager.getSingleton();
	}

	private String doOnce() {
		didaction = true;
		return RealProxy.getSingleton().gameModel();
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
		if (!didaction) {
			modeljson = doOnce();
		} else {
			try {
				if (modelversion == -1) {
					modeljson = RealProxy.getSingleton().gameModel();
				} else if (modelversion == 0 && (!GameManager.getSingleton().getbegin())) {
					modeljson = RealProxy.getSingleton().gameModel();
				} else {
					modeljson = RealProxy.getSingleton().gameModel(modelversion);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new PollException("Could not communicate with server");
			}
		}

		// System.out.println("thread " + Thread.currentThread().getId() + "- "+modeljson);

		if (modeljson == null)

		{
			System.out.println("thread " + Thread.currentThread().getId() + "- modeljson is null-returning no changes");
		} else if (modeljson.equals("\"true\""))

		{
			System.out.println("thread " + Thread.currentThread().getId() + "- no changes to model number, thus no new model");
			return GameManager.getSingleton().getModel();
		} else

		{

			System.out.println("thread " + Thread.currentThread().getId() + "- NEW MODEL");
			model = (Game) ModelParser.parse(modeljson, Game.class);
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

	public void setPlayerWaitingController(PlayerWaitingController newPlayerWaitingController) {
		thisPlayerWaitingController = newPlayerWaitingController;
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
				if ((model != null) && (thisPlayerWaitingController != null)) {
					//System.out.print("thisPlayerWaitingController !=null and so with model too");
					if ((model.getversion() != modelversion) && manager.getbegin()) {//if theres a change in the model number
						System.out.print("REGULAR UPDATE");
						manager.update(model);
						modelversion = model.getversion();
					} else if ((!manager.getbegin())) {//IF THE SERVER ADDS A PLAYER BUT DOESNT UPDATE THE MODEL NUMBER FOR SOME REASON
						System.out.print("WAITING UPDATE");
						System.out.println("thread " + Thread.currentThread().getId() + "- compare");
						int gottenplayers = 0;
						for (int i = 0; i < model.getPlayers().size(); i++) {
							if (model.getPlayers().get(i) != null) {
								gottenplayers++;
							}
						}

						if (gottenplayers != previousplayers) {
							System.out.println("UPDATE WAITING CONTROLLER");
							System.out.println("thread " + Thread.currentThread().getId() + "- UPDATE!");
							Facade.getSingleton().SetGame(model);
							modelversion = -1;
							previousplayers = gottenplayers;
							thisPlayerWaitingController.update();
						} else {
							System.out.println("gottenplayers == previousplayers");
						}
					} else {
						System.out.println("modelversion == " + modelversion);
						System.out.println("GameManager.getSingleton().getbegin() == " + GameManager.getSingleton().getbegin());
					}
				} else {
					//System.out.print("OOPS");
				}
			} catch (PollException e) {
				e.printStackTrace();
			}
		}
	}

}
