package client.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import client.base.Controller;
import client.data.RobPlayerInfo;
import model.City;
import model.Facade;
import model.Hex;
import model.Map;
import model.ObjectNotFoundException;
import model.Player;
import model.Port;
import model.Road;
import model.Settlement;
import proxy.RealProxy;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {

	private IRobView robView;
	private HexLocation roblocation;
	private boolean firstturnroads = true;
	private boolean firstturnsettlements = false;
	private boolean secondturnroads = true;
	private boolean secondturnsettlements = false;
	private boolean usingSoldier;

	public MapController(IMapView view, IRobView robView) {

		super(view);
		usingSoldier = false;
		setRobView(robView);

	}

	@Override
	public IMapView getView() {

		return (IMapView) super.getView();
	}

	private IRobView getRobView() {
		return robView;
	}

	private void setRobView(IRobView robView) {
		this.robView = robView;
	}

	public void setUp() {
		setTiles();
		setHarbors();
		// road, then settlement. NEXT TURN: road, then settlement
	}

	public void setTiles() {

	}

	public void setHarbors() {

	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		//System.out.println("CAN PLACE ROAD? " + Facade.getSingleton().canBuildRoad(currentplayer, edgeLoc));
		return Facade.getSingleton().canBuildRoad(currentplayer, edgeLoc);

	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return Facade.getSingleton().canBuildSettlement(currentplayer, vertLoc);

	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return Facade.getSingleton().canBuildCity(currentplayer, vertLoc);

	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		roblocation = hexLoc;
		// System.out.println("canPlaceRobber");
		if (hexLoc.getX() == model.getMap().getRobber().getHl().getX()) {
			if (hexLoc.getY() == model.getMap().getRobber().getHl().getY()) {
				return false;
			}
		}
		if (Facade.getSingleton().getWaterHexes().contains(hexLoc)) {
			return false;
		}

		return true;

	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {

		getView().placeRoad(edgeLoc, CatanColor.toColor(thisplayer.getColor()));
		if (state.equals("Playing")) {
			System.out.println("placed road");
			RealProxy.getSingleton().buildRoad(thisplayer.getPlayerIndex(), edgeLoc, false);
		} else if (state.equals("FirstRound")) {
			RealProxy.getSingleton().buildRoad(thisplayer.getPlayerIndex(), edgeLoc, true);
			firstturnsettlements = true;
		} else if (state.equals("SecondRound")) {
			RealProxy.getSingleton().buildRoad(thisplayer.getPlayerIndex(), edgeLoc, true);
			secondturnsettlements = true;
		}
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {

		getView().placeSettlement(vertLoc, CatanColor.toColor(thisplayer.getColor()));
		if (state.equals("Playing")) {
			RealProxy.getSingleton().buildSettlement(thisplayer.getPlayerIndex(), vertLoc, false);
		} else if (state.equals("FirstRound") || state.equals("SecondRound")) {
			RealProxy.getSingleton().buildSettlement(thisplayer.getPlayerIndex(), vertLoc, true);
			RealProxy.getSingleton().finishTurn(thisplayer.getPlayerIndex());
		}
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {

		getView().placeCity(vertLoc, CatanColor.toColor(thisplayer.getColor()));
		RealProxy.getSingleton().buildCity(thisplayer.getPlayerIndex(), vertLoc);
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {

		System.out.println("placeRobber");

		Vector<Settlement> settlements = new Vector<Settlement>();
		Vector<City> cities = new Vector<City>();

		ArrayList<Player> owners = model.getPlayers();
		HashSet<RobPlayerInfo> robbable = new HashSet<RobPlayerInfo>();

		for (Settlement vo : model.getMap().getsettlements()) {
			settlements.add(vo);
		}
		for (City vo : model.getMap().getcities()) {
			cities.add(vo);
		}

		for (Settlement vertex : settlements) {
			ArrayList<HexLocation> hexLocs = vertex.getVertextLocation().getAdjacentHexes();
			for (HexLocation hexloc : hexLocs) {
				if (hexloc.equals(hexLoc)) {
					if (thisplayer.getPlayerIndex() != vertex.getOwner()) {
						RobPlayerInfo player = new RobPlayerInfo();
						player.setId(owners.get(vertex.getOwner()).getPlayerID());
						player.setName(owners.get(vertex.getOwner()).getName());
						player.setColor(owners.get(vertex.getOwner()).getColor());
						player.setPlayerIndex(owners.get(vertex.getOwner()).getPlayerIndex());
						String hexresource = Facade.getSingleton().whichresourceisthishex(hexLoc);
						System.out.println("THIS HEX CONTAINS" + hexresource);
						if (hexresource.equals("wood")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getWood());
						} else if (hexresource.equals("brick")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getBrick());
						} else if (hexresource.equals("sheep")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getSheep());
						} else if (hexresource.equals("wheat")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getWheat());
						} else if (hexresource.equals("ore")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getOre());
						} else {
							player.setNumCards(0);
						}

						robbable.add(player);
					}
				}
			}
		}

		for (City vertex : cities) {
			ArrayList<HexLocation> hexLocs = vertex.getVertextLocation().getAdjacentHexes();
			for (HexLocation hexloc : hexLocs) {
				if (hexloc.equals(hexLoc)) {
					if (thisplayer.getPlayerIndex() != vertex.getOwner()) {
						RobPlayerInfo player = new RobPlayerInfo();
						player.setId(owners.get(vertex.getOwner()).getPlayerID());
						player.setName(owners.get(vertex.getOwner()).getName());
						player.setColor(owners.get(vertex.getOwner()).getColor());
						player.setPlayerIndex(owners.get(vertex.getOwner()).getPlayerIndex());
						String hexresource = Facade.getSingleton().whichresourceisthishex(hexLoc);
						System.out.println("THIS HEX CONTAINS" + hexresource);
						if (hexresource.equals("wood")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getWood());
						} else if (hexresource.equals("brick")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getBrick());
						} else if (hexresource.equals("sheep")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getSheep());
						} else if (hexresource.equals("wheat")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getWheat());
						} else if (hexresource.equals("ore")) {
							player.setNumCards(owners.get(vertex.getOwner()).getResources().getOre());
						} else {
							player.setNumCards(0);
						}
						robbable.add(player);
					}
				}
			}
		}

		RobPlayerInfo[] robbableArray = new RobPlayerInfo[robbable.size()];
		robbable.toArray(robbableArray);

		model.getMap().relocateRober(hexLoc);
		getView().placeRobber(hexLoc);

		if (robbableArray.length == 1) {
			if (robbableArray[0].getPlayerIndex() == thisplayer.playerIndex) {
				RealProxy.getSingleton().robPlayer(thisplayer.playerIndex, -1, roblocation);
				if (usingSoldier) {
					usingSoldier = false;
				}
				if (this.getRobView().isModalShowing()) {
					this.getRobView().closeModal();
				}
			} else {
				getRobView().setPlayers(robbableArray);
				getRobView().showModal();
			}
		} else {
			getRobView().setPlayers(robbableArray);
			getRobView().showModal();
		}

	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		if (!robView.isModalShowing()) {
			System.out.println(pieceType + "!!!");
			getView().startDrop(pieceType, CatanColor.toColor(thisplayer.getColor()), true);
		}
	}

	@Override
	public void cancelMove() {

	}

	@Override
	public void playSoldierCard() {
		// setRobView(robView);
		startMove(PieceType.ROBBER, true, false);
		usingSoldier = true;

	}

	@Override
	public void playRoadBuildingCard() {
		startMove(PieceType.ROAD, true, false);
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// System.out.print("ROBBING PLAYER");
		if (this.getRobView().isModalShowing()) {
			this.getRobView().closeModal();
		}
		if (victim.getPlayerIndex() == thisplayer.playerIndex) {
			RealProxy.getSingleton().robPlayer(thisplayer.playerIndex, -1, roblocation);
		} else {
			RealProxy.getSingleton().robPlayer(thisplayer.playerIndex, victim.getPlayerIndex(), roblocation);
		}
		if (usingSoldier) {
			usingSoldier = false;
		}
	}

	@Override
	public void robPlayer() {
		if (this.getRobView().isModalShowing()) {
			this.getRobView().closeModal();
		}
		RealProxy.getSingleton().robPlayer(thisplayer.playerIndex, -1, roblocation);
	}

	@Override
	public void update() {
		System.out.print("Player Color" + model.getPlayers().get(0).getColor());
		usingSoldier = false;
		Map map = model.getmap();
		Hex[] hexs = map.getHexes();
		ArrayList<Port> ports = map.getPorts();
		ArrayList<Road> roads = map.getRoads();
		ArrayList<Settlement> set = map.getsettlements();
		ArrayList<City> cities = map.getcities();
		ArrayList<model.Player> players = model.getPlayers();

		for (Hex h : hexs) {
			HexType hexType = getHexType(h.getResource());
			HexLocation hexLoc = h.getLocation();
			getView().addHex(hexLoc, hexType);
			if (h.getNumber() != 0) {
				// System.out.print("setting number"+h.getNumber());
				getView().addNumber(hexLoc, h.getNumber());
			}
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

		for (int a = 0; a < ports.size(); a++) {
			// System.out.println(ports.get(a));
			HexLocation hexLoc = ports.get(a).getLocation();
			getView().addPort(new EdgeLocation(hexLoc, ports.get(a).getDirection()), getPortType(ports.get(a).getResource()));
		}

		for (int a = 0; a < set.size(); a++) {

			CatanColor color = CatanColor.BLUE;
			try {
				color = CatanColor.toColor(model.findPlayerbyindex(set.get(a).getOwner()).getColor());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
			getView().placeSettlement(set.get(a).getVertextLocation(), color);

		}

		for (int a = 0; a < cities.size(); a++) {

			CatanColor color = CatanColor.BLUE;
			try {
				color = CatanColor.toColor(model.findPlayerbyindex(cities.get(a).getOwner()).getColor());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
			getView().placeCity(cities.get(a).getVertextLocation(), color);
		}

		for (int a = 0; a < roads.size(); a++) {
			CatanColor color = CatanColor.toColor(players.get(roads.get(a).getOwner()).getColor());
			getView().placeRoad(roads.get(a).getLocation(), color);
		}

		getView().placeRobber(map.getRobber().getHl());

		if ((model.getTurnTracker().getStatus().equals("Robbing")) && (manager.getrobbingready() == true)) {
			if (model.getTurnTracker().getCurrentPlayer() == thisplayer.getPlayerIndex()) {
				manager.setrobbingready(false);
				startMove(PieceType.ROBBER, true, false);
			}
		} else {
			if (this.getRobView().isModalShowing()) {
				this.getRobView().closeModal();
			}
		}

		beginfirstturn();
	}

	public void beginfirstturn() {
		if ((thisplayer.getPlayerIndex() == currentplayer) && manager.getbegin()) {
			if ((state.equals("SecondRound"))) {

				if (secondturnsettlements) {
					System.out.println("SECONDTURN SETTLEMENT");
					startMove(PieceType.SETTLEMENT, true, false);
					secondturnsettlements = false;
				}
				if (secondturnroads) {
					System.out.println("SECONDTURN ROAD");
					startMove(PieceType.ROAD, true, false);
					secondturnroads = false;
				}
			} else if ((state.equals("FirstRound"))) {
				if (firstturnsettlements) {
					System.out.println("FIRSTTURN SETTLEMENT");
					startMove(PieceType.SETTLEMENT, true, false);
					firstturnsettlements = false;
				}
				if (firstturnroads) {
					System.out.println("FIRSTTURN ROAD");
					startMove(PieceType.ROAD, true, false);
					firstturnroads = false;
				}
			}

		}
	}

	public HexType getHexType(String str) {
		HexType result = HexType.BRICK;
		if (str.equals("brick")) {
			result = HexType.BRICK;
		} else if (str.equals("DESERT")) {
			result = HexType.DESERT;
		} else if (str.equals("ore")) {
			result = HexType.ORE;
		} else if (str.equals("sheep")) {
			result = HexType.SHEEP;
		} else if (str.equals("water")) {
			result = HexType.WATER;
		} else if (str.equals("wheat")) {
			result = HexType.WHEAT;
		} else if (str.equals("wood")) {
			result = HexType.WOOD;
		}
		return result;
	}

	public PortType getPortType(String str) {
		PortType result = PortType.BRICK;
		if (str != null) {
			if (str.equals("brick")) {
				result = PortType.BRICK;
			} else if (str.equals("ore")) {
				result = PortType.ORE;
			} else if (str.equals("sheep")) {
				result = PortType.SHEEP;
			} else if (str.equals("three")) {
				result = PortType.THREE;
			} else if (str.equals("wheat")) {
				result = PortType.WHEAT;
			} else if (str.equals("wood")) {
				result = PortType.WOOD;
			}
		} else {
			result = PortType.THREE;
		}
		return result;
	}

}