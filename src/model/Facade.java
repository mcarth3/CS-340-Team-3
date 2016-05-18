
package model;

import shared.definitions.*;

import java.util.ArrayList;

import javax.annotation.Resource;

import client.GameManager.GameManager;
import model.*;
import model.bank.ResourceList;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import poller.modeljsonparser.AbstractModelPartition;
import proxy.*;
import shared.locations.*;
/**
 * Created by Jesse R.
 */
public class Facade extends AbstractModelPartition {
    Game theGame;
    IServer proxy;
    ArrayList<ResourceList>discardedcards;

    //*************************************ADDED BELOW FOR SINGLETON
    private static Facade singleton = null;

    private static Facade theFacade;
    public static Facade getFacade()
    {
        if(theFacade == null)
        {
            theFacade = new Facade();
        }
        return theFacade;
    }
    //**************************************ADDED ABOVE FOR SINGLETON




    public Facade(RealProxy p) {
        theGame = null;
        proxy = p;
    }

    public Facade(RealProxy p,Game newgame) {
        theGame = newgame;
        proxy = p;
    }
    
    public Facade(MockProxy p) {
        theGame = null;
        proxy = p;
    }
    public Facade(MockProxy p,Game newgame) {
        theGame = newgame;
        proxy = p;
    }

    public Facade(Game newGame) {
    	theGame = newGame;

    }
    public Facade() {
		// TODO Auto-generated constructor stub
	}
    
    public void SetGame(Game newGame){
    	theGame = newGame;
    }
    
    public int getversion(){
    	return theGame.version; 
    }

