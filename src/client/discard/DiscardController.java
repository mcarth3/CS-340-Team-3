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
		int totalCards = currentPlayer.getResources().getTotal()/2;
		
		if (totalCards > 7) {
			n = totalCards/2;
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

	}

	@Override
	public void decreaseAmount(ResourceType resource) {
	}

	@Override
	public void discard() {

	}

	@Override
	public void update() {
		String turn = GameManager.getSingleton().getModel().getTurnTracker().getStatus();
		int cards = GameManager.getSingleton().getthisplayer().getResources().getTotal();

		if(turn == "Discarding" || (turn == "Robbing" && cards > 7)) {
			if(maxDiscardNum == 0) {
				this.getWaitView().showModal();
			} else {
				this.getDiscardView().showModal();
			}
			updateView();
		}
	}
	
	
	private int checkDiscardNum() {
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
		int currentBricks = currentHand.getBrick();
		int currentOres = currentHand.getOre();
		int currentSheeps = currentHand.getSheep();
		int currentWoods = currentHand.getWood();
		int currentWheats = currentHand.getWheat();
		
		int discardBricks = discardList.getBrick();
		int discardOres = discardList.getOre();
		int discardSheeps = discardList.getSheep();
		int discardWoods = discardList.getWood();
		int discardWheats = discardList.getWheat();
		
		getDiscardView().setStateMessage("Discarding: " + discardList.getTotal() + "/" + maxDiscardNum);
		
		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, currentBricks);
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, currentOres);
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, currentSheeps);
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, currentWoods);
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, currentWheats);
		
		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, discardBricks);
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, discardOres);
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, discardSheeps);
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, discardWoods);
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, discardWheats);
		
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK
													 , (currentBricks > 0 && discardBricks < currentBricks)
													 , (discardBricks > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE
													 , (currentOres > 0 && discardOres < currentOres)
													 , (discardOres > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP
													 , (currentSheeps > 0 && discardSheeps < currentSheeps)
													 , (discardSheeps > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD
													 , (currentWoods > 0 && discardWoods < currentWoods)
													 , (discardWoods > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT
													 , (currentWheats > 0 && discardWheats < currentWheats)
													 , (discardWheats > 0));
		
		switch(checkDiscardNum()) {
			case -1: getDiscardView().setDiscardButtonEnabled(false);
			case 0: getDiscardView().setDiscardButtonEnabled(true);
			case 1: getDiscardView().setDiscardButtonEnabled(false);
		}
	}
}

