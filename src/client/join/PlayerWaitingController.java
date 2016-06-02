package client.join;

import client.GameManager.GameManager;
import client.base.Controller;
import model.Game;
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
		String airesponse = RealProxy.getSingleton().gameListAI();
		String[] aiList = (String[]) ModelParser.parse(airesponse, String[].class);
		getView().setPlayers(GameManager.getSingleton().getModel().getPlayers());
		getView().setAIChoices(aiList);

		int gottenplayers = 0;
		for (int i = 0; i < GameManager.getSingleton().getModel().getPlayers().size(); i++) {
			if (GameManager.getSingleton().getModel().getPlayers().get(i) != null) {
				gottenplayers++;
			}
		}

		if (gottenplayers >= 4) {
			if (getView().isModalShowing()) {
				getView().closeModal();
			}
		} else {
			getView().setPlayers(GameManager.getSingleton().getModel().getPlayers());
			getView().showModal();
		}
	}

	@Override
	public void addAI() {
		String aitype = null;

		aitype = getView().getSelectedAI();
		RealProxy.getSingleton().gameAddAI(aitype);

		getView().setPlayers(GameManager.getSingleton().getModel().getPlayers());
		getView().closeModal();
		getView().showModal();
	}

	@Override
	public void update() {
		//System.out.println("ADDING NEXT PLAYER");
		Game tempmodel = GameManager.getSingleton().getModel();
		int gottenplayers = 0;
		for (int i = 0; i < tempmodel.getPlayers().size(); i++) {
			if (tempmodel.getPlayers().get(i) != null) {
				gottenplayers++;
			}
		}

		if (gottenplayers == 4) {
			if (getView().isModalShowing()) {
				getView().closeModal();
			}
			GameManager.getSingleton().setbegin(true);
		} else if (currentplayers != gottenplayers) {
			//	System.out.println("WAITING");
			if (getView().isModalShowing()) {
				getView().closeModal();
			}

			getView().setPlayers(tempmodel.getPlayers());
			getView().showModal();
			currentplayers = gottenplayers;
		}

	}

}
