package model;

import shared.locations.VertexLocation;

/**
 * Another piece owned by the player. Interacts with the player and the board. Must be purchased to be placed on the board.
 * <br><b>Domain:</b> 0-5 number of settlements 
 * @author Jesse McArthur
 */
public class Settlement extends VertexObject 
{
	VertexLocation vertextLocation;
	
	public Settlement(VertexLocation vertexLocation, int player)
	{
		owner = player;
		this.vertextLocation =vertexLocation;
	}
	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public VertexLocation getVertextLocation() {
		return vertextLocation;
	}

	public void setVertextLocation(VertexLocation vertextLocation) {
		this.vertextLocation = vertextLocation;
	}

	public boolean canPlaceAtLocation()  
	{
		return false;
	}
 	
	public void placeSettlement()throws IllegalMoveException
	{
		
	}
	
}