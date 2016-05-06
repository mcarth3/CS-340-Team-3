package model;


import model.DevelopmentCards.KnightSoldierCard;
import model.DevelopmentCards.ProgressCard;
import model.DevelopmentCards.VictoryPointCard;

import java.util.ArrayList;

/**
 * Created by Jesse on 5/2/2016.
 */
public class Person {


    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<ProgressCard> progressCards;
    private ArrayList<VictoryPointCard> victoryPointCards;
    private ArrayList<KnightSoldierCard> knightSoldierCards;
    //private ArrayList<Buildings> personBuildings;


    /**
     * takeTurn() should be called by the Game once another turn has finished.
     * This will allow the next designated player to pick their moves.
     */
    public void takeTurn()
    {

    }

    /**
     * PRE: called on by the functions getting a resource number (e.g. woolNumber())
     * POST: returns the number of cards of that type in the Person's resourceCards arrayList
     * * @param type
     * @return
     */
    private int getResourceNumber(String type)
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns number of Person's wool cards using getResourceNumber()
     * @return
     */
    public int woolNumber()
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns number of Person's ore cards using getResourceNumber()
     * @return
     */
    public int oreNumber()
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns number of Person's lumber cards using getResourceNumber()
     * @return
     */
    public int lumberNumber()
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns number of Person's grain cards using getResourceNumber()
     * @return
     */
    public int grainNumber()
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns number of Person's brick cards using getResourceNumber()
     * @return
     */
    public int brickNumber()
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns number of Person's victoryPointCards that are type
     * "victoryPoint"
     * @return
     */
    public int victoryPointNumber()
    {
        return 0;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns whether or not the Person owns a getLongestRoad VictoryPointCard
     * @return
     */
    public boolean hasLongestRoad()
    {
        return false;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns whether or not the Person owns a getLargestArmy VictoryPointCard
     * @return
     */
    public boolean hasLargestArmy()
    {
        return false;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns whether or not the Person owns a getRoadBuilding ProgressCard
     * @return
     */
    public boolean hasRoadBuilding()
    {
        return false;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns whether or not the Person owns a getYearOfPlenty ProgressCard
     * @return
     */
    public boolean hasYearOfPlenty()
    {
        return false;
    }

    /**
     * PRE: can be called by any class once this object has been initialized
     * POST: returns whether or not the Person owns a getMonopoly ProgressCard
     * @return
     */
    public boolean hasMonopoly()
    {
        return false;
    }







/*    /**
    * getGameData() MIGHT be used by a Game to have a person update their data (e.g.,
     * so they can see what's going on.
     */
    /*public void getGameData()
    {

    }*/


    //**********************Setters/Getters:


    public ArrayList<ResourceCard> getResourceCards() {
        return resourceCards;
    }

    public void setResourceCards(ArrayList<ResourceCard> resourceCards) {
        this.resourceCards = resourceCards;
    }

    public ArrayList<ProgressCard> getProgressCards() {
        return progressCards;
    }

    public void setProgressCards(ArrayList<ProgressCard> progressCards) {
        this.progressCards = progressCards;
    }

    public ArrayList<VictoryPointCard> getVictoryPointCards() {
        return victoryPointCards;
    }

    public void setVictoryPointCards(ArrayList<VictoryPointCard> victoryPointCards) {
        this.victoryPointCards = victoryPointCards;
    }

    public ArrayList<KnightSoldierCard> getKnightSoldierCards() {
        return knightSoldierCards;
    }

    public void setKnightSoldierCards(ArrayList<KnightSoldierCard> knightSoldierCards) {
        this.knightSoldierCards = knightSoldierCards;
    }
}
