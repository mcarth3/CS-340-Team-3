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
