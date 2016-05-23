package client.domestic;

import client.GameManager.GameManager;
import client.data.PlayerInfo;
import model.Facade;
import model.Player;
import model.TradeOffer;
import model.bank.ResourceList;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import states.State;
import states.StateEnum;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private int pid;
	private Facade theFacade;
	private Player thePlayer;
	private PlayerInfo[] theTraders;
	private int desiredTraderID;

	private Set<ResourceType> sending;
	private Set<ResourceType> recieving;
	int amountRecieving;
	int amountSending;

	private ResourceList listOfResources;



	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		sending = new TreeSet<>();
		recieving = new TreeSet<>();
		theFacade = GameManager.getSingleton().getModelfacade();
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {

		//**************Reset everything:
		//getTradeOverlay().reset();
		getTradeView().enableDomesticTrade(true);

		checkTradeValues();
		getTradeOverlay().setPlayerSelectionEnabled(true);

		getTradeOverlay().showModal();

		desiredTraderID = -1;

		sending.clear();
		recieving.clear();

		amountSending = 0;
		amountRecieving = 0;

		listOfResources = new ResourceList();
		//****************Reset everything^^^

		ArrayList<Player> thePlayers = theFacade.getGame().getPlayers();
		ArrayList<PlayerInfo> playersWithoutCurrent = new ArrayList<>();
		for(int i = 0; i < thePlayers.size(); i++ )
		{
			if(thePlayers.get(i).getPlayerID() != thePlayer.getPlayerID()) {
				if(!playersWithoutCurrent.contains(thePlayers.get(i).toPlayerInfo())) {
					playersWithoutCurrent.add(thePlayers.get(i).toPlayerInfo());
				}
			}
		}
		PlayerInfo[] arrayNoCurrentPlayer = new PlayerInfo[playersWithoutCurrent.size()];

		playersWithoutCurrent.toArray( arrayNoCurrentPlayer );
		theTraders = arrayNoCurrentPlayer;


		getTradeOverlay().setResourceSelectionEnabled(true);
		getTradeOverlay().setPlayers(theTraders);
		getTradeOverlay().setPlayerSelectionEnabled(true);
		checkResourceChanges();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		changeValueOfResourceByInt(resource, -1);
		checkTradeValues();
		checkResourceChanges();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		changeValueOfResourceByInt(resource, 1);
		checkTradeValues();
		checkResourceChanges();

		//System.out.println("DMT: increase a " + resource);
	}

	@Override
	public void sendTradeOffer() {
		reverseNegativeResources();
		System.out.println("Sending trade now! pid is " + thePlayer.getPlayerIndex() + " and desired rid is " + desiredTraderID);
		System.out.println("The recommended trade: " + listOfResources.toString());
		theFacade.tradePlayer(thePlayer.getPlayerIndex(), listOfResources, desiredTraderID);
		listOfResources = new ResourceList();
		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		//getTradeOverlay().setStateMessage("Select player!");
		desiredTraderID = playerIndex;
		System.out.println("Desired trader is " + desiredTraderID);
		checkTradeValues();
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		recieving.add(resource);
		sending.remove(resource);
		setValueOfResourceZero(resource);
		checkTradeValues();
		checkResourceChanges();
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		sending.add(resource);
		recieving.remove(resource);
		setValueOfResourceZero(resource);
		//System.out.println("DTC: " + listOfResources.toString());
		checkTradeValues();
		checkResourceChanges();
		//System.out.println("DMT: set a " + resource);
	}

	@Override
	public void unsetResource(ResourceType resource) {
		sending.remove(resource);
		recieving.remove(resource);
		setValueOfResourceZero(resource);
		checkTradeValues();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {


		theFacade.acceptTrade(pid, willAccept);
		getAcceptOverlay().closeModal();
	}

	/**
	 * sets all the resources designated to be receieved to a negative value. ONLY TO BE CALLED
	 * when the player has sent a trade request.
	 */
	public void reverseNegativeResources()
	{
		if(recieving.contains(ResourceType.BRICK))
		{
			listOfResources.setBrick(listOfResources.getBrick() * -1);
			System.out.println("Brick is negative!");
		}
		if(recieving.contains(ResourceType.WOOD))
		{
			listOfResources.setWood(listOfResources.getWood() * -1);
			System.out.println("Wood is negative!");
		}
		if(recieving.contains(ResourceType.WHEAT))
		{
			listOfResources.setWheat(listOfResources.getWheat() * -1);
		}
		if(recieving.contains(ResourceType.ORE))
		{
			listOfResources.setOre(listOfResources.getOre() * -1);
		}
		if(recieving.contains(ResourceType.SHEEP))
		{
			listOfResources.setSheep(listOfResources.getSheep() * -1);
		}
	}

	/**
	 * checks to make sure it's the player's turn and if they can trade.
	 */
	public void update(){
		System.out.println("Domestic Trade update()!");

		if(GameManager.getSingleton() != null && State.getInstance() != null) {
			System.out.println("DMU: No nulls!");

			thePlayer = GameManager.getSingleton().getthisplayer();
			//if (State.getCurrentState() == StateEnum.PLAY && thePlayer.resourcesOverZero().length > 0) {
			if (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("Playing")) {
				System.out.println("DMU: It's play time!");
				if(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer() == GameManager.getSingleton().getthisplayer().getPlayerIndex()) {
					System.out.println("DMU: It's the turn of me, who is " + GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer());
					System.out.println(", also known as " + thePlayer.getName());
					//getTradeOverlay().reset();
					getTradeView().enableDomesticTrade(true);
					//getTradeOverlay().setStateMessage("Select player!");
					checkTradeValues();
					getTradeOverlay().setPlayerSelectionEnabled(true);
					if(GameManager.getSingleton().getModel().getTradeO() == null && getWaitOverlay().isModalShowing())
					{
						getWaitOverlay().closeModal();
					}
				}
				else {
					System.out.println("It's not my turn!");
					getTradeView().enableDomesticTrade(false);
					getTradeOverlay().setStateMessage("Can't trade now!");
					getTradeOverlay().setPlayerSelectionEnabled(false);
				}
				if(GameManager.getSingleton().getModel().getTradeO() != null)
				{
					System.out.println("TradeO not null!");
					System.out.println("Reciever: " + GameManager.getSingleton().getModel().getTradeO().getReciever());
					System.out.println("This player index: " + GameManager.getSingleton().getthisplayer().getPlayerIndex());

					if(GameManager.getSingleton().getModel().getTradeO().getReciever() == GameManager.getSingleton().getthisplayer().getPlayerIndex())
					{

						pid = thePlayer.getPlayerIndex();
						formatAcceptOverlay();
						getAcceptOverlay().showModal();
					}

				}



			} else {
				getTradeView().enableDomesticTrade(false);
				getTradeOverlay().setStateMessage("Can't trade now!");
				getTradeOverlay().setPlayerSelectionEnabled(false);
			}

		}
	}


	/**
	 * is called if the current player has recieved a trade offer. Sets the Overlay to show the
	 * resources offered and asked for, and sets the button according to if this player can accept.
	 */
	public void formatAcceptOverlay()
	{
		TradeOffer theTrade = GameManager.getSingleton().getModel().getTradeO();
		System.out.println("Formatting AcceptOverlay!" + theTrade.getOffer());

		getAcceptOverlay().reset();
		//getAcceptOverlay().setPlayerName(Facade.getSingleton().getPlayerID());
		setAcceptResources(theTrade.getOffer());
		System.out.println("The trade offer while formatting AcceptOverlay!: " + theTrade.getOffer().toString());


		getAcceptOverlay().setAcceptEnabled(checkAcceptability(theTrade.getOffer()));

	}


	public boolean checkAcceptability(ResourceList theDemands)
	{
		boolean enoughResources = true;
		Player currentPlayer = GameManager.getSingleton().getthisplayer();
		ResourceList currentResources = currentPlayer.getResources();
		if(theDemands.getBrick() < 0)
		{
			if(currentResources.getBrick() < theDemands.getBrick() * -1)
			{
				enoughResources = false;
			}

		}

		if(theDemands.getWood() < 0)
		{
			if (currentResources.getWood() < theDemands.getWood() * -1) {
				enoughResources = false;
			}
		}

		if(theDemands.getSheep() < 0)
		{
			if(currentResources.getSheep() < theDemands.getSheep() * -1)
			{
				enoughResources = false;
			}
		}

		if(theDemands.getOre() < 0)
		{
			if(currentResources.getOre() < theDemands.getOre() * -1)
			{
				enoughResources = false;
			}
		}

		if(theDemands.getWheat() < 0)
		{
			if(currentResources.getWheat() < theDemands.getWheat() * -1)
			{
				enoughResources = false;
			}
		}

		return enoughResources;
	}

	/**
	 * sets the AcceptOverlay's resources both offered and asked for. Called by formatAcceptOverlay()
	 */
    public void setAcceptResources(ResourceList theDemands)
	{
		//BRICK
		if(theDemands.getBrick() > 0)
		{
			getAcceptOverlay().addGetResource(ResourceType.BRICK, theDemands.getBrick());
		}
		else if(theDemands.getBrick() < 0)
		{
			getAcceptOverlay().addGiveResource(ResourceType.BRICK, theDemands.getBrick());
		}

		//WOOD
		if(theDemands.getWood() > 0)
		{
			getAcceptOverlay().addGetResource(ResourceType.WOOD, theDemands.getWood());
		}
		else if(theDemands.getWood() < 0)
		{
			getAcceptOverlay().addGiveResource(ResourceType.WOOD, theDemands.getWood());
		}

		//SHEEP
		if(theDemands.getSheep() > 0)
		{
			getAcceptOverlay().addGetResource(ResourceType.SHEEP, theDemands.getSheep());
		}
		else if(theDemands.getSheep() < 0)
		{
			getAcceptOverlay().addGiveResource(ResourceType.SHEEP, theDemands.getSheep());
		}

		//ORE
		if(theDemands.getOre() > 0)
		{
			getAcceptOverlay().addGetResource(ResourceType.ORE, theDemands.getOre());
		}
		else if(theDemands.getOre() < 0)
		{
			getAcceptOverlay().addGiveResource(ResourceType.ORE, theDemands.getOre());
		}

		//WHEAT
		if(theDemands.getWheat() > 0)
		{
			getAcceptOverlay().addGetResource(ResourceType.WHEAT, theDemands.getWheat());
		}
		else if(theDemands.getWheat() < 0)
		{
			getAcceptOverlay().addGiveResource(ResourceType.WHEAT, theDemands.getWheat());
		}
	}

	/**
	 * changes the value of a resource in the listOfResources by the specified amount (by adding amount to the int in the listOfResources)
	 * @param type
	 * @param amount
     */
	public void changeValueOfResourceByInt(ResourceType type, int amount)
	{
		if(type == ResourceType.BRICK)
		{
			//System.out.println("DTC: Changing brick by " + amount);

			listOfResources.setBrick(listOfResources.getBrick() + amount);
			//System.out.println("DTC: Brick in listOfResources is now " + listOfResources.getBrick());

			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getBrick()));
		}
		if(type == ResourceType.ORE)
		{
			listOfResources.setOre(listOfResources.getOre() + amount);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getOre()));
		}
		if(type == ResourceType.WOOD)
		{
			listOfResources.setWood(listOfResources.getWood() + amount);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getWood()));
		}
		if(type == ResourceType.WHEAT)
		{
			listOfResources.setWheat(listOfResources.getWheat() + amount);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getWheat()));
		}
		if(type == ResourceType.SHEEP)
		{
			listOfResources.setSheep(listOfResources.getSheep() + amount);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getSheep()));
		}
	}

	/**
	 * sets the value of all resources in listOfResources to zero.
	 * @param type
     */
	public void setValueOfResourceZero(ResourceType type)
	{
		//System.out.println("DTC: Setting resources to zero!");

		if(type == ResourceType.BRICK)
		{
			listOfResources.setBrick(0);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getBrick()));
		}
		if(type == ResourceType.ORE)
		{
			listOfResources.setOre(0);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getOre()));
		}
		if(type == ResourceType.WOOD)
		{
			listOfResources.setWood(0);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getWood()));
		}
		if(type == ResourceType.WHEAT)
		{
			listOfResources.setWheat(0);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getWheat()));
		}
		if(type == ResourceType.SHEEP)
		{
			listOfResources.setSheep(0);
			getTradeOverlay().setResourceAmount(type, "" + (listOfResources.getSheep()));
		}
	}

	/**
	 * Checks if player has selected another player and resources to both send and recieve.
	 * Sets state message accordingly.
	 */
	public void checkTradeValues()
	{
		if(desiredTraderID > -1) {
			if (recieveListTotalOffer() > 0) {
				if(sendListTotalOffer() > 0)
				{
					getTradeOverlay().setStateMessage("TRADE");

					getTradeOverlay().setTradeEnabled(true);
				}
				else
				{
					getTradeOverlay().setStateMessage("Choose what to send!");
					getTradeOverlay().setTradeEnabled(false);
				}
			}
			else
			{
				getTradeOverlay().setStateMessage("Choose what to receive!");
				getTradeOverlay().setTradeEnabled(false);
			}
		}
		else
		{
			getTradeOverlay().setStateMessage("Choose player!");
			//getTradeOverlay().
			getTradeOverlay().setTradeEnabled(false);
		}
	}

	/**
	 * returns the total amount of resources being offered to send
	 */
	public int sendListTotalOffer()
	{
		int total = 0;
		for(ResourceType t: sending)
		{
			total += amountInResourceList(t);
		}
		return total;
	}

	/**
	returns the total amount of resources being asked to recieve
	 */
	public int recieveListTotalOffer()
	{
		int total = 0;
		for(ResourceType t: recieving)
		{
			total += amountInResourceList(t);
		}
		return total;
	}


	/**
	 * returns the amount of a specified type of resource in the resourceList
	 * @param type
     */
	public int amountInResourceList(ResourceType type)
	{
		if(type == ResourceType.BRICK)
		{
			return listOfResources.getBrick();

		}
		if(type == ResourceType.ORE)
		{
			return listOfResources.getOre();

		}
		if(type == ResourceType.WOOD)
		{
			return listOfResources.getWood();

		}
		if(type == ResourceType.WHEAT)
		{
			return listOfResources.getWheat();

		}
		if(type == ResourceType.SHEEP)
		{
			return listOfResources.getSheep();

		}
		System.out.println("ERROR in DomesticTradeController! Invalid type being checked in amountInResourceList()!");
		return -1;
	}

	/**
	 * changes all the increase/decrease buttons on the resources according to how many there are
	 * and if the player can decrease or increase any. (changes buttons so you can increase or decrease or neither)
	 */
	public void checkResourceChanges()
	{
		ResourceList theList = thePlayer.getResources();

		//WHEAT:
		boolean increase = false, decrease = false;
		if(sending.contains(ResourceType.WHEAT)) {
			if (theList.getWheat() > listOfResources.getWheat()) {
				increase = true;
			} else {
				increase = false;
			}
			if (listOfResources.getWheat() > 0) {
				decrease = true;
			} else {
				decrease = false;
			}

		}
		else if(recieving.contains(ResourceType.WHEAT))
		{
			increase = true;
			if(listOfResources.getWheat() > 0)
			{
				decrease = true;
			}
			else
			{
				decrease = false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WHEAT, increase, decrease);
		//WHEAT^^

//BRICK:
		increase = false;
		decrease = false;
		if(sending.contains(ResourceType.BRICK)) {
			if (theList.getBrick() > listOfResources.getBrick()) {
				increase = true;
			} else {
				increase = false;
			}
			if (listOfResources.getBrick() > 0) {
				decrease = true;
			} else {
				decrease = false;
			}

		}
		else if(recieving.contains(ResourceType.BRICK))
		{
			//System.out.println("DTC: Brick is in recieving, and checkResources knows it!");
			increase = true;
			//System.out.println("DTC: listOfResources has " + listOfResources.getBrick() + " brick, and checkResources knows it!");

			if(listOfResources.getBrick() > 0)
			{
				decrease = true;
				//System.out.println("DTC: Brick's greater than zero!");

			}
			else
			{
				decrease = false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.BRICK, increase, decrease);
		//BRICK^^

		//ORE:
		increase = false;
		decrease = false;
		if(sending.contains(ResourceType.ORE)) {
			if (theList.getOre() > listOfResources.getOre()) {
				increase = true;
			} else {
				increase = false;
			}
			if (listOfResources.getOre() > 0) {
				decrease = true;
			} else {
				decrease = false;
			}

		}
		else if(recieving.contains(ResourceType.ORE))
		{
			increase = true;
			if(listOfResources.getOre() > 0)
			{
				decrease = true;
			}
			else
			{
				decrease = false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.ORE, increase, decrease);
		//ORE^^


		//WOOD:
		increase = false;
		decrease = false;
		if(sending.contains(ResourceType.WOOD)) {
			if (theList.getWood() > listOfResources.getWood()) {
				increase = true;
			} else {
				increase = false;
			}
			if (listOfResources.getWood() > 0) {
				decrease = true;
			} else {
				decrease = false;
			}

		}
		else if(recieving.contains(ResourceType.WOOD))
		{
			increase = true;
			if(listOfResources.getWood() > 0)
			{
				decrease = true;
			}
			else
			{
				decrease = false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.WOOD, increase, decrease);
		//WOOD^^

		//SHEEP:
		increase = false;
		decrease = false;
		if(sending.contains(ResourceType.SHEEP)) {
			if (theList.getSheep() > listOfResources.getSheep()) {
				increase = true;
			} else {
				increase = false;
			}
			if (listOfResources.getSheep() > 0) {
				decrease = true;
			} else {
				decrease = false;
			}

		}
		else if(recieving.contains(ResourceType.SHEEP))
		{
			increase = true;
			if(listOfResources.getSheep() > 0)
			{
				decrease = true;
			}
			else
			{
				decrease = false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(ResourceType.SHEEP, increase, decrease);
		//ORE^^

	}

}

