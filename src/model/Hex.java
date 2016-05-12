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
  String resource;
  int chit;
  HexLocation hl;

  public Hex()//desert tile
  {
    resource = HexType.DESERT.toString();
    chit = 0;
  }

  public Hex(int x, int y)
  {
    hl = new HexLocation(x, y);
  }

  public Hex(String resrc, int num)
  {
    resource = resrc.toUpperCase();
    chit = num;
  }

  public Hex(String resc, int num, HexLocation HL)
  {
    resource = resc;
    chit = num;
    HL = hl;
  }

  public Hex(int x, int y, String resc, int num)
  {
    hl = new HexLocation(x, y);
    resource = resc;
    chit = num;
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
	  return chit;
  }

  public void setNumber(int chit)
  {
	  this.chit = chit;
  }

  public HexLocation getLocation()  {return hl; }
  public void setLocation(HexLocation HL)  {hl = HL; }
}

