package client.turntracker;

import java.util.ArrayList;

import client.GameManager.GameManager;
import client.base.Controller;
import model.Game;
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
		manager = GameManager.getSingleton();
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
		Game model = manager.getModel();
		RealProxy proxy = RealProxy.getSingleton();
		if (model != null) {
			if (model.canFinishTurn(manager.getthisplayer().getPlayerID())) {
				String returnJSON = proxy.finishTurn(manager.getthisplayer().getPlayerIndex());
				System.out.print("TT: YOU FINISHED YOUR TURN");
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

		// System.out.println(manager.getthisplayer().getColor());
		// if (getView() != null){
		// getView().setLocalPlayerColor(CatanColor.RED);
		// }
		GameManager gm = GameManager.getSingleton();

		getView().setLocalPlayerColor(gm.colorTemp);

		TurnTracker turnTracker = manager.getModel().getTurnTracker();
		ArrayList<Player> players = manager.getModel().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			boolean largestArmy = false;
			boolean longestRoad = false;
			boolean highLight = false;
			int playerIndex = -2;
			Player currentplayer = players.get(i);
			if (currentplayer != null) {
				playerIndex = currentplayer.getPlayerIndex();
				// System.out.println(getView().toString());
				if ((currentplayer != null)) {
					getView().initializePlayer(playerIndex, currentplayer.getName(), CatanColor.toColor(currentplayer.getColor()));
				}
				if (turnTracker.getCurrentPlayer() == playerIndex) {
					highLight = true;
				}
				if (turnTracker.getLargestArmy() == playerIndex) {
					largestArmy = true;
				}
				if (turnTracker.getLongestRoad() == playerIndex) {
					longestRoad = true;
				}
				getView().updatePlayer(playerIndex, currentplayer.getVictoryPoints(), highLight, largestArmy, longestRoad);
			}
		}

	}

	@Override
	public void update() {
		System.out.println("TT:updating turn tracker");
		System.out.println("TT:status " + GameManager.getSingleton().getModel().getTurnTracker().getStatus());
		System.out.println("TT:your resources " + GameManager.getSingleton().getthisplayer().getResources());
		initFromModel();
		Game model = manager.getModel();
		boolean enableButton = false;
		String message = "Waiting for Other Players";
		if (model.getTurnTracker().getCurrentPlayer() == manager.getthisplayer().getPlayerIndex()) {
			System.out.println("TT:it's your turn");
			if (model.getTurnTracker().getStatus().equals("Discarding")) {
				message = "Discarding";
			} else if (model.getTurnTracker().getStatus().equals("FirstRound")) {
				message = "First Round";
			} else if (model.getTurnTracker().getStatus().equals("Robbing")) {
				message = "Robbing";
			} else if (model.getTurnTracker().getStatus().equals("Rolling")) {
				message = "Rolling";
			} else if (model.getTurnTracker().getStatus().equals("SecondRound")) {
				message = "Second Round";
			} else if (model.getTurnTracker().getStatus().equals("Playing")) {
				message = "playing, but Model == null";
				if (model != null) {
					message = "playing, but cant finish turn";
					if (model.canFinishTurn(manager.getthisplayer().getPlayerID())) {
						message = "End Turn";
						enableButton = true;
					}
				}
			}
		} else {
			System.out.println("TT:it's player " + model.getTurnTracker().getCurrentPlayer() + "'s turn");
			// RealProxy.getSingleton().finishTurn(model.getTurnTracker().getCurrentPlayer());
		}
		this.getView().updateGameState(message, enableButton);
		System.out.println("TT:trade offer = " + model.getTradeO());

	}

}
