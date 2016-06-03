package client.turntracker;

import java.util.ArrayList;

import client.GameManager.GameManager;
import client.base.Controller;
import model.Player;
import model.TurnTracker;
import proxy.RealProxy;
import shared.definitions.CatanColor;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
	GameManager manager;

	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		System.out.println("TT:manager got initiated");
		// manager.createdefaultgame();
		// initFromModel();
	}

	@Override
	public ITurnTrackerView getView() {

		return (ITurnTrackerView) super.getView();
	}

	/**
	 * determines if you can end your turn and sends that request to server
	 * 
	 * @pre none
	 * @post request is sent to server to end the turn
	 */
	@Override
	public void endTurn() {
		RealProxy proxy = RealProxy.getSingleton();
		if (model != null) {
			if (model.canFinishTurn(thisplayer.getPlayerID())) {
				String returnJSON = proxy.finishTurn(thisplayer.getPlayerIndex());
				System.out.print("TT: TURN ENDED===================================================");
			}
		}

	}

	/**
	 * getView().setLocalPlayerColor( your Player's color); call
	 * getView().updatePlayer to update the player's achievements and score.
	 * 
	 * @pre none
	 * @post player color is set along with their achievements and score
	 */
	private void initFromModel() {

		// System.out.println(thisplayer.getColor());
		// if (getView() != null){
		// getView().setLocalPlayerColor(CatanColor.RED);
		// }
		getView().setLocalPlayerColor(GameManager.getSingleton().colorTemp);

		TurnTracker turnTracker = model.getTurnTracker();
		ArrayList<Player> players = model.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			boolean largestArmy = false;
			boolean longestRoad = false;
			boolean highLight = false;
			int playerIndex = -2;
			Player thisplayer = players.get(i);
			if (thisplayer != null) {
				playerIndex = thisplayer.getPlayerIndex();
				// System.out.println(getView().toString());
				if ((thisplayer != null)) {
					getView().initializePlayer(playerIndex, thisplayer.getName(), CatanColor.toColor(thisplayer.getColor()));
				}
				if (currentplayer == playerIndex) {
					highLight = true;
				}
				if (turnTracker.getLargestArmy() == playerIndex) {
					largestArmy = true;
				}
				if (turnTracker.getLongestRoad() == playerIndex) {
					longestRoad = true;
				}
				getView().updatePlayer(playerIndex, thisplayer.getVictoryPoints(), highLight, largestArmy, longestRoad);
			}
		}

	}

	@Override
	public void update() {
		System.out.println("TT:updating turn tracker");
		System.out.println("TT:status " + state);
		System.out.println("TT:your resources " + thisplayer.getResources());
		initFromModel();
		boolean enableButton = false;
		String message = "Waiting for Other Players";
		if (currentplayer == thisplayer.getPlayerIndex()) {
			System.out.println("TT:it's your turn");
			if (state.equals("Discarding")) {
				message = "Discarding";
			} else if (state.equals("FirstRound")) {
				message = "First Round";
			} else if (state.equals("Robbing")) {
				message = "Robbing";
			} else if (state.equals("Rolling")) {
				message = "Rolling";
			} else if (state.equals("SecondRound")) {
				message = "Second Round";
			} else if (state.equals("Playing")) {
				message = "playing, but Model == null";
				if (model != null) {
					message = "playing, but cant finish turn";
					if (model.canFinishTurn(thisplayer.getPlayerID())) {
						message = "End Turn";
						enableButton = true;
					}
				}
			}
		} else {
			System.out.println("TT:it's player " + currentplayer + "'s turn");
			// RealProxy.getSingleton().finishTurn(currentplayer);
		}
		this.getView().updateGameState(message, enableButton);
		System.out.println("TT:trade offer = " + model.getTradeO());

	}

}
