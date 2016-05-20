package tests;

import model.*;
import org.junit.Test;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class Facade_CAN_DO_methodTests {



    @Test
    void testCanPlayerBuySettlement() {
        Game theNewGame = new Game();
        //Facade newFacade = new Facade();
        theNewGame.addPlayer("bob", "123");
        Player thePlayer = theNewGame.getPlayers().get(0);
        HexLocation theLocation;
        //VertexLocation theLocation = new VertexLocation(HexLocation , VertexDirection.East);
        //newFacade.canPlayerBuySettlement("bob", 0)

        thePlayer.addResource(ResourceType.BRICK, 2);



    }

    @Test
    void testCanPlayerBuyCity() {

    }

    @Test
    void testCanPlayerBuyRoad() {

    }

    @Test
    void testCanPlayerAcceptTrade() {

    }

    @Test
    void testCanPlayerPlayDevCard() {

    }
}
