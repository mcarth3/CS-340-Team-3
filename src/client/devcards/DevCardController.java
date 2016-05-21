package client.devcards;

import client.GameManager.GameManager;
import model.Facade;
import model.InsufficientResourcesException;
import model.Player;
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
		try {
			theFacade.buyDevCard(thePlayer.getPlayerID());
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
		theFacade.playMonopoly(thePlayer.getPlayerID(), resource.name());

	}

	@Override
	public void playMonumentCard() {
		theFacade.playMonument(thePlayer.getPlayerID());
	}

	@Override
	public void playRoadBuildCard() {
		//theFacade.playRoadBuilding(); //roadbuilding state,
		roadAction.execute();		//TODO: How do I get the 2 locations? and what is a roadAction?
	}

	@Override
	public void playSoldierCard() {
		//theFacade.playSoldier();

		soldierAction.execute();	//TODO: how do i check the vertex id and edge location? and what is a soldierAction?
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

	}



}

