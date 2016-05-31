package server.jsonObjects;

import shared.definitions.ResourceType;

public class YOPJsonObject {
	private String type;
	private Integer playerIndex;
	ResourceType resource1;
	ResourceType resource2;

	public YOPJsonObject(String newtype, int newplayerIndex, ResourceType newresource1, ResourceType newresource2) {
		type = newtype;
		playerIndex = newplayerIndex;
		resource1 = newresource1;
		resource2 = newresource2;
	}

	public Integer getindex() {
		return playerIndex;
	}

	public ResourceType getresource1() {
		return resource1;
	}

	public ResourceType getresource2() {
		return resource2;
	}
}
