package controllers;

import java.util.Observable;

import client.base.Controller;
import client.turntracker.ITurnTrackerController;
import client.turntracker.ITurnTrackerView;

/**
 * controller for turn tracker
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
	}


	public ITurnTrackerView getView() {
		return null;
	}
	
	public void update(Observable arg0, Object arg1) {
	}

	
	/**
	 * determines if you can end your turn and sends that request to server
	 * @pre none
	 * @post request is sent to server to end the turn
	 */
	public void endTurn() {
	}
	
	/**
	 * getView().setLocalPlayerColor( your Player's color);
	 * call getView().updatePlayer to update the player's achievements and score.
	 * @pre none
	 * @post player color is set along with their achievements and score
	 */
	private void initFromModel() {
	}
}


