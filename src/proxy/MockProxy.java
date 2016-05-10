package proxy;

import java.util.ArrayList;
import javax.annotation.Resource;
import model.*;
import proxy.objects.*;
import shared.locations.*; 

public class MockProxy implements IServer{

	private static MockProxy singleton = null;
	
	/**
	 * The RealServerProxy follows the singleton pattern.
	 * @pre none
	 * @post a single static instance of the MockServerProxy.
	 * @return a singleton to the MockServerProxy
	 */

	public static MockProxy getSingleton() {
		if(singleton == null) {
			singleton = new MockProxy();
		}
		return singleton;
	}
	
	////-------- NON MOVE API ------------
	@Override
	public String userLogin(String username, String password) { 
		//userLoginInput
		//userLoginOutput
		return "Success"; 
	}

	@Override
	public String userRegister(String username, String password) {
		//userRegisterInput
		//userRegisterOutput		
		return "Success";
	}

	@Override
	public void gamesList() {
		// TODO Auto-generated method stub
		ArrayList<Game> games = new ArrayList();
		
		Game g1 = new Game(); 
		g1.title = "Default Game"; 
		g1.id = 0;
		ArrayList<Player> list1 = new ArrayList();
		Player p1a = new Player("orange", "Sam", 0);
		Player p1b = new Player("blue", "Brooke", 1);
		Player p1c = new Player("red", "Pete", 10);
		Player p1d = new Player("green", "Mark", 11);
		list1.add(p1a);
		list1.add(p1b);
		list1.add(p1c);
		list1.add(p1d);
		g1.players = list1; 
		
		Game g2 = new Game();
		g2.title = "AI Game";
		g2.id = 1; 
		ArrayList<Player> list2 = new ArrayList();
		Player p2a = new Player("orange", "Pete", 10);
		Player p2b = new Player("puce", "Miguel", -2);
		Player p2c = new Player("green", "Scott", -3);
		Player p2d = new Player("purple", "Hannah", -4);
		list2.add(p2a);
		list2.add(p2b);
		list2.add(p2c);
		list2.add(p2d);
		g2.players = list2; 
		
		Game g3 = new Game();
		g3.title = "Empty Game";
		g3.id = 2; 
		ArrayList<Player> list3 = new ArrayList();
		Player p3a = new Player("orange", "Sam", 0);
		Player p3b = new Player("blue", "Brooke", 1);
		Player p3c = new Player("red", "Pete", 10);
		Player p3d = new Player("green", "Mark", 11);
		list3.add(p3a);
		list3.add(p3b);
		list3.add(p3c);
		list3.add(p3d);
		g3.players = list3; 
		
		games.add(g1);
		games.add(g2);
		games.add(g3);
		
		//return games; 
	}

	@Override
	public void gamesCreate(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub		
		Game g = new Game();
		g.title = "My New Game";
		g.id = 3;
		ArrayList<Player> list = new ArrayList();
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		g.players = list; 
		
		//return g; 		
	}

	@Override
	public void gameJoin(Integer gameID, String color) {
		// TODO Auto-generated method stub
		
		//return Success
	}

	@Override
	public void gamesSave(Integer gameID, String fileName) {
		// TODO Auto-generated method stub
		
		//return Success
	}

	@Override
	public void gamesLoad(String fileName) {
		// TODO Auto-generated method stub

		// i don't know
	}

