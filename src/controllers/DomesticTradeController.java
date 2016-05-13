package controllers;

import shared.definitions.ResourceType;

/**
 * Controls trade between players.
 * Created by Jesse on 5/12/2016.
 */
public class DomesticTradeController {
    public DomesticTradeController() {
    }

    /**
    @pre: A person has made a request for trade and specified which person to trade with and what they demand from them.
     @post: The other person will be given the option to trade, and the map will be updated accordingly.
     */
    public boolean tradePersonForResource(String username, ResourceType type, int amount)
    {
        return false;
    }
}
