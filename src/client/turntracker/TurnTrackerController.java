package client.turntracker;


import java.util.ArrayList;

import client.GameManager.GameManager;
import client.base.*;
import model.Game;
import model.Player;
import model.TurnTracker;
import poller.modeljsonparser.ModelParser;
import proxy.RealProxy;
import shared.definitions.CatanColor;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
	GameManager manager;

	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		System.out.println("manager got initiated"); 
		manager = GameManager.getSingleton();
	//	manager.createdefaultgame();
	//	initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		Game model = manager.getModel();
		RealProxy proxy = RealProxy.getSingleton();
		if (model != null){
			if (model.canFinishTurn(manager.getthisplayer().getPlayerID())){
				String returnJSON = proxy.finishTurn(manager.getthisplayer().getPlayerIndex());
				model = ModelParser.parse2(returnJSON);
				manager.updateGame(model);			
			}
		}

	}
	

	private void initFromModel() {

		//System.out.println(manager.getthisplayer().getColor());
		//if (getView() != null){
			//getView().setLocalPlayerColor(CatanColor.RED);
	//	}
		GameManager gm = GameManager.getSingleton();
		
		getView().setLocalPlayerColor(gm.colorTemp);
		
		TurnTracker turnTracker = manager.getModel().getTurnTracker();
		ArrayList<Player> players = manager.getModel().getPlayers();
		for(int i = 0; i < players.size(); i++) {
			boolean largestArmy = false;
			boolean longestRoad = false;
			boolean highLight = false;
			int playerIndex = -2;
			Player currentplayer = players.get(i);
			if(currentplayer != null) {
				playerIndex = currentplayer.getPlayerIndex();
		//		System.out.println(getView().toString());
				if ((currentplayer !=null)){
					getView().initializePlayer(playerIndex, currentplayer.getName(), currentplayer.getColor());
				}
				if(turnTracker.getCurrentPlayer() == playerIndex) {
					highLight = true;
				}
				if(turnTracker.getLargestArmy() == playerIndex) {
					largestArmy = true;
				}
				if(turnTracker.getLongestRoad() == playerIndex) {
					longestRoad = true;
				}
				getView().updatePlayer(playerIndex, currentplayer.getVictoryPoints(), highLight, largestArmy, longestRoad);	
			}
		}

	}
	public void update(){
		System.out.println("updating turn tracker");
		System.out.println("status "+GameManager.getSingleton().getModel().getTurnTracker().getStatus());
		initFromModel();
		Game model = manager.getModel();
		boolean enableButton = false;
		String message = "Waiting for Other Players";
		if(model.getTurnTracker().getCurrentPlayer() == manager.getthisplayer().getPlayerIndex()) {
			switch(model.getTurnTracker().getStatus()) {
			case "Discarding":
				message = "Discarding";
				break;
			case "FirstRound":
				message = "FirstRound";
				break;
			case "Robbing":
				message = "Robbing";
				break;
			case "Rolling":
				message = "Rolling";
				break;
			case "SecondRound":
				message = "Second Round";
				break;
			case "Playing":
				if (model != null){
					if (model.canFinishTurn(manager.getthisplayer().getPlayerID())){
						message = "End Turn";
						enableButton = true;
						break;
					}
				}
			default:
				message = "Waiting for Other Players";
				break;
			}	
		}
		this.getView().updateGameState(message, enableButton);
	}

}

