package controllers;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

/**
 * Controls interactions between players and their resource bar and DevCards.
 * Created by Jesse on 5/12/2016.
 */
public class ResourceBar_DevCardController {
    public ResourceBar_DevCardController() {
    }


    /**
     @pre: A person has clicked on a DevCard to play.
     @post: The controller enacts whatever the option for the DevCard type demands (e.g. if Monopoly, the player will choose what resource to monopolize.)
     */
    public void personPlayedDevCard(String username, DevCardType type)
    {

    }


    /**
     @pre: The map needs to know if the person has a type of Resource cards.
     @post: The function returns the number of the Resurce cards of that type.
     */
    public int personHasResourceCard(String username, ResourceType type)
    {
        return 0;
    }
}
