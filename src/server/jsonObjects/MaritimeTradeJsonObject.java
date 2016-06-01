package server.jsonObjects;

public class MaritimeTradeJsonObject {

    private String type;
    private Integer playerIndex;
    private Integer ratio;
    private String inputResource;
    private String outputResource;

    public MaritimeTradeJsonObject(String newtype, int newplayerIndex, int newRatio, String newInputResource, String newOutputResource) {
        type = newtype;
        playerIndex = newplayerIndex;
        ratio = newRatio;

            inputResource = newInputResource;

            inputResource = newInputResource;

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

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public String getInputResource() {
        return inputResource;
    }

    public void setInputResource(String inputResource) {
        this.inputResource = inputResource;
    }

    public String getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(String outputResource) {
        this.outputResource = outputResource;
    }
}
