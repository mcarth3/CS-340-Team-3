package model;



import model.bank.DevCardList;
import model.bank.ResourceList;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;

import java.util.ArrayList;

import model.Port;
import model.bank.DevCardList;
import model.bank.ResourceList;

/**
 * The class in the model containing all the necessary info of a Player (e.g. username, resource cards, buildings)
 * Created by Jesse on 5/2/2016.
 */
public class Player {


    private String username;
    private String password;
    
    //need from server 

    public Integer id;

   /** These are for the clientModel!*/

    public int cities;
    private String color;
    private boolean discarded;
    private int monuments;
    private String name;
    private DevCardList newDevCards;
    private DevCardList oldDevCards;
    private int playerIndex;
    private boolean playedDevCard;
    private int playerID;
    private ResourceList resources;
    private int roads;
    private int settlements;
    private int soldiers;
    private int victoryPoints;





    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<SpecialCard> specialCards;
    private ArrayList<Settlement> settlementArrayList;
    private ArrayList<Road> roadArrayList;
    private ArrayList<City> cityArrayList;
    //private ArrayList<Buildings> personBuildings;

    final int MAX_CITIES = 4;
    final int MAX_SETTLEMENTS = 5;
    final int MAX_ROADS = 15;


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

    private boolean checkSufficientResources(ResourceList resourcesRequirements) {
        if (resources.getBrick() >= resourcesRequirements.getBrick() && resources.getWood() >= resourcesRequirements.getWood()
                && resources.getOre() >= resourcesRequirements.getOre() && resources.getSheep() >= resourcesRequirements.getSheep()
                && resources.getWheat() >= resourcesRequirements.getWheat()) {
            return true;
        }
        return false;
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


    public boolean canBuildRoad() {
        if (resources.getBrick() > 0 && resources.getWood() > 0) {
            return true;
        }
        return false;
    }
    public boolean canBuildSettlement() {
        if (settlements > 0 && resources.getBrick() > 0 && resources.getWood() > 0 && resources.getSheep() > 0 && resources.getWheat() > 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks to see if building a city is a legal move for the player
     *
     * @return boolean whether or not the player can build a city
     */
    public boolean canBuildCity() {
        if (resources.getOre() > 2 && resources.getWheat() > 1 && settlements < MAX_SETTLEMENTS && cities > 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if trading resource cards with another player is a legal move for the player
     *
     * @return boolean whether or not the player can trade with another player
     */
    public boolean canOfferTrade() {
        if ((resources.getBrick() + resources.getWood() + resources.getOre() + resources.getSheep() + resources.getWheat()) > 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks to see accepting a trade request is a legal move for the player
     *
     * @return boolean whether or not the player can accept a trade offer from another player
     */
    public boolean canAcceptTrade(ResourceList resourcesRequirements) {
        return checkSufficientResources(resourcesRequirements);
    }

    /**
     * uses Monopoly
     *
     * @return boolean whether or not the player played a monopoly
     */
    public void playMonopoly() {
        oldDevCards.setMonopoly(oldDevCards.getMonopoly() - 1);
    }
    /**
     * Checks to see if Montoply is a legal move for the player
     *
     * @return boolean whether or not the player can monopoly
     */
    public boolean canMonopoly() {
        if (oldDevCards.getMonopoly() > 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks to see if buying a Developement Card is a legal move for the player
     *
     * @return boolean whether or not the player can buy a Developement card
     */
    public boolean canBuyDevcard() {
        if (resources.getOre() < 1 || resources.getWheat() < 1 || resources.getSheep() < 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canDiscardCards(ResourceList resourcesRequirements) {
        return checkSufficientResources(resourcesRequirements);
    }
    /**
     * Checks if the player has the resources available to initiate an offer
     * with the bank for a maritime trade
     * @param ports the ports that the player owns
     * @return boolean whether or not the player can trade with the bank
     */
    public boolean canMaritimeTrade(ArrayList<Port> ports)
    {   //4 is the default that can be initiated at any time.
        int ratio = 4;

        //check if ports offer a lower ratio
        for(Port port: ports)
        {
            if(port.getRatio() < ratio)
            {
                ratio = port.getRatio();
            }
        }

        boolean canTrade = false;
        if(resources.getBrick() >= ratio)
        {
            canTrade = true;
        }
        else if(resources.getSheep() >= ratio)
        {
            canTrade = true;
        }
        else if (resources.getOre() >= ratio)
        {
            canTrade = true;
        }
        else if(resources.getWheat() >= ratio)
        {
            canTrade = true;
        }
        else if(resources.getWood() >= ratio)
        {
            canTrade = true;
        }
        return canTrade;
    }
    public void depleteResource(ResourceType resource) {
        //switch statement to set the resource that matches the resource type to 0
        switch (resource) {
            case WOOD:
                resources.setWood(0);
                break;
            case BRICK:
                resources.setBrick(0);
                break;
            case SHEEP:
                resources.setSheep(0);
                break;
            case WHEAT:
                resources.setWheat(0);
                break;
            case ORE:
                resources.setOre(0);
                break;
        }
    }
    public void addResource(ResourceType resource, int numberOfResource) {
        //switch statement for each resource type adding them to the resource list
        switch (resource) {
            case WOOD:
                resources.setWood(resources.getWood() + numberOfResource);
                break;
            case BRICK:
                resources.setBrick(resources.getBrick() + numberOfResource);
                break;
            case SHEEP:
                resources.setSheep(resources.getSheep() + numberOfResource);
                break;
            case WHEAT:
                resources.setWheat(resources.getWheat() + numberOfResource);
                break;
            case ORE:
                resources.setOre(resources.getOre() + numberOfResource);
                break;
        }

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
    public ResourceList getResources() {
        return resources;
    }

    public void setResources(ResourceList resources) {
        this.resources = resources;
    }
    /**
     * If the player has more then 7 cards when the robber is moved half the cards must be discarded
     *
     * @return boolean whether or not it is true
     */
    public boolean canRobberDiscard() {
        if ((resources.getBrick() + resources.getWood() + resources.getOre() + resources.getSheep() + resources.getWheat()) > 7) {
            return true;
        }
        return false;
    }
    public boolean canRob() {
        return false;
    }
    /**
     * Checks to see if placing a Soldier card is a legal move for the player
     *
     * @return boolean whether or not the player can place the Soldier card
     */
    public boolean canPlaceSoldier() {
        if (oldDevCards.getSoldier() > 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks to see if placing a Year Of Plenty card is a legal move for the player
     *
     * @return boolean whether or not the player can play the Year of Plenty card
     */
    public boolean canYearOfPlenty() {
        if (oldDevCards.getYearOfPlenty() > 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks to see if building a road in a specific place is a legal move for the player
     *
     * @return boolean whether or not the player can road building
     */
    public boolean canPlayRoadBuilding() {
        if (oldDevCards.getRoadBuilding() > 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks to see if placing a Monument Card(?) is a legal move for the player
     *
     * @return boolean whether or not the player can place a monument
     */
    public boolean canPlaceMonument() {
        if (victoryPoints >= 10) {
            return true;
        }
        return false;
    }

}
