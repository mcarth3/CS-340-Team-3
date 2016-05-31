package server.jsonObjects;

import shared.locations.VertexLocation;

public class BuildSettlementJsonObject {
    private String type;
    private Integer playerIndex;
    private VertexLocation vertexLocation;
    private boolean free;

    public BuildSettlementJsonObject(String newtype, int newplayerIndex, VertexLocation newSettlementLocation, boolean newFree) {
        type = newtype;
        playerIndex = newplayerIndex;
        vertexLocation = newSettlementLocation;
        free = newFree;
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

    public VertexLocation getVertexLocation() {
        return vertexLocation;
    }

    public void setVertexLocation(VertexLocation vertexLocation) {
        this.vertexLocation = vertexLocation;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
