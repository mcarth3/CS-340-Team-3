package server.jsonObjects;

public class MonumentJsonObject {
    private String type;
    private Integer playerIndex;

    public MonumentJsonObject(String newtype, int newplayerIndex) {
        type = newtype;
        playerIndex = newplayerIndex;

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
}
