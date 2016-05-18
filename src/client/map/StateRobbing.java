package client.map;

import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import model.*;
import proxy.*;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jesse McArthur
 */
public class StateRobbing extends StateAbstract
{
    private IMapView view;
    private IRobView robView;
    private HexLocation hl;
    private int victim;
    private List<VertexObject> objects = new ArrayList<>();


    public StateRobbing(IMapView v, IRobView rv)
    {
        view = v;
        robView = rv;
        v.startDrop(PieceType.ROBBER, CatanColor.BLUE, false);
    }

    //if soldier is played can the cancelMove possibly be called, because during a roll 7 it can
//    public void cancelMove(){}
    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        if (victim == null) {
            Facade.getInstance().rob(Facade.getInstance().getPlayerIndex(), -1, hl);
        } else {
            this.victim = victim.getPlayerIndex();
            int pid = Facade.getInstance().getPlayerIndex();
            Facade.getInstance().rob(pid, victim.getPlayerIndex(), hl);
            robView.closeModal();
        }
    }

    @Override
    public void playSoldierCard(RobPlayerInfo victim) {
        this.victim = victim.getPlayerIndex();
        int pid = Facade.getInstance().getPlayerIndex();
        Facade.getInstance().rob(pid, victim.getPlayerIndex(), hl);
        robView.closeModal();
    }

    @Override
    public void playRoadBuildingCard() {

    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        return false;
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
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        objects = Facade.getInstance().getVObjectsAroundHexlocation(hexLoc);
        return Facade.getInstance().canMoveRobber(hexLoc);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc) {

    }

    @Override
    public void placeSettlement(VertexLocation vertLoc) {

    }

    @Override
    public void placeCity(VertexLocation vertLoc) {

    }

    @Override
    public void placeRobber(HexLocation hexLoc)
    {
        hl=hexLoc;
        Set<Integer> people = new HashSet<Integer>();
        for(VertexObject obj : objects)
        {

            people.add(obj.getOwner());
        }
        ArrayList<RobPlayerInfo> players = new ArrayList<RobPlayerInfo>();
        int i=0;
        int j=0;
        for(int x : people)
        {
            if (x != Facade.getInstance().getCurrentPlayer().getPlayerIndex() && Facade.getInstance().getGame().getPlayers().get(x).getResources().getSize() > 0) {
                ArrayList<Player> playas = Facade.getInstance().getGame().getPlayers();
                for (Player player : playas) {
                    if (player.getPlayerIndex() == x && player.getResources().getSize() > 0) {

                        players.add(new RobPlayerInfo());
                        j++;
                    }
                }
            }
            i++;
        }
        RobPlayerInfo[] realplz = new RobPlayerInfo[players.size()];
        int k = 0;
        for (RobPlayerInfo player : players) {
            realplz[k] = player;
            k++;
        }
        robView.setPlayers(realplz);
        robView.showModal();
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {

    }

    @Override
    public void cancelMove() {

    }

    @Override
    public String getName()   {        return "Robbing";    }

    public HexLocation getCurrentHL() {
        return hl;
    }


}
