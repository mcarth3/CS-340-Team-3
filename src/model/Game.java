package model;


import java.util.ArrayList;

import model.Port;
import model.Dice;
import model.TradeOffer;
import model.Map;
import model.Player;
import model.bank.ResourceList;
import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import model.TurnTracker;
import model.bank.Bank;

/**
 * Created by Jesse on 5/2/2016.
 */
public class Game extends AbstractModelPartition {

	//need from server
	private TradeOffer tradeO;
	public String title;
	public Integer id;
	public ArrayList<Player> players;
	public int modelversion;
	private Dice dice;

//    private ArrayList<Dice> twoDice;
    private Map theGameMap;
//    private Robber robber;
//    private ArrayList<Hex> board;
    private TurnTracker turnTracker;
    private Map map;
    public Game(int numberOfPlayers)
    {
    	tradeO = new TradeOffer();
    	title = "default game";
    	id = 0;
    	players = new ArrayList<Player>(numberOfPlayers);
    	modelversion = 0;
    	dice= new Dice();
        theGameMap = new Map();
        turnTracker = new TurnTracker();
        map = new Map();
    	

    }

    /**
     * @pre: acquire the username/password of player wanted to add to game
     * @post: Game now has a new player with said username and password inside its arrayList of players
     * @param username
     * @param password
     */
    public void addPlayer(String username, String password)
    {
        Player newPlayer = new Player();
        newPlayer.setName(username);
        newPlayer.setPassword(password);
        players.add(newPlayer);

    }


    public Player findPlayer(String username) throws ObjectNotFoundException {
        for(int i=0; i < players.size(); i++)
        {
            if(username.equals(players.get(i).getName()))
            {
                return players.get(i);
            }
        }
        throw new ObjectNotFoundException("failed to find player of username " + username + "!");

    }

    public Game() {
		// TODO Auto-generated constructor stub
	}


	public Game(Map m, Bank b, ArrayList<Player> ps, TurnTracker tt, TradeOffer tradeOffer) {
		// TODO Auto-generated constructor stub
	}

	/**
     * This will be called after a player completes their turn. The main data will then
     * be changed on the server so it is equal to the player's turn
     * @pre: A player's turn has completed, or the Game has initialized and is ready for the first turn.
     * @post: A player's turn completes (possibly leading to the nextTurn() or endGame()
     */
    public void nextTurn()
    {

    }

    /**
     * This will be called to ensure that the Game grabs all the updated info from the
     * server.
     * @pre initialize has to have been called before this, but once the Poller grabs a new model
     * from the server, it will call this.
     * @post: Game has all new info/model data from the server
     */
    public void syncBoard()
    {

    }

    /**
     * this is called at the very start of the game to initialize the singleton and
     * create all the classes needed to function.
     *
     * @pre: Game just started (or created). This could be called by the constructor since there may be so much
     * to
     * @post: Game has all necessary classes created to function (2-4 players, all necessary dice and hexes are created, etc)
     */
    public void initialize()
    {

    }
    public boolean canBuildRoad(int pid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(pid).canBuildRoad();
    }
    public boolean canBuildSettlement(int pid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(pid).canBuildSettlement();
    }
    public boolean canPlaceSettlement(VertexLocation vl) {
        return map.canAddSettlement(vl);
    }
    public Map getTheGameMap() {
        return theGameMap;
    }

    public void setTheGameMap(Map theGameMap) {
        this.theGameMap = theGameMap;
    }
    public boolean canPlaceRoad(EdgeLocation edge) {
        return theGameMap.canAddRoad(edge);
    }
    public boolean canDiscardCards(int pid, ResourceList rl) {
        return players.get(pid).canDiscardCards(rl);
    }
    /**
     * Checks to see if Montoply is a legal move for the player
     *
     * @return boolean whether or not the player can monopoly
     */
    public boolean canMonopoly(int pid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(pid).canMonopoly();
    }
    public boolean canFinishTurn(int pid) {
        return (turnTracker.getStatus().equals("SecondRound") && turnTracker.getCurrentPlayer() == pid);
    }
    /**
     * rolls the dice for a number 1-12
     *
     * @return boolean whether or not the player rolled the dice
     */
    public int roll(int pid) {
        return dice.rollDice();
    }
    /**
     * Checks to see if the player can roll the dice
     *
     * @return boolean whether or not the player can roll the dice
     */
    public boolean canRoll(int pid) {
        if (turnTracker.getStatus().equals("Rolling") && turnTracker.getCurrentPlayer() == pid)
            return true;
        return false;
    }
    public boolean canTradeBank(int pid, ResourceList rl) {

        return true;
    }
    /**
     * Checks to see if trading resources with the bank is a legal move for the player
     * based on the resources the player currently has.
     *
     * @param pid The id of the player making the trade
     * @return boolean whether or not the player can trade with the bank
     */
    public boolean canMaritimeTrade(int pid) throws IllegalMoveException {
        if (!turnTracker.getStatus().equals("Robbing") && pid != turnTracker.getCurrentPlayer())
            throw new IllegalMoveException("not the trading phase, or not the player's turn");

        ArrayList<Port> ports = (ArrayList) map.getPlayerPorts(pid);
        return players.get(pid).canMaritimeTrade(ports);
    }
    /**

     * @pre: Game just ended because a player earned 10 victory points
     * @post: victory message has been displayed (could return to a Menu?)
     */
    public void endGame()
    {

    }


