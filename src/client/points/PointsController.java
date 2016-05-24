package client.points;

import client.GameManager.GameManager;
import client.base.*;
import model.Game;
import model.Player;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		//initFromModel();
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		//<temp>		
		Player player = GameManager.getSingleton().getthisplayer();
		if(player != null){
			getPointsView().setPoints(player.getVictoryPoints());
		}else{
			getPointsView().setPoints(2);
		}
		//</temp>
	}
	public void update(){
		Player player = GameManager.getSingleton().getthisplayer();
		getPointsView().setPoints(player.getVictoryPoints());		
	}
	
}

