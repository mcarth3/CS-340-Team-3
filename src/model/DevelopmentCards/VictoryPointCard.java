package DevelopmentCards;

/**
 * Victory Cards consisting of victoryPoint, longestRoad, largestArmy, AND the value associated with these card types.
 * Created by Jesse on 5/2/2016.
 */
public class VictoryPointCard {

    private String type;
    private int cardValue;
    /**
     * Pre: Call to create a VictoryPointCard with a type (only victoryPoint, longestRoad, largestArmy)
     * Post: If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    VictoryPointCard(String initializeType, int value)
    {
        type = initializeType;
        cardValue = value;
    }

}
