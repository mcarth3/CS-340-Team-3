package model;

import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

public abstract class VertexObject{
  int owner;
  VertexLocation vertexLocation;


/**
   *@param VertexObject - an object to compareTo
   *@return if the location is being used already
   */
   boolean compareTo(VertexObject v)
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
