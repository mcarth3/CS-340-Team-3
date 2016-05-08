package proxy;

import java.util.ArrayList;

import javax.annotation.Resource;
import model.Game;
import model.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class MockProxy implements IServer{

	private static MockProxy singleton = null;
	
	@Override
	public void userLogin(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userRegister(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamesList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamesCreate(String name, ArrayList randomTiles, ArrayList randomNumbers, ArrayList randomPorts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameJoin(Player user, Integer gameID, String color) {
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
	public void gameModel(Player user, Integer versionNumber) {
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
	public void gameAddAI(Player user, Game game, String AIType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void utilChangeLogLevel(String loggingLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendChat(String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptTrade(Boolean willAccept) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discardCards(ArrayList discardedCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollNumber(Integer number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildRoad(Boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildSettlement(Boolean free, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildCity(VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerTrade(ArrayList of_er, Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maritimeTrade(Integer ratio, Resource inputResource, Resource outputResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(HexLocation location, Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyDevCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Soldier(HexLocation location, Integer playerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Year_of_Plenty(Resource resource1, Resource resource2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Road_Building(EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Monopoly(Resource resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Monument() {
		// TODO Auto-generated method stub
		
	}
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
	

}
