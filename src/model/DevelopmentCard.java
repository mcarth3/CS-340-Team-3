package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.DevCardType;

/**
 * Cards including type roadBuilding, yearOfPlenty, and monopoly, victoryPoint, or Knight
 * Created by Jesse on 5/2/2016.
 */
public class DevelopmentCard extends AbstractModelPartition {

    private DevCardType type;

    /**
     * @pre: Call to create a ProgressCard with a type
     * (only roadBuilding, yearOfPlenty, monopoly, victoryPoint, or knight)
     * @post: If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    DevelopmentCard(DevCardType initializeType) throws FailureToAddException {
        type = initializeType;
        /*if(!type.equals("roadBuilding") && !type.equals("yearOfPlenty") && !type.equals("monopoly")
                && !type.equals("victoryPoint") && !type.equals("knight") )
        {
            throw new FailureToAddException("failed to add correct type of DevelopmentCard");
        }*/
    }


    /*
     * GETTERS & SETTERS:
     */



    public void setType(DevCardType type) {
        this.type = type;
    }

    public DevCardType getType() {
        return type;
    }
}
