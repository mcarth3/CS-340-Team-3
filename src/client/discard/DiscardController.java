package client.discard;

import java.util.ArrayList;

import client.base.Controller;
import client.misc.IMessageView;
import client.misc.IWaitView;
import client.misc.MessageView;
import model.Player;
import model.bank.ResourceList;
import proxy.RealProxy;
import shared.definitions.ResourceType;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	private IMessageView messageView;
	private ResourceList discardList;
	private int maxDiscardNum;

	/**
	 * DiscardController constructor
	 * 
	 * @param view
	 *            View displayed to let the user select cards to discard
	 * @param waitView
	 *            View displayed to notify the user that they are waiting for
	 *            other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {

		super(view);

		this.waitView = waitView;
		this.messageView = new MessageView();
		maxDiscardNum = calculateDiscardNum();
		discardList = new ResourceList();

		getDiscardView().setDiscardButtonEnabled(false);

	}

	/**
	 * calculates the number cards needed to be discarded
	 * 
	 * @pre none
	 * @post the number of cards to discard are returned
	 */
	private int calculateDiscardNum() {

		if (model == null)
			return 0;

		ArrayList<Player> players = model.getPlayers();
		int halfcards = thisplayer.getResources().getSize() / 2;
		System.out.println("DISCARD:max discard num= " + halfcards);

		return halfcards;
	}

	public IDiscardView getDiscardView() {
		maxDiscardNum = calculateDiscardNum();
		// System.out.println("Discard: get discardview");
		return (IDiscardView) super.getView();
	}

	public IWaitView getWaitView() {
		// System.out.println("Discard: get waitview");
		return waitView;
	}

	/**
	 * adds resource to a list to be discarded
	 * 
	 * @pre resource is not null
	 * @post resource added to discard list
	 */
	@Override
	public void increaseAmount(ResourceType resource) {
		maxDiscardNum = calculateDiscardNum();
		// System.out.println("DISCARD:updating");
		// update();
		// System.out.println("sheep= " + discardList.getSheep());
		if (resource == ResourceType.BRICK) {
			discardList.setBrick(discardList.getBrick() + 1);
		} else if (resource == ResourceType.ORE) {
			discardList.setOre(discardList.getOre() + 1);
		} else if (resource == ResourceType.SHEEP) {
			discardList.setSheep(discardList.getSheep() + 1);
		} else if (resource == ResourceType.WOOD) {
			discardList.setWood(discardList.getWood() + 1);
		} else if (resource == ResourceType.WHEAT) {
			discardList.setWheat(discardList.getWheat() + 1);
		}

		updateView();

	}

	/**
	 * removes a resource from the discard list
	 * 
	 * @pre resource is not null
	 * @post resource removed from discard list
	 */
	@Override
	public void decreaseAmount(ResourceType resource) {
		maxDiscardNum = calculateDiscardNum();
		// System.out.println("decreased amount to discard");
		if (resource == ResourceType.BRICK) {
			discardList.setBrick(discardList.getBrick() - 1);
		} else if (resource == ResourceType.ORE) {
			discardList.setOre(discardList.getOre() - 1);
		} else if (resource == ResourceType.SHEEP) {
			discardList.setSheep(discardList.getSheep() - 1);
		} else if (resource == ResourceType.WOOD) {
			discardList.setWood(discardList.getWood() - 1);
		} else if (resource == ResourceType.WHEAT) {
			discardList.setWheat(discardList.getWheat() - 1);
		}

		updateView();
	}

	/**
	 * send request to server with a map of resource types and how many are
	 * discarded
	 * 
	 * @pre none
	 * @post discard list sent to server
	 */
	@Override
	public void discard() {
		System.out.println("DISCARD:discard command sent");
		String message = "";
		ArrayList<Integer> discardCardarray = new ArrayList<Integer>();
		discardCardarray.add(discardList.getBrick());
		discardCardarray.add(discardList.getOre());
		discardCardarray.add(discardList.getSheep());
		discardCardarray.add(discardList.getWheat());
		discardCardarray.add(discardList.getWood());
		if (this.getDiscardView().isModalShowing()) {
			this.getDiscardView().closeModal();
		}
		message = RealProxy.getSingleton().discardCards(thisplayer.getPlayerIndex(), discardCardarray);
		manager.setrobbingready(true);
		// "discardedCards" in this order. THIS MIGHT CHANGE LATER
		// "brick": 1,
		// "ore": 2,
		// "sheep": 0,
		// "wheat": 0,
		// "wood": 1
	}

	@Override
	public void update() {
		System.out.println("DISCARD:discard controller update");
		int cards = thisplayer.getResources().getSize();

		if ((state.equals("Discarding") || (state.equals("Robbing")))) {
			// System.out.print("DISCARD:status is discarding or robbing");
			if ((manager.getdiscardedcheck() == false)) {
				// System.out.print("DISCARD:discarded check = false");
				manager.setdiscardedcheck(true);
				// System.out.println("DISCARD:cards" + cards);
				if (cards > 7) {
					maxDiscardNum = calculateDiscardNum();
					discardList = new ResourceList();
					this.getDiscardView().showModal();

					updateView();

				} else {
					System.out.print("DISCARD:<7 resources, so doing a blank discard");
					ArrayList<Integer> discardCardarray = new ArrayList<Integer>();
					discardCardarray.add(0);
					discardCardarray.add(0);
					discardCardarray.add(0);
					discardCardarray.add(0);
					discardCardarray.add(0);
					manager.setrobbingready(true);
					RealProxy.getSingleton().discardCards(thisplayer.playerIndex, discardCardarray);

				}
			}
		} else

		{
			manager.setdiscardedcheck(false);
			if (this.getDiscardView().isModalShowing()) {
				this.getDiscardView().closeModal();
			}
		}

	}

	/**
	 * checks the validity of how many cards are ready to be discarded
	 * 
	 * @return -1 if discard list < max discard number, 0 if discard list == max
	 *         discard number 1 if discard list > max discard number
	 * @pre none
	 * @post validity is returned
	 */
	private int checkifcorrect() {
		int n = -1;

		if (discardList.getSize() < maxDiscardNum) {
			n = -1;
		} else if (discardList.getSize() == maxDiscardNum) {
			n = 0;
		} else if (discardList.getSize() > maxDiscardNum) {
			n = 1;
		}

		return n;
	}

	/**
	 * updates the view with the number of resources
	 * 
	 * @pre none
	 * @post view is updated
	 */

	private void updateView() {
		ResourceList currentHand = thisplayer.getResources();
		maxDiscardNum = calculateDiscardNum();
		getDiscardView().setStateMessage("Discarding: " + discardList.getSize() + "/" + maxDiscardNum);

		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, currentHand.getBrick());
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, currentHand.getOre());
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, currentHand.getSheep());
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, currentHand.getWood());
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, currentHand.getWheat());

		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, discardList.getBrick());
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, discardList.getOre());
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, discardList.getSheep());
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, discardList.getWood());
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, discardList.getWheat());

		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, (currentHand.getBrick() > 0 && discardList.getBrick() < currentHand.getBrick()), (discardList.getBrick() > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, (currentHand.getOre() > 0 && discardList.getOre() < currentHand.getOre()), (discardList.getOre() > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, (currentHand.getSheep() > 0 && discardList.getSheep() < currentHand.getSheep()), (discardList.getSheep() > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, (currentHand.getWood() > 0 && discardList.getWood() < currentHand.getWood()), (discardList.getWood() > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, (currentHand.getWheat() > 0 && discardList.getWheat() < currentHand.getWheat()), (discardList.getWheat() > 0));

		if (checkifcorrect() == -1) {
			getDiscardView().setDiscardButtonEnabled(false);
		} else if (checkifcorrect() == 0) {
			getDiscardView().setDiscardButtonEnabled(true);
		} else if (checkifcorrect() == 1) {
			getDiscardView().setDiscardButtonEnabled(false);
		}

	}
}
