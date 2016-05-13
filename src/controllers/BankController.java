package controllers;

import model.bank.Bank;
import model.bank.InsufficientResourcesException;

/**
 * Created by Jesse on 5/12/2016.
 */
public class BankController {
    private Bank theBank;

    public BankController(Bank theBank) {
        this.theBank = theBank;
    }

    public void addWood(int add) throws InsufficientResourcesException {
        theBank.addWood(add);
    }

    public void addOre(int add) throws InsufficientResourcesException {
        theBank.addOre(add);
    }

}
