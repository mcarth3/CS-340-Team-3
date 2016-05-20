package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import client.GameManager.GameManager;
import client.base.*;
import client.data.*;
import model.City;
import model.Facade;
import model.Game;
import model.Hex;
import model.Map;
import model.ObjectNotFoundException;
import model.Player;
import model.Port;
import model.Road;
import model.Settlement;
import proxy.RealProxy;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
		initFromModel();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void initFromModel() {
		
		//<temp>
		
//		Random rand = new Random();
//
//		for (int x = 0; x <= 3; ++x) {
//			
//			int maxY = 3 - x;			
//			for (int y = -3; y <= maxY; ++y) {				
//				int r = rand.nextInt(HexType.values().length);
//				HexType hexType = HexType.values()[r];
//				HexLocation hexLoc = new HexLocation(x, y);
//				getView().addHex(hexLoc, hexType);
//				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.N),
//						CatanColor.RED);
//				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SW),
//						CatanColor.BLUE);
//				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.S),
//						CatanColor.ORANGE);
//				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
//				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
//			}
//			
//			if (x != 0) {
//				int minY = x - 3;
//				for (int y = minY; y <= 3; ++y) {
//					int r = rand.nextInt(HexType.values().length);
//					HexType hexType = HexType.values()[r];
//					HexLocation hexLoc = new HexLocation(-x, y);
//					getView().addHex(hexLoc, hexType);
//					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.N),
//							CatanColor.RED);
//					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SW),
//							CatanColor.BLUE);
//					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.S),
//							CatanColor.ORANGE);
//					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
//					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
//				}
//			}
//		}
//		
//		PortType portType = PortType.BRICK;
//		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.N), portType);
//		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.S), portType);
//		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NE), portType);
//		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SE), portType);
//		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SW), portType);
//		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.N), portType);
//		
//		getView().placeRobber(new HexLocation(0, 0));
//		
//		getView().addNumber(new HexLocation(-2, 0), 2);
//		getView().addNumber(new HexLocation(-2, 1), 3);
//		getView().addNumber(new HexLocation(-2, 2), 4);
//		getView().addNumber(new HexLocation(-1, 0), 5);
//		getView().addNumber(new HexLocation(-1, 1), 6);
//		getView().addNumber(new HexLocation(1, -1), 8);
//		getView().addNumber(new HexLocation(1, 0), 9);
//		getView().addNumber(new HexLocation(2, -2), 10);
//		getView().addNumber(new HexLocation(2, -1), 11);
//		getView().addNumber(new HexLocation(2, 0), 12);
		
		//</temp>
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return Facade.getSingleton().canBuildRoad(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer(), edgeLoc);

	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}
	public void update() {   
		System.out.print(GameManager.getSingleton().getModel().getPlayers().get(0).getColor());
		
		//if we need to get our player's turn - if(model.getTurnTracker().getCurrentPlayer() == manager.getthisplayer().getPlayerIndex()) {
    	GameManager gm = GameManager.getSingleton();

        Game game = gm.getModel();
        Map map = game.getmap();
        Hex[] hexs = map.getHexes();
        ArrayList<Port> ports = map.getPorts();
        ArrayList<Road> roads = map.getRoads();
        ArrayList<Settlement> set = map.getsettlements();
        ArrayList<City> cities = map.getcities();
        ArrayList<model.Player> players = game.getPlayers();
        
 
        for (Hex h : hexs){
        	HexType hexType = null; 
        	hexType = getHexType(h.getResource());

        	//h.getNumber()
        	HexLocation hexLoc = h.getLocation();
        	getView().addHex(hexLoc, hexType);
        	System.out.println(h.getNumber()); 
        	getView().addNumber(hexLoc, 8);
        	
        }   
        
    	getView().addHex(new HexLocation(-3, 0), HexType.WATER);
    	getView().addHex(new HexLocation(-2, -1), HexType.WATER);
    	getView().addHex(new HexLocation(-1, -2), HexType.WATER);
    	getView().addHex(new HexLocation(0, -3), HexType.WATER);
    	getView().addHex(new HexLocation(1, -3), HexType.WATER);
    	getView().addHex(new HexLocation(2, -3), HexType.WATER);
    	getView().addHex(new HexLocation(3, -3), HexType.WATER);
    	getView().addHex(new HexLocation(3, -2), HexType.WATER);
    	getView().addHex(new HexLocation(3, -1), HexType.WATER);
    	getView().addHex(new HexLocation(3, 0), HexType.WATER);
    	getView().addHex(new HexLocation(2, 1), HexType.WATER);
    	getView().addHex(new HexLocation(1, 2), HexType.WATER);
    	getView().addHex(new HexLocation(0, 3), HexType.WATER);
    	getView().addHex(new HexLocation(-1, 3), HexType.WATER);
    	getView().addHex(new HexLocation(-2, 3), HexType.WATER);
    	getView().addHex(new HexLocation(-3, 3), HexType.WATER);
    	getView().addHex(new HexLocation(-3, 2), HexType.WATER);
    	getView().addHex(new HexLocation(-3, 1), HexType.WATER);
        
        for(int a=0; a<ports.size(); a++){
        	//System.out.println(ports.get(a)); 
        	HexLocation hexLoc = ports.get(a).getLocation(); 
        	getView().addPort(new EdgeLocation(hexLoc, ports.get(a).getDirection()), getPortType(ports.get(a).getResource()));
        }
        
		for (int a=0; a<set.size(); a++) {
			CatanColor color = CatanColor.BLUE;
			try {
				color = CatanColor.toColor(GameManager.getSingleton().getModel().findPlayerbyid(set.get(a).getOwner()).getColor());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
			getView().placeSettlement(set.get(a).getVertextLocation(), color);
			
		}

		for (int a=0; a<cities.size(); a++) {
			CatanColor color= CatanColor.BLUE;
			try {
				color = CatanColor.toColor(GameManager.getSingleton().getModel().findPlayerbyid(cities.get(a).getOwner()).getColor());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
			getView().placeCity(cities.get(a).getVertextLocation(), color);
		}

		for (int a=0; a<roads.size(); a++) {
			CatanColor color = CatanColor.toColor(players.get(roads.get(a).getOwner()).getColor());
			getView().placeRoad(roads.get(a).getLocation(), color);
		}

		getView().placeRobber(map.getRobber().getHl());
        
        //THIS IS JUST TEMPORARY
        if (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("Robbing")){
        	RealProxy.getSingleton().robPlayer(1, 1, new HexLocation(0, 0));
        }
    }
	
    public HexType getHexType(String str){
    	HexType result = HexType.BRICK;
    	if(str.equals("brick")){
    		result = HexType.BRICK;
    	}
    	else if(str.equals("DESERT")){
    		result = HexType.DESERT;
    	}
    	else if(str.equals("ore")){
    		result = HexType.ORE;
    	}
    	else if(str.equals("sheep")){
    		result = HexType.SHEEP;
    	}
    	else if(str.equals("water")){
    		result = HexType.WATER;
    	}
    	else if(str.equals("wheat")){
    		result = HexType.WHEAT;
    	}
    	else if(str.equals("wood")){
    		result = HexType.WOOD;
    	}
    	return result; 
    }
    public PortType getPortType(String str){
    	PortType result = PortType.BRICK;
	    if(str != null){
    		if(str.equals("brick")){
	    		result = PortType.BRICK;
	    	}
	    	else if(str.equals("ore")){
	    		result = PortType.ORE;
	    	}
	    	else if(str.equals("sheep")){
	    		result = PortType.SHEEP;
	    	}
	    	else if(str.equals("three")){
	    		result = PortType.THREE;
	    	}
	    	else if(str.equals("wheat")){
	    		result = PortType.WHEAT;
	    	}
	    	else if(str.equals("wood")){
	    		result = PortType.WOOD;
	    	}
    	}
	    else{
	    	result = PortType.THREE; 
	    }
    	return result; 
    }
	
}
