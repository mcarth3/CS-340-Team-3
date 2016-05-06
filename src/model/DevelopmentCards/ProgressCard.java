package DevelopmentCards;

/**
 * Cards including type roadBuilding, yearOfPlenty, and monopoly
 * Created by Jesse on 5/2/2016.
 */
public class ProgressCard {

    private String type;

    /**
     * Pre: Call to create a ProgressCard with a type (only roadBuilding, yearOfPlenty, and monopoly)
     * Post: If a valid type is entered, the object is created. If
     * the type is invalid, an error is thrown
     * @param initializeType
     */
    ProgressCard(String initializeType)
    {
        type = initializeType;
    }
}
