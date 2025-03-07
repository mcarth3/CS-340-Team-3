package client.devcards;

import client.GameManager.GameManager;
import client.base.Controller;
import client.base.IAction;
import model.Facade;
import model.InsufficientResourcesException;
import model.bank.DevCardList;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;

	private Facade theFacade;

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
		return (IPlayDevCardView) super.getView();
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
		System.out.println("thread " + Thread.currentThread().getId() + "- Buying a card!");
		try {
			getBuyCardView().closeModal();
			theFacade.buyDevCard(thisplayer.getPlayerIndex());
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}

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
		theFacade.playMonopoly(thisplayer.getPlayerIndex(), resource.name().toLowerCase());
	}

	@Override
	public void playMonumentCard() {
		theFacade.playMonument(thisplayer.getPlayerIndex());
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
		System.out.println("thread " + Thread.currentThread().getId() + "- Resource 1: " + resource1);
		System.out.println("thread " + Thread.currentThread().getId() + "- Resource 2: " + resource2);

		if (resource1 == resource2) {
			if (GameManager.getSingleton().getModel().getBank().getResourceType(resource1) < 2) {
				System.out.println("thread " + Thread.currentThread().getId() + "- play year of plenty card failed - preconditions not met");

			}
		} else {
			int resourceOneForBank = GameManager.getSingleton().getModel().getBank().getResourceType(resource1);
			int resourceTwoForBank = GameManager.getSingleton().getModel().getBank().getResourceType(resource2);
			if (resourceOneForBank < 1
					|| resourceTwoForBank < 1) {
				System.out.println("thread " + Thread.currentThread().getId() + "- play year of plenty card failed - preconditions not met");

			} else {
				theFacade.playYearOfPlenty(thisplayer.getPlayerIndex(), resource1, resource2);
			}
		}

	}

	@Override
	public void update() {
		theFacade = Facade.getSingleton();
		if (theFacade != null && manager != null && thisplayer != null) {
			if (theFacade.canBuyDevcard(thisplayer.getPlayerIndex())) {

			}
			DevCardList dcl = thisplayer.oldDevCards;
			if (dcl.getYearOfPlenty() > 0 && !thisplayer.isPlayedDevCard()) {
				getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
				getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, dcl.getYearOfPlenty());
			} else {
				getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
				getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, 0);
			}
			if (dcl.getMonopoly() > 0 && !thisplayer.isPlayedDevCard()) {
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
				getPlayCardView().setCardAmount(DevCardType.MONOPOLY, dcl.getMonopoly());
			} else {
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
				getPlayCardView().setCardAmount(DevCardType.MONOPOLY, 0);
			}
			if (dcl.getSoldier() > 0 && !thisplayer.isPlayedDevCard()) {
				getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
				getPlayCardView().setCardAmount(DevCardType.SOLDIER, dcl.getSoldier());
			} else {
				getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
				getPlayCardView().setCardAmount(DevCardType.SOLDIER, 0);
			}
			if (dcl.getRoadBuilding() > 0 && !thisplayer.isPlayedDevCard()) {
				getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
				getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, dcl.getRoadBuilding());
			} else {
				getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
				getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, 0);
			}
			if ((dcl.getMonument() > 0 || thisplayer.newDevCards.getMonument() > 0) && !thisplayer.isPlayedDevCard()) {
				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
				getPlayCardView().setCardAmount(DevCardType.MONUMENT, dcl.getMonument() + thisplayer.newDevCards.getMonument());
			} else {
				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
				getPlayCardView().setCardAmount(DevCardType.MONUMENT, 0);
			}

		}
	}

}
