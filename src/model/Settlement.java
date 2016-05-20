package model;

import shared.locations.VertexLocation;

/**
 * Another piece owned by the player. Interacts with the player and the board. Must be purchased to be placed on the board.
 * <br><b>Domain:</b> 0-5 number of settlements 
 * @author Jesse McArthur
 */
public class Settlement 
{
	VertexLocation location;
	Integer owner; 
	
	public Settlement(VertexLocation vertexLocation, Integer player)
	{
		this.owner = player;
		this.location =vertexLocation;
	}
	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public VertexLocation getVertextLocation() {
		return location;
	}

	public void setVertextLocation(VertexLocation vertextLocation) {
		this.location = vertextLocation;
	}

	public boolean canPlaceAtLocation()  
	{
		return false;
	}
 	
	public void placeSettlement()throws IllegalMoveException
	{
		
	}
	
}