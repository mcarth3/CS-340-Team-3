package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
/**
 * @author Jesse McArthur
 */
public abstract class VertexObject extends AbstractModelPartition {
	  int owner;
	  VertexLocation vertexLocation;


	/**
	   *@param VertexObject - an object to compareTo
	   *@return if the location is being used already
	   */
	   public void VertexObject(){
		   owner = 0;
		   vertexLocation = new VertexLocation(new HexLocation(0, 0), VertexDirection.West);
	   }
	   
	   boolean compareTo(VertexObject vo)
	   {
		   return false;
	   }

	   public int getOwner() 
	   {
		   return owner;
	   }

	   public void setOwner(int owner) 
	   {
		   this.owner = owner;
	   }

	   public VertexLocation getLocation() 
	   {
		   return vertexLocation;
	   }

	   public void setLocation(VertexLocation location) 
	   {
		   this.vertexLocation = location;
	   }
	   
	}
