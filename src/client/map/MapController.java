package client.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import client.GameManager.GameManager;
import client.base.Controller;
import client.data.RobPlayerInfo;
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

	public MapController(IMapView view, IRobView robView) {

		super(view);

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
		return Facade.getSingleton().canBuildRoad(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer(), edgeLoc);

	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return Facade.getSingleton().canBuildSettlement(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer(), vertLoc);

	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return Facade.getSingleton().canBuildCity(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer(), vertLoc);

	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		roblocation = hexLoc;
		// System.out.println("canPlaceRobber");
		if (hexLoc.getX() == GameManager.getSingleton().getModel().getMap().getRobber().getHl().getX()) {
			if (hexLoc.getY() == GameManager.getSingleton().getModel().getMap().getRobber().getHl().getY()) {
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

		getView().placeRoad(edgeLoc, CatanColor.toColor(GameManager.getSingleton().getthisplayer().getColor()));
		if (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("Playing")) {
			System.out.println("placed road");
			RealProxy.getSingleton().buildRoad(GameManager.getSingleton().getthisplayer().getPlayerIndex(), edgeLoc, false);
		} else if (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("FirstRound") || GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("SecondRound")) {
			RealProxy.getSingleton().buildRoad(GameManager.getSingleton().getthisplayer().getPlayerIndex(), edgeLoc, true);
		}
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {

		getView().placeSettlement(vertLoc, CatanColor.toColor(GameManager.getSingleton().getthisplayer().getColor()));
		if (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("Playing")) {
			RealProxy.getSingleton().buildSettlement(GameManager.getSingleton().getthisplayer().getPlayerIndex(), vertLoc, false);
		} else if (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("FirstRound") || GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("SecondRound")) {
			RealProxy.getSingleton().buildSettlement(GameManager.getSingleton().getthisplayer().getPlayerIndex(), vertLoc, true);
		}
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {

		getView().placeCity(vertLoc, CatanColor.toColor(GameManager.getSingleton().getthisplayer().getColor()));
		RealProxy.getSingleton().buildCity(GameManager.getSingleton().getthisplayer().getPlayerIndex(), vertLoc);
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {

		System.out.println("placeRobber");

		Vector<Settlement> settlements = new Vector<Settlement>();
		Vector<City> cities = new Vector<City>();

		ArrayList<Player> owners = GameManager.getSingleton().getModel().getPlayers();
		HashSet<RobPlayerInfo> robbable = new HashSet<RobPlayerInfo>();

		for (Settlement vo : GameManager.getSingleton().getModel().getMap().getsettlements()) {
			settlements.add(vo);
		}
		for (City vo : GameManager.getSingleton().getModel().getMap().getcities()) {
			cities.add(vo);
		}

		for (Settlement vertex : settlements) {
			ArrayList<HexLocation> hexLocs = vertex.getVertextLocation().getAdjacentHexes();
			for (HexLocation hexloc : hexLocs) {
				if (hexloc.equals(hexLoc)) {
					if (GameManager.getSingleton().getthisplayer().getPlayerIndex() != vertex.getOwner()) {
						RobPlayerInfo player = new RobPlayerInfo();
						player.setId(owners.get(vertex.getOwner()).getPlayerID());
						player.setName(owners.get(vertex.getOwner()).getName());
						player.setColor(owners.get(vertex.getOwner()).getColor());
						player.setPlayerIndex(owners.get(vertex.getOwner()).getPlayerIndex());
						player.setNumCards(0);
						robbable.add(player);
					}
				}
			}
		}

		for (City vertex : cities) {
			ArrayList<HexLocation> hexLocs = vertex.getVertextLocation().getAdjacentHexes();
			for (HexLocation hexloc : hexLocs) {
				if (hexloc.equals(hexLoc)) {
					if (GameManager.getSingleton().getthisplayer().getPlayerIndex() != vertex.getOwner()) {
						RobPlayerInfo player = new RobPlayerInfo();
						player.setId(owners.get(vertex.getOwner()).getPlayerID());
						player.setName(owners.get(vertex.getOwner()).getName());
						player.setColor(owners.get(vertex.getOwner()).getColor());
						player.setPlayerIndex(owners.get(vertex.getOwner()).getPlayerIndex());
						player.setNumCards(0);
						robbable.add(player);
					}
				}
			}
		}

		RobPlayerInfo[] robbableArray = new RobPlayerInfo[robbable.size()];
		robbable.toArray(robbableArray);

		GameManager.getSingleton().getModel().getMap().relocateRober(hexLoc);
		getView().placeRobber(hexLoc);
		getRobView().setPlayers(robbableArray);
		getRobView().showModal();

	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		if (!robView.isModalShowing()) {
			getView().startDrop(pieceType, CatanColor.toColor(GameManager.getSingleton().getthisplayer().getColor()), true);
		}
	}

	@Override
	public void cancelMove() {

	}

	@Override
	public void playSoldierCard() {
		// setRobView(robView);
		startMove(PieceType.ROBBER, true, false);
	}

	@Override
	public void playRoadBuildingCard() {

	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		if (this.getRobView().isModalShowing()) {
			this.getRobView().closeModal();
		}
		RealProxy.getSingleton().robPlayer(GameManager.getSingleton().getthisplayer().playerIndex, victim.getPlayerIndex(), roblocation);
	}

	@Override
	public void update() {
		System.out.print("Player Color" + GameManager.getSingleton().getModel().getPlayers().get(0).getColor());
		GameManager gm = GameManager.getSingleton();

		Game game = gm.getModel();
		Map map = game.getmap();
		Hex[] hexs = map.getHexes();
		ArrayList<Port> ports = map.getPorts();
		ArrayList<Road> roads = map.getRoads();
		ArrayList<Settlement> set = map.getsettlements();
		ArrayList<City> cities = map.getcities();
		ArrayList<model.Player> players = game.getPlayers();

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
				color = CatanColor.toColor(game.findPlayerbyindex(set.get(a).getOwner()).getColor());
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
			getView().placeSettlement(set.get(a).getVertextLocation(), color);

		}

		for (int a = 0; a < cities.size(); a++) {

			CatanColor color = CatanColor.BLUE;
			try {
				color = CatanColor.toColor(game.findPlayerbyindex(cities.get(a).getOwner()).getColor());
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

		if ((game.getTurnTracker().getStatus().equals("Robbing")) && (GameManager.getSingleton().getrobbingready() == true)) {
			if (game.getTurnTracker().getCurrentPlayer() == GameManager.getSingleton().getthisplayer().getPlayerIndex()) {
				GameManager.getSingleton().setrobbingready(false);
				startMove(PieceType.ROBBER, true, false);
			}
		} else {
			if (this.getRobView().isModalShowing()) {
				this.getRobView().closeModal();
			}
		}

		if ((GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("FirstRound")) || (GameManager.getSingleton().getModel().getTurnTracker().getStatus().equals("SecondRound"))) {
			if (GameManager.getSingleton().getthisplayer().getPlayerIndex() == GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer()) {

				startMove(PieceType.ROAD, true, false);
				startMove(PieceType.SETTLEMENT, true, false);

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
