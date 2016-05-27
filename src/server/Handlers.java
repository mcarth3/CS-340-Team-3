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

	public static void deserialize(String[] args) {

	}

	/**
	 * Checks if rolling the dice can occur and called the rollNumber command
	 * object
	 * 
	 * @pre the data sent with the command is in the valid format for a
	 *      rollNumber jsonobject
	 * @post rollNumber Command is sent
	 */
	private HttpHandler rollNumber = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	private HttpHandler robPlayer = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	private HttpHandler finishTurn = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	private HttpHandler buyDevCard = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	private HttpHandler Year_of_Plenty = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	private HttpHandler Road_Building = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	private HttpHandler Soldier = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
}
