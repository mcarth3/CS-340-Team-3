package model;



import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.locations.*;
/**
 * The map class represents the game board and everything on it. This includes hexes,
 * ports, roads, settlements, cities, the radius of the board, and the robber.
 * @author Jesse McArthur
 */
public class Map
{
	ArrayList<Hex>hexes;
	ArrayList<Port> ports;
	ArrayList<Road> roads;
	ArrayList<VertexObject> buildings;// that replace settlements and cities.
	int radius = -1;
	Robber robber;
	
	public Map()
	{
		hexes = new ArrayList<Hex>();
		ports = new ArrayList<Port>();
		roads = new ArrayList<Road>();
		robber = new Robber();
		buildings = new ArrayList<VertexObject>();
		
	}

	public void clearHexes(){
		
	}
	public void clearBuildings(){
		
	}


	/**
	 * initialize a new map when game is created
	 * @pre Game map empty	
	 * @post Game map filled ready for play
	 */
	public void initialize() {}



	/**
	 * checks to see if hex can be added
	 */
	public boolean canAddHex()
	{
		return true;

	}

	public void addHexDesert(int x, int y) throws FailureToAddException//may not be used
	{
		
	}

	/**
	 * adds a hex to the maps list of hexes
	 *
	 * @pre HExes aren't generated	
	 * @post generate hexes
	 * @param x        - horizontal location of hex
	 * @param y        - diagonal location of hex
	 * @param resource - type of resource obtained from hex
	 * @param number   - index of owner
	 */
	public void addHex(int x, int y, String resource, int number) throws FailureToAddException//may not be used
	{

	
	}

	/**
	 * checks to see if port can be added
	 * @pre No ports added
	 * @post ports where necessary
	 */
	public boolean canAddPort(Port port)
	{
		return true;
	}

	/**
	 * adds a port to the maps list of ports
	 * @pre No ports added	
	 * @post ports where necessary
	 * @param x         - horizontal location of hex related to port
	 * @param y         - diagonal location of hex related to port
	 * @param resource  - type of resource obtained from hex
	 * @param ed - direction from hex the port is located
	 * @param ratio     - the ratio of resources tradeable (i.e 1:2, 1:4)
	 */
	public void addPort(int x, int y, String resource, EdgeDirection ed, int ratio) throws FailureToAddException
	{
		
	}

	/**
	 * checks to see if road can be added
	 */
	
	public boolean canAddRoad(EdgeLocation edgeLocation)
	{
		return true;
	}

	/**
	 * adds a road to the maps list of roads
	 * @pre Roads not added	
	 * @post allows user to add road if available space
	 * @param x         - horizontal location of hex
	 * @param y         - diagonal location of hex
	 * @param direction - direction from hex that road is located
	 * @param pid     - index of owner
	 */
	
	public void addRoad(int x, int y, EdgeDirection direction, int pid) throws FailureToAddException
	{
		
	}

	/**
	 * @pre No settlement added	
	 * @post adds settlement if available
	 * checks to see if settlement can be added
	 */
	
	public boolean canAddSettlement(VertexLocation settlementLocation)
	{
		return false;
	}

	/**
	 * adds a settlement to the maps list of settlements
	 * @pre No settlement added	 
	 * @post adds settlement if available
	 * @param x         - horizontal location of hex
	 * @param y         - diagonal location of hex
	 * @param direction - direction from hex that settlement is located
	 * @param pid     - index of owner
	 */
	
	public void addSettlement(int x, int y, VertexDirection direction, int pid) throws FailureToAddException
	{
		
	}

	/**
	 * @pre No city added	
	 * @post Adds city if available
	 * checks to see if City can be added
	 */

	public boolean canAddCity(VertexLocation vertexLocation)
	{
		return true;
	}

	/**
	 * adds a city to the maps list of cities
	 * @pre No city added	
	 * @post Adds city if available
	 * @param x         - horizontal location of hex
	 * @param y         - diagonal location of hex
	 * @param direction - direction from hex that city is located
	 * @param pid    - index of owner
	 */
	
	public void addCity(int x, int y, VertexDirection direction, int pid) throws FailureToAddException
	{
		
	}

	/**
	 * @pre No city added 	
	 * @post adds city if available
	 * checks to see if robber can be relocated
	 */

	public boolean canRelocateRobber(HexLocation targetHex)
	{
		return true;
	}
	public ArrayList<Port> checkForPorts(ArrayList<VertexObject> builds)
	{
		return ports;	
		
	}
	/**
	 * moves robber to a new hex location
	 * @pre no robber added	
	 * @post adds robber of available
	 * @param targetHex - location of hex robber is to be moved to
	 */
	
	public void relocateRober(HexLocation targetHex)
	{
	
	}
	//getters and setters
	public ArrayList<Hex> getHexes() {
		return hexes;
	}

	public void setHexes(ArrayList<Hex> hexes) {
		this.hexes = hexes;
	}

	public ArrayList<Port> getPorts() {
		return ports;
	}

	/**
	 * Returns all of the ports that belong to a player
	 * @pre  Players ports blank	
	 * @post retuns their ports
	 * @param playerID
	 * @return
	 */
	public List<Port> getPlayerPorts(int playerID)
	{
		return ports;
		
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

	public ArrayList<VertexObject> getBuildings() {
		return buildings;
	}

	public void setBuildings(ArrayList<VertexObject> buildings) {
		this.buildings = buildings;
	}

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
