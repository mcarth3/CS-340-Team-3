package client.domestic;

import client.GameManager.GameManager;
import client.data.PlayerInfo;
import model.Facade;
import model.Player;
import model.bank.ResourceList;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import states.State;
import states.StateEnum;

import java.util.ArrayList;
import java.util.Set;


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

		getTradeOverlay().showModal();


		ArrayList<Player> thePlayers = theFacade.getGame().getPlayers();
		ArrayList<PlayerInfo> playersWithoutCurrent = new ArrayList<>();
		for(int i = 0; i < thePlayers.size(); i++ )
		{
			if(thePlayers.get(i).getPlayerID() != pid) {
				playersWithoutCurrent.add(thePlayers.get(i).toPlayerInfo());
			}
		}
		PlayerInfo[] arrayNoCurrentPlayer = new PlayerInfo[playersWithoutCurrent.size()];

		playersWithoutCurrent.toArray( arrayNoCurrentPlayer );
		theTraders = arrayNoCurrentPlayer;



		getTradeOverlay().setPlayers(theTraders);

	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		changeValueOfResourceByInt(resource, -1);
		checkTradeValues();

	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		changeValueOfResourceByInt(resource, 1);
		checkTradeValues();
	}

	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		getTradeOverlay().setStateMessage("Select player!");
		desiredTraderID = playerIndex;
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		recieving.add(resource);
		sending.remove(resource);
		setValueOfResourceZero(resource);
		checkTradeValues();
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		sending.add(resource);
		recieving.remove(resource);
		setValueOfResourceZero(resource);
		checkTradeValues();
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

		getAcceptOverlay().closeModal();
	}
	
	public void update(){

		thePlayer = GameManager.getSingleton().getthisplayer();
		if(State.getCurrentState() == StateEnum.PLAY && thePlayer.resourcesOverZero().length > 0)
		{
			getTradeView().enableDomesticTrade(true);
			getTradeOverlay().setStateMessage("Select player!");
			getTradeOverlay().setPlayerSelectionEnabled(true);
		}
		else
		{
			getTradeView().enableDomesticTrade(false);
			getTradeOverlay().setStateMessage("Can't trade now!");
			getTradeOverlay().setPlayerSelectionEnabled(false);
		}


	}


	public void changeValueOfResourceByInt(ResourceType type, int amount)
	{
		if(type == ResourceType.BRICK)
		{
			listOfResources.setBrick(listOfResources.getBrick() + amount);
		}
		if(type == ResourceType.ORE)
		{
			listOfResources.setOre(listOfResources.getOre() + amount);
		}
		if(type == ResourceType.WOOD)
		{
			listOfResources.setWood(listOfResources.getWood() + amount);
		}
		if(type == ResourceType.WHEAT)
		{
			listOfResources.setWheat(listOfResources.getWheat() + amount);
		}
		if(type == ResourceType.SHEEP)
		{
			listOfResources.setSheep(listOfResources.getSheep() + amount);
		}
	}

	public void setValueOfResourceZero(ResourceType type)
	{
		if(type == ResourceType.BRICK)
		{
			listOfResources.setBrick(0);
		}
		if(type == ResourceType.ORE)
		{
			listOfResources.setOre(0);
		}
		if(type == ResourceType.WOOD)
		{
			listOfResources.setWood(0);
		}
		if(type == ResourceType.WHEAT)
		{
			listOfResources.setWheat(0);
		}
		if(type == ResourceType.SHEEP)
		{
			listOfResources.setSheep(0);
		}
	}

	/**
	 * Checks if player has selected another player and resources to both send and recieve.
	 * Sets state message accordingly.
	 */
	public void checkTradeValues()
	{
		if(desiredTraderID > -1) {
			if (recieving.size() > 0) {
				if(sending.size() > 0)
				{
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
			getTradeOverlay().setTradeEnabled(false);
		}
	}


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
			increase = true;
			if(listOfResources.getBrick() > 0)
			{
				decrease = true;
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

