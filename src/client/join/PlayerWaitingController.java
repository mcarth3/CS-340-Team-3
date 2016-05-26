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
		// System.out.println("WAITING");
		Game tempmodel = ModelParser.parse2(RealProxy.getSingleton().gameModel(-1));
		// System.out.println("---------" + tempmodel.getPlayers() +
		// "--------------");
		getView().setPlayers(tempmodel.getPlayers());
		boolean fullobby = true;
		for (int i = 0; i < tempmodel.getPlayers().size(); i++) {
			if (tempmodel.getPlayers().get(i) == null) {
				fullobby = false;
			}
		}
		if (fullobby) {
			if (getView().isModalShowing()) {
				getView().closeModal();
				GameManager.getSingleton().setbegin(true);
				GameManager.getSingleton().update(ModelParser.parse2(RealProxy.getSingleton().gameModel(-1)));
			}
		} else {
			System.out.println("WAITING");
			getView().showModal();
		}
	}

}
