package model;



import java.util.ArrayList;

/**
 * The class in the model containing all the necessary info of a Player (e.g. username, resource cards, buildings)
 * Created by Jesse on 5/2/2016.
 */
public class Player {


    private String username;
    private String password;

    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<SpecialCard> specialCards;
    private ArrayList<Settlement> settlementArrayList;
    private ArrayList<Road> roadArrayList;
    private ArrayList<City> cityArrayList;
    //private ArrayList<Buildings> personBuildings;


    /**
     * @pre should either be called by the Game or the turnTracker so that a player can take their turn
     * @post will essentially call to the Controller using this person so that the player can make the turn.
     * takeTurn() should be called by the Game once another turn has finished.
     * This will allow the next designated player to pick their moves.
     */
    public void takeTurn()
    {

    }

    /**
     * @pre called on by the functions getting a resource number (e.g. woolNumber())
     * @post returns the number of cards of that type in the Person's resourceCards arrayList
     * * @param type
     * @return
     */
    private int getResourceNumber(String type)
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns number of Person's wool cards using getResourceNumber()
     * @return
     */
    public int woolNumber()
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns number of Person's ore cards using getResourceNumber()
     * @return
     */
    public int oreNumber()
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns number of Person's lumber cards using getResourceNumber()
     * @return
     */
    public int lumberNumber()
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns number of Person's grain cards using getResourceNumber()
     * @return
     */
    public int grainNumber()
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns number of Person's brick cards using getResourceNumber()
     * @return
     */
    public int brickNumber()
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns number of Person's victoryPointCards that are type
     * "victoryPoint"
     * @return
     */
    public int victoryPointNumber()
    {
        return 0;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns whether or not the Person owns a getLongestRoad VictoryPointCard
     * @return
     */
    public boolean hasLongestRoad()
    {
        return false;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns whether or not the Person owns a getLargestArmy VictoryPointCard
     * @return
     */
    public boolean hasLargestArmy()
    {
        return false;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns whether or not the Person owns a getRoadBuilding ProgressCard
     * @return
     */
    public boolean hasRoadBuilding()
    {
        return false;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns whether or not the Person owns a getYearOfPlenty ProgressCard
     * @return
     */
    public boolean hasYearOfPlenty()
    {
        return false;
    }

    /**
     * @pre can be called by any class once this object has been initialized
     * @post returns whether or not the Person owns a getMonopoly ProgressCard
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    public void setDevelopmentCards(ArrayList<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards;
    }

    public ArrayList<SpecialCard> getSpecialCards() {
        return specialCards;
    }

    public void setSpecialCards(ArrayList<SpecialCard> specialCards) {
        this.specialCards = specialCards;
    }

    public ArrayList<Settlement> getSettlementArrayList() {
        return settlementArrayList;
    }

    public void setSettlementArrayList(ArrayList<Settlement> settlementArrayList) {
        this.settlementArrayList = settlementArrayList;
    }

    public ArrayList<Road> getRoadArrayList() {
        return roadArrayList;
    }

    public void setRoadArrayList(ArrayList<Road> roadArrayList) {
        this.roadArrayList = roadArrayList;
    }

    public ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public void setCityArrayList(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
    }
}
