package server.jsonObjects;

import model.bank.ResourceList;

public class OfferTradeJsonObject {
        private String type;
        private Integer playerIndex;
        private ResourceList offer;
        private Integer receiver;

        public OfferTradeJsonObject(String newtype, int newplayerIndex, ResourceList newOffer, int newReceiver) {
            type = newtype;
            playerIndex = newplayerIndex;
            offer = newOffer;
            receiver = newReceiver;
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

    public ResourceList getOffer() {
        return offer;
    }

    public void setOffer(ResourceList offer) {
        this.offer = offer;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }
}
