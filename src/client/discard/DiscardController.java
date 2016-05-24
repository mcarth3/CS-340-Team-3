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
		
		if (GameManager.getSingleton().getModel() == null) return 0;
		
		ArrayList<Player> players = GameManager.getSingleton().getModel().getPlayers();
		Player currentPlayer = players.get(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer());
		int halfcards = currentPlayer.getResources().getSize()/2;
		System.out.println("max discard num= " +halfcards);
	
		return halfcards;
	}


	public IDiscardView getDiscardView() {
	//	System.out.println("Discard: get discardview");
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
	//	System.out.println("Discard: get waitview");
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		System.out.println("updating");
		//update();
	//	System.out.println("sheep= " + discardList.getSheep());
		if (resource == ResourceType.BRICK){
			discardList.setBrick(discardList.getBrick() + 1);
		}else if (resource == ResourceType.ORE){
			discardList.setOre(discardList.getOre() + 1);
		}else if (resource == ResourceType.SHEEP){
			discardList.setSheep(discardList.getSheep() + 1);
		}else if (resource == ResourceType.WOOD){
			discardList.setWood(discardList.getWood() + 1);
		}else if (resource == ResourceType.WHEAT){
			discardList.setWheat(discardList.getWheat() + 1);
		}

	
		updateView();
			
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		//System.out.println("decreased amount to discard");
		if (resource == ResourceType.BRICK){
			discardList.setBrick(discardList.getBrick() - 1);
		}else if (resource == ResourceType.ORE){
			discardList.setOre(discardList.getOre() - 1);
		}else if (resource == ResourceType.SHEEP){
			discardList.setSheep(discardList.getSheep() - 1);
		}else if (resource == ResourceType.WOOD){
			discardList.setWood(discardList.getWood() - 1);
		}else if (resource == ResourceType.WHEAT){
			discardList.setWheat(discardList.getWheat() - 1);
		}

		updateView();
	}

	@Override
	public void discard() {
		System.out.println("discard command sent");
		String message ="";
		ArrayList<Integer> discardCardarray = new ArrayList<Integer>();
		discardCardarray.add(discardList.getBrick());
		discardCardarray.add(discardList.getOre());
		discardCardarray.add(discardList.getSheep());
		discardCardarray.add(discardList.getWheat());
		discardCardarray.add(discardList.getWood());
		if (this.getDiscardView().isModalShowing()){
			this.getDiscardView().closeModal();
		}
		message = RealProxy.getSingleton().discardCards((Integer)GameManager.getSingleton().getthisplayer().getPlayerIndex(), discardCardarray);
		GameManager.getSingleton().setrobbingready(true);
//		"discardedCards"  in this order. THIS MIGHT CHANGE LATER
//	    "brick": 1,
//	    "ore": 2,
//	    "sheep": 0,
//	    "wheat": 0,
//	    "wood": 1
	}

	@Override
	public void update() {
		System.out.println("discard controller update");
		String status = GameManager.getSingleton().getModel().getTurnTracker().getStatus();
		int cards = GameManager.getSingleton().getthisplayer().getResources().getSize();
		if((status.equals("Discarding") ||(status.equals("Robbing")))&& !(GameManager.getSingleton().getdiscardedcheck())) {
			GameManager.getSingleton().setdiscardedcheck(true);
			System.out.println("cards"+cards);
			if(cards > 7){
				maxDiscardNum = calculateDiscardNum();
				System.out.println("max discard"+maxDiscardNum);
				if(maxDiscardNum == 0) {
				} else {
					this.getDiscardView().showModal();
				}
				updateView();
			
			}else{
				System.out.print("<7 resources");
				ArrayList<Integer> discardCardarray = new ArrayList<Integer>();
				discardCardarray.add(0);
				discardCardarray.add(0);
				discardCardarray.add(0);
				discardCardarray.add(0);
				discardCardarray.add(0);
				GameManager.getSingleton().setrobbingready(true);
				RealProxy.getSingleton().discardCards(GameManager.getSingleton().getthisplayer().playerIndex, discardCardarray);
			
			}
		}else{
			GameManager.getSingleton().setdiscardedcheck(false);
			//if (this.getDiscardView().isModalShowing()){
			//	this.getDiscardView().closeModal();
			//}
		}
	}
	
	
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
	
	
	private void updateView() {
		ResourceList currentHand = GameManager.getSingleton().getthisplayer().getResources();
		getDiscardView().setStateMessage("Discarding: " + discardList.getSize() + "/" + maxDiscardNum);
		System.out.println("Discarding: " + discardList.getSize() + "/" + maxDiscardNum);
		System.out.println("sheep: " + discardList.getSheep());
		System.out.println("brick: " + discardList.getBrick());
		System.out.println("wood: " + discardList.getWood());
		System.out.println("ore: " + discardList.getOre());
		System.out.println("wheat: " + discardList.getWheat());
		
		
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
		
		if (checkifcorrect() == -1){
			 getDiscardView().setDiscardButtonEnabled(false);
		}else if (checkifcorrect() == 0){
			 getDiscardView().setDiscardButtonEnabled(true);
		}else if (checkifcorrect() == 1){
			 getDiscardView().setDiscardButtonEnabled(false);
		}

	}
}