	@Override
	public void gameModel(Integer versionNumber) {
		// TODO Auto-generated method stub
		
		
//		{
//			  "bank": {
//			    "brick": "integer",
//			    "ore": "integer",
//			    "sheep": "integer",
//			    "wheat": "integer",
//			    "wood": "integer"
//			  },
//			  "chat": {
//			    "lines": [
//			      {
//			        "message": "string",
//			        "source": "string"
//			      }
//			    ]
//			  },
//			  "log": {
//			    "lines": [
//			      {
//			        "message": "string",
//			        "source": "string"
//			      }
//			    ]
//			  },
//			  "map": {
//			    "hexes": [
//			      {
//			        "location": {
//			          "x": "integer",
//			          "y": "integer"
//			        },
//			        "resource": "string",
//			        "number": "integer"
//			      }
//			    ],
//			    "ports": [
//			      {
//			        "resource": "string",
//			        "location": {
//			          "x": "integer",
//			          "y": "integer"
//			        },
//			        "direction": "string",
//			        "ratio": "integer"
//			      }
//			    ],
//			    "roads": [
//			      {
//			        "owner": "index",
//			        "location": {
//			          "x": "integer",
//			          "y": "integer",
//			          "direction": "string"
//			        }
//			      }
//			    ],
//			    "settlements": [
//			      {
//			        "owner": "index",
//			        "location": {
//			          "x": "integer",
//			          "y": "integer",
//			          "direction": "string"
//			        }
//			      }
//			    ],
//			    "cities": [
//			      {
//			        "owner": "index",
//			        "location": {
//			          "x": "integer",
//			          "y": "integer",
//			          "direction": "string"
//			        }
//			      }
//			    ],
//			    "radius": "integer",
//			    "robber": {
//			      "x": "integer",
//			      "y": "integer"
//			    }
//			  },
//			  "players": [
//			    {
//			      "cities": "index",
//			      "color": "string",
//			      "discarded": "boolean",
//			      "monuments": "index",
//			      "name": "string",
//			      "newDevCards": {
//			        "monopoly": "index",
//			        "monument": "index",
//			        "roadBuilding": "index",
//			        "soldier": "index",
//			        "yearOfPlenty": "index"
//			      },
//			      "oldDevCards": {
//			        "monopoly": "index",
//			        "monument": "index",
//			        "roadBuilding": "index",
//			        "soldier": "index",
//			        "yearOfPlenty": "index"
//			      },
//			      "playerIndex": "index",
//			      "playedDevCard": "boolean",
//			      "playerID": "integer",
//			      "resources": {
//			        "brick": "integer",
//			        "ore": "integer",
//			        "sheep": "integer",
//			        "wheat": "integer",
//			        "wood": "integer"
//			      },
//			      "roads": "index",
//			      "settlements": "integer",
//			      "soldiers": "integer",
//			      "victoryPoints": "integer"
//			    }
//			  ],
//			  "tradeOffer": {
//			    "sender": "integer",
//			    "receiver": "integer",
//			    "offer": {
//			      "brick": "integer",
//			      "ore": "integer",
//			      "sheep": "integer",
//			      "wheat": "integer",
//			      "wood": "integer"
//			    }
//			  },
//			  "turnTracker": {
//			    "currentTurn": "index",
//			    "status": "string",
//			    "longestRoad": "index",
//			    "largestArmy": "index"
//			  },
//			  "version": "index",
//			  "winner": "index"
//			}
		
	}

	@Override
	public void gameReset(Player user, Game game) {
		// TODO Auto-generated method stub
		
		// i don't know
	}

	@Override
	public void gameCommandsGet(Player user, Game game) {
		// TODO Auto-generated method stub
		
		// i don't know
	}

	@Override
	public void gameCommandsPost(Player user, Game game) {
		// TODO Auto-generated method stub
		
		// i don't know
	}

	@Override
	public void gameListAI() {
		// TODO Auto-generated method stub
		
//		[
//		  "LARGEST_ARMY"
//		]
	}

	@Override
	public void gameAddAI(String AIType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void utilChangeLogLevel(String loggingLevel) {
		// TODO Auto-generated method stub
		
	}
	
	
	////-------- MOVE API ------------
	@Override
	public void sendChat(Integer playerIndex, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptTrade(Integer playerIndex, Boolean willAccept) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discardCards(Integer playerIndex, ArrayList discardedCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollNumber(Integer playerIndex, Integer number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildRoad(Integer playerIndex, EdgeLocation roadLocation, Boolean free) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildSettlement(Integer playerIndex, VertexLocation vertexLocation, Boolean free) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildCity(Integer playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerTrade(Integer playerIndex, ArrayList offer, Integer receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maritimeTrade(Integer playerIndex, Integer ratio, String inputResource, String outputResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(Integer playerIndex, Integer victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishTurn(Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyDevCard(Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Soldier(Integer playerIndex, Integer victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Year_of_Plenty(Integer playerIndex, Resource resource1, Resource resource2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Road_Building(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Monopoly(String resource, Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Monument(Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}
	

}
