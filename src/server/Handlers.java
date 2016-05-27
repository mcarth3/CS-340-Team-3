package server;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Created by Jesse on 5/26/2016. This class might contain 25 methods. However,
 * we could modify it to make it 25 different Handlers that implement a
 * particular interface called Handler. Regardless of the design, this will be
 * called upon from the Server API and will create its own commands and call
 * execute() on them.
 */
public class Handlers {

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
	 * @param jsondata
	 *            the data given with the command to the server
	 * @param givenclass
	 *            the class to deserialize the data into
	 * @pre givenclass is a valid jsonobject and jsondata is in the valid format
	 *      for the givenclass
	 * @post the specified jsonobject is returned from the parsed data
	 */
	public static <T> T deserialize(String jsondata, Class<T> givenclass) {
		return null;

	}
//--------------------- Nate's Start ---------------------------
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      userLogin jsonobject
	 * @post user is logged in 
	 */
	private HttpHandler userLogin = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      userRegister jsonobject
	 * @post user is registered
	 */
	private HttpHandler userRegister = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      gamesList jsonobject
	 * @post returns a list of games
	 */
	private HttpHandler gamesList = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      gamesCreate jsonobject
	 * @post new game is created, add to model 
	 */
	private HttpHandler gamesCreate = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      gamesJoin jsonobject
	 * @post user is added to a game 
	 */
	private HttpHandler gamesJoin = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      gameModel jsonobject
	 * @post the model is returned
	 */
	private HttpHandler gameModel = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      movesSendChat jsonobject
	 * @post message is added to the game 
	 */
	private HttpHandler movesSendChat = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
//--------------------- Nate's End ---------------------------
	/**
	 * Checks if rolling the dice can occur and calls the rollNumber command
	 * object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      rollNumber jsonobject
	 * @post rollNumber Command is sent
	 */
	private HttpHandler rollNumber = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * Checks if robbing a player can occur and calls the robPlayer command
	 * object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      robPlayer jsonobject
	 * @post robPlayer Command is sent
	 */
	private HttpHandler robPlayer = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * Checks if finishing the turn can occur and calls the finishTurn command
	 * object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      finishTurn jsonobject
	 * @post finishTurn Command is sent
	 */
	private HttpHandler finishTurn = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * Checks if a dev card can be bought and calls the buyDevCard command
	 * object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      buyDevCard jsonobject
	 * @post buyDevCard Command is sent
	 */
	private HttpHandler buyDevCard = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * Checks if playing a year of plenty card can occur and calls the
	 * Year_of_Plenty command object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      Year_of_Plenty jsonobject
	 * @post Year_of_Plenty Command is sent
	 */
	private HttpHandler Year_of_Plenty = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * Checks if playing a road building card can occur and calls the
	 * Road_Building command object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a
	 *      Road_Building jsonobject
	 * @post Road_Building Command is sent
	 */
	private HttpHandler Road_Building = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	/**
	 * Checks if playing a soldier card can occur and calls the Soldier command
	 * object
	 * 
	 * @param http_exchange
	 *            the rest of the data given with the command to the server
	 * @pre the data sent with the command is in the valid format for a Soldier
	 *      jsonobject
	 * @post Soldier Command is sent
	 */
	private HttpHandler Soldier = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
}
