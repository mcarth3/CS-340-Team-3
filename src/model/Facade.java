
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import client.GameManager.GameManager;
import client.data.PlayerInfo;
import model.bank.ResourceList;
import poller.modeljsonparser.AbstractModelPartition;
import proxy.IServer;
import proxy.MockProxy;
import proxy.RealProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by Jesse R.
 */
public class Facade extends AbstractModelPartition {
	Game theGame;
	IServer proxy;
	ArrayList<ResourceList> discardedcards;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	TurnTracker Turn;
	private boolean closeMap = false;
	boolean ready = false;

	// *************************************ADDED BELOW FOR SINGLETON
	private static Facade singleton = null;

	private static Facade theFacade;
	/*
	 * public static Facade getFacade() { if(theFacade == null) { theFacade =
	 * new Facade(); } return theFacade; }
	 */
	// **************************************ADDED ABOVE FOR SINGLETON

	public Facade(RealProxy p) {
		theGame = null;
		proxy = p;
	}

	public Facade(RealProxy p, Game newgame) {
		theGame = newgame;
		proxy = p;
	}

	public Facade(MockProxy p) {
		theGame = null;
		proxy = p;
	}

	public Facade(MockProxy p, Game newgame) {
		theGame = newgame;
		proxy = p;
	}

	/*
	 * public Facade(Game newGame) { theGame = newGame;
	 * 
	 * } public Facade() { theGame = null; }
	 */

	public void SetGame(Game newGame) {
		theGame = newGame;
	}

	public int getversion() {
		return theGame.version;
	}

	/**
	 * function to check if a known player can buy a settlement and put it in a
	 * known location
	 * 
	 * @pre: give username of Player in the Game and VertexLocation of the
	 *       location where you want to check if a settlement can be built there
	 * @post: function checks both person's resources and map's settlements and
	 *        returns whether or not this particular player can build a
	 *        settlement at the given location
	 */
	/*
	 * public boolean canPlayerBuySettlement(String username, VertexLocation
	 * location) throws ObjectNotFoundException { Player newPlayer =
	 * theGame.findPlayer(username);
	 * if(newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 1 &&
	 * newPlayer.returnResourceNumber(ResourceType.WOOD) >= 1 &&
	 * newPlayer.returnResourceNumber(ResourceType.BRICK) >= 1 &&
	 * newPlayer.returnResourceNumber(ResourceType.SHEEP) >= 1 ) {
	 * if(theGame.getmap().canAddSettlement(location)) { return true; } }
	 * 
	 * return false; }
	 */

	/**
	 * function to check if a known player can buy a city and put it in a known
	 * location
	 * 
	 * @pre: give username of Player in the Game and VertexLocation of the
	 *       location where you want to check if a city can be built there
	 * @post: function checks both person's resources and settlements and
	 *        returns whether or not this particular player can build a
	 *        settlement at the given location
	 */
	// public boolean canPlayerBuyCity(String username, VertexLocation location)
	// throws ObjectNotFoundException {
	// Player newPlayer = theGame.findPlayer(username);
	// if(newPlayer.returnResourceNumber(ResourceType.ORE) >= 3 &&
	// newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 2)
	// {
	// if(map.findSettlement(location) == true)
	// {
	// return true;
	// }

	// }

	// return false;

	// }

	/**
	 * function to check if a known player can buy a road and put it in a known
	 * location
	 * 
	 * @pre: give username of Player in the Game and EdgeLocation of the
	 *       location where you want to check if a road can be built
	 * @post: function checks both person's resources and map's edges and
	 *        returns whether or not this particular player can build a road at
	 *        the given edge
	 */
	/*
	 * public boolean canPlayerBuyRoad(String username, EdgeLocation edge)
	 * throws ObjectNotFoundException { Player newPlayer =
	 * theGame.findPlayer(username);
	 * if(newPlayer.returnResourceNumber(ResourceType.WOOD) >= 1 &&
	 * newPlayer.returnResourceNumber(ResourceType.BRICK) >= 1) {
	 * if(theGame.getmap().canAddRoad(edge)) { return true; }
	 * 
	 * }
	 * 
	 * return false; }
	 */

