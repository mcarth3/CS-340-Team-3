package server.jsonObjects;

import model.bank.ResourceList;

public class DiscardCardsJsonObject {
    private String type;
    private Integer playerIndex;
    private ResourceList discardedCards;

    public DiscardCardsJsonObject(String newtype, int newplayerIndex, ResourceList newDiscardedCards) {
        type = newtype;
        playerIndex = newplayerIndex;
        discardedCards = newDiscardedCards;
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

    public ResourceList getDiscardedCards() {
        return discardedCards;
    }

    public void setDiscardedCards(ResourceList discardedCards) {
        this.discardedCards = discardedCards;
    }
}
