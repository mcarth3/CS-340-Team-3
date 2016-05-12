/**
 * A class holding a resource card only of type ore, lumber, brick, grain, or wool
 * Created by Jesse on 5/2/2016.
 */

package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.ResourceType;

public class ResourceCard extends AbstractModelPartition {

    private ResourceType type;

    /**
     * @pre: Call to create a Resource Card with a type (e.g. ONLY ore, lumber, brick, grain, or wool.)
     * @post: If a valid type is entered, the ResourceCard is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    public ResourceCard(ResourceType initializeType) throws FailureToAddException {
        type = initializeType;
        /*if(!type.equals("ore") && !type.equals("lumber") && !type.equals("brick")
                && !type.equals("wool") && !type.equals("grain") )
        {
            throw new FailureToAddException("failed to add correct type of ResourceCard");
        }*/
    }


    /**********************************************
     * Setters & Getters:
     */
    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }
}
