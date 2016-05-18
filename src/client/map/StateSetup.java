package client.map;

import client.base.*;
import client.data.*;
import client.data.RobPlayerInfo;
import model.*;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
/**
 * @author Jesse McArthur
 */
public class StateSetup extends StateAbstract
{
    private IMapView view;
    private IRobView robView;
    CatanColor color;
    Facade facade;
    boolean setRoad = false, setSettlement = false;

    public StateSetup(IMapView v, IRobView rv)
    {
        view = v;
        robView = rv;
        facade = Facade.getInstance();
        color = facade.getCurrentPlayer().getColor();

        //players start with 15 roads and 5 settlements
        int pid = facade.getPlayerIndex();
        int roads = facade.getGame().getPlayers().get(pid).getRoads();
        int settlements = facade.getGame().getPlayers().get(pid).getSettlements();
      //  cancelMove();
        startMove(PieceType.ROAD, true, true);
    }
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        Facade.getInstance().setCloseMap(false);
        if(setRoad)
            return false;
        return Facade.getInstance().canPlaceRoadSetup(edgeLoc.getNormalizedLocation());
    }
    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        if(setSettlement)
            return false;
      return Facade.getInstance().canPlaceSettlement(vertLoc);
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        return false;
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        return false;
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {
        view.placeRoad(edgeLoc, color);
        setRoad = true;
        Facade.getInstance().placeRoad(Facade.getInstance().getCurrentPlayer().getPlayerIndex(), edgeLoc.getNormalizedLocation(), true, true);
        Facade.getInstance().getGame();
        startMove(PieceType.SETTLEMENT, true, false);
    }
  

    @Override
    public void placeCity(VertexLocation vertLoc) {

    }

    @Override
    public void placeRobber(HexLocation hexLoc) {

    }

  

    @Override
    public void cancelMove() {
     //   view.closeModal();
    }

    @Override
    public void robPlayer(RobPlayerInfo victim) {

    }

    @Override
    public void playSoldierCard(RobPlayerInfo victim) {

    }

    @Override
    public void playRoadBuildingCard() {

    }

    public boolean finishedSetup()
    {
        return (setRoad && setSettlement);
    }
    @Override
    public String getName() {return "Setup"; }
	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		
		
	}
	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		
		
	}
}
