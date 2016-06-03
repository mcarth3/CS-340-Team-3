package client.points;

import client.base.Controller;

/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;

	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView model finished view, which is displayed when the model is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {

		super(view);

		setFinishedView(finishedView);

		//initFromModel();
	}

	public IPointsView getPointsView() {

		return (IPointsView) super.getView();
	}

	public IGameFinishedView getFinishedView() {
		return finishedView;
	}

	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		//<temp>		
		if (thisplayer != null) {
			getPointsView().setPoints(thisplayer.getVictoryPoints());
		} else {
			getPointsView().setPoints(2);
		}
		//</temp>
	}

	@Override
	public void update() {
		if (model.getWinner() != -1) {
			String name = model.getPlayers().get(model.getPlayerIndex(model.getWinner())).getName();
			if (manager.playerIdTemp == model.getWinner()) {
				getFinishedView().setWinner(name, true);
				getFinishedView().showModal();
			} else {
				getFinishedView().setWinner(name, false);
				getFinishedView().showModal();
			}
		}

		getPointsView().setPoints(thisplayer.getVictoryPoints());
	}

}
