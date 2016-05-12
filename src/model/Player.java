package model;
import java.util.ArrayList;
import model.bank.DevCardList;
import model.bank.ResourceList;
import model.Port;
import model.TradeOffer;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;
import model.bank.InsufficientResourcesException;

public class Player {

    private String username;
    private String password;
    
    //need from server 

    public Integer id;

   /** These are for the clientModel!*/
    //player qualities
    String color;
    String name;
    int biggestRoadLength;
    int playerID;
    int playerIndex;

    //placeable items
    int cities;//how many cities the player has left to play
    int roads;//how many roads the player has left to play
    int settlements;//how many settlements the player has left to play
    //cards, resources, etc
    int monuments;
    int soldiers;
    DevCardList newDevCards;
    DevCardList oldDevCards;
    boolean playedDevCard;
    ResourceList resources;
    boolean discarded;
    int victoryPoints;
    
    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<SpecialCard> specialCards;
    private ArrayList<Settlement> settlementArrayList;
    private ArrayList<Road> roadArrayList;
    private ArrayList<City> cityArrayList;
    //these are the maximum values a player can have in a game and also the starting amount
    final int MAX_CITIES = 4;
    final int MAX_SETTLEMENTS = 5;
    final int MAX_ROADS = 15;



    public Player(String color, String playerName, int ID) {
        resources = new ResourceList();
        newDevCards = new DevCardList();
        oldDevCards = new DevCardList();
        this.setName(playerName);
        this.setPlayerID(ID);
        this.color = color;
    }

    public Player(String newColor, String newName, String newPassword, int newBiggestRoadLength, int ID, int newCityAmount, int newRoadAmount, int newSettlementAmount, int newMonumentAmount,
                  int newSoldierAmount, DevCardList newNewDevCardList, DevCardList newOldDevCardList, boolean newPlayedDevCard, ResourceList newResourcesAmounts, boolean newDiscarded, int newVictoryPointAmount) {
        this.setColor(newColor);
        this.setName(newName);
        this.setPassword(newPassword);
        this.setBiggestRoadLength(newBiggestRoadLength);
        this.setPlayerID(ID);
        this.setCities(newCityAmount);
        this.setRoads(newRoadAmount);
        this.setSettlements(newSettlementAmount);
        this.setMonuments(newMonumentAmount);
        this.setSoldiers(newSoldierAmount);
        this.setNewDevCards(newNewDevCardList);
        this.setOldDevCards(newOldDevCardList);
        this.setPlayedDevCard(newPlayedDevCard);
        this.setResources(newResourcesAmounts);
        this.setDiscarded(newDiscarded);
        this.setVictoryPoints(newVictoryPointAmount);
    }



