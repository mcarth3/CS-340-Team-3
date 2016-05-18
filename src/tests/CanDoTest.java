package tests;

import java.util.ArrayList;
import java.util.TreeMap;

import model.bank.DevCardList;
import model.bank.ResourceList;
import org.junit.Test;
import model.*;
import proxy.*;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.*;
/**
 * @author Jesse McArthur
 */
public class CanDoTest
{
    Facade f;
    IServer p;

    public void initializeEmpty()
    {
        f = new Facade();
        p = new RealProxy(new Game());
        f.Reinitialize(new Game());
    }

    //create a full game
    public void initializeFull()
    {
        f = new Facade();
        ResourceList b = new ResourceList(13, 16, 12, 16, 18);
        TurnTracker tt = new TurnTracker(3, "roll");
        tt.setLongestRoad(1);

        Player p1 = new Player(CatanColor.RED,"JesseM", 0);
        p1.setCities(3);
        p1.setSettlements(3);
        p1.setRoads(9);
        p1.setNewDevCards(new DevCardList(0, 0, 0 , 2, 0));
        p1.setResources(new ResourceList(0, 3, 0, 2, 0));

        Player p2 = new Player(CatanColor.GREEN,"Jesse", 1);
        p2.setCities(3);
        p2.setSettlements(3);
        p2.setRoads(13);
        p2.setOldDevCards(new DevCardList(1, 0, 0, 1, 0));
        p2.setResources(new ResourceList(1, 1, 1, 1, 1));

        Player p3 = new Player(CatanColor.BLUE,"Mike", 2);
        p3.setCities(4);
        p3.setSettlements(3);
        p3.setRoads(10);
        p3.setNewDevCards(new DevCardList(0, 1, 0, 0, 1));
        p3.setResources(new ResourceList(0, 1, 1, 1 , 0));

        Player p4 = new Player(CatanColor.WHITE,"Nathan", 3);
        p4.setCities(4);
        p4.setSettlements(2);
        p4.setRoads(12);
        p4.setNewDevCards(new DevCardList(0, 0, 1, 1, 0));
        p4.setResources(new ResourceList(0, 0, 3, 2, 1));

        ArrayList<Player> ps = new ArrayList<Player>();
        ps.add(p1);
        ps.add(p2);
        ps.add(p3);
        ps.add(p4);

        Map m = new Map();
        TreeMap<HexLocation, Hex> hexes = new TreeMap<HexLocation, Hex>();
        hexes.put(new HexLocation(-1,-1), new Hex());
        hexes.put(new HexLocation(3,4), new Hex("BRICK", 9));
        hexes.put(new HexLocation(4,2), new Hex("ORE", 4));
     //   m.setHexes(hexes);
        Robber r = new Robber();
        r.setHl(new HexLocation(3,4));
        m.setRobber(r);

        ArrayList<VertexObject> bldgs = new ArrayList<VertexObject>();
        bldgs.add(new City(new VertexLocation(new HexLocation(1, 2), VertexDirection.NorthWest), 0));
        bldgs.add(new City(new VertexLocation(new HexLocation(3, 2), VertexDirection.NorthEast), 1));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(2, 4), VertexDirection.East), 0));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(1, 4), VertexDirection.East), 1));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(2, 3), VertexDirection.NorthEast), 2));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(2, 3), VertexDirection.NorthWest), 2));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(4, 3), VertexDirection.East), 3));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(4, 3), VertexDirection.West), 3));
        bldgs.add(new Settlement(new VertexLocation(new HexLocation(5, 1), VertexDirection.West), 3));
      //  m.setBuildings(bldgs);

        ArrayList<Port> ports = new ArrayList<Port>();
        Port p = new Port(5, 1, EdgeDirection.NW, 3);
        p.setOwner(3);
        ports.add(p);
        p = (new Port(1, 2, "Wood", EdgeDirection.SE, 2));
        p.setOwner(0);
        ports.add(p);
        p = (new Port(3, 4, "Wheat", EdgeDirection.NW, 2));
        p.setOwner(1);
        ports.add(p);
        m.setPorts(ports);

        Game g = new Game(m, b, ps, tt, new TradeOffer());
        f.Reinitialize(g);
    }

    @Test
    public void testCanBuildSettlement()
    {
        initializeFull();
        TurnTracker tt = f.gettheGame().getTurnTracker();
        tt.setCurrentPlayer(1);
        f.gettheGame().setTt(tt);

        assert(f.canBuildSettlement(1));
        assert(!f.canBuildSettlement(0));

        tt.setCurrentPlayer(0);
        f.gettheGame().setTt(tt);
        assert(!f.canBuildSettlement(0));

        System.out.println("Can Build Settlement - can Do, Not turn, Insufficent Materials");
    }

    @Test
    public void testCanBuildRoad()
    {
        initializeFull();
        TurnTracker tt = f.gettheGame().getTurnTracker();
        tt.setCurrentPlayer(1);
        f.gettheGame().setTt(tt);

        assert(f.canBuildRoad(1));
        assert(!f.canBuildRoad(0));

        tt.setCurrentPlayer(0);
        f.gettheGame().setTt(tt);
        assert(!f.canBuildRoad(0));

        System.out.println("Can Build Road - can Do, Not turn, Insufficent Materials");

    }

    @Test
    public void testCanBuildCity()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);

        assert(f.canBuildCity(1));
        assert(!f.canBuildCity(0));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().setTt(turnTracker);
        assert(!f.canBuildCity(0));

        System.out.println("Can Build City - can Do, Not turn, Insufficient Materials");

    }


    @Test
    public void testCanDiscardCards()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        ResourceList resourceList = new ResourceList(1,5,2,6,4);
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        assert(f.canDiscardCards(1,resourceList));
        assert(!f.canDiscardCards(0,resourceList));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().setTt(turnTracker);
        ResourceList emptyList = new ResourceList(0,0,0,0,0);
        f.gettheGame().getPlayers().get(0).setResources(emptyList);
        assert(!f.canDiscardCards(0,resourceList));

    }


    @Test
    public void testCanRollNumber()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);

        assert (f.canRoll(1));
        assert (!f.canRoll(0));
    }


    @Test
    public void canOfferTrade()
    {
        initializeFull();
        
        assert(!f.canTradePlayer(0,1, new ResourceList(-1, 1, 0, 0, 0)));
        assert(f.canTradePlayer(0,1, new ResourceList(0, -1, 1, 0, 0)));
        
        System.out.println("Can Offer Trade - can Do, Not turn, Insufficent Materials");
    }
    @Test
    public void canMaritimeTrade()
    {
        initializeFull();
        
        assert(f.canTradeBank(0, new ResourceList(0, 3, 0, 2, 0)));
        assert(!f.canTradeBank(1, new ResourceList(1, 1, 1, 1, 1)));
        
        System.out.println("Can Offer Trade - can Do, Not turn, Insufficent Materials");
    }
    @Test
    public void canFinishTurn()
    {
        //player 3 cannot finish turn on "roll" status so it declines
        assert(!f.canFinishTurn(3));
        
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.updateStatus("finish");
        f.gettheGame().setTt(turnTracker);
        //changes player 3's status to finish and allows it to return true
        assert(f.canFinishTurn(3));
    }
    @Test
    public void canBuyDevCard()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        ResourceList resourceList = new ResourceList(1,5,2,6,4);
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        assert(f.canBuyDevcard(1));
        assert(!f.canBuyDevcard(0));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().setTt(turnTracker);
        ResourceList emptyList = new ResourceList(0,0,0,0,0);
        f.gettheGame().getPlayers().get(0).setResources(emptyList);
        assert(!f.canBuyDevcard(0));

        System.out.println("Can buy DevCard - can Do, Not turn, Insufficient Materials");
    }
    @Test
    public void canUseYearOfPlenty()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        f.gettheGame().getPlayers().get(1).getOldDevCards().setYearOfPlenty(5);
        assert(f.canYearOfPlenty(1));
        assert(!f.canYearOfPlenty(0));

        f.gettheGame().getPlayers().get(0).getOldDevCards().setYearOfPlenty(0);
        turnTracker.setCurrentPlayer(0);
        f.gettheGame().setTt(turnTracker);
        assert(!f.canYearOfPlenty(0));

        System.out.println("Can use YearOfPlenty - can Do, Not turn, Insufficient Materials");
    }
    @Test
    public void canUseRoadBuilding()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        f.gettheGame().getPlayers().get(1).getOldDevCards().setRoadBuilding(5);
        assert(f.canRoadBuilding(1));
        assert(!f.canRoadBuilding(0));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().getPlayers().get(0).getOldDevCards().setRoadBuilding(0);
        f.gettheGame().setTt(turnTracker);
        assert(!f.canRoadBuilding(0));

        System.out.println("Can use RoadBuilding card - can Do, Not turn, Insufficient Materials");
    }
    @Test
    public void canUseSoldier()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        f.gettheGame().getPlayers().get(1).getOldDevCards().setSoldier(5);
        assert(f.canPlaySoldier(1));
        assert(!f.canPlaySoldier(0));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().getPlayers().get(0).getOldDevCards().setSoldier(0);
        f.gettheGame().setTt(turnTracker);
        assert(!f.canPlaySoldier(0));

        System.out.println("Can use Soldier card - can Do, Not turn, Insufficient Materials");
    }
    @Test
    public void canUseMonopoly()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        f.gettheGame().getPlayers().get(1).getOldDevCards().setMonopoly(5);
        assert(f.canMonopoly(1));
        assert(!f.canMonopoly(0));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().getPlayers().get(0).getOldDevCards().setMonopoly(0);
        f.gettheGame().setTt(turnTracker);
        assert(!f.canMonopoly(0));

        System.out.println("Can use Montopoly card - can Do, Not turn, Insufficient Materials");
    }
    @Test
    public void canUseMonument()
    {
        initializeFull();
        TurnTracker turnTracker = f.gettheGame().getTurnTracker();
        turnTracker.setCurrentPlayer(1);
        f.gettheGame().setTt(turnTracker);
        f.gettheGame().getPlayers().get(1).getOldDevCards().setMonument(5);
        assert(f.canUseMonument(1));
        assert(!f.canUseMonument(0));

        turnTracker.setCurrentPlayer(0);
        f.gettheGame().getPlayers().get(0).getOldDevCards().setMonument(0);
        f.gettheGame().setTt(turnTracker);
        assert(!f.canUseMonument(0));

        System.out.println("Can use Soldier card - can Do, Not turn, Insufficient Materials");
    }
    @Test
    public void canMoveRobber()
    {
        initializeFull();
        TurnTracker tt = f.gettheGame().getTurnTracker();
        tt.setCurrentPlayer(1);
        f.gettheGame().setTt(tt);
        
        assert(!f.canMoveRobber(new HexLocation(-1,-1)));
        assert(!f.canMoveRobber(new HexLocation(3,4)));
        assert(f.canMoveRobber(new HexLocation(4,2)));
        
    }

}