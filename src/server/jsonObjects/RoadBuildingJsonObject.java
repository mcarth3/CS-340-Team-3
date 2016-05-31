package server.jsonObjects;

import shared.locations.EdgeLocation;

public class RoadBuildingJsonObject {
	private String type;
	private Integer playerIndex;
	EdgeLocation spot1;
	EdgeLocation spot2;

	public RoadBuildingJsonObject(String newtype, int newplayerIndex, EdgeLocation newspot1, EdgeLocation newspot2) {
		type = newtype;
		playerIndex = newplayerIndex;
		spot1 = newspot1;
		spot2 = newspot2;
	}

	public Integer getindex() {
		return playerIndex;
	}

	public EdgeLocation getspot1() {
		return spot1;
	}

	public EdgeLocation getspot2() {
		return spot2;
	}
}
