package model;

/**
 * Cards including type roadBuilding, yearOfPlenty, and monopoly, victoryPoint, or Knight
 * Created by Jesse on 5/2/2016.
 */
public class DevelopmentCard {

    private String type;

    /**
     * Pre: Call to create a ProgressCard with a type
     * (only roadBuilding, yearOfPlenty, monopoly, victoryPoint, or Knight)
     * Post: If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    DevelopmentCard(String initializeType)
    {
        type = initializeType;
    }
}
