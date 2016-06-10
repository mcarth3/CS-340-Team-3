package client.maritime;

import java.util.ArrayList;

import client.base.Controller;
import model.City;
import model.Facade;
import model.Port;
import model.Settlement;
import model.bank.ResourceList;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	private int pid;
	private Facade theFacade;
	private ResourceType[] getResources;
	private ResourceType[] giveResources;
	private ResourceType theGiving;
	private ResourceType theGetting;
	private int amountGiving;
	private int amountGetting;

	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {

		super(tradeView);

		//pid = thisplayer.getPlayerID();

		setTradeOverlay(tradeOverlay);
	}

	public IMaritimeTradeView getTradeView() {

		return (IMaritimeTradeView) super.getView();
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
		//System.out.println("Making Trade!");
		getTradeOverlay().closeModal();
		theFacade.meritimeTrade(pid, amountGiving, theGiving.toString().toLowerCase(), theGetting.toString().toLowerCase());
		//System.out.println("pid: " + pid);
		//System.out.println("amountGiving: " + amountGiving);
		//System.out.println("The Giving: " + theGiving.toString());
		//System.out.println("The Getting: " + theGetting.toString());
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

	public ResourceType[] getsWithoutGive() {
		ResourceType[] basicResources = new ResourceType[5];
		basicResources[0] = ResourceType.BRICK;
		basicResources[1] = ResourceType.WOOD;
		basicResources[2] = ResourceType.WHEAT;
		basicResources[3] = ResourceType.ORE;
		basicResources[4] = ResourceType.SHEEP;

		ArrayList<ResourceType> theResourcesList = new ArrayList<>();
		for (int i = 0; i < basicResources.length; i++) {
			if (basicResources[i] != theGiving) {
				theResourcesList.add(basicResources[i]);
			}
		}

		ResourceType[] simpleArray = new ResourceType[theResourcesList.size()];
		theResourcesList.toArray(simpleArray);
		return simpleArray;

	}

	@Override
	public void update() {

		theFacade = Facade.getSingleton();
		if (manager != null && thisplayer != null) {
			//if (thePlayer.canOfferBankTrade())
			if (canOfferMaritimeTrade()
					&& currentplayer == thisplayer.getPlayerIndex()) {

				pid = thisplayer.getPlayerIndex();
				//theFacade.getGame().canMaritimeTrade(pid);
				//giveResources = thePlayer.resourcesOverThree();
				giveResources = playerResourcesOverPorts();
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
	public boolean canOfferMaritimeTrade() {
		if ((playerResourcesOverPorts().length > 0) && (currentplayer == thisplayer.getPlayerIndex()) && state.equals("Playing")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * checks if thisPlayer can offer any maritimeTrade at all. If they have the amount needed for a port they've built on,
	 * or have 4 of any, the ResourceType of the necessary type will be added.
	 * @return
	 */
	public ResourceType[] playerResourcesOverPorts() {
		ResourceList resources = thisplayer.getResources();
		ArrayList<ResourceType> resourceList = new ArrayList<ResourceType>();
		if (resources.getBrick() >= checkPossibleAmount(ResourceType.BRICK)) {
			resourceList.add(ResourceType.BRICK);
		}
		if (resources.getWood() >= checkPossibleAmount(ResourceType.WOOD)) {
			resourceList.add(ResourceType.WOOD);
		}
		if (resources.getOre() >= checkPossibleAmount(ResourceType.ORE)) {
			resourceList.add(ResourceType.ORE);
		}
		if (resources.getSheep() >= checkPossibleAmount(ResourceType.SHEEP)) {
			resourceList.add(ResourceType.SHEEP);
		}
		if (resources.getWheat() >= checkPossibleAmount(ResourceType.WHEAT)) {
			resourceList.add(ResourceType.WHEAT);
		}

		ResourceType[] simpleArray = new ResourceType[resourceList.size()];
		resourceList.toArray(simpleArray);
		if (simpleArray == null) {
			simpleArray = new ResourceType[0];
		}
		return simpleArray;

	}

	/**
	 * checks the possible lowest amount a resourceType can be traded for. Default is 0, but if thisPlayer
	 * has cities or settlements next to a port, it may be 2 or 3.
	 * @param type
	 * @return
	 */
	public int checkPossibleAmount(ResourceType type) {
		boolean threeToOne = false;
		boolean twoToOne = false;
		ArrayList<City> allCities = model.getMap().getcities();
		ArrayList<Settlement> allSettlements = model.getMap().getsettlements();
		ArrayList<Port> allPorts = model.getMap().getPorts();
		//TreeSet<ResourceType> theFoundTypes= new TreeSet<>();

		/*for(int i = 0; i < allSettlements.size(); i++)
		{
			if(allSettlements.get(i).getOwner() == thePlayer.getPlayerIndex())
			{
				System.out.println("Settlement normal location: " + allSettlements.get(i).getVertextLocation().getNormalizedLocation());
			}
		}
		for(int j = 0; j < allPorts.size(); j++) {
			ArrayList<VertexLocation> portVertices = allPorts.get(j).getEdgeLocation().getVertices();
		
			for (int v = 0; v < portVertices.size(); v++) {
				System.out.println("Port " + j + " (type: " +
						allPorts.get(j).getResource() + ") normal location: " + portVertices.get(v).getNormalizedLocation());
		
			}
		}*/

		for (int j = 0; j < allPorts.size(); j++) {
			ArrayList<VertexLocation> portVertices = allPorts.get(j).getEdgeLocation().getVertices();
			//System.out.println("Checking matches for port of type " + allPorts.get(j).getResource());
			boolean found = false;
			for (int v = 0; v < portVertices.size(); v++) {

				for (int i = 0; i < allCities.size(); i++) {
					if (allCities.get(i).getOwner() == thisplayer.getPlayerIndex()) {
						String portNormalString = portVertices.get(v).getNormalizedLocation().toString();
						//System.out.println("PortNormal: " + portNormalString);
						String cityNormalString = allCities.get(i).getVertextLocation().getNormalizedLocation().toString();
						//System.out.println("SettlementNormal: ");
						if (portNormalString.equals(cityNormalString)) {
							found = true;
						}
						//if(portVertices.get(v).getNormalizedLocation().equals(allCities.get(i).getVertextLocation().getNormalizedLocation()))
						if (portVertices.get(v).getDir() == allCities.get(i).getVertextLocation().getDir()
								&& portVertices.get(v).getHexLoc().getX() == allCities.get(i).getVertextLocation().getHexLoc().getX() &&
								portVertices.get(v).getHexLoc().getY() == allCities.get(i).getVertextLocation().getHexLoc().getY()) {

						}
					}
				}

				for (int i = 0; i < allSettlements.size(); i++) {
					if (allSettlements.get(i).getOwner() == thisplayer.getPlayerIndex()) {

						String portNormalString = portVertices.get(v).getNormalizedLocation().toString();
						//System.out.println("PortNormal: " + portNormalString);
						String settlementNormalString = allSettlements.get(i).getVertextLocation().getNormalizedLocation().toString();
						//System.out.println("SettlementNormal: ");
						if (portNormalString.equals(settlementNormalString)) {
							found = true;
						}
					}
				}

			}
			if (found) {
				//System.out.println("Port Resource Type: " + allPorts.get(j).getResource());
				if (allPorts.get(j).getResource() == null) {
					threeToOne = true;
					//System.out.println("Three-to-one port owned!");
				} else if (type == stringToRecType(allPorts.get(j).getResource())) {
					twoToOne = true; //could just return 2 if this is too slow.
					//System.out.println("Two-to-one port owned!");
				}
			}
		}

		if (twoToOne) {
			return 2;
		} else if (threeToOne) {
			return 3;
		} else {
			return 4;
		}
	}

	public ResourceType stringToRecType(String s) {
		String low = s.toLowerCase();
		if (low.equals("wood")) {
			return ResourceType.WOOD;
		}
		if (low.equals("brick")) {
			return ResourceType.BRICK;
		}
		if (low.equals("ore")) {
			return ResourceType.ORE;
		}
		if (low.equals("sheep")) {
			return ResourceType.SHEEP;
		} else if (low.equals("wheat")) {
			return ResourceType.WHEAT;
		}

		System.out.println("thread " + Thread.currentThread().getId() + "- \n\n\nInvalid resource Type in MaritimeTradeController, stringToRecType()!!!\n\n\n");
		return ResourceType.BRICK;
	}

}
