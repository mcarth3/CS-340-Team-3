package controllers;

import shared.definitions.ResourceType;
import shared.locations.VertexLocation;

/**
 * Controls trade between players and docks.
 * Created by Jesse on 5/12/2016.
 */
public class MaritimeTradeController {
    public MaritimeTradeController() {
    }


    /**
     @pre: A person has made a request for trade and specified which dock to trade with and what they demand from them.
     @post: The trade of resources will be performed, and the map will be updated accordingly.
     */
    public void tradeDockResource(String usesrname, VertexLocation dockLocation, ResourceType type, int amount)
    {

    }
}
