
package model.bank;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jesse McArthur
 */

public class DevCardList {

    private int monopoly;
    private int monument;
    private int roadBuilding;
    private int soldier;
    private int yearOfPlenty;

    public DevCardList() {
        monopoly = 0;
        monument = 0;
        roadBuilding = 0;
        soldier = 0;
        yearOfPlenty = 0;
    }


    public DevCardList(int mono, int monu, int road, int sldr, int yop) {
        monopoly = mono;
        monument = monu;
        roadBuilding = road;
        soldier = sldr;
        yearOfPlenty = yop;
    }

    /**
     * this method will randomly decide which card the bank will give
     *
     * @return cardtype of card to be returned
     */
    public String buyDevCard() throws InsufficientResourcesException {
        //Initialize array
        ArrayList<Integer> randomize = new ArrayList<Integer>();
        //for every amount of every type of card in the bank, add a certain number to the arraylist depending on the type
        for (int i = 0; i < monopoly; i++) {
            randomize.add(0);
        }
        for (int i = 0; i < monument; i++) {
            randomize.add(1);
        }
        for (int i = 0; i < roadBuilding; i++) {
            randomize.add(2);
        }
        for (int i = 0; i < soldier; i++) {
            randomize.add(3);
        }
        for (int i = 0; i < yearOfPlenty; i++) {
            randomize.add(4);
        }
        //Generate a random number from 0 to the size of the array minus 1
        Random rand = null;
        int min = 0;
        int max = randomize.size() - 1;
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        //Use this number to access the random index of the arraylist, which gets the
        //(0,1,2,3,4) depending on what the random number was indexed to. Then returns
        //what type the card that was chosen was, in the form of a string.
        int chosenCard = randomize.get(randomNum);
        if (chosenCard == 0) {
            return "monopoly";
        }
        if (chosenCard == 1) {
            return "monument";
        }
        if (chosenCard == 2) {
            return "roadbuilding";
        }
        if (chosenCard == 3) {
            return "soldier";
        }
        if (chosenCard == 4) {
            return "yearofplenty";
        }
        return null;
    }

    /**
     * @return can a devcard be purchased
     */
    public boolean canBuyDevCard(ResourceList playersHand) {
        int ore = playersHand.getOre();
        int sheep = playersHand.getSheep();
        int wheat = playersHand.getWheat();
        if (ore < 1 || sheep < 1 || wheat < 1) {
            return false;
        }
        return true;
    }

    /**
     * @param type of card that wants to be used
     *             probably should have multiple methods, but this will go though a giant switch on the enum types
     * @return if can use a DevCard
     */
    public boolean canUseDevCard(String type) {
        return false;
    }

    //useDevCard is basically using the get/set.  The facade can take care of the rest of the stuff

    public int getSize() {
        int total = 0;
        total = total + monopoly + monument + yearOfPlenty + roadBuilding + soldier;
        return total;
    }

    public int getMonopoly() {
        return monopoly;
    }

    public void setMonopoly(int monopoly) {
        this.monopoly = monopoly;
    }

    public int getMonument() {
        return monument;
    }

    public void setMonument(int monument) {
        this.monument = monument;
    }

    public int getRoadBuilding() {
        return roadBuilding;
    }

    public void setRoadBuilding(int roadBuilding) {
        this.roadBuilding = roadBuilding;
    }

    public int getSoldier() {
        return soldier;
    }

    public void setSoldier(int soldier) {
        this.soldier = soldier;
    }

    public int getYearOfPlenty() {
        return yearOfPlenty;
    }

    public void setYearOfPlenty(int yearOfPlenty) {
        this.yearOfPlenty = yearOfPlenty;
    }
}

