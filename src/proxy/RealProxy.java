package proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.annotation.Resource;
import com.google.gson.JsonObject;


import model.Game;
import model.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class RealProxy implements IServer{
	
	private ClientCommunicator cc = new ClientCommunicator(); 
	
	@Override
	public String userLogin(String username, String password) {
		JsonObject obj = new JsonObject();
        obj.addProperty("username", username);
        obj.addProperty("password", password);
		return cc.send(obj, "/user/login"); 
	}

	@Override
	public String userRegister(String username, String password) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
        obj.addProperty("username", username);
        obj.addProperty("password", password);
		return cc.send(obj, "/user/register"); 
	}

	@Override
	public void gamesList() {
		// TODO Auto-generated method stub
		System.out.println("gamesList()");
		JsonObject obj = new JsonObject();
		System.out.println(cc.send(obj, "/games/list")); 
		
	}

	@Override
	public void gamesCreate(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameJoin(Integer gameID, String color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamesSave(Integer gameID, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamesLoad(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String gameModel(Integer versionNumber) {
		JsonObject obj = new JsonObject();
		return cc.send(obj, "/game/model?version="+versionNumber); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameReset(Player user, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameCommandsGet(Player user, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameCommandsPost(Player user, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameListAI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameAddAI(String AIType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void utilChangeLogLevel(String loggingLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendChat(Integer playerIndex, String content) {
		JsonObject obj = new JsonObject();
        obj.addProperty("username", "SAM");
        obj.addProperty("password", "sam");
		cc.send(obj, "/user/register"); 
		
		JsonObject obj2 = new JsonObject();
        obj2.addProperty("type", "sendChat");
        obj2.addProperty("playerIndex", "0");
        obj2.addProperty("content", "This is the new message.");
		cc.send(obj2, "/moves/sendChat");
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
