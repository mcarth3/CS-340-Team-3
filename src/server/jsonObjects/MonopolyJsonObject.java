package server.jsonObjects;

public class MonopolyJsonObject {

    private String type;
    private String resource;
    private Integer playerIndex;

    public MonopolyJsonObject(String newtype, String newresource, int newplayerIndex) {
        type = newtype;
        resource = newresource;
        playerIndex = newplayerIndex;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(Integer playerIndex) {
        this.playerIndex = playerIndex;
    }
}
