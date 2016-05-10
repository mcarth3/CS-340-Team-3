package model

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
    ResourceCard treeCard = new ResourceCard("lumber");
    newCards.add(treeCard);
    ResourceCard treeCard2 = new ResourceCard("lumber");
    newCards.add(treeCard2);
    ResourceCard rockCard = new ResourceCard("ore");
    newCards.add(rockCard);
    ResourceCard brickCard = new ResourceCard("brick");
    newCards.add(brickCard);
    ResourceCard grainCard = new ResourceCard("grain");
    newCards.add(grainCard);

    Player newPlayer = new Player();
    newPlayer.setResourceCards(newCards);

    int woodCount = newPlayer.lumberNumber();
    assertEquals(woodCount, 2);
    assertEquals(newPlayer.oreNumber(), 1);
    assertEquals(newPlayer.brickNumber(), 1);
    assertEquals(newPlayer.grainNumber(), 1);
    assertEquals(newPlayer.woolNumber(), 0);





}


}