	/**
	 * function to check if a known player can trade a certain amount of a
	 * particular resource
	 * 
	 * @pre: give username of Player in the Game, ResourceType of the type of
	 *       desired Resource from this Player, and the number of these
	 *       resources asked for
	 * @post: function checks person's resources of the given type and if they
	 *        have enough of them to trade for the specified amount, then
	 *        returns whether or not they can
	 */
	/*
	 * public boolean canPlayerAcceptTrade(String username, ResourceType
	 * typeRequired, int resourceNumber) throws ObjectNotFoundException { Player
	 * newPlayer = theGame.findPlayer(username); if
	 * (newPlayer.returnResourceNumber(typeRequired) >= resourceNumber) { return
	 * true; } return false; }
	 */

	/**
	 * function to check if a known player can play a particular type of
	 * DevelopmentCard
	 * 
	 * @pre: give username of Player in the Game type of DevelopmentCard to see
	 *       if the person possesses it
	 * @post: function checks Player's Development Cards and returns the truth
	 *        of whether or not they possess that card
	 */
	// public boolean canPlayerPlayDevCard(String username, DevCardType type)
	// throws ObjectNotFoundException {
	// Player newPlayer = theGame.findPlayer(username);
	// if (newPlayer.returnDevCardValue(type) >= 1) {
	// return true;
	// }
	// return false;
	// }

	public Game gettheGame() {
		return theGame;
	}

	public void Reinitialize(Game g) {
		theGame = g;
	}

	public void gamesCreate(String s) throws FailedCreateGameException {
		proxy.gamesCreate(s, false, false, false);
	}

	public void gameJoin(int playerId, String s) throws InvalidUserException {
		proxy.gameJoin(playerId, s);
	}

	/**
	 * Checks to see if building a road is a legal move for the player
	 *
	 * @return boolean whether or not the player can build a road
	 */
	public boolean canBuildRoad(int playerId, EdgeLocation edge) {
		if (theGame == null)// game isnt null
			return false;
		//first/second round logic
		if (theGame.getTurnTracker().getStatus().equals("FirstRound") || theGame.getTurnTracker().getStatus().equals("SecondRound")) {
			if (edge != null) {
				System.out.println("edge location " + edge.getNormalizedLocation());
				for (int i = 0; i < theGame.getMap().getRoads().size(); i++) {
					if (theGame.getMap().getRoads().get(i).getLocation().getNormalizedLocation().toString().equals(edge.getNormalizedLocation().toString())) { // if space is taken
						return false;
					}
				}
				VertexLocation location1 = edge.getVertices().get(0);
				VertexLocation location2 = edge.getVertices().get(1);
				//if any edges are touching another player's settlement, reject. this is because if you put a road right next to one, you'll be too close to place a settlement
				for (int i = 0; i < theGame.getMap().getsettlements().size(); i++) {
					if (theGame.getMap().getsettlements().get(i).getOwner() != theGame.getTurnTracker().getCurrentPlayer()) {
						if (theGame.getMap().getsettlements().get(i).getVertextLocation().getNormalizedLocation().toString().equals(location1.getNormalizedLocation().toString())) {
							return false;
						}
						if (theGame.getMap().getsettlements().get(i).getVertextLocation().getNormalizedLocation().toString().equals(location2.getNormalizedLocation().toString())) {
							return false;
						}
					}
				}

				return waterboundarytest(edge);//if the road in map bounds (for default map size)
			}

		} else {//playing state logic
			// System.out.println("Playing");
			if (edge != null) {
				// System.out.println("edge!= null");
				// System.out.println("edge location " +edge.getNormalizedLocation());
				for (int i = 0; i < theGame.getMap().getRoads().size(); i++) {
					// System.out.println("map location " +theGame.getMap().getRoads().get(i).getLocation().getNormalizedLocation());
					if (theGame.getMap().getRoads().get(i).getLocation().getNormalizedLocation().toString().equals(edge.getNormalizedLocation().toString())) { // if space is taken
						//System.out.println("map road location = " +theGame.getMap().getRoads().get(i).getLocation().getNormalizedLocation());
						//System.out.println("overlapping");
						return false;
					}
				}
				if (!edge.hadconnectingroad(theGame, GameManager.getSingleton().getthisplayer().getPlayerIndex())) { // if you dont have a connecting road
					// System.out.println("no connecting road-so false");
					return false;
				}
			}
		}
		if (!waterboundarytest(edge)) {
			return false;
		}
		return theGame.canBuildRoad(playerId);
	}

