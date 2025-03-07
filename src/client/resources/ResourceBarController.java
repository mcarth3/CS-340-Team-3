package client.resources;

import java.util.HashMap;
import java.util.Map;

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
		Player playerinventory = model.getPlayers().get(currentplayer);
		int thisplayerindex = thisplayer.getPlayerIndex();

		if (playerinventory.getResources().getWood() < 1) {
			return false;
		} else if (playerinventory.getResources().getBrick() < 1) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(state.equals("Playing"))
				&& !((playerinventory.getRoads() <= 15))) {
			return false;
		} else {
			return true;
		}
	}

	public boolean abletobuildsettlement() {
		Player playerinventory = model.getPlayers().get(currentplayer);
		int thisplayerindex = thisplayer.getPlayerIndex();

		if (playerinventory.getResources().getWood() < 1) {
			return false;
		} else if (playerinventory.getResources().getBrick() < 1) {
			return false;
		} else if (playerinventory.getResources().getSheep() < 1) {
			return false;
		} else if (playerinventory.getResources().getWheat() < 1) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(state.equals("Playing"))
				&& !((playerinventory.getSettlements() <= 5))) {
			return false;
		} else {
			return true;
		}
	}

	public boolean abletobuildcity() {
		Player playerinventory = model.getPlayers().get(currentplayer);
		int thisplayerindex = thisplayer.getPlayerIndex();

		if (playerinventory.getResources().getOre() < 3) {
			return false;
		} else if (playerinventory.getResources().getWheat() < 2) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(state.equals("Playing"))
				&& !((playerinventory.getCities() <= 4))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks if the player who's playing right now has the resources to play a devCard.
	 * @return
	 */
	public boolean canDoBuyDevCard() {
		Player playerinventory = model.getPlayers().get(currentplayer);
		int thisplayerindex = thisplayer.getPlayerIndex();

		if (playerinventory.getResources().getOre() < 1) {
			return false;
		} else if (playerinventory.getResources().getWheat() < 1) {
			return false;
		} else if (playerinventory.getResources().getSheep() < 1) {
			return false;
		} else if (!(currentplayer == thisplayerindex) && !(state.equals("Playing"))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks if the player who's playing right now has devCard to play.
	 * @return
	 */
	public boolean canDoPlayDevCard() {
		Player playerinventory = model.getPlayers().get(currentplayer);
		int thisplayerindex = thisplayer.getPlayerIndex();

		if (playerinventory.getOldDevCards().getSize() < 1 && playerinventory.getNewDevCards().getMonument() < 1) {
			return false;
		} else if (!(currentplayer == thisplayerindex) || !(state.equals("Playing"))) {
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
		if (canDoBuyDevCard()) {
			executeElementAction(ResourceBarElement.BUY_CARD);
		}
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
		if (canDoPlayDevCard()) {
			executeElementAction(ResourceBarElement.PLAY_CARD);
		}
	}

	private void executeElementAction(ResourceBarElement element) {

		if (elementActions.containsKey(element)) {

			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	@Override
	public void update() {
		System.out.println("thread " + Thread.currentThread().getId() + "- updating resourcebarcontroller");
		if (thisplayer != null) {
			setResources();
			setBuildingPieces();
			updateButtons();
		}
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

		//buy card:
		if (canDoBuyDevCard()) {
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}

		//play card:
		if (canDoPlayDevCard()) {
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}

		if (abletobuildcity()) {
			getView().setElementEnabled(ResourceBarElement.CITY, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.CITY, false);
		}

	}

	private void setBuildingPieces() {
		getView().setElementAmount(ResourceBarElement.CITY, thisplayer.getCities());
		getView().setElementAmount(ResourceBarElement.ROAD, thisplayer.getRoads());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, thisplayer.getSettlements());
	}

	/**
	 * For each resource owned, call and update getView()'s setElementAmount
	 * 
	 * @pre none
	 * @post resources are set
	 */
	private void setResources() {
		getView().setElementAmount(ResourceBarElement.ROAD, thisplayer.getRoads());
		getView().setElementAmount(ResourceBarElement.CITY, thisplayer.getCities());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, thisplayer.getSettlements());
		getView().setElementAmount(ResourceBarElement.BRICK, thisplayer.getResources().getBrick());
		getView().setElementAmount(ResourceBarElement.WHEAT, thisplayer.getResources().getWheat());
		getView().setElementAmount(ResourceBarElement.WOOD, thisplayer.getResources().getWood());
		getView().setElementAmount(ResourceBarElement.ORE, thisplayer.getResources().getOre());
		getView().setElementAmount(ResourceBarElement.SHEEP, thisplayer.getResources().getSheep());

	}

}
