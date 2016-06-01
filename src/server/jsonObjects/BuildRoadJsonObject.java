package server.jsonObjects;

import shared.locations.EdgeLocation;

public class BuildRoadJsonObject {
    private String type;
    private Integer playerIndex;
    private EdgeLocation roadLocation;
    private boolean free;

    public BuildRoadJsonObject(String newtype, int newplayerIndex, EdgeLocation newRoadLocation, boolean newFree) {
        type = newtype;
        playerIndex = newplayerIndex;
        roadLocation = newRoadLocation;
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

    public EdgeLocation getRoadLocation() {
        return roadLocation;
    }

    public void setRoadLocation(EdgeLocation roadLocation) {
        this.roadLocation = roadLocation;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
