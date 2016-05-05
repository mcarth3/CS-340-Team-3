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
	
	

	public void clearHexes(){
		
	}
	public void clearBuildings(){
		
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
		
return true;
	}


	public void addHexDesert(int x, int y) 
	{
		
	}

	/**
	 * adds a hex to the maps list of hexes
	 *
	 * @param x        - horizontal location of hex
	 * @param y        - diagonal location of hex
	 * @param resource - type of resource obtained from hex
	 * @param number   - index of owner
	 */
	public void addHex(int x, int y, String resource, int number) 
	{

		
	}

	/**
	 * checks to see if port can be added
	 */
	public boolean canAddPort(Port port)
	{
	  return true;
	}

	/**
	 * adds a port to the maps list of ports
	 *
	 * @param x         - horizontal location of hex related to port
	 * @param y         - diagonal location of hex related to port
	 * @param resource  - type of resource obtained from hex
	 * @param ed - direction from hex the port is located
	 * @param ratio     - the ratio of resources tradeable (i.e 1:2, 1:4)
	 */
	public void addPort(int x, int y, String resource, EdgeDirection ed, int ratio) 
	{
		
	}

	/**
	 * checks to see if road can be added
	 */
	public boolean canAddRoad(Road road)
	
	{
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
	
	public void addRoad(int x, int y, EdgeDirection direction, int pid) 
	{
	
	}


	public boolean canAddSettlement(VertexLocation settlementLocation)
	{
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
	public void addSettlement(int x, int y, VertexDirection direction, int pid) 
	{
		
	}

	/**
	 * checks to see if City can be added
	 */
	
	public boolean canAddCity(VertexLocation vertexLocation)
	{
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
	
	public void addCity(int x, int y, VertexDirection direction, int pid) 
	{
		
	}

	/**
	 * checks to see if robber can be relocated
	 */

	public boolean canRelocateRobber(HexLocation targetHex)
	{
		return true;
	}
	
	/**
	 * moves robber to a new hex location
	 *
	 * @param  - horizontal location of hex robber is to be moved to
	 * @param  - diagonal location of hex robber is to be moved to
	 */
	
	public void relocateRober(HexLocation targetHex)
	{
		
	}



}
