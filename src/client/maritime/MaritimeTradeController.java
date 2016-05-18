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



		getTradeOverlay().showGiveOptions(getResources);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {

		int givingAmount = 0;//TODO: Once again, how do I see the number demanded?
		for(int i =0; i < givingAmount; i++) {
			thePlayer.depleteResource(theGiving);//TODO: these have to be depleted in the server, too
		}
		thePlayer.addResource(theGetting, 0); //TODO: Once again, how do I see the number demanded?
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getTradeOverlay().selectGiveOption(resource, 0); //TODO: again, how do I know the # resources they want?
		getTradeOverlay().setTradeEnabled(true);
		getTradeOverlay().setStateMessage("Now trade it!");
		theGetting = resource;
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		getTradeOverlay().selectGiveOption(resource, 0); //TODO: HOW do I discover how many resources they want?
		theGiving = resource;
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
		ArrayList<ResourceType> theResourcesList = new ArrayList<>();
		for(int i = 0; i < getResources.length; i++ )
		{
			if(getResources[i] != theGiving) {
				theResourcesList.add(getResources[i]);
			}
		}

		ResourceType[] simpleArray = new ResourceType[ theResourcesList.size() ];
		theResourcesList.toArray( simpleArray );
		return simpleArray;

	}

	public void update(){
		pid = GameManager.getSingleton().getthisplayer().getPlayerID();
		thePlayer = GameManager.getSingleton().getthisplayer();
		getResources = thePlayer.resourcesOverOne();

		if(thePlayer.canOfferBankTrade())
		{
			getTradeView().enableMaritimeTrade(true);
			getTradeOverlay().setCancelEnabled(true);
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("Choose what to give!");
		}
		else
		{
			getTradeView().enableMaritimeTrade(false);
		}


	}



}

