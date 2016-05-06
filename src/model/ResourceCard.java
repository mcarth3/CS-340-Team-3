/**
 * A class holding a resource card only of type ore, lumber, brick, grain, or wool
 * Created by Jesse on 5/2/2016.
 */

package model;

public class ResourceCard {

    private String type;

    /**
     * Pre: Call to create a Resource Card with a type (e.g. ore, lumber, brick, grain, or wool.)
     * Post: If a valid type is entered, the ResourceCard is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    ResourceCard(String initializeType)
    {
        type = initializeType;
    }
}
