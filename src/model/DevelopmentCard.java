package model;

/**
 * Cards including type roadBuilding, yearOfPlenty, and monopoly, victoryPoint, or Knight
 * Created by Jesse on 5/2/2016.
 */
public class DevelopmentCard {

    private String type;

    /**
     * @pre: Call to create a ProgressCard with a type
     * (only roadBuilding, yearOfPlenty, monopoly, victoryPoint, or knight)
     * @post: If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    DevelopmentCard(String initializeType) throws FailureToAddException {
        type = initializeType;
        if(!type.equals("roadBuilding") && !type.equals("yearOfPlenty") && !type.equals("monopoly")
                && !type.equals("victoryPoint") && !type.equals("knight") )
        {
            throw new FailureToAddException("failed to add correct type of DevelopmentCard");
        }
    }


    /**
     * GETTERS & SETTERS:
     */

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
