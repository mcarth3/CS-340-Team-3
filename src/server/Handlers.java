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

	private HttpHandler validate_user_handler = new HttpHandler() {// temp atm,
																	// replace
																	// all of
																	// these
																	// with the
																	// right
																	// commands
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler get_project_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler get_sample_image_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler download_batch_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler submit_batch_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler get_fields_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler search_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};

	private HttpHandler download_file_handler = new HttpHandler() {
		@Override
		public void handle(HttpExchange http_exchange) throws IOException {
		}
	};
}
