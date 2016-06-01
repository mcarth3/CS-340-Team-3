package server;

//import server.handlers.*;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

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
		} catch (IOException e) {
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
		
		server.createContext("/moves/rollNumber", rollNumber);
		server.createContext("/moves/robPlayer", robPlayer);
		server.createContext("/moves/finishTurn", finishTurn);
		server.createContext("/moves/buyDevCard", buyDevCard);
		server.createContext("/moves/Soldier", Soldier);
		server.createContext("/moves/Year_of_Plenty", Year_of_Plenty);
		server.createContext("/moves/Road_Building", Road_Building);

		server.createContext("/moves/Monopoly", Monopoly);
		server.createContext("/moves/Monument", Monument);
		server.createContext("/moves/buildRoad", BuildRoad);
		server.createContext("/moves/buildSettlement", BuildSettlement);
		server.createContext("/moves/buildCity", BuildCity);
		server.createContext("/moves/offerTrade", OfferTrade);
		server.createContext("/moves/acceptTrade", AcceptTrade);
		server.createContext("/moves/maritimeTrade", MaritimeTrade); //TODO: this is spelled martimeTrade in the specs, is it "maritimeTrade"?
		server.createContext("/moves/discardCards", DiscardCards);



		System.out.println("Server is starting");

		server.start();
	}

	public Handlers handlers = new Handlers();
	private HttpHandler userLogin = handlers.getUserLoginHandler();
	private HttpHandler userRegister = handlers.getUserRegisterHandler();
	private HttpHandler gamesList = handlers.getGamesListHandler();
	private HttpHandler gamesCreate = handlers.getGamesCreateHandler();
	private HttpHandler gamesJoin = handlers.getGamesJoinHandler();
	private HttpHandler gameModel = handlers.getGameModelHandler();
	private HttpHandler movesSendChat = handlers.getMovesSendChatHandler();
	
	private HttpHandler rollNumber = handlers.rollNumberHandler();
	private HttpHandler robPlayer = handlers.robPlayerHandler();
	private HttpHandler finishTurn = handlers.finishTurnHandler();
	private HttpHandler buyDevCard = handlers.buyDevCardHandler();
	private HttpHandler Soldier = handlers.SoldierHandler();
	private HttpHandler Year_of_Plenty = handlers.Year_of_PlentyHandler();
	private HttpHandler Road_Building = handlers.Road_BuildingHandler();

	private HttpHandler Monopoly = handlers.MonopolyHandler();
	private HttpHandler Monument = handlers.MonumentHandler();
	private HttpHandler BuildRoad = handlers.BuildRoadHandler();
	private HttpHandler BuildSettlement = handlers.BuildSettlementHandler();
	private HttpHandler BuildCity = handlers.BuildCityHandler();
	private HttpHandler OfferTrade = handlers.OfferTradeHandler();
	private HttpHandler AcceptTrade = handlers.AcceptTradeHandler();
	private HttpHandler MaritimeTrade = handlers.MaritimeTradeHandler();
	private HttpHandler DiscardCards = handlers.DiscardCardsHandler();




	public static void decode(String[] args) {

	}

	public static void main(String[] args) {
		new Server().run(portNum);

	}

}