    /*********************************************
     * GETTERS AND SETTERS:
     **/





    /**
     * Checks to see if placing a city is a legal move for the player
     *
     * @return boolean whether or not the player can place a city
     */
    public boolean canPlaceCity(VertexLocation vl) {
        return map.canAddCity(vl);
    }
    /**
     * Checks to see if building a city is a legal move for the player
     *
     * @return boolean whether or not the player can build a city
     */
    public boolean canBuildCity(int pid) {
        if (turnTracker.getCurrentPlayer() != pid && turnTracker.getStatus().equals("Playing"))
            return false;
        return players.get(pid).canBuildCity();
    }
    /**
     * Checks to see accepting a trade request is a legal move for the player
     *
     * @return boolean whether or not the player can accept a trade offer from another player
     */
    public boolean canAcceptTrade(int pid) {
        return players.get(pid).canAcceptTrade(tradeO.getSentList());
    }
    /**
     * Set up the TradeOffer
     */
    public boolean canTradePlayer(int pid, int rid, ResourceList rl) throws IllegalMoveException, InsufficientResourcesException {
        if (turnTracker.getStatus().equals("FirstRoundound") && pid != turnTracker.getCurrentPlayer())
            throw new IllegalMoveException("not the trading phase, or not the player's turn");

        if (pid == rid)
            throw new IllegalMoveException("No trading yourself!");

        tradeO = new TradeOffer(pid, rid, rl);
        return players.get(pid).canOfferTrade();


    }
    public boolean canBuyDevcard(int pid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(pid).canBuyDevcard();
    }
    /**
     * Checks to see if moving the robber is a legal move for the player
     *
     * @return boolean whether or not the player can move the robber
     */
    public boolean canMoveRobber(HexLocation hl) {
        return map.canRelocateRobber(hl);
    }
    /**
     * Robs a player of one resource card
     *
     * @return boolean whether or not the player chose to rob
     */
    public void rob(int pid, ResourceType rt) throws IllegalMoveException, InsufficientResourcesException {
        int cp = turnTracker.getCurrentPlayer();
        if (cp == pid)
            throw new IllegalMoveException("Can't rob yourself");
        players.get(pid).depleteResource(rt);
        players.get(cp).addResource(rt, 1);
    }

    public boolean canRob(int pid, int vid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(vid).canRob();
    }
    /**
     * Checks to see if placing a Soldier card is a legal move for the player
     *
     * @return boolean whether or not the player can place the Soldier card
     */
    public boolean canPlaySoldier(int pid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(pid).canPlaceSoldier();
    }
    /**
     * Checks to see if placing a Year Of Plenty card is a legal move for the player
     *
     * @return boolean whether or not the player can play the Year of Plenty card
     */
    public boolean canYearOfPlenty(int pid) {
        if (turnTracker.getCurrentPlayer() != pid)
            return false;
        return players.get(pid).canYearOfPlenty();
    }
    /**
     * Checks to see if building a road in a specific place is a legal move for the player
     *
     * @return boolean whether or not the player can road building
     */


	public boolean canRoadBuilding(int pid) {
		  if (turnTracker.getCurrentPlayer() != pid)
	            return false;
	        return players.get(pid).canPlayRoadBuilding();
	}
	  /**
     * Checks to see if placing a Monument Card(?) is a legal move for the player
     *
     * @return boolean whether or not the player can place a monument
     */
    public boolean canUseMonument(int pid) {
        if (pid != turnTracker.getCurrentPlayer())
            return false;
        return players.get(pid).canPlaceMonument();
    }
    public TurnTracker getTt() {
        return turnTracker;
    }

    public void setTt(TurnTracker tt) {
        this.turnTracker = tt;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    

}
