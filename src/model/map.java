package model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import model.bank.ResourceList;
import poller.modeljsonparser.AbstractModelPartition;
import proxy.RealProxy;
import shared.definitions.HexType;
import shared.locations.*;

/**
 * @author Jesse McArthur
 */

public class Map extends AbstractModelPartition {
	Hex[] hexes;

	ArrayList<Road> roads;
	ArrayList<VertexObject> buildings;
	//ArrayList<ResourceList> resources;
	ArrayList<City> cities;
	ArrayList<Settlement> settlements;
	int radius;
	ArrayList<Port> ports;
	Robber robber;
	public Map()
	{
		hexes = new Hex[19];
		ports = new ArrayList<Port>();
		roads = new ArrayList<Road>();
		robber = new Robber();
		settlements = new ArrayList<Settlement>();
		cities = new ArrayList<City>();
		radius = -1;
	}
	


	public Map(Hex[] newhexes,ArrayList<Port> newports,ArrayList<Road> newroads, ArrayList<City> newcities,ArrayList<Settlement> newsettlements, int newradius,Robber newrobber)
	{//to prevent problems, we intitialize array lists if they come back null
		if (newhexes !=null){
			hexes = newhexes;
		}else{
			hexes = new Hex[19];
		}
		if (newports !=null){
			ports = newports;
		}else{
			ports = new ArrayList<Port>();
		}
		if (newroads !=null){
			roads = newroads;
		}else{
			roads = new ArrayList<Road>();
		}
		if (newcities !=null){
			cities = newcities;
		}else{
			cities = new ArrayList<City>();
		}
		
		if (newsettlements !=null){
			settlements = newsettlements;
		}else{
			settlements = new ArrayList<Settlement>();
		}
		robber = newrobber;
		radius = newradius;
	}
//	public void clearHexes(){
//		hexes.clear();
//	}
	public void clearcities(){
		cities.clear();
	}
	public void clearsettlements(){
		settlements.clear();
	}


	/**
	 * initialize a new map when game is created
	 */
	public void initialize() {}



	/**
	 * checks to see if hex can be added
	 */
//	public boolean canAddHex()
//	{
//		if (hexes.size() > 32)
//		{
//			return false;
//		}
//		return true;

//	}//may not be used


	//public void addHexDesert(int x, int y) throws FailureToAddException//may not be used
	//{
	//	Hex hex = new Hex(x,y);
		//hexes.put(hex.getLocation(), hex);
	//}

	/**
	 * adds a hex to the maps list of hexes
	 *
	 * @param x        - horizontal location of hex
	 * @param y        - diagonal location of hex
	 * @param resource - type of resource obtained from hex
	 * @param number   - index of owner
	 */
//	public void addHex(int x, int y, String resource, int number) throws FailureToAddException//may not be used
//	{

//		String numberString = new String(Integer.toString(number));
//		Hex hex = new Hex(x,y,resource,number);
	//	hexes.put(hex.getLocation(), hex);
//	}

	/**
	 * checks to see if port can be added
	 */
	public boolean canAddPort(Port port)
	{
		if(port == null)
		{
			return false;
		}
		if(ports.contains(port))
		{
			return false;
		}
		return true;
	}

	/**
	 * adds a port to the maps list of ports
	 * @pre No port is there
	 * @post port is initialized
	 * @param x         - horizontal location of hex related to port
	 * @param y         - diagonal location of hex related to port
	 * @param resource  - type of resource obtained from hex
	 * @param ed - direction from hex the port is located
	 * @param ratio     - the ratio of resources tradeable (i.e 1:2, 1:4)
	 */
	public void addPort(int x, int y, String resource, EdgeDirection ed, int ratio) throws FailureToAddException
	{
		Port port = new Port(x,y,resource, ed, ratio);
		ports.add(port);
	}

	/**
	 * checks to see if road can be added
	 */
	public boolean canAddRoad(EdgeLocation edgeLocation)
	{
		if (edgeLocation == null)
		{
			return false;
		}
		if (roads.contains(edgeLocation))
		{
			return false;
		}
		return true;
	}

