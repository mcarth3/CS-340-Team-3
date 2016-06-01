package server.jsonObjects;

public class MovesSendChatJsonObject {
	private String type; 
	private Integer playerIndex;
	private String content; 
	
	public MovesSendChatJsonObject(String t, Integer p, String c){
		type = t;
		playerIndex = p;
		content = c; 
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(Integer playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
