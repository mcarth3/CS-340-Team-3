package proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.annotation.Resource;

import com.google.gson.JsonObject;

import model.*;
import poller.modeljsonparser.ModelParser;
import proxy.objects.*;
import shared.definitions.ResourceType;
import shared.locations.*; 

public class MockProxy implements IServer{

	private static MockProxy singleton = null;
	public String model = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":4,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"red\"},{\"resources\":{\"brick\":4,\"wood\":0,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"}]},\"chat\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"new message here\"},{\"source\":\"Sam\",\"message\":\"new message here\"}]},\"bank\":{\"brick\":20,\"wood\":21,\"sheep\":20,\"wheat\":19,\"ore\":22},\"turnTracker\":{\"status\":\"Playing\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":5}";
	public String newGame = "{\"title\": \"Test Game\",\"id\": 4,\"players\":[{},{},{},{}]}";
	public String defaultGameList = "[{'title': 'Default Game','id': 0,'players': [{'color': 'orange','name': 'Sam','id': 0},{'color': 'blue','name': 'Brooke','id': 1},{'color': 'red','name': 'Pete','id': 10},{'color': 'green','name': 'Mark','id': 11}]},{'title': 'AI Game','id': 1,'players': [{'color': 'orange','name': 'Pete','id': 10},{'color': 'puce','name': 'Quinn','id': -2},{'color': 'red','name': 'Ken','id': -3},{'color': 'green','name': 'Miguel','id': -4}]},{'title': 'Empty Game','id': 2,'players': [{'color': 'orange','name': 'Sam','id': 0},{'color': 'blue','name': 'Brooke','id': 1},{'color': 'red','name': 'Pete','id': 10},{'color': 'green','name': 'Mark','id': 11 }]}}"; 
	
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
		return "Success"; 
	}

	@Override
	public String userRegister(String username, String password) {
		return "Success";
	}

	@Override
	public String gamesList() {
		// TODO Auto-generated method stub
		ArrayList<Game> games = new ArrayList();
		
		Game g1 = new Game(); 
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
		
//		return games.toString(); 
		return defaultGameList;
	}

	@Override
	public String gamesCreate(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub		
		Game g = new Game();
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
		
		//return g.toString();
		return newGame; 
	}

	@Override
	public String gameJoin(Integer gameID, String color) {
		// TODO Auto-generated method stub
		return "Success";
	}

	@Override
	public String gamesSave(Integer gameID, String fileName) {
		// TODO Auto-generated method stub
		return "Success";
	}

	@Override
	public String gamesLoad(String fileName) {
		// TODO Auto-generated method stub
		return "Success";
	}

	@Override
	public String gameModel(Integer versionNumber) {
		String modeljsondata;
		try {
			modeljsondata = readFile("testmodel.json");
		} catch (IOException e) {
			modeljsondata = "";
			e.printStackTrace();
		}
		return modeljsondata;
	
	}
	
	private String readFile(String fileName) throws IOException {
		String data;
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Scanner scanner = new Scanner(bufferedReader);
		data = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return data;
	}
	
	@Override
	public String gameReset(Player user, Game game) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String gameCommandsGet(Player user, Game game) {
		// TODO Auto-generated method stub
		return "Not Implemented";
	}

	@Override
	public String gameCommandsPost(Player user, Game game) {
		// TODO Auto-generated method stub
		return "Not Implemented";
	}

	@Override
	public String gameListAI() {
		// TODO Auto-generated method stub
		return "Not Implemented";
	}

	@Override
	public String gameAddAI(String AIType) {
		// TODO Auto-generated method stub
		return "Not Implemented";
	}

	@Override
	public String utilChangeLogLevel(String loggingLevel) {
		// TODO Auto-generated method stub
		return "Success";
	}
	
	
	////-------- MOVE API ------------
	@Override
	public String sendChat(Integer playerIndex, String content) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String acceptTrade(Integer playerIndex, Boolean willAccept) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String discardCards(Integer playerIndex, ArrayList discardedCards) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String rollNumber(Integer playerIndex, Integer number) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String buildRoad(Integer playerIndex, EdgeLocation roadLocation, Boolean free) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String buildSettlement(Integer playerIndex, VertexLocation vertexLocation, Boolean free) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String buildCity(Integer playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String offerTrade(Integer playerIndex, ArrayList offer, Integer receiver) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String maritimeTrade(Integer playerIndex, Integer ratio, String inputResource, String outputResource) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String robPlayer(Integer playerIndex, Integer victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String finishTurn(Integer playerIndex) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String buyDevCard(Integer playerIndex) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String Soldier(Integer playerIndex, Integer victimIndex, HexLocation location) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String Year_of_Plenty(Integer playerIndex, ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String Road_Building(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String Monopoly(String resource, Integer playerIndex) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public String Monument(Integer playerIndex) {
		// TODO Auto-generated method stub
		return model;
	}
	


}
// {"deck":{"yearOfPlenty":2,"monopoly":2,"soldier":14,"roadBuilding":2,"monument":5},"map":{"hexes":[{"location":{"x":0,"y":-2}},{"resource":"brick","location":{"x":1,"y":-2},"number":4},{"resource":"wood","location":{"x":2,"y":-2},"number":11},{"resource":"brick","location":{"x":-1,"y":-1},"number":8},{"resource":"wood","location":{"x":0,"y":-1},"number":3},{"resource":"ore","location":{"x":1,"y":-1},"number":9},{"resource":"sheep","location":{"x":2,"y":-1},"number":12},{"resource":"ore","location":{"x":-2,"y":0},"number":5},{"resource":"sheep","location":{"x":-1,"y":0},"number":10},{"resource":"wheat","location":{"x":0,"y":0},"number":11},{"resource":"brick","location":{"x":1,"y":0},"number":5},{"resource":"wheat","location":{"x":2,"y":0},"number":6},{"resource":"wheat","location":{"x":-2,"y":1},"number":2},{"resource":"sheep","location":{"x":-1,"y":1},"number":9},{"resource":"wood","location":{"x":0,"y":1},"number":4},{"resource":"sheep","location":{"x":1,"y":1},"number":10},{"resource":"wood","location":{"x":-2,"y":2},"number":6},{"resource":"ore","location":{"x":-1,"y":2},"number":3},{"resource":"wheat","location":{"x":0,"y":2},"number":8}],"roads":[{"owner":1,"location":{"direction":"S","x":-1,"y":-1}},{"owner":3,"location":{"direction":"SW","x":-1,"y":1}},{"owner":3,"location":{"direction":"SW","x":2,"y":-2}},{"owner":2,"location":{"direction":"S","x":1,"y":-1}},{"owner":0,"location":{"direction":"S","x":0,"y":1}},{"owner":2,"location":{"direction":"S","x":0,"y":0}},{"owner":1,"location":{"direction":"SW","x":-2,"y":1}},{"owner":0,"location":{"direction":"SW","x":2,"y":0}}],"cities":[],"settlements":[{"owner":3,"location":{"direction":"SW","x":-1,"y":1}},{"owner":3,"location":{"direction":"SE","x":1,"y":-2}},{"owner":2,"location":{"direction":"SW","x":0,"y":0}},{"owner":2,"location":{"direction":"SW","x":1,"y":-1}},{"owner":1,"location":{"direction":"SW","x":-2,"y":1}},{"owner":0,"location":{"direction":"SE","x":0,"y":1}},{"owner":1,"location":{"direction":"SW","x":-1,"y":-1}},{"owner":0,"location":{"direction":"SW","x":2,"y":0}}],"radius":3,"ports":[{"ratio":3,"direction":"NW","location":{"x":2,"y":1}},{"ratio":2,"resource":"ore","direction":"S","location":{"x":1,"y":-3}},{"ratio":2,"resource":"brick","direction":"NE","location":{"x":-2,"y":3}},{"ratio":2,"resource":"wheat","direction":"S","location":{"x":-1,"y":-2}},{"ratio":2,"resource":"wood","direction":"NE","location":{"x":-3,"y":2}},{"ratio":3,"direction":"SW","location":{"x":3,"y":-3}},{"ratio":2,"resource":"sheep","direction":"NW","location":{"x":3,"y":-1}},{"ratio":3,"direction":"N","location":{"x":0,"y":3}},{"ratio":3,"direction":"SE","location":{"x":-3,"y":0}}],"robber":{"x":0,"y":-2}},"players":[{"resources":{"brick":0,"wood":1,"sheep":1,"wheat":4,"ore":0},"oldDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"newDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"roads":13,"cities":4,"settlements":3,"soldiers":0,"victoryPoints":2,"monuments":0,"playedDevCard":false,"discarded":false,"playerID":0,"playerIndex":0,"name":"Sam","color":"red"},{"resources":{"brick":4,"wood":0,"sheep":1,"wheat":0,"ore":1},"oldDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"newDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"roads":13,"cities":4,"settlements":3,"soldiers":0,"victoryPoints":2,"monuments":0,"playedDevCard":false,"discarded":false,"playerID":1,"playerIndex":1,"name":"Brooke","color":"blue"},{"resources":{"brick":0,"wood":1,"sheep":1,"wheat":1,"ore":0},"oldDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"newDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"roads":13,"cities":4,"settlements":3,"soldiers":0,"victoryPoints":2,"monuments":0,"playedDevCard":false,"discarded":false,"playerID":10,"playerIndex":2,"name":"Pete","color":"red"},{"resources":{"brick":0,"wood":1,"sheep":1,"wheat":0,"ore":1},"oldDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"newDevCards":{"yearOfPlenty":0,"monopoly":0,"soldier":0,"roadBuilding":0,"monument":0},"roads":13,"cities":4,"settlements":3,"soldiers":0,"victoryPoints":2,"monuments":0,"playedDevCard":false,"discarded":false,"playerID":11,"playerIndex":3,"name":"Mark","color":"green"}],"log":{"lines":[{"source":"Sam","message":"Sam built a road"},{"source":"Sam","message":"Sam built a settlement"},{"source":"Sam","message":"Sam\u0027s turn just ended"},{"source":"Brooke","message":"Brooke built a road"},{"source":"Brooke","message":"Brooke built a settlement"},{"source":"Brooke","message":"Brooke\u0027s turn just ended"},{"source":"Pete","message":"Pete built a road"},{"source":"Pete","message":"Pete built a settlement"},{"source":"Pete","message":"Pete\u0027s turn just ended"},{"source":"Mark","message":"Mark built a road"},{"source":"Mark","message":"Mark built a settlement"},{"source":"Mark","message":"Mark\u0027s turn just ended"},{"source":"Mark","message":"Mark built a road"},{"source":"Mark","message":"Mark built a settlement"},{"source":"Mark","message":"Mark\u0027s turn just ended"},{"source":"Pete","message":"Pete built a road"},{"source":"Pete","message":"Pete built a settlement"},{"source":"Pete","message":"Pete\u0027s turn just ended"},{"source":"Brooke","message":"Brooke built a road"},{"source":"Brooke","message":"Brooke built a settlement"},{"source":"Brooke","message":"Brooke\u0027s turn just ended"},{"source":"Sam","message":"Sam built a road"},{"source":"Sam","message":"Sam built a settlement"},{"source":"Sam","message":"Sam\u0027s turn just ended"},{"source":"Sam","message":"Sam rolled a 8."},{"source":"Sam","message":"Sam rolled a 8."},{"source":"Sam","message":"Sam rolled a 8."}]},"chat":{"lines":[{"source":"Sam","message":"new message here"},{"source":"Sam","message":"new message here"}]},"bank":{"brick":20,"wood":21,"sheep":20,"wheat":19,"ore":22},"turnTracker":{"status":"Playing","currentTurn":0,"longestRoad":-1,"largestArmy":-1},"winner":-1,"version":5}
// {'deck':{'yearOfPlenty':2,'monopoly':2,'soldier':14,'roadBuilding':2,'monument':5},'map':{'hexes':[{'location':{'x':0,'y':-2}},{'resource':'brick','location':{'x':1,'y':-2},'number':4},{'resource':'wood','location':{'x':2,'y':-2},'number':11},{'resource':'brick','location':{'x':-1,'y':-1},'number':8},{'resource':'wood','location':{'x':0,'y':-1},'number':3},{'resource':'ore','location':{'x':1,'y':-1},'number':9},{'resource':'sheep','location':{'x':2,'y':-1},'number':12},{'resource':'ore','location':{'x':-2,'y':0},'number':5},{'resource':'sheep','location':{'x':-1,'y':0},'number':10},{'resource':'wheat','location':{'x':0,'y':0},'number':11},{'resource':'brick','location':{'x':1,'y':0},'number':5},{'resource':'wheat','location':{'x':2,'y':0},'number':6},{'resource':'wheat','location':{'x':-2,'y':1},'number':2},{'resource':'sheep','location':{'x':-1,'y':1},'number':9},{'resource':'wood','location':{'x':0,'y':1},'number':4},{'resource':'sheep','location':{'x':1,'y':1},'number':10},{'resource':'wood','location':{'x':-2,'y':2},'number':6},{'resource':'ore','location':{'x':-1,'y':2},'number':3},{'resource':'wheat','location':{'x':0,'y':2},'number':8}],'roads':[{'owner':1,'location':{'direction':'S','x':-1,'y':-1}},{'owner':3,'location':{'direction':'SW','x':-1,'y':1}},{'owner':3,'location':{'direction':'SW','x':2,'y':-2}},{'owner':2,'location':{'direction':'S','x':1,'y':-1}},{'owner':0,'location':{'direction':'S','x':0,'y':1}},{'owner':2,'location':{'direction':'S','x':0,'y':0}},{'owner':1,'location':{'direction':'SW','x':-2,'y':1}},{'owner':0,'location':{'direction':'SW','x':2,'y':0}}],'cities':[],'settlements':[{'owner':3,'location':{'direction':'SW','x':-1,'y':1}},{'owner':3,'location':{'direction':'SE','x':1,'y':-2}},{'owner':2,'location':{'direction':'SW','x':0,'y':0}},{'owner':2,'location':{'direction':'SW','x':1,'y':-1}},{'owner':1,'location':{'direction':'SW','x':-2,'y':1}},{'owner':0,'location':{'direction':'SE','x':0,'y':1}},{'owner':1,'location':{'direction':'SW','x':-1,'y':-1}},{'owner':0,'location':{'direction':'SW','x':2,'y':0}}],'radius':3,'ports':[{'ratio':3,'direction':'NW','location':{'x':2,'y':1}},{'ratio':2,'resource':'ore','direction':'S','location':{'x':1,'y':-3}},{'ratio':2,'resource':'brick','direction':'NE','location':{'x':-2,'y':3}},{'ratio':2,'resource':'wheat','direction':'S','location':{'x':-1,'y':-2}},{'ratio':2,'resource':'wood','direction':'NE','location':{'x':-3,'y':2}},{'ratio':3,'direction':'SW','location':{'x':3,'y':-3}},{'ratio':2,'resource':'sheep','direction':'NW','location':{'x':3,'y':-1}},{'ratio':3,'direction':'N','location':{'x':0,'y':3}},{'ratio':3,'direction':'SE','location':{'x':-3,'y':0}}],'robber':{'x':0,'y':-2}},'players':[{'resources':{'brick':0,'wood':1,'sheep':1,'wheat':4,'ore':0},'oldDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'newDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'roads':13,'cities':4,'settlements':3,'soldiers':0,'victoryPoints':2,'monuments':0,'playedDevCard':false,'discarded':false,'playerID':0,'playerIndex':0,'name':'Sam','color':'red'},{'resources':{'brick':4,'wood':0,'sheep':1,'wheat':0,'ore':1},'oldDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'newDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'roads':13,'cities':4,'settlements':3,'soldiers':0,'victoryPoints':2,'monuments':0,'playedDevCard':false,'discarded':false,'playerID':1,'playerIndex':1,'name':'Brooke','color':'blue'},{'resources':{'brick':0,'wood':1,'sheep':1,'wheat':1,'ore':0},'oldDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'newDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'roads':13,'cities':4,'settlements':3,'soldiers':0,'victoryPoints':2,'monuments':0,'playedDevCard':false,'discarded':false,'playerID':10,'playerIndex':2,'name':'Pete','color':'red'},{'resources':{'brick':0,'wood':1,'sheep':1,'wheat':0,'ore':1},'oldDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'newDevCards':{'yearOfPlenty':0,'monopoly':0,'soldier':0,'roadBuilding':0,'monument':0},'roads':13,'cities':4,'settlements':3,'soldiers':0,'victoryPoints':2,'monuments':0,'playedDevCard':false,'discarded':false,'playerID':11,'playerIndex':3,'name':'Mark','color':'green'}],'log':{'lines':[{'source':'Sam','message':'Sam built a road'},{'source':'Sam','message':'Sam built a settlement'},{'source':'Sam','message':'Sam\u0027s turn just ended'},{'source':'Brooke','message':'Brooke built a road'},{'source':'Brooke','message':'Brooke built a settlement'},{'source':'Brooke','message':'Brooke\u0027s turn just ended'},{'source':'Pete','message':'Pete built a road'},{'source':'Pete','message':'Pete built a settlement'},{'source':'Pete','message':'Pete\u0027s turn just ended'},{'source':'Mark','message':'Mark built a road'},{'source':'Mark','message':'Mark built a settlement'},{'source':'Mark','message':'Mark\u0027s turn just ended'},{'source':'Mark','message':'Mark built a road'},{'source':'Mark','message':'Mark built a settlement'},{'source':'Mark','message':'Mark\u0027s turn just ended'},{'source':'Pete','message':'Pete built a road'},{'source':'Pete','message':'Pete built a settlement'},{'source':'Pete','message':'Pete\u0027s turn just ended'},{'source':'Brooke','message':'Brooke built a road'},{'source':'Brooke','message':'Brooke built a settlement'},{'source':'Brooke','message':'Brooke\u0027s turn just ended'},{'source':'Sam','message':'Sam built a road'},{'source':'Sam','message':'Sam built a settlement'},{'source':'Sam','message':'Sam\u0027s turn just ended'},{'source':'Sam','message':'Sam rolled a 8.'},{'source':'Sam','message':'Sam rolled a 8.'},{'source':'Sam','message':'Sam rolled a 8.'}]},'chat':{'lines':[{'source':'Sam','message':'new message here'},{'source':'Sam','message':'new message here'}]},'bank':{'brick':20,'wood':21,'sheep':20,'wheat':19,'ore':22},'turnTracker':{'status':'Playing','currentTurn':0,'longestRoad':-1,'largestArmy':-1},'winner':-1,'version':5}
// "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":4,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"red\"},{\"resources\":{\"brick\":4,\"wood\":0,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"}]},\"chat\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"new message here\"},{\"source\":\"Sam\",\"message\":\"new message here\"}]},\"bank\":{\"brick\":20,\"wood\":21,\"sheep\":20,\"wheat\":19,\"ore\":22},\"turnTracker\":{\"status\":\"Playing\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":5}";

/*
{
	  "deck": {
	    "yearOfPlenty": 2,
	    "monopoly": 2,
	    "soldier": 14,
	    "roadBuilding": 2,
	    "monument": 5
	  },
	  "map": {
	    "hexes": [
	      {
	        "location": {
	          "x": 0,
	          "y": -2
	        }
	      },
	      {
	        "resource": "brick",
	        "location": {
	          "x": 1,
	          "y": -2
	        },
	        "number": 4
	      },
	      {
	        "resource": "wood",
	        "location": {
	          "x": 2,
	          "y": -2
	        },
	        "number": 11
	      },
	      {
	        "resource": "brick",
	        "location": {
	          "x": -1,
	          "y": -1
	        },
	        "number": 8
	      },
	      {
	        "resource": "wood",
	        "location": {
	          "x": 0,
	          "y": -1
	        },
	        "number": 3
	      },
	      {
	        "resource": "ore",
	        "location": {
	          "x": 1,
	          "y": -1
	        },
	        "number": 9
	      },
	      {
	        "resource": "sheep",
	        "location": {
	          "x": 2,
	          "y": -1
	        },
	        "number": 12
	      },
	      {
	        "resource": "ore",
	        "location": {
	          "x": -2,
	          "y": 0
	        },
	        "number": 5
	      },
	      {
	        "resource": "sheep",
	        "location": {
	          "x": -1,
	          "y": 0
	        },
	        "number": 10
	      },
	      {
	        "resource": "wheat",
	        "location": {
	          "x": 0,
	          "y": 0
	        },
	        "number": 11
	      },
	      {
	        "resource": "brick",
	        "location": {
	          "x": 1,
	          "y": 0
	        },
	        "number": 5
	      },
	      {
	        "resource": "wheat",
	        "location": {
	          "x": 2,
	          "y": 0
	        },
	        "number": 6
	      },
	      {
	        "resource": "wheat",
	        "location": {
	          "x": -2,
	          "y": 1
	        },
	        "number": 2
	      },
	      {
	        "resource": "sheep",
	        "location": {
	          "x": -1,
	          "y": 1
	        },
	        "number": 9
	      },
	      {
	        "resource": "wood",
	        "location": {
	          "x": 0,
	          "y": 1
	        },
	        "number": 4
	      },
	      {
	        "resource": "sheep",
	        "location": {
	          "x": 1,
	          "y": 1
	        },
	        "number": 10
	      },
	      {
	        "resource": "wood",
	        "location": {
	          "x": -2,
	          "y": 2
	        },
	        "number": 6
	      },
	      {
	        "resource": "ore",
	        "location": {
	          "x": -1,
	          "y": 2
	        },
	        "number": 3
	      },
	      {
	        "resource": "wheat",
	        "location": {
	          "x": 0,
	          "y": 2
	        },
	        "number": 8
	      }
	    ],
	    "roads": [
	      {
	        "owner": 1,
	        "location": {
	          "direction": "S",
	          "x": -1,
	          "y": -1
	        }
	      },
	      {
	        "owner": 3,
	        "location": {
	          "direction": "SW",
	          "x": -1,
	          "y": 1
	        }
	      },
	      {
	        "owner": 3,
	        "location": {
	          "direction": "SW",
	          "x": 2,
	          "y": -2
	        }
	      },
	      {
	        "owner": 2,
	        "location": {
	          "direction": "S",
	          "x": 1,
	          "y": -1
	        }
	      },
	      {
	        "owner": 0,
	        "location": {
	          "direction": "S",
	          "x": 0,
	          "y": 1
	        }
	      },
	      {
	        "owner": 2,
	        "location": {
	          "direction": "S",
	          "x": 0,
	          "y": 0
	        }
	      },
	      {
	        "owner": 1,
	        "location": {
	          "direction": "SW",
	          "x": -2,
	          "y": 1
	        }
	      },
	      {
	        "owner": 0,
	        "location": {
	          "direction": "SW",
	          "x": 2,
	          "y": 0
	        }
	      }
	    ],
	    "cities": [],
	    "settlements": [
	      {
	        "owner": 3,
	        "location": {
	          "direction": "SW",
	          "x": -1,
	          "y": 1
	        }
	      },
	      {
	        "owner": 3,
	        "location": {
	          "direction": "SE",
	          "x": 1,
	          "y": -2
	        }
	      },
	      {
	        "owner": 2,
	        "location": {
	          "direction": "SW",
	          "x": 0,
	          "y": 0
	        }
	      },
	      {
	        "owner": 2,
	        "location": {
	          "direction": "SW",
	          "x": 1,
	          "y": -1
	        }
	      },
	      {
	        "owner": 1,
	        "location": {
	          "direction": "SW",
	          "x": -2,
	          "y": 1
	        }
	      },
	      {
	        "owner": 0,
	        "location": {
	          "direction": "SE",
	          "x": 0,
	          "y": 1
	        }
	      },
	      {
	        "owner": 1,
	        "location": {
	          "direction": "SW",
	          "x": -1,
	          "y": -1
	        }
	      },
	      {
	        "owner": 0,
	        "location": {
	          "direction": "SW",
	          "x": 2,
	          "y": 0
	        }
	      }
	    ],
	    "radius": 3,
	    "ports": [
	      {
	        "ratio": 3,
	        "direction": "NW",
	        "location": {
	          "x": 2,
	          "y": 1
	        }
	      },
	      {
	        "ratio": 2,
	        "resource": "ore",
	        "direction": "S",
	        "location": {
	          "x": 1,
	          "y": -3
	        }
	      },
	      {
	        "ratio": 2,
	        "resource": "brick",
	        "direction": "NE",
	        "location": {
	          "x": -2,
	          "y": 3
	        }
	      },
	      {
	        "ratio": 2,
	        "resource": "wheat",
	        "direction": "S",
	        "location": {
	          "x": -1,
	          "y": -2
	        }
	      },
	      {
	        "ratio": 2,
	        "resource": "wood",
	        "direction": "NE",
	        "location": {
	          "x": -3,
	          "y": 2
	        }
	      },
	      {
	        "ratio": 3,
	        "direction": "SW",
	        "location": {
	          "x": 3,
	          "y": -3
	        }
	      },
	      {
	        "ratio": 2,
	        "resource": "sheep",
	        "direction": "NW",
	        "location": {
	          "x": 3,
	          "y": -1
	        }
	      },
	      {
	        "ratio": 3,
	        "direction": "N",
	        "location": {
	          "x": 0,
	          "y": 3
	        }
	      },
	      {
	        "ratio": 3,
	        "direction": "SE",
	        "location": {
	          "x": -3,
	          "y": 0
	        }
	      }
	    ],
	    "robber": {
	      "x": 0,
	      "y": -2
	    }
	  },
	  "players": [
	    {
	      "resources": {
	        "brick": 0,
	        "wood": 1,
	        "sheep": 1,
	        "wheat": 2,
	        "ore": 0
	      },
	      "oldDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "newDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "roads": 13,
	      "cities": 4,
	      "settlements": 3,
	      "soldiers": 0,
	      "victoryPoints": 2,
	      "monuments": 0,
	      "playedDevCard": false,
	      "discarded": false,
	      "playerID": 0,
	      "playerIndex": 0,
	      "name": "Sam",
	      "color": "orange"
	    },
	    {
	      "resources": {
	        "brick": 2,
	        "wood": 0,
	        "sheep": 1,
	        "wheat": 0,
	        "ore": 1
	      },
	      "oldDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "newDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "roads": 13,
	      "cities": 4,
	      "settlements": 3,
	      "soldiers": 0,
	      "victoryPoints": 2,
	      "monuments": 0,
	      "playedDevCard": false,
	      "discarded": false,
	      "playerID": 1,
	      "playerIndex": 1,
	      "name": "Brooke",
	      "color": "blue"
	    },
	    {
	      "resources": {
	        "brick": 0,
	        "wood": 1,
	        "sheep": 1,
	        "wheat": 1,
	        "ore": 0
	      },
	      "oldDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "newDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "roads": 13,
	      "cities": 4,
	      "settlements": 3,
	      "soldiers": 0,
	      "victoryPoints": 2,
	      "monuments": 0,
	      "playedDevCard": false,
	      "discarded": false,
	      "playerID": 10,
	      "playerIndex": 2,
	      "name": "Pete",
	      "color": "red"
	    },
	    {
	      "resources": {
	        "brick": 0,
	        "wood": 1,
	        "sheep": 1,
	        "wheat": 0,
	        "ore": 1
	      },
	      "oldDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "newDevCards": {
	        "yearOfPlenty": 0,
	        "monopoly": 0,
	        "soldier": 0,
	        "roadBuilding": 0,
	        "monument": 0
	      },
	      "roads": 13,
	      "cities": 4,
	      "settlements": 3,
	      "soldiers": 0,
	      "victoryPoints": 2,
	      "monuments": 0,
	      "playedDevCard": false,
	      "discarded": false,
	      "playerID": 11,
	      "playerIndex": 3,
	      "name": "Mark",
	      "color": "green"
	    }
	  ],
	  "log": {
	    "lines": [
	      {
	        "source": "Sam",
	        "message": "Sam built a road"
	      },
	      {
	        "source": "Sam",
	        "message": "Sam built a settlement"
	      },
	      {
	        "source": "Sam",
	        "message": "Sam's turn just ended"
	      },
	      {
	        "source": "Brooke",
	        "message": "Brooke built a road"
	      },
	      {
	        "source": "Brooke",
	        "message": "Brooke built a settlement"
	      },
	      {
	        "source": "Brooke",
	        "message": "Brooke's turn just ended"
	      },
	      {
	        "source": "Pete",
	        "message": "Pete built a road"
	      },
	      {
	        "source": "Pete",
	        "message": "Pete built a settlement"
	      },
	      {
	        "source": "Pete",
	        "message": "Pete's turn just ended"
	      },
	      {
	        "source": "Mark",
	        "message": "Mark built a road"
	      },
	      {
	        "source": "Mark",
	        "message": "Mark built a settlement"
	      },
	      {
	        "source": "Mark",
	        "message": "Mark's turn just ended"
	      },
	      {
	        "source": "Mark",
	        "message": "Mark built a road"
	      },
	      {
	        "source": "Mark",
	        "message": "Mark built a settlement"
	      },
	      {
	        "source": "Mark",
	        "message": "Mark's turn just ended"
	      },
	      {
	        "source": "Pete",
	        "message": "Pete built a road"
	      },
	      {
	        "source": "Pete",
	        "message": "Pete built a settlement"
	      },
	      {
	        "source": "Pete",
	        "message": "Pete's turn just ended"
	      },
	      {
	        "source": "Brooke",
	        "message": "Brooke built a road"
	      },
	      {
	        "source": "Brooke",
	        "message": "Brooke built a settlement"
	      },
	      {
	        "source": "Brooke",
	        "message": "Brooke's turn just ended"
	      },
	      {
	        "source": "Sam",
	        "message": "Sam built a road"
	      },
	      {
	        "source": "Sam",
	        "message": "Sam built a settlement"
	      },
	      {
	        "source": "Sam",
	        "message": "Sam's turn just ended"
	      },
	      {
	        "source": "Sam",
	        "message": "Sam rolled a 8."
	      }
	    ]
	  },
	  "chat": {
	    "lines": []
	  },
	  "bank": {
	    "brick": 22,
	    "wood": 21,
	    "sheep": 20,
	    "wheat": 21,
	    "ore": 22
	  },
	  "turnTracker": {
	    "status": "Playing",
	    "currentTurn": 0,
	    "longestRoad": -1,
	    "largestArmy": -1
	  },
	  "winner": -1,
	  "version": 1
	}
*/