	/**
	 * adds a road to the maps list of roads
	 *
	 * @param x         - horizontal location of hex
	 * @param y         - diagonal location of hex
	 * @param direction - direction from hex that road is located
	 * @param      - index of owner
	 */
	public void addRoad(int x, int y, EdgeDirection direction, int pid) throws FailureToAddException
	{
		HexLocation hexLocation = new HexLocation(x,y);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation,direction);
		Road road = new Road(edgeLocation,pid);
		roads.add(road);
	}

	/**
	 * checks to see if settlement can be added
	 */
	public boolean canAddSettlement(VertexLocation settlementLocation)
	{
		if (settlementLocation == null)
		{
			return false;
		}
		
		for (VertexObject VObjIter: cities)
		{
			if (VObjIter.getLocation() == settlementLocation)
			{
				return false;
			}
		}
		for (VertexObject VObjIter: settlements)
		{
			if (VObjIter.getLocation() == settlementLocation)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * adds a settlement to the maps list of settlements
	 *
	 * @param x         - horizontal location of hex
	 * @param y         - diagonal location of hex
	 * @param direction - direction from hex that settlement is located
	 * @param      - index of owner
	 */
	public void addSettlement(int x, int y, VertexDirection direction, int pid) throws FailureToAddException
	{
		HexLocation hex = new HexLocation(x,y);
		VertexLocation location =  new VertexLocation(hex, direction);
		Settlement settlement = new Settlement(location,pid);
		settlements.add(settlement);
	}

	/**
	 * checks to see if City can be added
	 */
	public boolean canAddCity(VertexLocation vertexLocation)
	{
		if (vertexLocation == null)
		{
			return false;
		}
		
		for (VertexObject VObjIter: cities)
		{
			if (VObjIter.getLocation() == vertexLocation)
			{
				return false;
			}
		}
		for (VertexObject VObjIter: settlements)
		{
			if (VObjIter.getLocation() == vertexLocation)
			{
				return false;
			}
		}
		//used to loop through buildings and use 	'if (VObjIter.getLocation() == vertexLocation && !(VObjIter instanceof VertexObject))'

		return true;
	}

	/**
	 * adds a city to the maps list of cities
	 *
	 * @param x         - horizontal location of hex
	 * @param y         - diagonal location of hex
	 * @param direction - direction from hex that city is located
	 * @param      - index of owner
	 */
	public void addCity(int x, int y, VertexDirection direction, int pid) throws FailureToAddException
	{
		HexLocation hex = new HexLocation(x,y);
		VertexLocation location =  new VertexLocation(hex, direction);
		City city = new City(location, pid);
		cities.add(city);
	}

	/**
	 * checks to see if robber can be relocated
	 */
	public ArrayList<VertexObject> getVObjectsAroundHexlocation(HexLocation landing)
	{
		HexLocation landingSW = new HexLocation(landing.getX() - 1, landing.getY() + 1);
		HexLocation landingS = new HexLocation(landing.getX(), landing.getY() + 1);
		HexLocation landingSE = new HexLocation(landing.getX() + 1, landing.getY());
		ArrayList<VertexObject> returningBuildings = new ArrayList<VertexObject>();

		for(VertexObject vertex : buildings)
		{
			HexLocation hl = vertex.getLocation().getNormalizedLocation().getHexLoc();
			if(hl.compareTo(landing) == 0)
			{
				VertexDirection vd = vertex.getLocation().getNormalizedLocation().getDir();
				if(vd == VertexDirection.East || vd == VertexDirection.NorthEast || vd == VertexDirection.NorthWest || vd == VertexDirection.West)
					returningBuildings.add(vertex);
			}
			else if(hl.compareTo(landingSW) == 0)
			{
				VertexDirection vd = vertex.getLocation().getNormalizedLocation().getDir();
				if(vd == VertexDirection.East || vd == VertexDirection.NorthEast)
					returningBuildings.add(vertex);
			}
			else if(hl.compareTo(landingS) == 0)
			{
				VertexDirection vd = vertex.getLocation().getNormalizedLocation().getDir();
				if(vd == VertexDirection.NorthEast || vd == VertexDirection.NorthWest)
					returningBuildings.add(vertex);
			}
			else if(hl.compareTo(landingSE) == 0)
			{
				VertexDirection vd = vertex.getLocation().getNormalizedLocation().getDir();
				if(vd == VertexDirection.NorthWest || vd == VertexDirection.West)
					returningBuildings.add(vertex);
			}
		}
		return returningBuildings;
	}

	public ArrayList<Port> checkForPorts(ArrayList<VertexObject> builds)
	{	
		ArrayList<Port> playerPorts = new ArrayList<Port>();
		for(int i=0; i<ports.size(); i++)
		{
			for(int j=0; j<builds.size(); j++)
			{
				if(ports.get(i).getLocation().equals(builds.get(j).getLocation().getHexLoc()) && ports.get(i).getDirection().equals(builds.get(j).getLocation().getDir()))
				{
					playerPorts.add(ports.get(i));
				}
			}
		}
		return playerPorts;
	}
	/**
	 * moves robber to a new hex location
	 *
	 * @param  - horizontal location of hex robber is to be moved to
	 * @param  - diagonal location of hex robber is to be moved to
	 */

	public void relocateRober(HexLocation targetHex)
	{
		
		robber.setHl(targetHex);
	}
	//getters and setters
	public Hex[] getHexes() {
		return hexes;
	}

	public void setHexes(Hex[] hexes) {
		this.hexes = hexes;
	}

	public ArrayList<Port> getPorts() {
		return ports;
	}

	/**
	 * Returns all of the ports that belong to a player
	 * @param playerID
	 * @return
	 */
	public List<Port> getPlayerPorts(int playerID)
	{
		List<Port> playerPorts = new ArrayList<Port>();
		for(Port port: ports)
		{
			if(port.getOwner() == playerID)
			{
				playerPorts.add(port);
			}
		}
		return playerPorts;
	}
	public void setPorts(ArrayList<Port> ports) {
		this.ports = ports;
	}

	public ArrayList<Road> getRoads() {
		return roads;
	}

	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}



	public int getRadius() {
		return radius;
	}

	public ArrayList<City> getcities() {
		return cities;
	}

	public ArrayList<Settlement> getsettlements() {
		return settlements;
	}
	public void setcities(ArrayList<City> cities) {
		this.cities = cities;
	}
	public void setsettlements(ArrayList<Settlement> settlements) {
		this.settlements = settlements;
	}

//	public ArrayList<ResourceList> getResources() {
//		return resources;
//	}

//	public void setResources(ArrayList<ResourceList> resources) {
//		this.resources = resources;
//	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Robber getRobber() {
		return robber;
	}

	public void setRobber(Robber robber) {
		this.robber = robber;
	}


	public boolean canPlaceRoadSetup(EdgeLocation el)
	{

		if (el == null)
		{
			return false;
		}
		for(int i = 0; i < buildings.size(); i++)
		{
			if(placable(el.getNormalizedLocation(), buildings.get(i).getLocation().getNormalizedLocation()))
				return false;
		}
	


		roads.add(new Road(el, Facade.getInstance().getCurrentPlayer().getPlayerIndex()));
		if(extraPlacable(el.getNormalizedLocation()))
		{
			roads.remove(roads.size() - 1);
			return true;
		}


		return false;
	}
	public boolean extraPlacable(EdgeLocation el)
	{
		if(buildings.size() == 0)
			return true;
		System.out.println(el.getDir());
		VertexLocation vl, vl2, vl3, vl4;
		switch(el.getDir())
		{
			//NW NE N
			case NW:
				vl = new VertexLocation(el.getHexLoc(), VertexDirection.West);
				if(canAddSettlement(vl))
					return true;
				vl2 = new VertexLocation(el.getHexLoc(), VertexDirection.NorthWest);
				if(canAddSettlement(vl2))
					return true;
				vl3 = new VertexLocation(new HexLocation(el.getHexLoc().getX() - 1, el.getHexLoc().getY()), VertexDirection.East);
				if(canAddSettlement(vl3))
					return true;
				vl4 = new VertexLocation(new HexLocation(el.getHexLoc().getX()  - 1, el.getHexLoc().getY() + 1), VertexDirection.NorthEast);
				if(canAddSettlement(vl4))
					return true;
				return false;
			case NE:
				vl = new VertexLocation(el.getHexLoc(), VertexDirection.East);
				if(canAddSettlement(vl))
					return true;
				vl2 = new VertexLocation(el.getHexLoc(), VertexDirection.NorthEast);
				if(canAddSettlement(vl2))
					return true;
				vl3 = new VertexLocation(new HexLocation(el.getHexLoc().getX()+1, el.getHexLoc().getY() - 1), VertexDirection.West);
				if(canAddSettlement(vl3))
					return true;
				vl4 = new VertexLocation(new HexLocation(el.getHexLoc().getX()+1, el.getHexLoc().getY()), VertexDirection.NorthWest);
				if(canAddSettlement(vl4))
					return true;
				return false;
			default:
				vl = new VertexLocation(el.getHexLoc(), VertexDirection.NorthEast);
				if(canAddSettlement( vl))
					return true;
				vl2 = new VertexLocation(el.getHexLoc(), VertexDirection.NorthWest);
				if(canAddSettlement(vl2))
					return true;
				vl3 = new VertexLocation(new HexLocation(el.getHexLoc().getX() - 1, el.getHexLoc().getY()), VertexDirection.East);
				if(canAddSettlement(vl3))
					return true;
				vl4 = new VertexLocation(new HexLocation(el.getHexLoc().getX() + 1, el.getHexLoc().getY() - 1), VertexDirection.West);
				if(canAddSettlement(vl4))
					return true;
				return false;
		}

	}
	public boolean oceanPlacable(VertexLocation vl) {
		int x = vl.getHexLoc().getX();
		int y = vl.getHexLoc().getY();

		if (x == 3 && vl.getDir() == VertexDirection.NorthWest) {
			if (y == -3)
				return false;
			return true;
		}
		else if (x == -3 && vl.getDir() == VertexDirection.NorthEast)
		{
			if(y == 0)
				return false;
			return true;
		}
		else if(y < 1)
			return false;
		else if(x == -3)
			return false;
		return true;

	}
	public boolean roadOceanPlayable(EdgeLocation el)
	{
		int x = el.getHexLoc().getX();
		int y = el.getHexLoc().getY();

		//System.out.println(el.getDir() + " " + x + " " + y);
		if(y <= 0)
		{
			if(x == 3 && el.getDir() == EdgeDirection.NW)
			{
				if( y != -3)
					return true;
				return false;
			}
			return false;
		}
		if(x == -3 && el.getDir() == EdgeDirection.NE)
			return true;
		if(y == 3)
		{
			if(el.getDir() == EdgeDirection.NE && x != 0)
				return true;
			if((el.getDir() == EdgeDirection.N) && x != -3)
				return true;
			if(x == 0 && el.getDir() == EdgeDirection.N)
				return true;

			return false;
		}
		if(x == 1 && y == 2)
		{
			if(el.getDir() == EdgeDirection.N || el.getDir() == EdgeDirection.NW)
				return true;
			return false;
		}
		if(x == 2 && y == 1)
		{
			if(el.getDir() == EdgeDirection.N || el.getDir() == EdgeDirection.NW)
				return true;
			return false;
		}
		return false;
	}
	public boolean placable(EdgeLocation roadDir, VertexLocation settlementDir)
	{
		roadDir = roadDir.getNormalizedLocation();
		settlementDir = settlementDir.getNormalizedLocation();
		VertexDirection sed = settlementDir.getDir();
		HexLocation roadHex = roadDir.getHexLoc();
		HexLocation settHex = settlementDir.getHexLoc();
		HexLocation hl;
		HexLocation hl2;


		switch(roadDir.getDir())
		{
			case NW:
				if (roadHex.compareTo(settHex) == 0 && (sed == VertexDirection.NorthWest || sed == VertexDirection.West))
					return true;
				hl = new HexLocation(roadDir.getHexLoc().getX() - 1, roadDir.getHexLoc().getY() + 1);
				if(sed == VertexDirection.NorthEast && settHex.compareTo(hl) == 0)
					return true;
				hl2 = new HexLocation(roadDir.getHexLoc().getX() - 1, roadDir.getHexLoc().getY());
				if(sed == VertexDirection.East && settHex.compareTo(hl2) == 0)
					return true;
				return false;
			case NE:
				if (roadHex.compareTo(settHex) == 0 && (sed == VertexDirection.NorthEast || sed == VertexDirection.East))
					return true;
				hl = new HexLocation(roadDir.getHexLoc().getX()+1, roadDir.getHexLoc().getY());
				if(sed == VertexDirection.NorthWest && settHex.compareTo(hl) == 0)
					return true;
				hl2 = new HexLocation(roadDir.getHexLoc().getX() + 1, roadDir.getHexLoc().getY() - 1);
				if(sed == VertexDirection.West && settHex.compareTo(hl2) == 0)
					return true;
				return false;
			case N:
				if (roadHex.compareTo(settHex) == 0 && (sed == VertexDirection.NorthWest || sed == VertexDirection.NorthEast))
					return true;
				hl = new HexLocation(roadDir.getHexLoc().getX() - 1, roadDir.getHexLoc().getY());
				if(sed == VertexDirection.East && settHex.compareTo(hl) == 0)
					return true;
				hl2 = new HexLocation(roadDir.getHexLoc().getX() + 1, roadDir.getHexLoc().getY() - 1);
				if(sed == VertexDirection.West && settHex.compareTo(hl2) == 0)
					return true;
				return false;
		}

//		System.out.println("Oops " + settlementDir);

		return false;
	}



	public boolean canRelocateRobber(HexLocation hl) {
		
		return false;
	}


//	public static boolean findSettlement(VertexLocation location) {
 //      for(int i=0; i < settlements.size(); i++)
//	    {
//	    	if(settlementArrayList.get(i).getVertextLocation() == theLocation)
//	        {
//	             return true;
//	        }
//	    }
//	        return false;
        //throw new ObjectNotFoundException("failed to find settlement of location !");
    

//	}
}