    public Player() {
		// TODO Auto-generated constructor stub
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
     * adds a devCard to the newDevCards DevCardList
     * @throws model.bank.InsufficientResourcesException 
     */
    public void drawDevCard() throws InsufficientResourcesException {
        if (resources.getOre() < 1 || resources.getWheat() < 1 || resources.getSheep() < 1) {
            throw new InsufficientResourcesException();
        } else {
            newDevCards.buyDevCard();
        }

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

    /**
     * Places a city on the map if it is a legal move.
     */
    public void placeCity() throws InsufficientResourcesException {
        if (cities < 1) {
            throw new InsufficientResourcesException();
        } else {
            cities--;
        }

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
     * Places a road on the map if it is a legal move.
     */
    public void placeRoad() throws InsufficientResourcesException {
        if (roads < 1) {
            throw new InsufficientResourcesException();
        } else {
            roads--;
        }
    }

    /**
     * Places a settlement on the map if it is a legal move.
     */
    public void placeSettlement() throws InsufficientResourcesException {
        if (settlements < 1) {
            throw new InsufficientResourcesException();
        } else {
            settlements--;
        }
    }

    /**
     * uses a development card.
     */
    public void playDevCard(String cardType) {
        playedDevCard = true;
    }

    /**
     * discards the card
     */
    public void discardCard(String cardType) {
        if (playedDevCard = true) {
            discarded = true;
        }
    }


    /**
     * uses Monopoly
     *
     * @return boolean whether or not the player played a monopoly
     */
    public void playMonopoly() {
        oldDevCards.setMonopoly(oldDevCards.getMonopoly() - 1);
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
     * plays the road build card
     *
     * @return boolean
     */
    public void playRoadBuilding() {
        oldDevCards.setRoadBuilding(oldDevCards.getRoadBuilding() - 1);
    }

    /**
     * plays a monument card
     *
     * @return boolean whether or not the player placed a monument
     */
    public void placeMonument() {
        monuments--;
        oldDevCards.setMonument(oldDevCards.getMonument() - 1);
    }

    /**
     * plays the year of plenty card for a given player
     *
     * @return boolean whether or not the player played the year of plenty card
     */
    public void playYearOfPlenty() {
        oldDevCards.setYearOfPlenty(oldDevCards.getYearOfPlenty() - 1);
    }

    /**
     * Places a Soldier and grants the effects he brings
     *
     * @return boolean whether or not the player played the soldier card
     */
    public void placeSoldier() {
        soldiers--;
        oldDevCards.setSoldier(oldDevCards.getSoldier() - 1);
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

    /**
     * Robs a player of one resource card
     *
     * @return boolean whether or not the player chose to rob
     */
    public void rob(ResourceType resourceRecieved) {
        switch (resourceRecieved) {
            case WOOD:
                resources.setWood(resources.getWood() + 1);
                break;
            case BRICK:
                resources.setBrick(resources.getBrick() + 1);
                break;
            case SHEEP:
                resources.setSheep(resources.getSheep() + 1);
                break;
            case WHEAT:
                resources.setWheat(resources.getWheat() + 1);
                break;
            case ORE:
                resources.setOre(resources.getOre() + 1);
                break;
        }
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

    /**
     * Checks to see if playing a Developement Card is a legal move for the player
     *
     * @return boolean whether or not the player can play a Developement card
     */
    public boolean canPlayDevcard() {
        if (oldDevCards.getSize() > 0 || newDevCards.getSize() > 0) {
            return true;
        }
        return false;
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
     * Checks to see if robbing another player is a legal move for the player
     *
     * @return boolean whether or not the player can rob another player
     */
    public boolean canRob() {
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

    /**
     * Checks to see accepting a trade request is a legal move for the player
     *
     * @return boolean whether or not the player can accept a trade offer from another player
     */
    public boolean canAcceptTrade(ResourceList resourcesRequirements) {
        return checkSufficientResources(resourcesRequirements);
    }

    public boolean canOfferTrade(ResourceList resourcesRequirements) {
        return checkSufficientResources(resourcesRequirements);
    }

    public boolean canDiscardCards(ResourceList resourcesRequirements) {
        return checkSufficientResources(resourcesRequirements);
    }


    /**
     * Checks to see if the player can win the game
     *
     * @return boolean whether or not the player can win (have 10 victory points)
     */
    public boolean canWin() {
        if (victoryPoints >= 10) {
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
     * Checks to see if the player has sufficient resources for a settlement
     *
     * @return boolean whether or not the player can build a settlement
     */
    public boolean canBuildSettlement() {
        if (settlements > 0 && resources.getBrick() > 0 && resources.getWood() > 0 && resources.getSheep() > 0 && resources.getWheat() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the player has sufficient resources for a road
     *
     * @return boolean whether or not the player can build a road
     */
    public boolean canBuildRoad() {
        if (resources.getBrick() > 0 && resources.getWood() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the player can be robbed by another player
     *
     * @return boolean whether or not the player can be robbed
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBiggestRoadLength() {
        return biggestRoadLength;
    }

    public void setBiggestRoadLength(int biggestRoadLength) {
        this.biggestRoadLength = biggestRoadLength;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public int getSettlements() {
        return settlements;
    }

    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    public int getMonuments() {
        return monuments;
    }

    public void setMonuments(int monuments) {
        this.monuments = monuments;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }

    public DevCardList getNewDevCards() {
        return newDevCards;
    }

    public void setNewDevCards(DevCardList newDevCards) {
        this.newDevCards = newDevCards;
    }

    public DevCardList getOldDevCards() {
        return oldDevCards;
    }

    public void setOldDevCards(DevCardList oldDevCards) {
        this.oldDevCards = oldDevCards;
    }

    public boolean isPlayedDevCard() {
        return playedDevCard;
    }

    public void setPlayedDevCard(boolean playedDevCard) {
        this.playedDevCard = playedDevCard;
    }

    public ResourceList getResources() {
        return resources;
    }

    public void setResources(ResourceList resources) {
        this.resources = resources;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getNumBuildings()
    {
        return settlements+cities;
    }
    public ArrayList<ResourceCard> getResourceCards() {
        return resourceCards;
    }

    public void setResourceCards(ArrayList<ResourceCard> resourceCards) {
        this.resourceCards = resourceCards;
    }
}

