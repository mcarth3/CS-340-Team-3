package client.maritime;

import client.GameManager.GameManager;
import model.*;
import model.bank.ResourceList;
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

		/*for(int i =0; i < amountGiving; i++) {
			thePlayer.depleteResource(theGiving);
		}
		thePlayer.addResource(theGetting, amountGetting);*/
		System.out.println("Making Trade!");
		getTradeOverlay().closeModal();
		theFacade.meritimeTrade(pid, amountGiving, theGiving.toString().toLowerCase(), theGetting.toString().toLowerCase());		//TODO: this needs to be modified to implement ports' ratios
		System.out.println("pid: " + pid);
		System.out.println("amountGiving: " + amountGiving);
		System.out.println("The Giving: " + theGiving.toString());
		System.out.println("The Getting: " + theGetting.toString());
		getTradeOverlay().reset();
	}

	@Override
	public void cancelTrade() {
		amountGiving = 0;
		amountGetting = 0;

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		amountGetting = 1;
		getTradeOverlay().selectGetOption(resource, amountGetting);
		getTradeOverlay().setTradeEnabled(true);
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().setStateMessage("Now trade it!");
		theGetting = resource;
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		amountGiving = checkPossibleAmount(resource);						//TODO: this needs to be modified to implement ports' ratios
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
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().showGiveOptions(giveResources);
		getTradeOverlay().setStateMessage("Choose what to give!");
		getTradeOverlay().setTradeEnabled(false);
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

		theFacade = Facade.getSingleton();
		if(GameManager.getSingleton() != null) {
			thePlayer = GameManager.getSingleton().getthisplayer();
			if (thePlayer.canOfferBankTrade())        //TODO: this needs to be changed for ports
			{

				pid = GameManager.getSingleton().getthisplayer().getPlayerIndex();
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

	/**
	 * checks if thisPlayer can offer any maritimeTrade at all. If they have the amount needed for a port they've built on,
	 * or have 4 of any, this should be true.
	 * @return
     */
	public boolean canOfferMaritimeTrade()
	{


		return false;
	}

	/**
	 * checks if thisPlayer can offer any maritimeTrade at all. If they have the amount needed for a port they've built on,
	 * or have 4 of any, the ResourceType of the necessary type will be added.
	 * @return
     */
	public ResourceType[] playerResourcesOverPorts()
	{
		ResourceList resources = thePlayer.getResources();
		ArrayList<ResourceType> resourceList = new ArrayList<ResourceType>();
		if(resources.getBrick() > 3)
		{
			resourceList.add(ResourceType.BRICK);
		}
		if(resources.getWood() > 3)
		{
			resourceList.add(ResourceType.WOOD);
		}
		if(resources.getOre() > 3)
		{
			resourceList.add(ResourceType.ORE);
		}
		if(resources.getSheep() > 3)
		{
			resourceList.add(ResourceType.SHEEP);
		}
		if(resources.getWheat() > 3)
		{
			resourceList.add(ResourceType.WHEAT);
		}

		ResourceType[] simpleArray = new ResourceType[ resourceList.size() ];
		resourceList.toArray( simpleArray );
		return simpleArray;


	}

	/**
	 * checks the possible lowest amount a resourceType can be traded for. Default is 0, but if thisPlayer
	 * has cities or settlements next to a port, it may be 2 or 3.
	 * @param type
	 * @return
     */
	public int checkPossibleAmount(ResourceType type)
	{
		boolean threeToOne = false;
		ArrayList<City> allCities = GameManager.getSingleton().getModel().getMap().getcities();
		ArrayList<Settlement> allSettlements = GameManager.getSingleton().getModel().getMap().getsettlements();
		ArrayList<Port> allPorts = GameManager.getSingleton().getModel().getMap().getPorts();

		for(int i= 0; i < allCities.size(); i++)
		{
			//if(allCities.get(i).)
		}

		for(int i= 0; i < allSettlements.size(); i++)
		{
			//if(allCities.get(i).)
		}


		if(threeToOne)
		{
			return 3;
		}
		return 4;
	}



}