	private boolean waterboundarytest(EdgeLocation edge) {
		System.out.println("edge location " + edge.getNormalizedLocation());
		if (edge.getNormalizedLocation().getX() < -3 || edge.getNormalizedLocation().getX() > 3) {
			return false;
		}
		if (edge.getNormalizedLocation().getX() == -3) {
			if (edge.getNormalizedLocation().getY() > 3 || edge.getNormalizedLocation().getY() < 1) {
				return false;
			}
			if (edge.getNormalizedLocation().getDir().toString() != "NE") {
				return false;
			}
		}

		if (edge.getNormalizedLocation().getX() == -2) {
			if (edge.getNormalizedLocation().getY() > 3 || edge.getNormalizedLocation().getY() < 0) {
				return false;
			}
			if (edge.getNormalizedLocation().getY() == 3 && ((edge.getNormalizedLocation().getDir().toString() != "N") && (edge.getNormalizedLocation().getDir().toString() != "NE"))) {
				return false;
			}
		}
		if (edge.getNormalizedLocation().getX() == -1) {
			if (edge.getNormalizedLocation().getY() > 3 || edge.getNormalizedLocation().getY() < -1) {
				return false;
			}
			if (edge.getNormalizedLocation().getY() == 3 && ((edge.getNormalizedLocation().getDir().toString() != "N") && (edge.getNormalizedLocation().getDir().toString() != "NE"))) {
				return false;
			}
		}
		if (edge.getNormalizedLocation().getX() == 0) {
			if (edge.getNormalizedLocation().getY() > 3 || edge.getNormalizedLocation().getY() < -2) {
				return false;
			}
			if (edge.getNormalizedLocation().getY() == 3 && ((edge.getNormalizedLocation().getDir().toString() != "N"))) {
				return false;
			}
		}
		if (edge.getNormalizedLocation().getX() == 1) {
			if (edge.getNormalizedLocation().getY() > 2 || edge.getNormalizedLocation().getY() < -2) {
				return false;
			}
			if (edge.getNormalizedLocation().getY() == 2 && ((edge.getNormalizedLocation().getDir().toString() != "N") && (edge.getNormalizedLocation().getDir().toString() != "NW"))) {
				return false;
			}
		}
		if (edge.getNormalizedLocation().getX() == 2) {
			if (edge.getNormalizedLocation().getY() > 1 || edge.getNormalizedLocation().getY() < -2) {
				return false;
			}
			if (edge.getNormalizedLocation().getY() == 1 && ((edge.getNormalizedLocation().getDir().toString() != "N") && (edge.getNormalizedLocation().getDir().toString() != "NW"))) {
				return false;
			}
		}
		if (edge.getNormalizedLocation().getX() == 3) {
			if (edge.getNormalizedLocation().getY() > 0 || edge.getNormalizedLocation().getY() < -2) {
				return false;
			}
			if (edge.getNormalizedLocation().getDir().toString() != "NW") {
				return false;
			}
		}

		return true;
	}

	public boolean isHexHasResource(HexLocation hexLoc) {
		for (Hex hex : theGame.getMap().getHexes()) {
			HexLocation resourceHexLoc = hex.getLocation();
			if (hexLoc.equals(resourceHexLoc))
				return true;
		}
		return false;
	}

	/**
	 * Checks to see if placing a road is a legal move for the player
	 * 
	 * @param b
	 *
	 * @return boolean whether or not the player can place a road
	 */
	public boolean canPlaceRoad(EdgeLocation el, boolean b) {
		if (theGame == null)
			return false;
		return theGame.canPlaceRoad(el);
	}

