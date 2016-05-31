package server.jsonObjects;

public class FinishJsonObject {
	private String type;
	private Integer playerIndex;

	public FinishJsonObject(String newtype, int newplayerIndex) {
		type = newtype;
		playerIndex = newplayerIndex;
	}

	public Integer getindex() {
		return playerIndex;
	}

}
