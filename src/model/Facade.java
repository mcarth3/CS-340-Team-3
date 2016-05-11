package model;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;

/**
 * A Facade for the Controller to interact with the Game Model. This will also contain several different can-Do's for the
 * game and players (e.g. canPlayDevCard)
 * Created by Jesse on 5/10/2016.
 */
public class Facade {
    private Game theGame;

    public Facade()
    {

    }

    public boolean canPlayerBuySettlement(String username, VertexLocation location) throws ObjectNotFoundException {


        return false;
    }

    public boolean canPlayerBuyCity(String username, VertexLocation location) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.ORE) >= 3 && newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 2)
        {
            if(newPlayer.findSettlement(location) == true)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public void canPlayerBuyRoad(int playerIndex)
    {

    }

    public void canPlayerAcceptTrade(int playerIndex, ResourceType type, int resourceNumber)
    {

    }

    public void canPlayerPlayDevCard(int playerIndex, DevCardType type)
    {

    }




}