	/**
	 * Places a Road at a given location on the map
	 * 
	 * @param b
	 *
	 * return boolean whether or not the player built the road (perhaps
	 *         placeholder return values for all of the do methods)
	 */
	public void placeRoad(int pid, EdgeLocation el, boolean free, boolean b) {
		if (theGame != null) {
			if (theGame.canBuildRoad(pid) && theGame.canPlaceRoad(el))
				proxy.buildRoad(pid, el, free);
		}
	}

	/**
	 * Checks to see if building a settlement is a legal move for the player
	 *
	 * @return boolean whether or not the player can build a settlement
	 */
	public boolean canBuildSettlement(int playerIndex, VertexLocation location) {
		if (gettheGame() == null) {
			return false;
		}
		if (gettheGame().getTurnTracker().getCurrentPlayer() != playerIndex) {
			return false;
		}
		for (int i = 0; i < theGame.getMap().getsettlements().size(); i++) {
			if (theGame.getMap().getsettlements().get(i).getVertextLocation().getNormalizedLocation().toString().equals(location.getNormalizedLocation().toString())) { // taken
				return false;
			}
		}
		for (int i = 0; i < theGame.getMap().getcities().size(); i++) {
			if (theGame.getMap().getcities().get(i).getVertextLocation().getNormalizedLocation().toString().equals(location.getNormalizedLocation().toString())) {
				return false;
			}
		}
		if (!location.hadconnectingroad(gettheGame(), playerIndex)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks to see if placing a settlement is a legal move for the player
	 *
	 * @return boolean whether or not the player can place a settlement
	 */
	public boolean canPlaceSettlement(VertexLocation vl) {
		if (theGame == null)
			return false;
		return theGame.canPlaceSettlement(vl);
	}

	/**
	 * Places a Settlement at a given location on the map
	 *
	 * return boolean whether or not the player placed a settlement
	 */
	public void placeSettlement(int pid, VertexLocation vl, boolean free) throws IllegalBuildException {
		if (theGame != null) {
			if (canPlaceSettlement(vl) && canBuildSettlement(pid, vl))
				proxy.buildSettlement(pid, vl, free);
		}
	}

	/**
	 * Checks to see if building a city is a legal move for the player
	 *
	 * @return boolean whether or not the player can build a city
	 */
	public boolean canBuildCity(int playerIndex, VertexLocation location) {
		if (gettheGame() == null)
			return false;
		if (gettheGame().getTurnTracker().getCurrentPlayer() != playerIndex)
			return false;
		boolean matcheslocation = false;
		for (int i = 0; i < theGame.getMap().getcities().size(); i++) {
			if (theGame.getMap().getcities().get(i).getVertextLocation().getNormalizedLocation().toString().equals(location.getNormalizedLocation().toString())) {
				return false;
			}
		}
		for (int i = 0; i < theGame.getMap().getsettlements().size(); i++) {
			if (theGame.getMap().getsettlements().get(i).getVertextLocation().getNormalizedLocation().toString().equals(location.getNormalizedLocation().toString())) {
				matcheslocation = true;
			}
		}
		if (matcheslocation == false) {
			return false;
		}
		if (!location.hadconnectingroad(gettheGame(), playerIndex)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks to see if placing a city is a legal move for the player
	 *
	 * @return boolean whether or not the player can place a city
	 */
	public boolean canPlaceCity(VertexLocation vl) {
		if (theGame == null)
			return false;
		return theGame.canPlaceCity(vl);
	}

	/**
	 * Places a City at a given location on the map
	 *
	 * return boolean whether or not the player placed the city
	 */
	public void placeCity(int pid, VertexLocation vl) throws IllegalBuildException {
		if (theGame != null) {
			if (theGame.canBuildCity(pid) && theGame.canPlaceCity(vl))
				proxy.buildCity(pid, vl);
		}
	}

	/**
	 * Checks to see if building a road in a specific place is a legal move for
	 * the player
	 *
	 * @return boolean whether or not the player can road building
	 */
	public boolean canRoadBuilding(int pid) {
		if (theGame == null)
			return false;
		return theGame.canRoadBuilding(pid);
	}

	/**
	 * plays the road build card
	 *
	 * return boolean
	 */
	public void playRoadBuilding(int pid, EdgeLocation el1, EdgeLocation el2) {
		if (theGame != null) {
			if (theGame.canRoadBuilding(pid) && theGame.canPlaceRoad(el1) && theGame.canPlaceRoad(el2))
				proxy.Road_Building(pid, el1, el2);
		}
	}

	/**
	 * Checks to see if placing a Monument Card(?) is a legal move for the
	 * player
	 *
	 * @return boolean whether or not the player can place a monument
	 */
	public boolean canUseMonument(int pid) {
		if (theGame == null)
			return false;
		return theGame.canUseMonument(pid);
	}

	public void playMonument(int index) {
		if (theGame != null)
			proxy.Monument(index);
	}

	/**
	 * Checks to see if placing a Year Of Plenty card is a legal move for the
	 * player
	 *
	 * @return boolean whether or not the player can play the Year of Plenty
	 *         card
	 */
	public boolean canYearOfPlenty(int pid) {
		if (theGame == null)
			return false;
		return theGame.canYearOfPlenty(pid);
	}

	/**
	 * plays the year of plenty card for a given player
	 *
	 * return boolean whether or not the player played the year of plenty card
	 */
	public void playYearOfPlenty(int index, ResourceType r1, ResourceType r2) {
		if (theGame != null)
			if (theGame.canYearOfPlenty(index))
				proxy.Year_of_Plenty(index, r1, r2);
	}

	/**
	 * Checks to see if placing a Soldier card is a legal move for the player
	 *
	 * @return boolean whether or not the player can place the Soldier card
	 */
	public boolean canPlaySoldier(int pid) {
		if (theGame == null)
			return false;
		return theGame.canPlaySoldier(pid);
	}

	/**
	 * Places a Soldier and grants the effects he brings
	 *
	 * return boolean whether or not the player played the soldier card
	 */
	public void playSoldier(int pid, int vid, HexLocation hl) throws IllegalMoveException {
		if (theGame != null) {
			if (theGame.canPlaySoldier(pid) && theGame.canRob(pid, vid) && theGame.canMoveRobber(hl))
				proxy.Soldier(pid, vid, hl);
		}
	}

	/**
	 * Checks to see if robbing another player is a legal move for the player
	 *
	 * @return boolean whether or not the player can rob another player
	 */
	public boolean canRob(int pid, int vid) {
		if (theGame != null)
			return theGame.canRob(pid, vid);
		return true;
	}

	/**
	 * Checks to see if moving the robber is a legal move for the player
	 *
	 * @return boolean whether or not the player can move the robber
	 */
	public boolean canMoveRobber(HexLocation hl) {
		if (theGame == null)
			return false;
		return theGame.canMoveRobber(hl);
	}

	/**
	 * Robs a player of one resource card
	 *
	 * return boolean whether or not the player chose to rob
	 */
	public void rob(int pid, int vid, HexLocation hl) {
		if (pid == -1)
			System.out.println("No player to be robbed?");
		if (theGame != null) {
			if (theGame.canRob(pid, vid) && theGame.canMoveRobber(hl))
				proxy.robPlayer(pid, vid, hl);
		}
	}

	/**
	 * Checks to see if trading resources with the bank is a legal move for the
	 * player
	 *
	 * @return boolean whether or not the player can trade with the bank
	 */
	public boolean canTradeBank(int pid, ResourceList rl) {
		if (theGame == null)
			return false;
		return theGame.canTradeBank(pid, rl);
	}

	/**
	 * completes a transaction of resources with the bank
	 *
	 * return boolean whether or not the player traded with the bank
	 */
	public void meritimeTrade(int playerId, int ratio, String in, String out) {
		if (proxy != null)
			proxy.maritimeTrade(playerId, ratio, in, out);
	}

	/**
	 * Checks to see if the player can roll the dice
	 *
	 * @return boolean whether or not the player can roll the dice
	 */
	public boolean canRoll(int pid) {
		if (theGame == null)
			return false;
		// return theGame.canRoll(pid); /**Jesse Robinson commented this until
		// it would be fixed
		return true;
	}

	/**
	 * Tells the facade to send the proxy a roll command with a specific number
	 */
	public void rollThisInt(int pid, int roll) {
		if (theGame != null) {
			if (canRoll(pid)) {
				int number = roll;

				if (number != -1) {
					proxy.rollNumber(pid, number);
				} else {
					System.out.println("not a rolling phase");
				}
			}
		}
	}

	/**
	 * rolls the dice for a number 1-12 EDITED BY JESSE R. TO RETURN AN INT
	 * 
	 * @return boolean whether or not the player rolled the dice
	 */
	public int roll(int pid) {
		if (theGame != null) {
			if (canRoll(pid)) {
				int number = theGame.rollGameDice();

				if (number != -1) {
					proxy.rollNumber(pid, number);
					return number;
				} else {
					System.out.println("not a rolling phase");
				}
			} else {
				return 0;
			}
		}
		return 0;
	}

	public boolean canFinishTurn(int pid) {
		if (theGame == null)
			return false;
		return theGame.canFinishTurn(pid);
	}

	public void FinishTurn(int pid) {
		proxy.finishTurn(pid);
	}

	public boolean canDiscardCards(int pid, ResourceList rl) {
		if (theGame == null)
			return false;
		return theGame.canDiscardCards(pid, rl);
	}

	public void DiscardCards(int pid, ResourceList rl) throws InsufficientResourcesException {
		if (theGame != null) {
			if (theGame.canDiscardCards(pid, rl))
				proxy.discardCards(pid, discardedcards);
		}
	}

	/**
	 * Checks to see if buying a Developement Card is a legal move for the
	 * player
	 *
	 * @return boolean whether or not the player can buy a Developement card
	 */
	public boolean canBuyDevcard(int index) {
		if (theGame == null)
			return false;
		return theGame.canBuyDevcard(index);
	}

	/**
	 * Buys a developement card and increases the amount for the purchasing
	 * player
	 *
	 * return boolean whether or not the player bought the dev card
	 */
	public void buyDevCard(int index) throws InsufficientResourcesException {
		if (theGame != null) {
			if (theGame.canBuyDevcard(index)) {
				proxy.buyDevCard(index);
			}
		}
	}

	/**
	 * Checks to see if Monoply is a legal move for the player
	 *
	 * @return boolean whether or not the player can monopoly
	 */
	public boolean canMonopoly(int pid) {
		if (theGame == null)
			return false;
		return theGame.canMonopoly(pid);
	}

	/**
	 * uses Monopoly
	 *
	 * return boolean whether or not the player played a monopoly
	 */
	public void playMonopoly(int index, String r) {
		if (theGame != null) {
			if (theGame.canMonopoly(index))
				proxy.Monopoly(r, index);
		}
	}

	/**
	 * Set up the TradeOffer
	 */
	public boolean canTradePlayer(int pid, int rid, ResourceList rl) {
		try {
			if (theGame != null)
				return theGame.canTradePlayer(pid, rid, rl);
		} catch (InsufficientResourcesException e) {
			System.out.println("Not enough resources " + e.getMessage());
		} catch (IllegalMoveException e) {
			System.out.println("An illegal move" + e.getMessage());
		}
		return true;
	}

	/**
	 * enacts the trade offer of the specified player
	 *
	 * return boolean whether or not the player traded with another player
	 */
	public void tradePlayer(int pid, ResourceList rl, int rid) {
		try {
			if (theGame != null) {
				if (theGame.canTradePlayer(pid, rid, rl))
					proxy.offerTrade(pid, resourceListToArrayList(rl), rid);
			}
		} catch (InsufficientResourcesException e) {
			System.out.println("Not enough resources " + e.getMessage());
		} catch (IllegalMoveException e) {
			System.out.println("An illegal move" + e.getMessage());
		}
	}

	public ArrayList resourceListToArrayList(ResourceList theList) {
		ArrayList<Integer> returnList = new ArrayList<>();
		returnList.add(theList.getBrick());
		returnList.add(theList.getOre());
		returnList.add(theList.getSheep());
		returnList.add(theList.getWheat());
		returnList.add(theList.getWood());
		return returnList;
	}

	/**
	 * Checks to see accepting a trade request is a legal move for the player
	 *
	 * @return boolean whether or not the player can accept a trade offer from
	 *         another player
	 */
	public boolean canAcceptTrade(int pid, int vid, ResourceList offer) {
		if (theGame == null)
			return false;
		return theGame.canAcceptTrade(pid);

	}

	/**
	 * accepts the trade offer of another player
	 *
	 * return boolean whether or not the player accepted a trade offer
	 */
	public void acceptTrade(int pid, boolean acceptance) {
		if (theGame != null) {
			if (theGame.canAcceptTrade(pid))
				proxy.acceptTrade(pid, acceptance);
		}
	}

	public static Facade getSingleton() {
		if (singleton == null) {
			singleton = new Facade(RealProxy.getSingleton());
		}
		return singleton;
	}

	public static Facade getInstance() {
		return theFacade;
	}

	// observer methods
	public void addObserver(Observer x) {
		observers.add(x);
	}

	public int getPlayerID() {
		return proxy.getPlayerId();
	}

	public int getPlayerIndex() {
		return theGame.getCurrentPlayer().getPlayerIndex();
	}

	public Game getGame() {
		return theGame;
	}

	public PlayerInfo getCurrentPlayerInfo() {
		PlayerInfo curPlayer = new PlayerInfo();
		CurrentPlayer myPlayer = getCurrentPlayer();
		curPlayer.setId(myPlayer.getPlayerId());
		curPlayer.setPlayerIndex(myPlayer.getPlayerIndex());
		curPlayer.setName(myPlayer.getUsername());
		curPlayer.setColor(myPlayer.getColor());
		return curPlayer;
	}

	public CurrentPlayer getCurrentPlayer() {
		return theGame.getCurrentPlayer();
	}

	public List<VertexObject> getVObjectsAroundHexlocation(HexLocation location) {
		return theGame.getVObjectsAroundHexlocation(location);

	}

	public boolean isCloseMap() {
		return closeMap;
	}

	public void setCloseMap(boolean closeMap) {
		this.closeMap = closeMap;
	}

	public boolean canPlaceRoadSetup(EdgeLocation el) {
		if (theGame == null)
			return false;
		return theGame.canPlaceRoadSetup(el);
	}

	public void retrievegame() {

	}

	public boolean isReady() {

		return ready;
	}

	public void setCurPlayerIndex() {
		getCurrentPlayer().setPlayerIndex(theGame.getPlayerIndex(getCurrentPlayer().getPlayerId()));
	}

	public ArrayList<HexLocation> getWaterHexes() {

		ArrayList<HexLocation> waterHexes = new ArrayList<HexLocation>();
		waterHexes.add(new HexLocation(0, -3));
		waterHexes.add(new HexLocation(1, -3));
		waterHexes.add(new HexLocation(2, -3));
		waterHexes.add(new HexLocation(3, -3));
		waterHexes.add(new HexLocation(3, -3));
		waterHexes.add(new HexLocation(3, -2));
		waterHexes.add(new HexLocation(3, -1));
		waterHexes.add(new HexLocation(3, 0));
		waterHexes.add(new HexLocation(2, 1));
		waterHexes.add(new HexLocation(1, 2));
		waterHexes.add(new HexLocation(0, 3));
		waterHexes.add(new HexLocation(-1, 3));
		waterHexes.add(new HexLocation(-2, 3));
		waterHexes.add(new HexLocation(-3, 3));
		waterHexes.add(new HexLocation(-3, 2));
		waterHexes.add(new HexLocation(-3, 1));
		waterHexes.add(new HexLocation(-3, 0));
		waterHexes.add(new HexLocation(-2, -1));
		waterHexes.add(new HexLocation(-1, -2));

		return waterHexes;
	}

	public String whichresourceisthishex(HexLocation hexLoc) {
		for (Hex hex : theGame.getMap().getHexes()) {
			HexLocation resourceHexLoc = hex.getLocation();
			if (hexLoc.equals(resourceHexLoc))
				return hex.getResource();
		}
		return "";
	}

}