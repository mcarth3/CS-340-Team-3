package model;

import model.VertexObject;

import shared.locations.VertexLocation;

/**
 * The city class represents a city object.
 * <br><b>Domain:</b> The owner index must be between 0 and 4
 * @author Jesse McArthur
 */
public class City 
{
	int owner;
	VertexLocation location;
	public City(VertexLocation vertexLocation, int player)
	{
		owner = player;
		location = vertexLocation;
	}
	
	public int getOwner() 
	{
		return owner;
	}
	public void setOwner(int owner) 
	{
		this.owner = owner;
	}
	public VertexLocation getVertextLocation()
	{
		return location;
	}
	public void setLocation(VertexLocation location) {
		this.location = location;
	}
	public boolean canPlaceAtLocation(VertexLocation location)
	{
		return true;
	}
	public void placeCity()
	{
		
	}
}
