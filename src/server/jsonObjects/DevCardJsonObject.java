package server.jsonObjects;

public class DevCardJsonObject {
	private String type;
	private Integer playerIndex;

	public DevCardJsonObject(String newtype, int newplayerIndex) {
		type = newtype;
		playerIndex = newplayerIndex;
	}

	public Integer getindex() {
		return playerIndex;
	}
}
