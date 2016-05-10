package proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.annotation.Resource;


import model.Game;
import model.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class RealProxy implements IServer{

	@Override
	public String userLogin(String username, String password) {
		// TODO Auto-generated method stub
		// create userLoginInput(username, password)
		
		return "Success"; 
	}

	@Override
	public String userRegister(String username, String password) {
		// TODO Auto-generated method stub
		
		return "Success"; 
	}
//	public void handle(HttpExchange exchange) throws IOException {
//		DownloadBatch_Params params = new DownloadBatch_Params();
//		DownloadBatch_Result dbr = new DownloadBatch_Result();
//		
//		params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
//		dbr = ServerFacade.DownloadBatch(params);
//		
//		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//		xmlStream.toXML(dbr, exchange.getResponseBody()); 
//		exchange.close();
//	}

	@Override
	public void gamesList() {
		// TODO Auto-generated method stub
		
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
	public void gameModel(Integer versionNumber) {
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
