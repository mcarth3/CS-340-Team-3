package tests;

import static org.junit.Assert.fail;

import model.FailureToAddException;
import model.Player;
import model.ResourceCard;
import org.junit.Test;

import static org.junit.Assert.*;

import shared.definitions.ResourceType;

import java.util.ArrayList;

public class PlayerTest {
    @Test
    public void test() throws FailureToAddException {

        ArrayList<ResourceCard> newCards = new ArrayList<ResourceCard>();
        ResourceCard treeCard = new ResourceCard(ResourceType.WOOD);
        newCards.add(treeCard);
        ResourceCard treeCard2 = new ResourceCard(ResourceType.WOOD);
        newCards.add(treeCard2);
        ResourceCard rockCard = new ResourceCard(ResourceType.ORE);
        newCards.add(rockCard);
        ResourceCard brickCard = new ResourceCard(ResourceType.BRICK);
        newCards.add(brickCard);
        ResourceCard grainCard = new ResourceCard(ResourceType.WHEAT);
        newCards.add(grainCard);

        Player newPlayer = new Player();


     /*   int woodCount = newPlayer.returnResourceNumber(ResourceType.WOOD);
        assertEquals(woodCount, 2);
        assertEquals(newPlayer.returnResourceNumber(ResourceType.ORE), 1);
        assertEquals(newPlayer.returnResourceNumber(ResourceType.BRICK), 1);
        assertEquals(newPlayer.returnResourceNumber(ResourceType.WHEAT), 1);
        assertEquals(newPlayer.returnResourceNumber(ResourceType.SHEEP), 0);
    */

    }
}