	/**
     * function to check if a known player can buy a settlement and put it in a known location
     * @pre: give username of Player in the Game and VertexLocation of the location where you want to check if a settlement can be built there
     * @post: function checks both person's resources and map's settlements and returns whether or not this particular player can build a settlement at the given location
     */
    public boolean canPlayerBuySettlement(String username, VertexLocation location) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 1 && newPlayer.returnResourceNumber(ResourceType.WOOD) >= 1 &&
        newPlayer.returnResourceNumber(ResourceType.BRICK) >= 1 && newPlayer.returnResourceNumber(ResourceType.SHEEP) >= 1 )
        {
            if(theGame.getTheGameMap().canAddSettlement(location))
            {
                return true;
            }
        }

        return false;
    }



    /**
     * function to check if a known player can buy a city and put it in a known location
     * @pre: give username of Player in the Game and VertexLocation of the location where you want to check if a city can be built there
     * @post: function checks both person's resources and settlements and returns whether or not this particular player can build a settlement at the given location
     */
    public boolean canPlayerBuyCity(String username, VertexLocation location) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.ORE) >= 3 && newPlayer.returnResourceNumber(ResourceType.WHEAT) >= 2)
        {
            if(newPlayer.findSettlement(location) == true)
            {
                return true;
            }

        }

            return false;

    }



    /**
     * function to check if a known player can buy a road and put it in a known location
     * @pre: give username of Player in the Game and EdgeLocation of the location where you want to check if a road can be built
     * @post: function checks both person's resources and map's edges and returns whether or not this particular player can build a road at the given edge
     */
    public boolean canPlayerBuyRoad(String username, EdgeLocation edge) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if(newPlayer.returnResourceNumber(ResourceType.WOOD) >= 1 && newPlayer.returnResourceNumber(ResourceType.BRICK) >= 1)
        {
            if(theGame.getTheGameMap().canAddRoad(edge))
            {
                return true;
            }

        }

        return false;
    }



    /**
     * function to check if a known player can trade a certain amount of a particular resource
     * @pre: give username of Player in the Game, ResourceType of the type of desired Resource from this Player, and
     * the number of these resources asked for
     * @post: function checks person's resources of the given type and if they have enough of them to trade for the
     * specified amount, then returns whether or not they can
     */
    public boolean canPlayerAcceptTrade(String username, ResourceType typeRequired, int resourceNumber) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if (newPlayer.returnResourceNumber(typeRequired) >= resourceNumber) {
            return true;
        }
        return false;
    }


    /**
     * function to check if a known player can play a particular type of DevelopmentCard
     * @pre: give username of Player in the Game type of DevelopmentCard to see if the person possesses it
     * @post: function checks Player's Development Cards and returns the truth of whether or not they possess that card
     */
    public boolean canPlayerPlayDevCard(String username, DevCardType type) throws ObjectNotFoundException {
        Player newPlayer = theGame.findPlayer(username);
        if (newPlayer.returnDevCardValue(type) >= 1) {
            return true;
        }
        return false;
    }
    
    public Game gettheGame() {
    	return theGame;
    }
    public void Reinitialize(Game g) {
        theGame = g;
    }

    public void gamesCreate(String s) throws FailedCreateGameException {
        proxy.gamesCreate(s, false, false, false);
    }

    public void gameJoin(int playerId,String s) throws InvalidUserException {
        proxy.gameJoin(playerId,s);
    }

    /**
     * Checks to see if building a road is a legal move for the player
     *
     * @return boolean whether or not the player can build a road
     */
    public boolean canBuildRoad(int playerId) {
        if (theGame == null)
            return false;
        return theGame.canBuildRoad(playerId);
    }

    /**
     * Checks to see if placing a road is a legal move for the player
     *
     * @return boolean whether or not the player can place a road
     */
    public boolean canPlaceRoad(EdgeLocation el) {
        if (theGame == null)
            return false;
        return theGame.canPlaceRoad(el);
    }

    /**
     * Places a Road at a given location on the map
     *
     * @return boolean whether or not the player built the road (perhaps placeholder return values for all of the do methods)
     */
    public void placeRoad(int pid, EdgeLocation el, boolean free) {
        if (theGame != null) {
            if (theGame.canBuildRoad(pid) && theGame.canPlaceRoad(el))
                proxy.buildRoad(pid, el, free);
        }
    }

    /**
     * Checks to see if building a settlement is a legal move for the player
     *
     * @return boolean whether or not the player can build a settlement
     */
    public boolean canBuildSettlement(int pid) {
        if (theGame == null)
            return false;
        return theGame.canBuildSettlement(pid);
    }

    /**
     * Checks to see if placing a settlement is a legal move for the player
     *
     * @return boolean whether or not the player can place a settlement
     */
    public boolean canPlaceSettlement(VertexLocation vl) {
        if (theGame == null)
            return false;
        return theGame.canPlaceSettlement(vl);
    }

    /**
     * Places a Settlement at a given location on the map
     *
     * @return boolean whether or not the player placed a settlement
     */
    public void placeSettlement(int pid, VertexLocation vl, boolean free) throws IllegalBuildException {
        if (theGame != null) {
            if (canPlaceSettlement(vl) && canBuildSettlement(pid))
				proxy.buildSettlement(pid, vl, free);
        }
    }

    /**
     * Checks to see if building a city is a legal move for the player
     *
     * @return boolean whether or not the player can build a city
     */
    public boolean canBuildCity(int pid) {
        if (theGame == null)
            return false;
        return theGame.canBuildCity(pid);
    }

    /**
     * Checks to see if placing a city is a legal move for the player
     *
     * @return boolean whether or not the player can place a city
     */
    public boolean canPlaceCity(VertexLocation vl) {
        if (theGame == null)
            return false;
        return theGame.canPlaceCity(vl);
    }

    /**
     * Places a City at a given location on the map
     *
     * @return boolean whether or not the player placed the city
     */
    public void placeCity(int pid, VertexLocation vl) throws IllegalBuildException {
        if (theGame != null) {
            if (theGame.canBuildCity(pid) && theGame.canPlaceCity(vl))
				proxy.buildCity(pid, vl);
        }
    }

    /**
     * Checks to see if building a road in a specific place is a legal move for the player
     *
     * @return boolean whether or not the player can road building
     */
    public boolean canRoadBuilding(int pid) {
        if (theGame == null)
            return false;
        return theGame.canRoadBuilding(pid);
    }

    /**
     * plays the road build card
     *
     * @return boolean
     */
    public void playRoadBuilding(int pid, EdgeLocation el1, EdgeLocation el2) {
        if (theGame != null) {
            if (theGame.canRoadBuilding(pid) && theGame.canPlaceRoad(el1) && theGame.canPlaceRoad(el2))
                proxy.Road_Building(pid, el1,el2);
        }
    }

    /**
     * Checks to see if placing a Monument Card(?) is a legal move for the player
     *
     * @return boolean whether or not the player can place a monument
     */
    public boolean canUseMonument(int pid) {
        if (theGame == null)
            return false;
        return theGame.canUseMonument(pid);
    }

    public void playMonument(int pid) {
        if (theGame != null)
            proxy.Monument(pid);
    }

    /**
     * Checks to see if placing a Year Of Plenty card is a legal move for the player
     *
     * @return boolean whether or not the player can play the Year of Plenty card
     */
    public boolean canYearOfPlenty(int pid) {
        if (theGame == null)
            return false;
        return theGame.canYearOfPlenty(pid);
    }

    /**
     * plays the year of plenty card for a given player
     *
     * @return boolean whether or not the player played the year of plenty card
     */
    public void playYearOfPlenty(int pid, ResourceType r1, ResourceType r2) {
        if (theGame != null)
            if (theGame.canYearOfPlenty(pid))
                proxy.Year_of_Plenty(pid, r1, r2);
    }

    /**
     * Checks to see if placing a Soldier card is a legal move for the player
     *
     * @return boolean whether or not the player can place the Soldier card
     */
    public boolean canPlaySoldier(int pid) {
        if (theGame == null)
            return false;
        return theGame.canPlaySoldier(pid);
    }

    /**
     * Places a Soldier and grants the effects he brings
     *
     * @return boolean whether or not the player played the soldier card
     */
    public void playSoldier(int pid, int vid, HexLocation hl) throws IllegalMoveException {
        if (theGame != null) {
            if (theGame.canPlaySoldier(pid) && theGame.canRob(pid, vid) && theGame.canMoveRobber(hl))
                proxy.Soldier(pid, vid, hl);
        }
    }

    /**
     * Checks to see if robbing another player is a legal move for the player
     *
     * @return boolean whether or not the player can rob another player
     */
    public boolean canRob(int pid, int vid) {
        if (theGame != null)
            return theGame.canRob(pid, vid);
        return true;
    }

    /**
     * Checks to see if moving the robber is a legal move for the player
     *
     * @return boolean whether or not the player can move the robber
     */
    public boolean canMoveRobber(HexLocation hl) {
        if (theGame == null)
            return false;
        return theGame.canMoveRobber(hl);
    }

    /**
     * Robs a player of one resource card
     *
     * @return boolean whether or not the player chose to rob
     */
    public void rob(int pid, int vid, HexLocation hl) {
        if (pid == -1)
            System.out.println("No player to be robbed?");
        if (theGame != null) {
            if (theGame.canRob(pid, vid) && theGame.canMoveRobber(hl))
                proxy.robPlayer(pid, vid, hl);
        }
    }

    /**
     * Checks to see if trading resources with the bank is a legal move for the player
     *
     * @return boolean whether or not the player can trade with the bank
     */
    public boolean canTradeBank(int pid, ResourceList rl) {
        if (theGame == null)
            return false;
        return theGame.canTradeBank(pid, rl);
    }

    /**
     * completes a transaction of resources with the bank
     *
     * @return boolean whether or not the player traded with the bank
     */
    public void meritimeTrade(int playerId, int ratio, String in, String out) {
        if (proxy != null)
            proxy.maritimeTrade(playerId, ratio, in, out);
    }

    /**
     * Checks to see if the player can roll the dice
     *
     * @return boolean whether or not the player can roll the dice
     */
    public boolean canRoll(int pid) {
        if (theGame == null)
            return false;
        //return theGame.canRoll(pid); /**Jesse Robinson commented this until it would be fixed
        return true;
    }

    /**
     * rolls the dice for a number 1-12
     *EDITED BY JESSE R. TO RETURN AN INT
     * @return boolean whether or not the player rolled the dice
     */
    public int roll(int pid) {
        if (theGame != null) {
            if (canRoll(pid)) {
                //     int number = theGame.roll(pid);
                int number = theGame.rollGameDice();

                //   if (number != -1)
                proxy.rollNumber(pid, number);
                return number;
                //     else
                //         System.out.println("not a rolling phase");

            }
            else
            {
                return 0;
            }
        }
        return 0;
    }

    public boolean canFinishTurn(int pid) {
        if (theGame == null)
            return false;
        return theGame.canFinishTurn(pid);
    }

    public void FinishTurn(int pid) {
        proxy.finishTurn(pid);
    }

    public boolean canDiscardCards(int pid, ResourceList rl) {
        if (theGame == null)
            return false;
        return theGame.canDiscardCards(pid, rl);
    }

    public void DiscardCards(int pid, ResourceList rl) throws InsufficientResourcesException {
        if (theGame != null) {
            if (theGame.canDiscardCards(pid, rl))
				proxy.discardCards(pid, discardedcards);
        }
    }

    /**
     * Checks to see if buying a Developement Card is a legal move for the player
     *
     * @return boolean whether or not the player can buy a Developement card
     */
    public boolean canBuyDevcard(int pid) {
        if (theGame == null)
            return false;
        return theGame.canBuyDevcard(pid);
    }

    /**
     * Buys a developement card and increases the amount for the purchasing player
     *
     * @return boolean whether or not the player bought the dev card
     */
    public void buyDevCard(int pid) throws InsufficientResourcesException {
        if (theGame != null) {
            if (theGame.canBuyDevcard(pid)) {
                proxy.buyDevCard(pid);
            }
        }
    }


    /**
     * Checks to see if Monoply is a legal move for the player
     *
     * @return boolean whether or not the player can monopoly
     */
    public boolean canMonopoly(int pid) {
        if (theGame == null)
            return false;
        return theGame.canMonopoly(pid);
    }

    /**
     * uses Monopoly
     *
     * @return boolean whether or not the player played a monopoly
     */
    public void playMonopoly(int pid, String r) {
        if (theGame != null) {
            if (theGame.canMonopoly(pid))
                proxy.Monopoly(r, pid);
        }
    }


    /**
     * Set up the TradeOffer
     */
    public boolean canTradePlayer(int pid, int rid, ResourceList rl) {
        try {
            if (theGame != null)
                return theGame.canTradePlayer(pid, rid, rl);
        } catch (InsufficientResourcesException e) {
            System.out.println("Not enough resources " + e.getMessage());
        } catch (IllegalMoveException e) {
            System.out.println("An illegal move" + e.getMessage());
        }
        return true;
    }

    /**
     * enacts the trade offer of the specified player
     *
     * @return boolean whether or not the player traded with another player
     */
    public void tradePlayer(int pid, ResourceList rl, int rid) {
        try {
            if (theGame != null) {
                if (theGame.canTradePlayer(pid, rid, rl))
                    proxy.offerTrade(pid, discardedcards,rid);
            }
        } catch (InsufficientResourcesException e) {
            System.out.println("Not enough resources " + e.getMessage());
        } catch (IllegalMoveException e) {
            System.out.println("An illegal move" + e.getMessage());
        }
    }

    /**
     * Checks to see accepting a trade request is a legal move for the player
     *
     * @return boolean whether or not the player can accept a trade offer from another player
     */
    public boolean canAcceptTrade(int pid, int vid, ResourceList offer) {
        if (theGame == null)
            return false;
     //   return theGame.canAcceptTrade(pid);
        return true;
    }

    /**
     * accepts the trade offer of another player
     *
     * @return boolean whether or not the player accepted a trade offer
     */
    public void acceptTrade(int pid, boolean acceptance) {
        if (theGame != null) {
      //      if (theGame.canAcceptTrade(pid))
                proxy.acceptTrade(pid, acceptance);
        }
    }
	public static Facade getSingleton()  {
		if(singleton == null) {
			singleton = new Facade();
		}
		return singleton;	
	}
	
  


}