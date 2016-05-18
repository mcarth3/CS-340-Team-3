package client.map;


import client.data.RobPlayerInfo;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
/**
 * @author Jesse McArthur
 */
public abstract class StateAbstract
{
//don't implement . . .
    abstract public boolean canPlaceRoad(EdgeLocation edgeLoc);
    abstract public boolean canPlaceSettlement(VertexLocation vertLoc);
    abstract public boolean canPlaceCity(VertexLocation vertLoc);
    abstract public boolean canPlaceRobber(HexLocation hexLoc);
    abstract public void placeRoad(EdgeLocation edgeLoc);
    abstract public void placeSettlement(VertexLocation vertLoc);
    abstract public void placeCity(VertexLocation vertLoc);
    abstract public void placeRobber(HexLocation hexLoc);
    abstract public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected);
    abstract public void cancelMove();
    abstract public void robPlayer(RobPlayerInfo victim);
    abstract public void playSoldierCard(RobPlayerInfo victim);
    abstract public void playRoadBuildingCard();
    abstract public String getName();
}
