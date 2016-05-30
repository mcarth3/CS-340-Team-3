package server.jsonObjects;

public class RollJsonObject {
	private String type;
	private Integer playerIndex;
	private Integer number;

	public RollJsonObject(String newtype, int newplayerIndex, int newnumber) {
		type = newtype;
		playerIndex = newplayerIndex;
		number = newnumber;
	}

	public Integer getindex() {
		return playerIndex;
	}

	public Integer getnumber() {
		return number;
	}

}
