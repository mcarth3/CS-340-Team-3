package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import client.GameManager.GameManager;
import client.base.*;
import client.data.*;
import model.*;
import states.State;
import states.StateEnum;



/**
 * Implementation for the map controller
 * @author Jesse McArthur
 */
public class MapController extends Controller implements IMapController {

    private IRobView robView;
    private StateAbstract state;
    private IMapView mapView;
    private int playing; //needed for debugging when playing by yourself
    private int secondRound = 0;
    private boolean soldier;

    private int roundNum = 0;

    public MapController(IMapView view, IRobView robView) {
        super(view);
        mapView = view;
        setRobView(robView);
        state = new StateDefault(view, robView);
        soldier = false;
    }

    public IMapView getView() {

        return (IMapView) super.getView();
    }

    private IRobView getRobView() {
        return robView;
    }
    
    private void setRobView(IRobView robView) {
        this.robView = robView;
    }

    public boolean finishedSetup() {
        StateSetup s = new StateSetup(getView(), robView);
        if (s.getClass() != state.getClass())
            return false;
        return ((StateSetup) state).finishedSetup();
    }

    private void changeState() {

        Facade facade = Facade.getInstance();
        String s = facade.getGame().getTurnTracker().getStatus();
        int pid = facade.getPlayerID();
         System.out.println(s + " " + pid);

        if (s.equalsIgnoreCase("FirstTurn") || s.equalsIgnoreCase("SecondTurn"))
        {
            state = new StateSetup(getView(), robView);
            if (((StateSetup) state).finishedSetup()) {
                state = new StateDefault(getView(), robView);
                facade.FinishTurn(pid);
            }
        }
        if (s.equalsIgnoreCase("Rolling")) {
            state = new StatePlayersTurn(getView(), robView);
           
        }
        if (s.equalsIgnoreCase("Playing") || s.equalsIgnoreCase("Robbing")) {
            playing++;
            if (playing == 3) {
                playing = 0;
                state = new StateDefault(getView(), robView);

                facade.FinishTurn(pid);
            }
        }


 
    }

    private boolean changeState(String s) {
      

        if(state.getName().equalsIgnoreCase("setup"))
        {
            if(((StateSetup)state).finishedSetup())
            {
                state = new StateDefault(getView(), robView);
                return false;
            }
            if(Facade.getInstance().isCloseMap())
                state = new StateDefault(getView(), robView);
          
        }

        if(Facade.getInstance().getCurrentPlayer().getPlayerIndex() != Facade.getInstance().getGame().getTurnTracker().getCurrentPlayer())
        {
            
            state = new StateDefault(getView(), robView);
            return true;
        }

        if (s.equalsIgnoreCase("RoadBuilding")){
            String test = s;
        }

        if(state.getName().equalsIgnoreCase("RoadBuilding"))
        {
            if(((StateRoadBuilding)state).finished())
                state = new StatePlayersTurn(getView(), robView);
            return true;
        }
           

        if (state.getName().equalsIgnoreCase("default")) {
            if (s.equalsIgnoreCase("FirstRound") || s.equalsIgnoreCase("SecondRound"))
                state = new StateSetup(getView(), robView);
            else if (s.equalsIgnoreCase("RoadBuilding"))
                state = new StateRoadBuilding(getView(), robView);
            else if (s.equalsIgnoreCase("Robbing"))
                state = new StateRobbing(getView(), robView);
            else if (s.equalsIgnoreCase("playing"))
                state = new StatePlayersTurn(getView(), robView);
            else
                state = new StateDefault(getView(), robView);
            
            return true;
        }

        
        if (((s.equalsIgnoreCase("FirstRound") || s.equalsIgnoreCase("SecondRound") && state.getName().equalsIgnoreCase("Setup"))
                || s.equalsIgnoreCase(state.getName())))
        {
            System.out.println(((MapView)getView()).getOverlaid());

                return false;
        }


        
        if (s.equalsIgnoreCase("RoadBuilding"))
            state = new StateRoadBuilding(getView(), robView);
        else if (s.equalsIgnoreCase("Robbing"))
            state = new StateRobbing(getView(), robView);
        else if (s.equalsIgnoreCase("playing"))
            state = new StatePlayersTurn(getView(), robView);
        else
            state = new StateDefault(getView(), robView);
        return true;
    }

    public void update(Observable observable, Object args) {
        if (!Facade.getInstance().isReady())
            return;

        GameManager gm = (GameManager) observable;
        

        String testState = state.getName();

        changeState(gm.getTurnTracker().getStatus());
        Facade.getInstance().setCurPlayerIndex();
        Facade facade = Facade.getInstance();
        Game map = gm.getModel();
    
       
    }


    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        return state.canPlaceRoad(edgeLoc);
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return state.canPlaceSettlement(vertLoc);
    }

    public boolean canPlaceCity(VertexLocation vertLoc) {
        return state.canPlaceCity(vertLoc);
}

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return state.canPlaceRobber(hexLoc);
    }

    public void placeRoad(EdgeLocation edgeLoc) {
        state.placeRoad(edgeLoc);

    }

    public void placeSettlement(VertexLocation vertLoc) {
        state.placeSettlement(vertLoc);
    }

    public void placeCity(VertexLocation vertLoc) {
        state.placeCity(vertLoc);
;
    }

    public void placeRobber(HexLocation hexLoc) {
        mapView.placeRobber(hexLoc);
        state.placeRobber(hexLoc);
        getView().placeRobber(hexLoc);

    }

    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
        state.startMove(pieceType, isFree, allowDisconnected);
    }

    public void cancelMove() {
        state.cancelMove();
    }

    public void playSoldierCard()
    {
        state = new StateRobbing(getView(), robView);
        soldier = true;
    }

    public void playRoadBuildingCard()
    {
        state = new StateRoadBuilding(getView(), robView);
        state.playRoadBuildingCard();
    }

    public void robPlayer(RobPlayerInfo victim) {
        if(soldier){
            soldier = false;
            state.playSoldierCard(victim);
        }else{
            state.robPlayer(victim);
        }

    }
}
