package client.resources;

import java.util.HashMap;
import java.util.Map;

import client.GameManager.GameManager;
import client.base.Controller;
import client.base.IAction;
import model.Player;

/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;

	public ResourceBarController(IResourceBarView view) {

		super(view);

		elementActions = new HashMap<ResourceBarElement, IAction>();
		getView().setElementEnabled(ResourceBarElement.ROAD, false);
		getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		getView().setElementEnabled(ResourceBarElement.CITY, false);
		getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView) super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is
	 * clicked by the user
	 * 
	 * @param element
	 *            The resource bar element with which the action is associated
	 * @param action
	 *            The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	/**
	 * check if player can build a road based on resources they have and calls
	 * executeElementAction();
	 * 
	 * @pre none
	 * @post places road
	 */
	@Override
	public void buildRoad() {
		if (abletobuildroad()) {
			executeElementAction(ResourceBarElement.ROAD);
		}
	}

	/**
	 * check if player can build a settlement based on resources they have and
	 * calls executeElementAction();
	 * 
	 * @pre none
	 * @post places settlement
	 */
	@Override
	public void buildSettlement() {
		if (abletobuildsettlement()) {
			executeElementAction(ResourceBarElement.SETTLEMENT);
		}
	}

	/**
	 * check if player can build a city based on resources they have and calls
	 * executeElementAction();
	 * 
	 * @pre none
	 * @post places city
	 */
	@Override
	public void buildCity() {
		if (abletobuildcity()) {
			executeElementAction(ResourceBarElement.CITY);
		}

	}

	public boolean abletobuildroad() {
		int currentplayer = GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer();
		String status = GameManager.getSingleton().getModel().getTurnTracker().getStatus();
		Player playerinventory = GameManager.getSingleton().getModel().getPlayers().get(currentplayer);
		int thisplayerindex = GameManager.getSingleton().getthisplayer().getPlayerIndex();

		if (playerinventory.getResources().getWood() < 1) {
			return false;
		} else if (playerinventory.getResources().getBrick() < 1) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(status.equals("Playing"))
				&& !((playerinventory.getRoads() <= 15))) {
			return false;
		} else {
			return true;
		}
	}

	public boolean abletobuildsettlement() {
		int currentplayer = GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer();
		String status = GameManager.getSingleton().getModel().getTurnTracker().getStatus();
		Player playerinventory = GameManager.getSingleton().getModel().getPlayers().get(currentplayer);
		int thisplayerindex = GameManager.getSingleton().getthisplayer().getPlayerIndex();

		if (playerinventory.getResources().getWood() < 1) {
			return false;
		} else if (playerinventory.getResources().getBrick() < 1) {
			return false;
		} else if (playerinventory.getResources().getSheep() < 1) {
			return false;
		} else if (playerinventory.getResources().getWheat() < 1) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(status.equals("Playing"))
				&& !((playerinventory.getSettlements() <= 5))) {
			return false;
		} else {
			return true;
		}
	}

	public boolean abletobuildcity() {
		int currentplayer = GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer();
		String status = GameManager.getSingleton().getModel().getTurnTracker().getStatus();
		Player playerinventory = GameManager.getSingleton().getModel().getPlayers().get(currentplayer);
		int thisplayerindex = GameManager.getSingleton().getthisplayer().getPlayerIndex();

		if (playerinventory.getResources().getOre() < 3) {
			return false;
		} else if (playerinventory.getResources().getWheat() < 2) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(status.equals("Playing"))
				&& !((playerinventory.getCities() <= 4))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * spends resources and gains card
	 * 
	 * @pre none
	 * @post card is bought
	 */
	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	/**
	 * check if player can play a card based on resources they have and calls
	 * executeElementAction();
	 * 
	 * @pre none
	 * @post plays card
	 */
	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}

	private void executeElementAction(ResourceBarElement element) {

		if (elementActions.containsKey(element)) {

			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	@Override
	public void update() {
		System.out.println("updating resourcebarcontroller");
		setResources();
		setBuildingPieces();
		updateButtons();
	}

	private void updateButtons() {

		if (abletobuildsettlement()) {
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		}

		if (abletobuildroad()) {
			getView().setElementEnabled(ResourceBarElement.ROAD, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
		}

		// if (canDoBuyDevCard()) {
		// getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		// } else {
		// getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		// }

		if (abletobuildcity()) {
			getView().setElementEnabled(ResourceBarElement.CITY, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.CITY, false);
		}

	}

	private void setBuildingPieces() {
		Player playerinventory = GameManager.getSingleton().getthisplayer();
		getView().setElementAmount(ResourceBarElement.CITY, playerinventory.getCities());
		getView().setElementAmount(ResourceBarElement.ROAD, playerinventory.getRoads());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, playerinventory.getSettlements());
	}

	/**
	 * For each resource owned, call and update getView()'s setElementAmount
	 * 
	 * @pre none
	 * @post resources are set
	 */
	private void setResources() {
		Player playerinventory = GameManager.getSingleton().getthisplayer();
		getView().setElementAmount(ResourceBarElement.ROAD, playerinventory.getRoads());
		getView().setElementAmount(ResourceBarElement.CITY, playerinventory.getCities());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, playerinventory.getSettlements());
		getView().setElementAmount(ResourceBarElement.BRICK, playerinventory.getResources().getBrick());
		getView().setElementAmount(ResourceBarElement.WHEAT, playerinventory.getResources().getWheat());
		getView().setElementAmount(ResourceBarElement.WOOD, playerinventory.getResources().getWood());
		getView().setElementAmount(ResourceBarElement.ORE, playerinventory.getResources().getOre());
		getView().setElementAmount(ResourceBarElement.SHEEP, playerinventory.getResources().getSheep());

	}

}
