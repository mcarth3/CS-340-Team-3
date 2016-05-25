package model;


import java.util.ArrayList;


import model.bank.ResourceList;
import model.clientModel.MessageList;
import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import model.bank.DevCardList;

/**
 * Created by Jesse on 5/2/2016.
 */
public class Game extends AbstractModelPartition {




	//needed
	private CurrentPlayer currentPlayer;
	private DevCardList deck;
    private Map map;
	public ArrayList<Player> players;
	public MessageList log;
	public MessageList chat;
	private ResourceList bank;
    private TurnTracker turnTracker;
	private int winner;
	public int version;
	private TradeOffer tradeOffer;
	
//removed
	private Dice gameDice;
	
	//public String title;
	//public Integer id;
	//private Dice dice;


	//unused
//    private ArrayList<Dice> twoDice;
//    private Robber robber;
//    private ArrayList<Hex> board;


    public Game(int numberOfPlayers)
    {
    	deck= new DevCardList();
    	players = new ArrayList<Player>(numberOfPlayers);
    	log=new MessageList();
		chat=new MessageList();
		bank=new ResourceList();
    	version = 0;
        turnTracker = new TurnTracker();
        map = new Map();
        winner = 0;
        gameDice=new Dice();
    }
    
    public Game(DevCardList newdeck,ArrayList<Player> newplayers,MessageList newlog,MessageList newchat,ResourceList newbank, Map newmap, TurnTracker newturnTracker,int newwinner,int newmodelversion) {
    	deck =newdeck;
    	if (newplayers !=null){
    		players = newplayers;
		}else{
			players = new ArrayList<Player>(0);
		}
		log=newlog;
		chat=newchat;
		bank=newbank;
    	version = newmodelversion;
        map = newmap;
        turnTracker = newturnTracker;
    	winner = newwinner;
        gameDice=new Dice();

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
//      newPlayer.setPassword(password);
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
    public Player findPlayerbyid(int id) throws ObjectNotFoundException {
        for(int i=0; i < players.size(); i++)
        {
            if(id==(players.get(i).getPlayerID()))
            {
                return players.get(i);
            }
        }
        throw new ObjectNotFoundException("failed to find player of id " + id + "!");

    }
    public Player findPlayerbyindex(int index) throws ObjectNotFoundException {
        for(int i=0; i < players.size(); i++)
        {
            if(index==(players.get(i).getPlayerIndex()))
            {
                return players.get(i);
            }
        }
        throw new ObjectNotFoundException("failed to find player of index " + index + "!");

    }

    public Game() {
        gameDice = new Dice();
		// TODO Auto-generated constructor stub
	}


	public Game(Map m, ResourceList b, ArrayList<Player> ps, TurnTracker tt, TradeOffer tradeOffer) {
		// TODO Auto-generated constructor stub
	    gameDice = new Dice();
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
    public Map getmap() {
        return map;
    }

    public void setmap(Map map) {
        this.map = map;
    }
    public boolean canPlaceRoad(EdgeLocation edge) {
        return map.canAddRoad(edge);
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
    	System.out.println("CAN FINISH TURN");
        return (turnTracker.getStatus().equals("Playing") && turnTracker.getCurrentPlayer() == pid);
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

    public int rollGameDice()
    {
        return gameDice.rollDice();
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
        return players.get(pid).canAcceptTrade(tradeOffer.getSentList());
    }
    /**
     * Set up the TradeOffer
     */
    public boolean canTradePlayer(int pid, int rid, ResourceList rl) throws IllegalMoveException, InsufficientResourcesException {
        if (turnTracker.getStatus().equals("FirstRoundound") && pid != turnTracker.getCurrentPlayer())
            throw new IllegalMoveException("not the trading phase, or not the player's turn");

        if (pid == rid)
            throw new IllegalMoveException("No trading yourself!");

        tradeOffer = new TradeOffer(pid, rid, rl);
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
  //  public boolean canMoveRobber(HexLocation hl) {
   //     return map.canRelocateRobber(hl);
    //}
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
    public boolean canMoveRobber(HexLocation hl) {
        return map.canRelocateRobber(hl);
    }

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
    public TurnTracker getTurnTracker() {
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
    
    public int getversion(){
    	return version; 
    }
    
    public Map getMap(){
    	return map;
    }
    public CurrentPlayer getCurrentPlayer()
    {
        return currentPlayer;
    }
    public ArrayList<VertexObject> getVObjectsAroundHexlocation(HexLocation location)
    {

        return map.getVObjectsAroundHexlocation(location);
    }

    public boolean canPlaceRoadSetup(EdgeLocation el)
    {
        return map.canPlaceRoadSetup(el);
    }

    public int getPlayerIndex(int playerId){
        for(int i = 0; i < 4; i++){
            if(players.get(i).getPlayerID() == playerId){
                return players.get(i).getPlayerIndex();
            }
        }
        return 0;
    }

    public TradeOffer getTradeO() {
        return tradeOffer;
    }

    public void setTradeO(TradeOffer tradeO) {
        this.tradeOffer = tradeO;
    }

    public Dice getGameDice() {
        return gameDice;
    }

    public void setGameDice(Dice gameDice) {
        this.gameDice = gameDice;
    }
}
