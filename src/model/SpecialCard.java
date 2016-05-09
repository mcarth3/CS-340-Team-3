package model;

/**
 * Special Cards consisting of longestRoad OR largestArmy
 * Created by Jesse on 5/2/2016.
 */
public class SpecialCard {

    private String type;
    /**
     * @pre Call to create a specialCard with a type (only longestRoad, largestArmy)
     * @post If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    SpecialCard(String initializeType)
    {
        type = initializeType;

    }

}
