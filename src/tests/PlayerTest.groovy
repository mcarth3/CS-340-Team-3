package tests

import model.Player
import model.ResourceCard
import shared.definitions.ResourceType

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * This Test checks if the player accurately assesses its own resourceCards
 * Created by Jesse on 5/10/2016.
 */
class PlayerTest extends GroovyTestCase {
@Test
    void test() {
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
    newPlayer.setResourceCards(newCards);

    int woodCount = newPlayer.returnResourceNumber(ResourceType.WOOD);
    assertEquals(woodCount, 2);
    assertEquals(newPlayer.returnResourceNumber(ResourceType.ORE), 1);
    assertEquals(newPlayer.returnResourceNumber(ResourceType.BRICK), 1);
    assertEquals(newPlayer.returnResourceNumber(ResourceType.WHEAT), 1);
    assertEquals(newPlayer.returnResourceNumber(ResourceType.SHEEP), 0);





}


}
