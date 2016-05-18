package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.HexType;
import shared.locations.HexLocation;
/**
 * The hex class represents a hex on the map. It has a location, resource type, 
 *	and an int.
 * @author Jesse McArthur
 *	
 */
public class Hex extends AbstractModelPartition {
	  HexLocation location;
	  int number;
	  String resource;
	  

  public Hex()//desert tile
  {
    resource = HexType.DESERT.toString();
    number = 0;
  }

  public Hex(int x, int y)
  {
	  location = new HexLocation(x, y);
  }

  public Hex(String resrc, int num)
  {
    resource = resrc.toUpperCase();
    number = num;
  }

  public Hex(String resc, int num, HexLocation HL)
  {
    resource = resc;
    number = num;
    location = HL;
  }

  public Hex(int x, int y, String resc, int num)
  {
	location = new HexLocation(x, y);
    resource = resc;
    number = num;
  }
  //Getters and Setters

  public String getResource() 
  {
	  return resource;
  }

  public void setResource(String resource) 
  {
	  this.resource = resource;
  }

  public int getNumber()
  {
	  return number;
  }

  public void setNumber(int chit)
  {
	  this.number = chit;
  }

  public HexLocation getLocation()  {return location; }
  public void setLocation(HexLocation HL)  {location = HL; }
}

