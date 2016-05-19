package client.discard;

import shared.definitions.*;

import java.util.ArrayList;
import java.util.Observable;

import client.GameManager.GameManager;
import client.base.*;
import client.misc.*;
import model.Player;
import model.bank.ResourceList;
import proxy.RealProxy;


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
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		this.messageView = new MessageView();
		maxDiscardNum = calculateDiscardNum();
		discardList = new ResourceList();
		
		getDiscardView().setDiscardButtonEnabled(false);
	}
	
	
	
	
	
	private int calculateDiscardNum() {
		int n = 0;
		
		if (GameManager.getSingleton().getModel() == null) return 0;
		
		ArrayList<Player> players = GameManager.getSingleton().getModel().getPlayers();
		Player currentPlayer = players.get(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer());
		int halfcards = currentPlayer.getResources().getTotal()/2;
		
		if (halfcards > 7) {
			n = halfcards;
		} else {
			n = 0;
		}
		
		return n;
	}


	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		
		switch(resource) {
			case BRICK:
				discardList.setBrick(discardList.getBrick() + 1);
			case ORE:
				discardList.setOre(discardList.getOre() + 1);
			case SHEEP:
				discardList.setSheep(discardList.getSheep() + 1);
			case WOOD:
				discardList.setWood(discardList.getWood() + 1);
			case WHEAT:
				discardList.setWheat(discardList.getWheat() + 1);
			}
	
		updateView();
			
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		
		switch(resource) {
		case BRICK:
			discardList.setBrick(discardList.getBrick() - 1);
		case ORE:
			discardList.setOre(discardList.getOre() - 1);
		case SHEEP:
			discardList.setSheep(discardList.getSheep() - 1);
		case WOOD:
			discardList.setWood(discardList.getWood() - 1);
		case WHEAT:
			discardList.setWheat(discardList.getWheat() - 1);
		}

		updateView();
	}

	@Override
	public void discard() {
		String message ="";
		ArrayList<Integer> discardCardarray = new ArrayList<Integer>();
		discardCardarray.add(discardList.getBrick());
		discardCardarray.add(discardList.getOre());
		discardCardarray.add(discardList.getSheep());
		discardCardarray.add(discardList.getWheat());
		discardCardarray.add(discardList.getWood());
		message = RealProxy.getSingleton().discardCards((Integer)GameManager.getSingleton().getthisplayer().getPlayerIndex(), discardCardarray);
		
//		"discardedCards"  in this order. THIS MIGHT CHANGE LATER
//	    "brick": 1,
//	    "ore": 2,
//	    "sheep": 0,
//	    "wheat": 0,
//	    "wood": 1
	}

	@Override
	public void update() {
		System.out.println("discarduptdate");
		String status = GameManager.getSingleton().getModel().getTurnTracker().getStatus();
		int cards = GameManager.getSingleton().getthisplayer().getResources().getTotal();

		if(status == "Playing" && cards > 7) {
			if(maxDiscardNum == 0) {
				this.getWaitView().showModal();
			} else {
				this.getDiscardView().showModal();
			}
			updateView();
		}
	}
	
	
	private int checkifinbounds() {
		int n = -1;
		
		if (discardList.getTotal() < maxDiscardNum) {
			n = -1;
		} else if (discardList.getTotal() == maxDiscardNum) {
			n = 0;
		} else if (discardList.getTotal() > maxDiscardNum) {
			n = 1;
		}
		
		return n;
	}
	
	
	private void updateView() {
		ResourceList currentHand = GameManager.getSingleton().getthisplayer().getResources();
		getDiscardView().setStateMessage("Discarding: " + discardList.getTotal() + "/" + maxDiscardNum);
		
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
		
		switch(checkifinbounds()) {
			case -1: getDiscardView().setDiscardButtonEnabled(false);
			case 0: getDiscardView().setDiscardButtonEnabled(true);
			case 1: getDiscardView().setDiscardButtonEnabled(false);
		}
	}
}

