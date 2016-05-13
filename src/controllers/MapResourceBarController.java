package controllers;

import java.util.Observable;

import client.base.Controller;
import client.base.IAction;
import client.resources.IResourceBarController;
import client.resources.IResourceBarView;
import client.resources.ResourceBarElement;

/**
 * Controller for Resource Bar
 */
public class MapResourceBarController extends Controller implements IResourceBarController {
	public MapResourceBarController(IResourceBarView view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IResourceBarView getView() {
		return null;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
	}

	/**
	 * when clicked on an element, sets the action to be taken
	 * 
	 * @param element the element of the resource bar which the action affects
	 * @param action the action that occurs to the element
	 * @pre element and actions are not null
	 * @post action is performed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {
	}
	
	/**
	 * For each resource owned, call and update getView()'s setElementAmount
	 * @pre none
	 * @post resources are set
	 */
	public void setResources() {
	}
	
	/**
	 * spends resources and gains card
	 * @pre none
	 * @post card is bought
	 */
	@Override
	public void buyCard() {
	}

	
	/** 
	 * check if player can play a card based on resources they have and calls executeElementAction();
	 * @pre none
	 * @post plays card
	 */
	@Override
	public void playCard() {		
	}
	

	
	/** 
	 * check if player can build a settlement based on resources they have and calls executeElementAction();
	 * @pre none
	 * @post places settlement
	 */
	@Override
	public void buildSettlement() {
	}
	
	public boolean canEnableBuildSettlement(Integer playerIndex) {
		return true;
	}

	/** 
	 * check if player can build a city based on resources they have and calls executeElementAction();
	 * @pre none
	 * @post places city
	 */
	@Override
	public void buildCity() {
	}
	
	public boolean canEnableBuildCity(Integer playerIndex) {
		return true;
	}
	
	
	/**
	 * check if player can build a road based on resources they have and calls executeElementAction();
	 * @pre none
	 * @post places road
	 */
	@Override
	public void buildRoad() {
	}
	
	public boolean canEnableBuildRoad(Integer playerIndex) {
		return true;
	}


	
	private void executeElementAction(ResourceBarElement element) {
	}

}

