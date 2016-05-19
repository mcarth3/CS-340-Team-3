package client.domestic;

import client.GameManager.GameManager;
import client.data.PlayerInfo;
import model.Facade;
import model.Player;
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



	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {

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
		checkTradeValues();
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		sending.add(resource);
		recieving.remove(resource);
		checkTradeValues();
	}

	@Override
	public void unsetResource(ResourceType resource) {
		sending.remove(resource);
		recieving.remove(resource);
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

}

