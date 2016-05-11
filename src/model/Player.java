package model;



import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;

import java.util.ArrayList;

/**
 * The class in the model containing all the necessary info of a Player (e.g. username, resource cards, buildings)
 * Created by Jesse on 5/2/2016.
 */
public class Player {


    private String username;
    private String password;
    
    //need from server 
    public String color;
    public String name;
    public Integer id;

    public boolean discarded;
    public int cities;
    public int playerIndex;
    public int playerID;



    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<SpecialCard> specialCards;
    private ArrayList<Settlement> settlementArrayList;
    private ArrayList<Road> roadArrayList;
    private ArrayList<City> cityArrayList;
    //private ArrayList<Buildings> personBuildings;


    public Player(){}
    public Player(String c, String n, Integer i){
    	color = c; 
    	name = n;
    	id = i; 
    }
    /**
     * @pre: should either be called by the Game or the turnTracker so that a player can take their turn
     * @post: will essentially call to the Controller using this person so that the player can make the turn.
     * takeTurn() should be called by the Game once another turn has finished.
     * This will allow the next designated player to pick their moves.
     */
    public void takeTurn()
    {

    }


    public boolean findSettlement(VertexLocation theLocation) throws ObjectNotFoundException {
        for(int i=0; i < settlementArrayList.size(); i++)
        {
            if(settlementArrayList.get(i).getVertextLocation() == theLocation)
            {
                return true;
            }
        }
        return false;
        //throw new ObjectNotFoundException("failed to find settlement of location !");
    }


    /**
     * @pre: called on by the functions getting a resource number (e.g. woolNumber())
     * @post: returns the number of cards of that type in the Person's resourceCards arrayList
     * * @param type
     * @return
     */
    public int returnResourceNumber(ResourceType searchType)
    {
        int counter =0;
        for(int i=0; i < resourceCards.size(); i++)
        {
            if(resourceCards.get(i).getType().equals(searchType))
            {
                counter++;
            }
        }

        return counter;
    }



    /**
     * @pre: Player and specialCards must have been initialized, can only be called by
     * HasLongestRoad() and hasLargestArmy()
     * @post: returned whether or not the player has one of the 2 cards
     * @return
     */
    private boolean searchSpecialCards(String type)
    {
        boolean isContained = false;
        for(int i=0; i < specialCards.size(); i++)
        {
            if(specialCards.get(i).getType().equals(type))
            {
                isContained = true;
            }
        }

        return isContained;

    }



    /**
     * @pre: can be called by any class once this object has been initialized
     * @post: returns whether or not the Person owns a getLongestRoad VictoryPointCard
     * @return
     */
    public boolean hasLongestRoad()
    {
        return searchSpecialCards("longestRoad");
    }

    /**
     * @pre: can be called by any class once this object has been initialized
     * @post: returns whether or not the Person owns a getLargestArmy VictoryPointCard
     * @return
     */
    public boolean hasLargestArmy()
    {
        return searchSpecialCards("largestArmy");
    }





    /**
     * @pre: called on by the functions checking for a person's development card (e.g. monopoly, yearOfPlenty, victoryPoint, or roadBuilding())
     * @post: returns the number of cards of that type in the Person's developmentCards arrayList
     * * @param type
     * @return
     */
    public int returnDevCardValue(DevCardType searchType)
    {
        int counter =0;
        for(int i=0; i < developmentCards.size(); i++)
        {
            if(developmentCards.get(i).getType().equals(searchType))
            {
                counter++;
            }
        }

        return counter;
    }









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
