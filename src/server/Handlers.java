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

	private HttpHandler validate_user_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
	// temp atm, have a version for each of the right commands

}
