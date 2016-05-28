package server;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.logging.*;
import com.sun.net.httpserver.*;
//import server.handlers.*;
import java.io.IOException;
import java.net.InetSocketAddress;


public class Server {
	
	private static int portNum = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private HttpServer server;
	
	private Server() {
		return;
	}
	
	private void run(int port) {
		
		try {
			server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {			
			return;
		}

		server.setExecutor(null); // use the default executor
		server.createContext("/user/login", userLogin);
		server.createContext("/user/register", userRegister);
		server.createContext("/games/list", gamesList);
		server.createContext("/games/create", gamesCreate);
		server.createContext("/games/join", gamesJoin);
		server.createContext("/game/model", gameModel);
		server.createContext("/moves/sendChat", movesSendChat);
		
		System.out.println("Server is starting"); 

		server.start();
	}
	public Handlers handlers = new Handlers(); 
	private HttpHandler userLogin =  handlers.getUserLoginHandler();
	private HttpHandler userRegister =  handlers.getUserRegisterHandler();
	private HttpHandler gamesList =  handlers.getGamesListHandler();
	private HttpHandler gamesCreate =  handlers.getGamesCreateHandler();
	private HttpHandler gamesJoin =  handlers.getGamesJoinHandler();
	private HttpHandler gameModel =  handlers.getGameModelHandler();
	private HttpHandler movesSendChat =  handlers.getMovesSendChatHandler();

	public static void decode(String[] args) {

	}
	
	public static void main(String[] args) {
		new Server().run(portNum);
	}

}
