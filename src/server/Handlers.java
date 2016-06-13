package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import poller.modeljsonparser.ModelParser;
import server.commands.*;
import server.jsonObjects.*;

/**
 * Created by Jesse on 5/26/2016. This class might contain 25 methods. However, we could modify it to make it 25 different
 * Handlers that implement a particular interface called Handler. Regardless of the design, this will be
 * called upon from the Server API and will create its own commands and call execute() on them.
 */
public class Handlers {
	
	private String userCookie = null;
	private String gameCookie = null;

	/*
	 * private void return_object(HttpExchange http_exchange, XStream x_Stream,
	 * Object object) throws IOException {
	 * http_exchange.sendResponseHeaders(200, 0); OutputStream output_stream =
	 * http_exchange.getResponseBody(); x_Stream.toXML(object, output_stream);
	 * output_stream.close(); http_exchange.close();
	 * Database.end_transaction(true); }
	 * 
	 * 
	 * 
	 * private Object read_request(HttpExchange http_exchange, XStream x_Stream)
	 * throws IOException { InputStreamReader stream_reader = new
	 * InputStreamReader(http_exchange.getRequestBody(), "utf-8");
	 * BufferedReader buffered_reader = new BufferedReader(stream_reader);
	 * String this_line; String XMLuser = ""; while ((this_line =
	 * buffered_reader.readLine()) != null) { XMLuser += this_line; } return
	 * x_Stream.fromXML(XMLuser); }
	 * 
	 * 
	 * 
	 * public void return_failed(HttpExchange http_exchange) throws IOException
	 * { http_exchange.sendResponseHeaders(400, 0); http_exchange.close(); if
	 * (Database.connection != null) { Database.end_transaction(false); } }
	 */
	/**
	 * Deserializes the data sent with the httprequest
	 * 
	 * @param http_exchange
	 * @param givenclass
	 * jsondata the data given with the command to the server
	 * @param givenclass the class to deserialize the data into
	 * @pre givenclass is a valid jsonobject and jsondata is in the valid format for the givenclass
	 * @post the specified jsonobject is returned from the parsed data
	 */
	public static <T> Object deserialize(HttpExchange http_exchange, Class<T> givenclass) {
		BufferedReader br = new BufferedReader(new InputStreamReader(http_exchange.getRequestBody()));
		StringBuilder sb = new StringBuilder();
		String line;
		boolean first = true;
		try {
			while ((line = br.readLine()) != null) {
				if (first) {
					sb.append(line);
					first = false;
				} else {
					sb.append("\n" + line);
				}
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// this is what we get from the server
		//System.out.println(sb.toString());

		String json = sb.toString();

		return ModelParser.parse(json, givenclass);
	}

	/**
	 * Serializes the data sent with the httprequest
	 * 
	 * @param givenobject 
	 * givenclass the class to serialize the data from
	 * @pre givenclass is a valid jsonobject
	 * @post the specified jsonobject is returned as a string in JSON format
	 */
	public static String serialize(Object givenobject) {
		return ModelParser.toJson(givenobject);

	}

	// --------------------- Nate's Start ---------------------------
	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a userLogin jsonobject
	 * @post user is logged in
	 */
	private HttpHandler userLogin = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			
			UserLoginJsonObject uljo = (UserLoginJsonObject) deserialize(http_exchange, UserLoginJsonObject.class);
			ICommand c = new UserLoginCommand();
			String response = (String) c.execute(uljo);
			CommandList.getSingleton().addCommand(c);
			//String response = "Successcatan.user=%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D;Path=/;";
			if(response.substring(0, 7).equals("Success")){
				String temp = response.replace("Successcatan.user=","");
				temp = temp.replace(";Path=/;","");
				userCookie = temp;
				System.out.println(userCookie);
				
				//http_exchange.getResponseHeaders().set("Set-cookie", userCookie);
				http_exchange.sendResponseHeaders(200, response.length());
			}else{
				http_exchange.sendResponseHeaders(400, response.length());
			}
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getUserLoginHandler() {
		return userLogin;
	}

	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a userRegister jsonobject
	 * @post user is registered
	 */
	private HttpHandler userRegister = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {			
			UserRegisterJsonObject urjo = (UserRegisterJsonObject) deserialize(http_exchange, UserRegisterJsonObject.class);
			ICommand c = new UserRegisterCommand();
			String response = (String) c.execute(urjo);
			CommandList.getSingleton().addCommand(c);
			if(response.substring(0, 7).equals("Success")){
				String temp = response.replace("Successcatan.user=","");
				temp = temp.replace(";Path=/;","");
				userCookie = temp;
				http_exchange.sendResponseHeaders(200, response.length());
			}else{
				http_exchange.sendResponseHeaders(400, response.length());
			}
			//String response = "Successcatan.user=%7B%22authentication%22%3A%221224085268%22%2C%22name%22%3A%22Wayne%22%2C%22password%22%3A%22johnwayne%22%2C%22playerID%22%3A13%7D;Path=/;";
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getUserRegisterHandler() {
		return userRegister;
	}

	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a gamesList jsonobject
	 * @post returns a list of games
	 */
	private HttpHandler gamesList = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			ICommand c = new GamesListCommand();
			
			//Doesn't need any input
			Object output = c.execute(null);
			CommandList.getSingleton().addCommand(c);
			String response = serialize(output);
			//System.out.println(response); 
			//String response = "[{\"title\":\"Default Game\",\"id\":0,\"players\":[{\"color\":\"orange\",\"name\":\"Sam\",\"id\":0},{\"color\":\"blue\",\"name\":\"Brooke\",\"id\":1},{\"color\":\"red\",\"name\":\"Pete\",\"id\":10},{\"color\":\"green\",\"name\":\"Mark\",\"id\":11}]},{\"title\":\"AI Game\",\"id\":1,\"players\":[{\"color\":\"orange\",\"name\":\"Pete\",\"id\":10},{\"color\":\"blue\",\"name\":\"Ken\",\"id\":-2},{\"color\":\"yellow\",\"name\":\"Scott\",\"id\":-3},{\"color\":\"green\",\"name\":\"Squall\",\"id\":-4}]},{\"title\":\"Empty Game\",\"id\":2,\"players\":[{\"color\":\"orange\",\"name\":\"Sam\",\"id\":0},{\"color\":\"blue\",\"name\":\"Brooke\",\"id\":1},{\"color\":\"red\",\"name\":\"Pete\",\"id\":10},{\"color\":\"green\",\"name\":\"Mark\",\"id\":11}]},{\"title\":\"Dev Game\",\"id\":3,\"players\":[{\"color\":\"purple\",\"name\":\"Sam\",\"id\":0},{\"color\":\"puce\",\"name\":\"Hannah\",\"id\":-2},{\"color\":\"white\",\"name\":\"Steve\",\"id\":-3},{\"color\":\"yellow\",\"name\":\"Quinn\",\"id\":-4}]},{\"title\":\"Buy Dev Card\",\"id\":4,\"players\":[{\"color\":\"green\",\"name\":\"Sam\",\"id\":0},{\"color\":\"orange\",\"name\":\"Scott\",\"id\":-2},{\"color\":\"yellow\",\"name\":\"Quinn\",\"id\":-3},{\"color\":\"blue\",\"name\":\"Steve\",\"id\":-4}]},{\"title\":\"Winning game\",\"id\":5,\"players\":[{\"color\":\"purple\",\"name\":\"Sam\",\"id\":0},{\"color\":\"puce\",\"name\":\"Ken\",\"id\":-2},{\"color\":\"white\",\"name\":\"Hannah\",\"id\":-3},{\"color\":\"yellow\",\"name\":\"Miguel\",\"id\":-4}]},{\"title\":\"New John Wayne Game\",\"id\":6,\"players\":[{\"color\":\"purple\",\"name\":\"John\",\"id\":12},{\"color\":\"white\",\"name\":\"Ken\",\"id\":-2},{\"color\":\"puce\",\"name\":\"Squall\",\"id\":-3},{\"color\":\"yellow\",\"name\":\"Steve\",\"id\":-4}]}]";
			http_exchange.sendResponseHeaders(200, response.length());
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getGamesListHandler() {
		return gamesList;
	}

	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a gamesCreate jsonobject
	 * @post new game is created, add to model
	 */
	private HttpHandler gamesCreate = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			GamesCreateJsonObject gcjo = (GamesCreateJsonObject) deserialize(http_exchange, GamesCreateJsonObject.class); 
			ICommand c = new GamesCreateCommand(); 
			
			String response = (String) c.execute(gcjo);
			CommandList.getSingleton().addCommand(c);

			//String response = "{\"title\":\"New Game Title\",\"id\":7,\"players\":[{},{},{},{}]}";
			http_exchange.sendResponseHeaders(200, response.length());
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getGamesCreateHandler() {
		return gamesCreate;
	}

	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a gamesJoin jsonobject
	 * @post user is added to a game
	 */
	private HttpHandler gamesJoin = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			GamesJoinJsonObject gjjo = (GamesJoinJsonObject) deserialize(http_exchange, GamesJoinJsonObject.class); 
			ICommand c = new GamesJoinCommand();
			String response = (String) c.execute(gjjo); 
			CommandList.getSingleton().addCommand(c);
			
//			System.out.println("THIS IS THE HEADER");
//			System.out.println(http_exchange.getRequestHeaders().get("Cookie"));
			List<String> cookies = http_exchange.getRequestHeaders().get("Cookie");
			String cookie = cookies.get(0); 
			cookie = cookie.replaceAll("catan.user=", "");
			//cookie = cookie.replaceAll("", "");
			System.out.println(java.net.URLDecoder.decode(cookie, "UTF-8"));
			//String response = "Successcatan.game=0;Path=/;";
			if(response.substring(0, 7).equals("Success")){
				http_exchange.sendResponseHeaders(200, response.length());
			}else{
				http_exchange.sendResponseHeaders(400, response.length());
			}	
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getGamesJoinHandler() {
		return gamesJoin;
	}

	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a gameModel jsonobject
	 * @post the model is returned
	 */
	private HttpHandler gameModel = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			ICommand c = new GameModelCommand(); 
			String url = http_exchange.getRequestURI().getQuery();
			url = url.replace("version=","");
			int version = Integer.parseInt(url);
			String response = serialize(c.execute(version));
			CommandList.getSingleton().addCommand(c);
			//String response = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"brown\"},{\"resources\":{\"brick\":1,\"wood\":0,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":23,\"wood\":21,\"sheep\":20,\"wheat\":22,\"ore\":22},\"turnTracker\":{\"status\":\"Rolling\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}";
			//http_exchange.getResponseHeaders().set("Set-cookie", "cookiehere");
			http_exchange.sendResponseHeaders(200, response.length());
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getGameModelHandler() {
		return gameModel;
	}

	/**
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a  movesSendChat jsonobject
	 * @post message is added to the game
	 */
	private HttpHandler movesSendChat = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			MovesSendChatJsonObject mscjo = (MovesSendChatJsonObject) deserialize(http_exchange, MovesSendChatJsonObject.class);
			ICommand c = new MovesSendChatCommand();
			String response = serialize(c.execute(mscjo));
			//String response = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":13,\"roadBuilding\":2,\"monument\":3},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"NW\",\"x\":-2,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":-2}},{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":-2,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SE\",\"x\":-1,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":-2}},{\"owner\":1,\"location\":{\"direction\":\"NW\",\"x\":-1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"NE\",\"x\":0,\"y\":-2}},{\"owner\":1,\"location\":{\"direction\":\"SE\",\"x\":-1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"N\",\"x\":-2,\"y\":0}}],\"cities\":[{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":-2,\"y\":0}}],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"W\",\"x\":-2,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SE\",\"x\":-1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":-1,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"NE\",\"x\":0,\"y\":-2}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"NW\",\"x\":-1,\"y\":-1}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}}],\"robber\":{\"x\":1,\"y\":-1}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":6,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":1,\"roadBuilding\":0,\"monument\":1},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":3,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":4,\"monuments\":0,\"playedDevCard\":false,\"discarded\":true,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"brown\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":3,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":4,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":4,\"monuments\":0,\"playedDevCard\":false,\"discarded\":true,\"playerID\":-2,\"playerIndex\":1,\"name\":\"Scott\",\"color\":\"orange\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":3,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":-3,\"playerIndex\":2,\"name\":\"Quinn\",\"color\":\"yellow\"},{\"resources\":{\"brick\":0,\"wood\":3,\"sheep\":0,\"wheat\":0,\"ore\":3},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":-4,\"playerIndex\":3,\"name\":\"Steve\",\"color\":\"blue\"}],\"log\":{\"lines\":[{\"source\":\"Quinn\",\"message\":\"Quinn rolled a 8.\"},{\"source\":\"Quinn\",\"message\":\"Quinn\u0027s turn just ended\"},{\"source\":\"Steve\",\"message\":\"Steve rolled a 6.\"},{\"source\":\"Steve\",\"message\":\"Steve\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 11.\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Scott\",\"message\":\"Scott rolled a 10.\"},{\"source\":\"Scott\",\"message\":\"Scott\u0027s turn just ended\"},{\"source\":\"Quinn\",\"message\":\"Quinn rolled a 3.\"},{\"source\":\"Quinn\",\"message\":\"Quinn\u0027s turn just ended\"},{\"source\":\"Steve\",\"message\":\"Steve rolled a 3.\"},{\"source\":\"Steve\",\"message\":\"Steve\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 4.\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Scott\",\"message\":\"Scott rolled a 10.\"},{\"source\":\"Scott\",\"message\":\"Scott\u0027s turn just ended\"},{\"source\":\"Quinn\",\"message\":\"Quinn rolled a 7.\"},{\"source\":\"Quinn\",\"message\":\"Quinn\u0027s turn just ended\"},{\"source\":\"Steve\",\"message\":\"Steve rolled a 3.\"},{\"source\":\"Steve\",\"message\":\"Steve\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 8.\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Scott\",\"message\":\"Scott rolled a 3.\"},{\"source\":\"Scott\",\"message\":\"Scott\u0027s turn just ended\"},{\"source\":\"Quinn\",\"message\":\"Quinn rolled a 4.\"},{\"source\":\"Quinn\",\"message\":\"Quinn\u0027s turn just ended\"},{\"source\":\"Steve\",\"message\":\"Steve rolled a 2.\"},{\"source\":\"Steve\",\"message\":\"Steve\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam rolled a 7.\"},{\"source\":\"Sam\",\"message\":\"Sam moved the robber and robbed Sam.\"}]},\"chat\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"This is a message\"}]},\"bank\":{\"brick\":24,\"wood\":21,\"sheep\":18,\"wheat\":17,\"ore\":19},\"turnTracker\":{\"status\":\"Playing\",\"currentTurn\":0,\"longestRoad\":1,\"largestArmy\":-1},\"winner\":-1,\"version\":238}";
			if(response.length() < 5){
				response = "Invalid command";
				http_exchange.sendResponseHeaders(400, response.length());
			}else{
				http_exchange.sendResponseHeaders(200, response.length());
			}
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler getMovesSendChatHandler() {
		return movesSendChat;
	}

	// --------------------- Nate's End ---------------------------
	/**
	 * Checks if rolling the dice can occur and calls the rollNumber command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a rollNumber jsonobject
	 * @post rollNumber Command is sent
	 */
	private HttpHandler rollNumber = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			RollJsonObject rollingobject = (RollJsonObject) deserialize(http_exchange, RollJsonObject.class);
			RollNumberCommand rollcommand = new RollNumberCommand();

			String response = serialize(rollcommand.execute(rollingobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler rollNumberHandler() {
		return rollNumber;
	}

	/**
	 * Checks if robbing a player can occur and calls the robPlayer command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a robPlayer jsonobject
	 * @post robPlayer Command is sent
	 */
	private HttpHandler robPlayer = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			RobJsonObject robobject = (RobJsonObject) deserialize(http_exchange, RobJsonObject.class);
			RobPlayerCommand robcommand = new RobPlayerCommand();

			String response = serialize(robcommand.execute(robobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler robPlayerHandler() {
		return robPlayer;
	}

	/**
	 * Checks if finishing the turn can occur and calls the finishTurn command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a finishTurn jsonobject
	 * @post finishTurn Command is sent
	 */
	private HttpHandler finishTurn = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			FinishJsonObject finishTurnobject = (FinishJsonObject) deserialize(http_exchange, FinishJsonObject.class);
			FinishTurnCommand finishTurncommand = new FinishTurnCommand();

			String response = serialize(finishTurncommand.execute(finishTurnobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler finishTurnHandler() {
		return finishTurn;
	}

	/**
	 * Checks if a dev card can be bought and calls the buyDevCard command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a buyDevCard jsonobject
	 * @post buyDevCard Command is sent
	 */
	private HttpHandler buyDevCard = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			DevCardJsonObject devcardobject = (DevCardJsonObject) deserialize(http_exchange, DevCardJsonObject.class);
			BuyDevCardCommand buydevcardcommand = new BuyDevCardCommand();

			String response = serialize(buydevcardcommand.execute(devcardobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler buyDevCardHandler() {
		return buyDevCard;
	}

	/**
	 * Checks if playing a year of plenty card can occur and calls the Year_of_Plenty command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a Year_of_Plenty jsonobject
	 * @post Year_of_Plenty Command is sent
	 */
	private HttpHandler Year_of_Plenty = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			YOPJsonObject yopobject = (YOPJsonObject) deserialize(http_exchange, YOPJsonObject.class);
			YearOfPlentyCommand yearofplentycommand = new YearOfPlentyCommand();

			String response = serialize(yearofplentycommand.execute(yopobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler Year_of_PlentyHandler() {
		return Year_of_Plenty;
	}

	/**
	 * Checks if playing a road building card can occur and calls the
	 * Road_Building command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a Road_Building jsonobject
	 * @post Road_Building Command is sent
	 */
	private HttpHandler Road_Building = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			RoadBuildingJsonObject roadbuildingobject = (RoadBuildingJsonObject) deserialize(http_exchange, RoadBuildingJsonObject.class);
			RoadBuildingCardCommand roadbuildingcommand = new RoadBuildingCardCommand();

			String response = serialize(roadbuildingcommand.execute(roadbuildingobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler Road_BuildingHandler() {
		return Road_Building;
	}

	/**
	 * Checks if playing a soldier card can occur and calls the Soldier command object
	 * 
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a Soldier jsonobject
	 * @post Soldier Command is sent
	 */
	private HttpHandler Soldier = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			SoldierJsonObject soldierobject = (SoldierJsonObject) deserialize(http_exchange, SoldierJsonObject.class);
			SoldierCardCommand soldiercardcommand = new SoldierCardCommand();

			String response = serialize(soldiercardcommand.execute(soldierobject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler SoldierHandler() {
		return Soldier;
	}

	// --------------------- Mike's End ---------------------------
	/**
	 * Checks if Monopoly can occur and calls the Monopoly command object
	 *
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a Monopoly jsonobject
	 * @post Monopoly Command is sent
	 */
	private HttpHandler Monopoly = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			MonopolyJsonObject monopolyObject = (MonopolyJsonObject) deserialize(http_exchange, MonopolyJsonObject.class);
			MonopolyCommand monopolyCommand = new MonopolyCommand();

			String response = serialize(monopolyCommand.execute(monopolyObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(monopolyObject != null) {
				if (monopolyObject.getResource() == null || monopolyObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}

			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler MonopolyHandler() {
		return Monopoly;
	}

	/**
	 * Checks if Monument can occur and calls the Monument command object
	 *
	 * @param http_exchange the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a Monument jsonobject
	 * @post Monopoly Command is sent
	 */
	private HttpHandler Monument = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			MonumentJsonObject monumentObject = (MonumentJsonObject) deserialize(http_exchange, MonumentJsonObject.class);
			MonumentCommand monumentCommand = new MonumentCommand();

			String response = serialize(monumentCommand.execute(monumentObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(monumentObject != null) {
				if (monumentObject.toString() == null || monumentObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}


			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler MonumentHandler() {
		return Monument;
	}

	/**
	 * executes BuildRoadCommand
	 */
	private HttpHandler BuildRoad = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			BuildRoadJsonObject buildRoadJsonObject = (BuildRoadJsonObject) deserialize(http_exchange, BuildRoadJsonObject.class);
			BuildRoadCommand buildRoadCommand = new BuildRoadCommand();

			String response = serialize(buildRoadCommand.execute(buildRoadJsonObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(buildRoadJsonObject != null) {
				if (buildRoadJsonObject.getRoadLocation() == null || buildRoadJsonObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}


			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler BuildRoadHandler() {
		return BuildRoad;
	}

	/**
	 * executes BuildSettlementCommand
	 */
	private HttpHandler BuildSettlement = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			BuildSettlementJsonObject buildSettlementJsonObject = (BuildSettlementJsonObject) deserialize(http_exchange, BuildSettlementJsonObject.class);
			BuildSettlementCommand buildSettlementCommand = new BuildSettlementCommand();

			String response = serialize(buildSettlementCommand.execute(buildSettlementJsonObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(buildSettlementJsonObject != null) {
				if (buildSettlementJsonObject.getVertexLocation() == null || buildSettlementJsonObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}

			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler BuildSettlementHandler() {
		return BuildSettlement;
	}

	/**
	 * executes BuildCityCommand
	 */
	private HttpHandler BuildCity = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			BuildCityJsonObject buildCityJsonObject = (BuildCityJsonObject) deserialize(http_exchange, BuildCityJsonObject.class);
			BuildCityCommand buildCityCommand = new BuildCityCommand();

			String response = serialize(buildCityCommand.execute(buildCityJsonObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(buildCityJsonObject != null) {
				if (buildCityJsonObject.getVertexLocation() == null || buildCityJsonObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}


			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler BuildCityHandler() {
		return BuildCity;
	}

	/**
	 * executes OfferTradeCommand
	 */
	private HttpHandler OfferTrade = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			OfferTradeJsonObject offerTradeJsonObject = (OfferTradeJsonObject) deserialize(http_exchange, OfferTradeJsonObject.class);
			OfferTradeCommand newCommand = new OfferTradeCommand();

			String response = serialize(newCommand.execute(offerTradeJsonObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(offerTradeJsonObject != null) {
				if (offerTradeJsonObject.getOffer() == null || offerTradeJsonObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}


			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler OfferTradeHandler() {
		return OfferTrade;
	}


	/**
	 * executes AcceptTradeCommand
	 */
	private HttpHandler AcceptTrade = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			AcceptTradeJsonObject newJSONObject = (AcceptTradeJsonObject) deserialize(http_exchange, AcceptTradeJsonObject.class);
			AcceptTradeCommand newCommand = new AcceptTradeCommand();

			String response = serialize(newCommand.execute(newJSONObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(newJSONObject != null) {
				if (newJSONObject.getType() == null || newJSONObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}

			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler AcceptTradeHandler() {
		return AcceptTrade;
	}


	/**
	 * executes MaritimeTradeCommand
	 */
	private HttpHandler MaritimeTrade = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			MaritimeTradeJsonObject newJSONObject = (MaritimeTradeJsonObject) deserialize(http_exchange, MaritimeTradeJsonObject.class);
			MaritimeTradeCommand newCommand = new MaritimeTradeCommand();

			String response = serialize(newCommand.execute(newJSONObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(newJSONObject != null) {
				if (newJSONObject.getOutputResource() == null || newJSONObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}

			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler MaritimeTradeHandler() {
		return MaritimeTrade;
	}


	/**
	 * executes DiscardCardsCommand
	 */
	private HttpHandler DiscardCards = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
			DiscardCardsJsonObject newJSONObject = (DiscardCardsJsonObject) deserialize(http_exchange, DiscardCardsJsonObject.class);
			DiscardCardsCommand newCommand = new DiscardCardsCommand();

			String response = serialize(newCommand.execute(newJSONObject));

			http_exchange.sendResponseHeaders(200, response.length());// this assumes the input is correct. you should check to see if there was valid input
			if(newJSONObject != null) {
				if (newJSONObject.getDiscardedCards() == null || newJSONObject.getPlayerIndex() == null) {
					http_exchange.sendResponseHeaders(400, response.length());

				}
			}
			else
			{
				http_exchange.sendResponseHeaders(400, response.length());
			}

			OutputStream os = http_exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	};

	public HttpHandler DiscardCardsHandler() {
		return DiscardCards;
	}



}
