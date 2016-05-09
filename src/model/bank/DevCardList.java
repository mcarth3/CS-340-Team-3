
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
    private int knight;
    private int yearOfPlenty;

    public DevCardList() {
        monopoly = 0;
        monument = 0;
        roadBuilding = 0;
        knight = 0;
        yearOfPlenty = 0;
    }


    public DevCardList(int mono, int monu, int road, int sldr, int yop) {
     
    }

    /**
     * this method will randomly decide which card the bank will give
     *
     * @return cardtype of card to be returned
     */
    public String buyDevCard() throws InsufficientResourcesException {

  

    

        return null;
    }

    /**
     * @return can a devcard be purchased
     */
    public boolean canBuyDevCard(ResourceList playersHand) {
  
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

   

    public int getSize() {
    return 0;
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

    public int getKnight() {
        return knight;
    }

    public void setKnight(int soldier) {
        this.knight = knight;
    }

    public int getYearOfPlenty() {
        return yearOfPlenty;
    }

    public void setYearOfPlenty(int yearOfPlenty) {
        this.yearOfPlenty = yearOfPlenty;
    }
}
