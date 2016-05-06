package model;

import shared.locations.EdgeLocation;
/**The road is an item held by the player. It can be placed on the board is specific locations. 
 * Interacts with players, the bank and the board. 
 * <br><b>Domain:</b> 0-15, there can be no more than 15 roads
 * @author Jesse McArthur
 */
public class Road {
 
  

	  int owner;
	  EdgeLocation location;
	  

	  public Road(EdgeLocation edgeLocation, int id)
	  {
		  
	  }
	  public int getOwner()
	  {
		  return owner;
	  }

	  public void setOwner(int owner)
	  {
		  this.owner = owner;
	  }

	  public EdgeLocation getLocation()
	  {
		  return location;
	  }

	  public void setLocation(EdgeLocation location) 
	  {
		  this.location = location;
	  }


}
