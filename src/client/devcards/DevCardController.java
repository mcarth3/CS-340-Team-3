package client.devcards;

import java.util.ArrayList;

import client.GameManager.GameManager;
import model.CurrentPlayer;
import model.Facade;
import model.Game;
import model.InsufficientResourcesException;
import model.Player;
import model.bank.DevCardList;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;

	private Facade theFacade;

	private Player thePlayer;

	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		System.out.println("Buying a card!");
		try {
			theFacade.buyDevCard(thePlayer.getPlayerIndex());
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {

		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		theFacade.playMonopoly(thePlayer.getPlayerID(), resource.name().toLowerCase());
	}

	@Override
	public void playMonumentCard() {
		theFacade.playMonument(thePlayer.getPlayerID());
	}

	@Override
	public void playRoadBuildCard() {
		roadAction.execute();		//TODO: Shouldn't have to do anything here. How do I get the 2 locations? and what is a roadAction?
	}

	@Override
	public void playSoldierCard() {
		soldierAction.execute();	//TODO: Shouldn't have to do anything here. how do i check the vertex id and edge location? and what is a soldierAction?
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		theFacade.playYearOfPlenty(thePlayer.getPlayerID(), resource1, resource2);
	}
	
	public void update(){
		theFacade = Facade.getSingleton();
		if(theFacade != null && GameManager.getSingleton() != null)
		{
			thePlayer = GameManager.getSingleton().getthisplayer();
			if(theFacade.canBuyDevcard(thePlayer.getPlayerID()))
			{

			}
		}
		
		DevCardList dcl = thePlayer.oldDevCards;
		System.out.println("DEV CARD LIST");
		System.out.println(dcl.getYearOfPlenty());
		System.out.println(dcl.getMonopoly());
		System.out.println(dcl.getSoldier());
		System.out.println(dcl.getRoadBuilding());
		System.out.println(dcl.getMonument()); 
		if(dcl.getYearOfPlenty() > 0 ){
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
			getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, dcl.getYearOfPlenty());
		}else{
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
			getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, 0);
		}
		if(dcl.getMonopoly() > 0){
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
			getPlayCardView().setCardAmount(DevCardType.MONOPOLY, dcl.getMonopoly());
		}else{
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			getPlayCardView().setCardAmount(DevCardType.MONOPOLY, 0);
		}
		if(dcl.getSoldier() > 0 ){
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
			getPlayCardView().setCardAmount(DevCardType.SOLDIER, dcl.getSoldier());
		}else{
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
			getPlayCardView().setCardAmount(DevCardType.SOLDIER, 0);
		}
		if(dcl.getRoadBuilding() > 0){
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
			getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, dcl.getRoadBuilding());
		}else{
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
			getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, 0);
		}
		if(dcl.getMonument() > 0){
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT,  true);
			getPlayCardView().setCardAmount(DevCardType.MONUMENT,  dcl.getMonument());
		}else{
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT,  false);
			getPlayCardView().setCardAmount(DevCardType.MONUMENT,  0);
		}
		
//		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
//		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
//		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
//		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
//		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
//		
				
	}



}

