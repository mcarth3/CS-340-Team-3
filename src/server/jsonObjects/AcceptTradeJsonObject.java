package server.jsonObjects;

public class AcceptTradeJsonObject {
    private String type;
    private Integer playerIndex;
    private boolean willAccept;

    public AcceptTradeJsonObject(String newtype, int newplayerIndex, boolean willAccept) {
        type = newtype;
        playerIndex = newplayerIndex;
        this.willAccept = willAccept;
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

    public boolean isWillAccept() {
        return willAccept;
    }

    public void setWillAccept(boolean willAccept) {
        this.willAccept = willAccept;
    }
}
