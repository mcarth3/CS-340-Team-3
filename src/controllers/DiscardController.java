package controllers;

import java.util.Observable;

import client.base.Controller;
import client.discard.IDiscardController;
import client.discard.IDiscardView;
import client.misc.IMessageView;
import client.misc.IWaitView;
import shared.definitions.ResourceType;

/**
 * controller for discarding
 */
public class DiscardController extends Controller implements IDiscardController {

	
	private IWaitView waitView;
	private IMessageView messageView;
	private int maxDiscardNum;
	
	
	/**
	 * DiscardController constructor
	 * @param view- the view displayed that lets the user discard cards
	 * @param waitView - View shows if they're waiting for other players to discard cards
	 * @pre view and waitview arent null
	 * @post discard controller constructed
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		super(view);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
	}

	public IDiscardView getDiscardView() {
		return null;
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}
	
	public IMessageView getMessageView() {
		return messageView;
	}

	
	/**
	 * adds resource to a list to be discarded
	 * @pre resource is not null
	 * @post resource added to discard list
	 */
	public void increaseAmount(ResourceType resource) {
	}

	/**
	 * removes a resource from the discard list
	 * @pre resource is not null
	 * @post resource removed from discard list
	 */
	public void decreaseAmount(ResourceType resource) {
	}

	/**
	 * send request to server with a map of resource types and how many are discarded
	 * @pre none
	 * @post discard list sent to server
	 */
	public void discard() {
	}


	/**
	 * calculates the number cards needed to be discarded
	 * @pre none
	 * @post the number of cards to discard are returned
	 */
	private int calculateDiscardNum() {
		return 1;
	}
	
	/**
	 * checks the validity of how many cards are ready to be discarded
	 * @return -1 if discard list < max discard number,
	 *  0 if discard list == max discard number
	 *  1 if discard list > max discard number
	 * @pre none
	 * @post validity is returned
	 */
	private int checkDiscardNum() {
		return 1;
	}
	
	/**
	 * updates the view with the number of cards
	 * @pre none
	 * @post view is updated
	 */
	private void updateView() {
	}
}

