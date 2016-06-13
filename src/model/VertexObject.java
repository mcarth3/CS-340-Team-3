package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.locations.VertexLocation;

/**
 * @author Jesse McArthur
 */
public abstract class VertexObject extends AbstractModelPartition {
//	  int owner;
//	  VertexLocation vertexLocation;

	/**
	   *@param newowner
	   *@param newvertexlocation 
	   * VertexObject - an object to compareTo
	   * return if the location is being used already
	   */

	public VertexObject(int newowner, VertexLocation newvertexlocation) {
		// owner = newowner;
		// vertexLocation = newvertexlocation;
	}
//	   
//	   boolean compareTo(VertexObject vo)
//	   {
//		   return false;
//	   }
//
//	   public int getOwner() 
//	   {
//		   return owner;
//	   }
//
//	   public void setOwner(int owner) 
//	   {
//		   this.owner = owner;
//	   }
//
//	   public VertexLocation getLocation() 
//	   {
//		   return vertexLocation;
//	   }
//
//	   public void setLocation(VertexLocation location) 
//	   {
//		   this.vertexLocation = location;
//	   }
//	   
}
