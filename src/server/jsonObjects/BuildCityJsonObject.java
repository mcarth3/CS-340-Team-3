package server.jsonObjects;

import shared.locations.VertexLocation;

public class BuildCityJsonObject {
    private String type;
    private Integer playerIndex;
    private VertexLocation vertexLocation;


    public BuildCityJsonObject(String newtype, int newplayerIndex, VertexLocation newCityLocation) {
        type = newtype;
        playerIndex = newplayerIndex;
        vertexLocation = newCityLocation;

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
}
