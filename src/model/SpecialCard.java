package model;

import poller.modeljsonparser.AbstractModelPartition;

/**
 * Special Cards consisting of longestRoad OR largestArmy
 * Created by Jesse on 5/2/2016.
 */
public class SpecialCard extends AbstractModelPartition {

    private String type;
    /**
     * @pre: Call to create a specialCard with a type (ONLY longestRoad or largestArmy)
     * @post: If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    SpecialCard(String initializeType) throws FailureToAddException {
        type = initializeType;
        if(!type.equals("longestRoad") && !type.equals("largestArmy"))
        {
            throw new FailureToAddException("failed to add correct type of SpecialCard");
        }
    }


    /**
     * GETTERS & SETTERS
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
