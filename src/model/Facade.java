package model;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * A Facade for the Controller to interact with the Game Model. This will also contain several different can-Do's for the
 * game and players (e.g. canPlayDevCard)
 * Created by Jesse on 5/10/2016.
 */
public class Facade {
    private Game theGame;

    public Facade(Game newGame)
    {
        theGame = newGame;
    }


    /**
     * function to check if a known player can buy a settlement and put it in a known location
     * @pre: give username of Player in the Game and VertexLocation of the location where you want to check if a settlement can be built there
     * @post: function checks both person's resources and map's settlements and returns whether or not this particular player can build a settlement at the given location
     */
    public boolean canPlayerBuySettlement(String username, VertexLocation location) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 1 && newPlayer.returnResourceNumber(ResourceType.WOOD) >= 1 &&
        newPlayer.returnResourceNumber(ResourceType.BRICK) >= 1 && newPlayer.returnResourceNumber(ResourceType.SHEEP) >= 1 )
        {
            if(theGame.getTheGameMap().canAddSettlement(location))
            {
                return true;
            }
        }

        return false;
    }



    /**
     * function to check if a known player can buy a city and put it in a known location
     * @pre: give username of Player in the Game and VertexLocation of the location where you want to check if a city can be built there
     * @post: function checks both person's resources and settlements and returns whether or not this particular player can build a settlement at the given location
     */
    public boolean canPlayerBuyCity(String username, VertexLocation location) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.ORE) >= 3 && newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 2)
        {
            if(newPlayer.findSettlement(location) == true)
            {
                return true;
            }

        }

            return false;

    }



    /**
     * function to check if a known player can buy a road and put it in a known location
     * @pre: give username of Player in the Game and EdgeLocation of the location where you want to check if a road can be built
     * @post: function checks both person's resources and map's edges and returns whether or not this particular player can build a road at the given edge
     */
    public boolean canPlayerBuyRoad(String username, EdgeLocation edge) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.WOOD) >= 1 && newPlayer.returnResourceNumber(ResourceType.BRICK) >= 1)
        {
            if(theGame.getTheGameMap().canAddRoad(edge))
            {
                return true;
            }

        }

        return false;
    }



    /**
     * function to check if a known player can trade a certain amount of a particular resource
     * @pre: give username of Player in the Game, ResourceType of the type of desired Resource from this Player, and
     * the number of these resources asked for
     * @post: function checks person's resources of the given type and if they have enough of them to trade for the
     * specified amount, then returns whether or not they can
     */
    public boolean canPlayerAcceptTrade(String username, ResourceType typeRequired, int resourceNumber) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if (newPlayer.returnResourceNumber(typeRequired) >= resourceNumber) {
            return true;
        }
        return false;
    }


    /**
     * function to check if a known player can play a particular type of DevelopmentCard
     * @pre: give username of Player in the Game type of DevelopmentCard to see if the person possesses it
     * @post: function checks Player's Development Cards and returns the truth of whether or not they possess that card
     */
    public boolean canPlayerPlayDevCard(String username, DevCardType type) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if (newPlayer.returnDevCardValue(type) >= 1) {
            return true;
        }
        return false;
    }




}
