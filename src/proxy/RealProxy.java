package proxy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import model.Game;
import model.Player;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class RealProxy implements IServer {

	private static RealProxy singleton = null;
	private ClientCommunicator cc = new ClientCommunicator();
	public String UserCookie = null;
	public String GameCookie = null;
	// %7B%22authentication%22%3A%222680927%22%2C%22name%22%3A%22SAM%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A12%7D

	public RealProxy(Game game) {
		// TODO Auto-generated constructor stub
	}

	public RealProxy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String userLogin(String username, String password) {
		//// Implement this one 
		JsonObject obj = new JsonObject();
		obj.addProperty("username", username);
		obj.addProperty("password", password);
		String cook = cc.send(obj, "/user/login", UserCookie, GameCookie);

		if (cook != null) {
			String answer = cook.substring(0, 7);
			cook = cook.replaceAll("Successcatan.user=", "");
			cook = cook.replaceAll(";Path=/;", "");
			//System.out.println(cook); 
			UserCookie = cook;
			String result = null;
			try {
				result = java.net.URLDecoder.decode(cook, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(result);
			return result;
		} else {
			return null;
		}

	}

	@Override
	public String userRegister(String username, String password) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
		obj.addProperty("username", username);
		obj.addProperty("password", password);
		return cc.send(obj, "/user/register", UserCookie, GameCookie);
	}

	@Override
	public String gamesList() {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
		String result = cc.send(obj, "/games/list", UserCookie, GameCookie);

		return result;
	}

	@Override
	public String gamesCreate(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
		obj.addProperty("randomTiles", randomTiles);
		obj.addProperty("randomNumbers", randomNumbers);
		obj.addProperty("randomPorts", randomPorts);
		obj.addProperty("name", name);
		String result = cc.send(obj, "/games/create", UserCookie, GameCookie);

		return result;
	}

	@Override
	public String gameJoin(Integer gameID, String color) {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", gameID);
		obj.addProperty("color", color);

		//System.out.println("User Cookie i am sending in:");
		//System.out.println(UserCookie);

		String cook = cc.send(obj, "/games/join", UserCookie, GameCookie);
		//System.out.println("game cookie being set to "+cook);
		if (cook != null) {
			String answer = cook.substring(0, 7);
			cook = cook.replaceAll("Successcatan.game=", "");
			cook = cook.replaceAll(";Path=/;", "");

			GameCookie = cook;

//			String result = null;
//			try {
//				result = java.net.URLDecoder.decode(cook, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			//System.out.println(result);
			return answer;
		} else {
			return null;
		}
	}

	@Override
	public String gamesSave(Integer gameID, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gamesLoad(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gameModel(Integer versionNumber) {
		JsonObject obj = new JsonObject();
		//System.out.println("obj sent "+obj);
		//System.out.println("user cookie " +UserCookie);
		//System.out.println("game cookie " +GameCookie);
		return cc.send(obj, "/game/model?version=" + versionNumber, UserCookie, GameCookie);
	}

	public String gameModel() {
		JsonObject obj = new JsonObject();
		return cc.send(obj, "/game/model?version=" + (-1), UserCookie, GameCookie);
	}

	@Override
	public String gameReset(Player user, Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gameCommandsGet(Player user, Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gameCommandsPost(Player user, Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gameListAI() {
		JsonObject obj = new JsonObject();
		return cc.send(obj, "/game/listAI", UserCookie, GameCookie);
	}

	@Override
	public String gameAddAI(String AIType) {
		JsonObject obj = new JsonObject();
		obj.addProperty("AIType", AIType);
		return cc.send(obj, "/game/addAI", UserCookie, GameCookie);
	}

	@Override
	public String utilChangeLogLevel(String loggingLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendChat(Integer playerIndex, String content) {
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "sendChat");
		obj.addProperty("playerIndex", playerIndex);
		obj.addProperty("content", content);
		String result = cc.send(obj, "/moves/sendChat", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String acceptTrade(Integer playerIndex, Boolean willAccept) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "acceptTrade");
		obj.addProperty("playerIndex", playerIndex);
		obj.addProperty("willAccept", willAccept);
		String result = cc.send(obj, "/moves/acceptTrade", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String discardCards(Integer playerIndex, ArrayList discardedCards) {
		// TODO Auto-generated method stub
		//// Implement this one
//			  "discardedCards"  in this order. THIS MIGHT CHANGE LATER
//			    "brick": 1,
//			    "ore": 2,
//			    "sheep": 0,
//			    "wheat": 0,
//			    "wood": 1
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "discardCards");
		obj.addProperty("playerIndex", playerIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("brick", discardedCards.get(0).toString());
		obj2.addProperty("ore", discardedCards.get(1).toString());
		obj2.addProperty("sheep", discardedCards.get(2).toString());
		obj2.addProperty("wheat", discardedCards.get(3).toString());
		obj2.addProperty("wood", discardedCards.get(4).toString());
		obj.add("discardedCards", obj2);

		String result = cc.send(obj, "/moves/discardCards", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String rollNumber(Integer playerIndex, Integer number) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "rollNumber");
		obj.addProperty("playerIndex", playerIndex);
		obj.addProperty("number", number);
		String result = cc.send(obj, "/moves/rollNumber", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String buildRoad(Integer playerIndex, EdgeLocation roadLocation, Boolean free) {
		// TODO Auto-generated method stub

		//// Implement this one		
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "buildRoad");
		obj.addProperty("playerIndex", playerIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("x", roadLocation.getHexLoc().getX());
		obj2.addProperty("y", roadLocation.getHexLoc().getY());
		obj2.addProperty("direction", roadLocation.getDir().name());
		obj.add("roadLocation", obj2);
		obj.addProperty("free", free);
		System.out.println("sending " + obj);
		String result = cc.send(obj, "/moves/buildRoad", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String buildSettlement(Integer playerIndex, VertexLocation vertexLocation, Boolean free) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "buildSettlement");
		obj.addProperty("playerIndex", playerIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("x", vertexLocation.getHexLoc().getX());
		obj2.addProperty("y", vertexLocation.getHexLoc().getY());
		obj2.addProperty("direction", vertexLocation.getDir().name());
		obj.add("vertexLocation", obj2);
		obj.addProperty("free", free);
		String result = cc.send(obj, "/moves/buildSettlement", UserCookie, GameCookie);
		//System.out.println(result);
		return result;
	}

	@Override
	public String buildCity(Integer playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "buildCity");
		obj.addProperty("playerIndex", playerIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("x", vertexLocation.getHexLoc().getX());
		obj2.addProperty("y", vertexLocation.getHexLoc().getY());
		obj2.addProperty("direction", vertexLocation.getDir().name());
		obj.add("vertexLocation", obj2);
		String result = cc.send(obj, "/moves/buildCity", UserCookie, GameCookie);
		//System.out.println(result);
		return result;
	}

	@Override
	public String offerTrade(Integer playerIndex, ArrayList offer, Integer receiver) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "offerTrade");
		obj.addProperty("playerIndex", playerIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("brick", offer.get(0).toString());
		obj2.addProperty("ore", offer.get(1).toString());
		obj2.addProperty("sheep", offer.get(2).toString());
		obj2.addProperty("wheat", offer.get(3).toString());
		obj2.addProperty("wood", offer.get(4).toString());
		obj.add("offer", obj2);
		obj.addProperty("receiver", receiver);
		String result = cc.send(obj, "/moves/offerTrade", UserCookie, GameCookie);

		return result;
	}

	@Override
	public String maritimeTrade(Integer playerIndex, Integer ratio, String inputResource, String outputResource) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "maritimeTrade");
		obj.addProperty("playerIndex", playerIndex);
		obj.addProperty("ratio", ratio);
		obj.addProperty("inputResource", inputResource);
		obj.addProperty("outputResource", outputResource);
		String result = cc.send(obj, "/moves/maritimeTrade", UserCookie, GameCookie);

		return result;
	}

	@Override
	public String robPlayer(Integer playerIndex, Integer victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "robPlayer");
		obj.addProperty("playerIndex", playerIndex);
		obj.addProperty("victimIndex", victimIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("x", location.getX());
		obj2.addProperty("y", location.getY());
		obj.add("location", obj2);
		String result = cc.send(obj, "/moves/robPlayer", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String finishTurn(Integer playerIndex) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "finishTurn");
		obj.addProperty("playerIndex", playerIndex);
		String result = cc.send(obj, "/moves/finishTurn", UserCookie, GameCookie);
		//System.out.println(result);
		return result;
	}

	@Override
	public String buyDevCard(Integer playerIndex) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "buyDevCard");
		obj.addProperty("playerIndex", playerIndex);
		String result = cc.send(obj, "/moves/buyDevCard", UserCookie, GameCookie);
		//System.out.println(result);
		return result;
	}

	@Override
	public String Soldier(Integer playerIndex, Integer victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "Soldier");
		obj.addProperty("playerIndex", playerIndex);
		obj.addProperty("victimIndex", victimIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("x", location.getX());
		obj2.addProperty("y", location.getY());
		obj.add("location", obj2);
		String result = cc.send(obj, "/moves/Soldier", UserCookie, GameCookie);
		//System.out.println(result);
		return result;
	}

	@Override
	public String Year_of_Plenty(Integer playerIndex, ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "Year_of_Plenty");
		obj.addProperty("playerIndex", playerIndex);
		//System.out.println("YEAR OF PLENTY " + resource1);
		obj.addProperty("resource1", resource1.toString()); // MAKE SURE THESE ARE RIGHT 
		obj.addProperty("resource2", resource2.toString()); // MAKE SURE THESE ARE RIGHT 
		String result = cc.send(obj, "/moves/Year_of_Plenty", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	public String getLower(ResourceType r) {
		String result = "";
		//WOOD, BRICK, SHEEP, WHEAT, ORE
		if (r == ResourceType.ORE) {
			result = "ore";
		} else if (r == ResourceType.BRICK) {
			result = "brick";
		} else if (r == ResourceType.SHEEP) {
			result = "sheep";
		} else if (r == ResourceType.WHEAT) {
			result = "wheat";
		} else if (r == ResourceType.WOOD) {
			result = "wood";
		}

		return result;
	}

	@Override
	public String Road_Building(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "Road_Building");
		obj.addProperty("playerIndex", playerIndex);
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("x", spot1.getHexLoc().getX());
		obj2.addProperty("y", spot1.getHexLoc().getY());
		obj2.addProperty("direction", spot1.getDir().name());
		JsonObject obj3 = new JsonObject();
		obj3.addProperty("x", spot2.getHexLoc().getX());
		obj3.addProperty("y", spot2.getHexLoc().getY());
		obj3.addProperty("direction", spot2.getDir().name());
		obj.add("spot1", obj2);
		obj.add("spot2", obj3);
		String result = cc.send(obj, "/moves/Road_Building", UserCookie, GameCookie);

		return result;
	}

	@Override
	public String Monopoly(String resource, Integer playerIndex) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "Monopoly");
		obj.addProperty("resource", resource);
		obj.addProperty("playerIndex", playerIndex);
		String result = cc.send(obj, "/moves/Monopoly", UserCookie, GameCookie);
		//System.out.println(result); 
		return result;
	}

	@Override
	public String Monument(Integer playerIndex) {
		// TODO Auto-generated method stub
		//// Implement this one
		JsonObject obj = new JsonObject();
		obj.addProperty("type", "Monument");
		obj.addProperty("playerIndex", playerIndex);
		String result = cc.send(obj, "/moves/Monument", UserCookie, GameCookie);
		//System.out.println(result);
		return result;
	}

	public static RealProxy getSingleton() {
		if (singleton == null) {
			singleton = new RealProxy();
		}
		return singleton;
	}

	@Override
	public int getPlayerId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
