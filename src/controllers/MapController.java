package controllers;


import shared.locations.*;

import java.util.ArrayList;
import java.util.Observable;


/**
 * Implementation for the map controller
 *
 * Created by Jesse M.
 */
public class MapController {



    public MapController() {
      
    }

 

  

    public boolean finishedSetup() {
       return true;
    }

    private void changeState() {

      

      
    }

    private boolean changeState(String s) {
 
        return true;
    }

    public void update(Observable observable, Object args) {

    }


    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        return true;
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return true;
    }

    public boolean canPlaceCity(VertexLocation vertLoc) {
        return true;
}

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return true;
    }

    public void placeRoad(EdgeLocation edgeLoc) {
       
    }

    public void placeSettlement(VertexLocation vertLoc) {
       
    }

    public void placeCity(VertexLocation vertLoc) {
        
    }

    public void placeRobber(HexLocation hexLoc) {
 
    }

    public void startMove(String pieceType, boolean isFree, boolean allowDisconnected) {
        
    }

    public void cancelMove() {
     
    }

    public void playSoldierCard()
    {
        
    }

    public void playRoadBuildingCard()
    {
       
    }

    public void robPlayer(String victim) {


    }
}