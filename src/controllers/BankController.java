package controllers;


import model.bank.InsufficientResourcesException;
import model.bank.ResourceList;

/**
 * Controls trade between players and the bank.
 * Created by Jesse on 5/12/2016.
 */
public class BankController {
    private ResourceList theBank;

    public BankController(ResourceList theBank) {
        this.theBank = theBank;
    }


    /**
     @pre: A person has made a request for trade and specified which person to trade with and what they demand from them.
     @post: The other person will be given the option to trade, and the map will be updated accordingly.
     */
    public void addWood(int add) throws InsufficientResourcesException {
        theBank.setWood(add);
    }


    /**
     @pre: A person has made a request for trade and specified which person to trade with and what they demand from them.
     @post: The other person will be given the option to trade, and the map will be updated accordingly.
     */
    public void addOre(int add) throws InsufficientResourcesException {
        theBank.setOre(add);
    }

}
