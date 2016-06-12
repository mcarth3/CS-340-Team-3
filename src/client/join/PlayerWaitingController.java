package client.join;

import client.GameManager.GameManager;
import client.base.Controller;
import model.Facade;
import model.Game;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import poller.modeljsonparser.ModelParser;
import proxy.RealProxy;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
	private int currentplayers = 1;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView) super.getView();
	}

	@Override
	public void start() {
		manager = GameManager.getSingleton();
		if (manager.getthisplayer() != null) {
			thisplayer = manager.getthisplayer();
		}
		if (manager.getModel() != null) {
			model = manager.getModel();
			if (model.getTurnTracker() != null) {
				state = model.getTurnTracker().getStatus();
				currentplayer = model.getTurnTracker().getCurrentPlayer();
			}
		} else {
			Facade.getSingleton().SetGame((Game) ModelParser.parse(RealProxy.getSingleton().gameModel(-1), Game.class));
			model = manager.getModel();
			if (model.getTurnTracker() != null) {
				state = model.getTurnTracker().getStatus();
				currentplayer = model.getTurnTracker().getCurrentPlayer();
			}
		}

		String airesponse = RealProxy.getSingleton().gameListAI();
		String[] aiList = (String[]) ModelParser.parse(airesponse, String[].class);

		if (aiList == null) {
			aiList = new String[1];
			aiList[0] = "LARGEST_ARMY";
		}
		getView().setPlayers(model.getPlayers());
		getView().setAIChoices(aiList);

		int gottenplayers = 0;
		for (int i = 0; i < model.getPlayers().size(); i++) {
			if (model.getPlayers().get(i) != null) {
				gottenplayers++;
			}
		}

		if (gottenplayers >= 4) {
			if (getView().isModalShowing()) {
				getView().closeModal();
				manager.setbegin(true);
			}
		} else {
			getView().setPlayers(model.getPlayers());
			getView().showModal();

		}
		try {
			ServerPoller.getSingleton();
		} catch (InvalidMockProxyException e) {
			e.printStackTrace();
		}
		try {
			ServerPoller.getSingleton().setPlayerWaitingController(this);
		} catch (InvalidMockProxyException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addAI() {
		String aitype = null;

		aitype = getView().getSelectedAI();

		RealProxy.getSingleton().gameAddAI(aitype);

		getView().setPlayers(model.getPlayers());
		getView().closeModal();
		getView().showModal();
	}

	@Override
	public void update() {
		manager = GameManager.getSingleton();
		if (manager.getthisplayer() != null) {
			thisplayer = manager.getthisplayer();
		}
		if (manager.getModel() != null) {
			model = manager.getModel();
			if (model.getTurnTracker() != null) {
				state = model.getTurnTracker().getStatus();
				currentplayer = model.getTurnTracker().getCurrentPlayer();
			}
		} else {
			Facade.getSingleton().SetGame((Game) ModelParser.parse(RealProxy.getSingleton().gameModel(-1), Game.class));
			model = manager.getModel();
			if (model.getTurnTracker() != null) {
				state = model.getTurnTracker().getStatus();
				currentplayer = model.getTurnTracker().getCurrentPlayer();
			}
		}

		System.out.println("WAIT UPDATE!!!!");
		int gottenplayers = 0;
		for (int i = 0; i < model.getPlayers().size(); i++) {
			if (model.getPlayers().get(i) != null) {
				gottenplayers++;
			}
		}

		if (gottenplayers == 4) {
			if (getView().isModalShowing()) {
				getView().closeModal();
			}
			manager.setbegin(true);

		} else if (currentplayers != gottenplayers) {
			System.out.println("WAITING");
			getView().setPlayers(model.getPlayers());
			if (getView().isModalShowing()) {
				getView().closeModal();
				System.out.println("model closed");
			} else {
				System.out.println("model is not open");
			}

			getView().showModal();
			currentplayers = gottenplayers;
		} else {
			System.out.println("currentplayers == gottenplayers || gottenplayer !=4 " + currentplayers + "-" + gottenplayers);
		}

	}

}
