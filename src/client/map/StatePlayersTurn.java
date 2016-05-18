package client.map;

import client.base.OverlayView;
import client.data.RobPlayerInfo;
import model.*;
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
 * @author Jesse McArthur.
 */
public class StatePlayersTurn extends StateAbstract {
    private IMapView view;
    private CatanColor color;
    private IRobView robView;
    private List<VertexObject> objects = new ArrayList<>();
    private HexLocation RobberHL;

    public StatePlayersTurn(IMapView v, IRobView rv) {
        view = v;
        color = Facade.getInstance().getCurrentPlayer().getColor();
        robView = rv;
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        return Facade.getInstance().canPlaceRoad(edgeLoc.getNormalizedLocation(), true);
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return Facade.getInstance().canPlaceSettlement(vertLoc);
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        return Facade.getInstance().canPlaceCity(vertLoc);
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        objects = Facade.getInstance().getVObjectsAroundHexlocation(hexLoc);
        return Facade.getInstance().canMoveRobber(hexLoc);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc) {
        System.out.println(color.toString());
        view.placeRoad(edgeLoc, color);
        Facade.getInstance().placeRoad(Facade.getInstance().getCurrentPlayer().getPlayerIndex(), edgeLoc.getNormalizedLocation(), false, true);
        Facade.getInstance().getGame();
          ((OverlayView) view).closeModal();
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc) {
        view.placeSettlement(vertLoc, color);
        try {
			Facade.getInstance().placeSettlement(Facade.getInstance().getCurrentPlayer().getPlayerIndex(), vertLoc.getNormalizedLocation(), false);
		} catch (IllegalBuildException e) {
			
			e.printStackTrace();
		}
        Facade.getInstance().getGame();
        ((OverlayView) view).closeModal();
    }

    @Override
    public void placeCity(VertexLocation vertLoc) {
        view.placeCity(vertLoc, color);
        try {
			Facade.getInstance().placeCity(Facade.getInstance().getCurrentPlayer().getPlayerIndex(), vertLoc.getNormalizedLocation());
		} catch (IllegalBuildException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ((OverlayView) view).closeModal();
    }

    @Override
    public void placeRobber(HexLocation hexLoc) {
        if(hexLoc != null){
            RobberHL = hexLoc;
            Set<Integer> people = new HashSet<Integer>();
            for (VertexObject obj : objects) {
                people.add(obj.getOwner());
            }
            ArrayList<RobPlayerInfo> players = new ArrayList<RobPlayerInfo>();
            int i = 0;
            int j = 0;
            for (int x : people) {
                if (x != Facade.getInstance().getCurrentPlayer().getPlayerIndex()) {
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
            robView.setPlayers(players.toArray(new RobPlayerInfo[players.size()]));
            robView.showModal();
        }
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
        view.startDrop(pieceType, color, true);
    }

    @Override
    public void cancelMove() {
        ((OverlayView) view).closeModal();
    }

    @Override
    public void robPlayer(RobPlayerInfo victim) {
        int vid;
        if (victim != null) {
            vid = victim.getPlayerIndex();
        }else{
            vid = -1;
        }

        int pid = Facade.getInstance().getPlayerIndex();
        Facade.getInstance().rob(pid, vid, RobberHL);
        robView.closeModal();
    }

    @Override
    public void playSoldierCard(RobPlayerInfo victim) {
        int vid = victim.getPlayerIndex();
        int pid = Facade.getInstance().getPlayerIndex();
        try {
            Facade.getInstance().playSoldier(pid, victim.getPlayerIndex(), RobberHL);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        robView.closeModal();
    }

    @Override
    public void playRoadBuildingCard() {
    }

    @Override
    public String getName() {
        return "Playing";
    }
}

