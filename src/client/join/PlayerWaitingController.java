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
		update();
	}

	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
		GameManager.getSingleton().setbegin(true);
		GameManager.getSingleton().update(ModelParser.parse2(RealProxy.getSingleton().gameModel(-1)));
	}

	@Override
	public void update() {
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
		} else {
			// System.out.println("WAITING");
			getView().showModal();
			currentplayers = gottenplayers;
			getView().setPlayers(tempmodel.getPlayers());
		}

	}

}
