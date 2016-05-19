package client.maritime;

import client.GameManager.GameManager;
import model.Facade;
import model.Player;
import shared.definitions.*;
import client.base.*;

import java.util.ArrayList;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	private int pid;
	private Facade theFacade;
	private Player thePlayer;
	private ResourceType[] getResources;
	private ResourceType[] giveResources;
	private ResourceType theGiving;
	private ResourceType theGetting;
	private int amountGiving;
	private int amountGetting;

	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);


		//pid = GameManager.getSingleton().getthisplayer().getPlayerID();

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {



		getTradeOverlay().showGiveOptions(giveResources);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {

		for(int i =0; i < amountGiving; i++) {
			thePlayer.depleteResource(theGiving);
		}
		thePlayer.addResource(theGetting, amountGetting);
		getTradeOverlay().closeModal();
		theFacade.meritimeTrade(pid, amountGiving, theGiving.toString(), theGetting.toString());		//TODO: this needs to be modified to implement ports' ratios

	}

	@Override
	public void cancelTrade() {
		amountGiving = 0;
		amountGetting = 0;

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		amountGetting = 1;						//TODO: this needs to be modified to implement ports' ratios
		getTradeOverlay().selectGetOption(resource, amountGetting);
		getTradeOverlay().setTradeEnabled(true);
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().setStateMessage("Now trade it!");
		theGetting = resource;
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		amountGiving = 4;						//TODO: this needs to be modified to implement ports' ratios
		getTradeOverlay().selectGiveOption(resource, amountGiving);
		theGiving = resource;
		getResources = getsWithoutGive();
		getTradeOverlay().showGetOptions(getResources);
		getTradeOverlay().setStateMessage("Choose what to get!");
	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().showGetOptions(getResources);
		getTradeOverlay().setStateMessage("Choose what to get!");
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().showGiveOptions(giveResources);
		getTradeOverlay().setStateMessage("Choose what to give!");
	}

	public ResourceType[] getsWithoutGive()
	{
		ResourceType[] basicResources = new ResourceType[5];
		basicResources[0] = ResourceType.BRICK;
		basicResources[1] = ResourceType.WOOD;
		basicResources[2] = ResourceType.WHEAT;
		basicResources[3] = ResourceType.ORE;
		basicResources[4] = ResourceType.SHEEP;


		ArrayList<ResourceType> theResourcesList = new ArrayList<>();
		for(int i = 0; i < basicResources.length; i++ )
		{
			if(basicResources[i] != theGiving) {
				theResourcesList.add(basicResources[i]);
			}
		}

		ResourceType[] simpleArray = new ResourceType[ theResourcesList.size() ];
		theResourcesList.toArray( simpleArray );
		return simpleArray;

	}

	public void update(){



theFacade = Facade.getFacade();
		if(GameManager.getSingleton() != null) {
			thePlayer = GameManager.getSingleton().getthisplayer();
			if (thePlayer.canOfferBankTrade())        //TODO: this needs to be changed for ports
			{

				pid = GameManager.getSingleton().getthisplayer().getPlayerID();
				//theFacade.getGame().canMaritimeTrade(pid);
				thePlayer = GameManager.getSingleton().getthisplayer();
				giveResources = thePlayer.resourcesOverThree();                //TODO: this needs to be changed to implement port ratios.
				getTradeView().enableMaritimeTrade(true);
				getTradeOverlay().setCancelEnabled(true);
				getTradeOverlay().setTradeEnabled(false);
				getTradeOverlay().setStateMessage("Choose what to give!");
			} else {
				getTradeView().enableMaritimeTrade(false);
			}
		}

	}



}

