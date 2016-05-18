package client.map;

import client.data.PlayerInfo;
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
public class StateRoadBuilding extends StateAbstract
{
    int RoadsLaid = 0;
    private IMapView view;
    CatanColor color;

    public StateRoadBuilding(IMapView v, IRobView robView)
    {
        view = v;
        color = Facade.getInstance().getCurrentPlayer().getColor();
     //   color = facade.getCatanColor();
    }

    public int getRoadsLaid()
    {
        return RoadsLaid;
    }
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return Facade.getInstance().canPlaceRoad(edgeLoc.getNormalizedLocation(), true);
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return false;
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
        Facade.getInstance().placeRoad(Facade.getInstance().getCurrentPlayer().getPlayerId(), edgeLoc.getNormalizedLocation(), true, true);
        Facade.getInstance().getGame();
        if(RoadsLaid == 0)
            startMove(PieceType.ROAD, true, false);
        RoadsLaid++;
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc) {

    }

    @Override
    public void placeCity(VertexLocation vertLoc) {

    }

    @Override
    public void placeRobber(HexLocation hexLoc) {

    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {
        view.startDrop(pieceType, color, false);
    }

    @Override
    public void cancelMove() {

    }


    @Override
    public void playSoldierCard(RobPlayerInfo victim) {

    }

    @Override
    public void playRoadBuildingCard() {
        startMove(PieceType.ROAD, true, false);
    }

    @Override
    public String getName() { return "RoadBuilding"; }
//    public void cancelMove() {}

    public boolean finished()
    {
        if(getRoadsLaid() == 2)
            return true;
        return false;
    }

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// TODO Auto-generated method stub
		
	}
}
