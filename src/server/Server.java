package server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class Server {
	private static int portNum = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	public static void main(String[] args) {
		Server server = new Server(portNum);
		server.run();
	}

	private void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}

		HttpServer http_server;
		try {
			http_server = HttpServer.create(new InetSocketAddress(portNum), MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		http_server.setExecutor(null);
		// http_server.createContext("/validate_user", validate_user_handler);
		// //worked when handlers were in this file
		// http_server.createContext("/get_projects", get_project_handler);
		// http_server.createContext("/get_sample_image",
		// get_sample_image_handler);
		// http_server.createContext("/download_batch", download_batch_handler);
		// http_server.createContext("/submit_batch", submit_batch_handler);
		// http_server.createContext("/get_fields", get_fields_handler);
		// http_server.createContext("/search", search_handler);
		// http_server.createContext("/", download_file_handler);
		http_server.start();
	}

	private Server(int portNum) {
		Server.portNum = portNum;
	}

	public void return_failed(HttpExchange http_exchange) throws IOException {
	}

	public static void decode(String[] args) {

	}

}
