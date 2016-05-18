package model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import model.bank.ResourceList;
import poller.modeljsonparser.AbstractModelPartition;
import proxy.RealProxy;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.locations.*;

/**
 * @author Jesse McArthur
 */

public class map extends AbstractModelPartition {
	TreeMap<HexLocation,Hex> hexes;
	ArrayList<Road> roads;
	ArrayList<City> cities;
	ArrayList<Settlement> settlements;
	//	ArrayList<VertexObject> buildings;
//	ArrayList<ResourceList> resources;
	int radius;
	ArrayList<Port> ports;
	Robber robber;
	public map()
	{
		hexes = new TreeMap<HexLocation,Hex>();
		ports = new ArrayList<Port>();
		roads = new ArrayList<Road>();
		robber = new Robber();
		cities = new ArrayList<City>();
		settlements = new ArrayList<Settlement>();
		radius = -1;
	}
	


	public map(TreeMap<HexLocation,Hex> newhexes,ArrayList<Port> newports,ArrayList<Road> newroads, ArrayList<City> newcities,ArrayList<Settlement> newsettlements, int newradius,Robber newrobber)
	{//to prevent problems, we intitialize array lists if they come back null
		if (newhexes !=null){
			hexes = newhexes;
		}else{
			hexes = new TreeMap<HexLocation,Hex>();
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
	public void clearHexes(){
		hexes.clear();
	}
	public void clearBuildings(){
		cities.clear();
		settlements.clear();
	}


	/**
	 * initialize a new map when game is created
	 */
	public void initialize() {}



	/**
	 * checks to see if hex can be added
	 */
	public boolean canAddHex()
	{
		if (hexes.size() > 32)
		{
			return false;
		}
		return true;

	}//may not be used


	public void addHexDesert(int x, int y) throws FailureToAddException//may not be used
	{
		Hex hex = new Hex(x,y);
		hexes.put(hex.getLocation(), hex);
	}

	/**
	 * adds a hex to the maps list of hexes
	 *
	 * @param x        - horizontal location of hex
	 * @param y        - diagonal location of hex
	 * @param resource - type of resource obtained from hex
	 * @param number   - index of owner
	 */
	public void addHex(int x, int y, String resource, int number) throws FailureToAddException//may not be used
	{

		String numberString = new String(Integer.toString(number));
		Hex hex = new Hex(x,y,resource,number);
		hexes.put(hex.getLocation(), hex);
	}

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
		
	//	for (VertexObject VObjIter: buildings)
	//	{
	//		if (VObjIter.getLocation() == settlementLocation)
	//		{
	//			return false;
	//		}
	//	}
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
	
	public boolean canRelocateRobber(HexLocation targetHex)
	{
		
		if (hexes.get(targetHex).resource == "Ocean" || hexes.get(targetHex).resource == "Sea")
		{
			return false;
		}
		return true;
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
	public TreeMap<HexLocation, Hex> getHexes() {
		return hexes;
	}

	public void setHexes(TreeMap<HexLocation, Hex> hexes) {
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

	public ArrayList<City> getCities() {
		return cities;
	}
	public ArrayList<Settlement> getSettlements() {
		return settlements;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}
	public void setSettlements(ArrayList<Settlement> settlements) {
		this.settlements = settlements;
	}

	//public ArrayList<ResourceList> getResources() {
	//	return resources;
	//}

	//public void setResources(ArrayList<ResourceList> resources) {
	//	this.resources = resources;
	//}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Robber getRobber() {
		return robber;
	}

	public void setRobber(Robber robber) {
		this.robber = robber;
	}
}
