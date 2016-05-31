package server.jsonObjects;

import shared.locations.HexLocation;

public class SoldierJsonObject {
	private String type;
	private Integer playerIndex;
	private Integer victimIndex;
	private HexLocation location;

	public SoldierJsonObject(String newtype, int newplayerIndex, int newvictimIndex, HexLocation newlocation) {
		type = newtype;
		playerIndex = newplayerIndex;
		victimIndex = newvictimIndex;
		location = newlocation;
	}

	public Integer getindex() {
		return playerIndex;
	}

	public Integer getvictimindex() {
		return victimIndex;
	}

	public HexLocation getlocation() {
		return location;
	}
}
