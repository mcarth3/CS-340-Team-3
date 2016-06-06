package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
 * The hex class represents a hex on the map. It has a location, resource type, 
 *	and an int.
 * @author Jesse McArthur
 *	
 */
public class Hex extends AbstractModelPartition {
	public HexLocation location;
	public int number;
	public String resource;

	public Hex()//desert tile
	{
		resource = HexType.DESERT.toString();
		number = 0;
	}

	public Hex(int x, int y) {
		location = new HexLocation(x, y);
	}

	public Hex(String resrc, int num) {
		resource = resrc.toUpperCase();
		number = num;
	}

	public Hex(String resc, int num, HexLocation HL) {
		resource = resc;
		number = num;
		location = HL;
	}

	public Hex(int x, int y, String resc, int num) {
		location = new HexLocation(x, y);
		resource = resc;
		number = num;
	}
	//Getters and Setters

	public String getResource() {
		return resource;
	}

	public ResourceType getResourceAsType() {
		if (resource.equals("wood")) {
			return ResourceType.WOOD;
		}
		if (resource.equals("brick")) {
			return ResourceType.BRICK;
		}
		if (resource.equals("sheep")) {
			return ResourceType.SHEEP;
		}
		if (resource.equals("wheat")) {
			return ResourceType.WHEAT;
		}
		if (resource.equals("ore")) {
			return ResourceType.ORE;
		}
		return null;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int chit) {
		this.number = chit;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation HL) {
		location = HL;
	}
}
