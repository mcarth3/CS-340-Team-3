package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import client.data.GameInfo;
import model.AllInfo;
import model.FailureToAddException;
import model.Game;
import model.ObjectNotFoundException;
import model.Player;
import model.TradeOffer;
import model.UserInfo;
import model.bank.DevCardList;
import model.bank.ResourceList;
import model.clientModel.MessageLine;
import poller.modeljsonparser.ModelParser;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by Jesse on 5/26/2016. This Facade will be called upon by the commands and will be the only class to modify the model on the Server's side.
 */
public class ServerFacade {

	private static ServerFacade singleton = null;
	private Game model = null;
	private AllInfo all = null;
	private String currentUsername;

	public static ServerFacade getSingleton() {
		if (singleton == null) {
			singleton = new ServerFacade();
		}
		return singleton;
	}

	public ServerFacade() {
		String data;

		File fileEverything = new File("alltestgames.json");

		File file = new File("testmodel.json");
		FileReader fileReader = null;
		try {
			//fileReader = new FileReader(file);
			fileReader = new FileReader(fileEverything);
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO IMPORT MODEL");
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Scanner scanner = new Scanner(bufferedReader);
		data = scanner.useDelimiter("\\Z").next();
		scanner.close();

		all = (AllInfo) ModelParser.parse(data, AllInfo.class);
	}

	public void updatemodelnumber() {
		model.updatemodelnumber();
	}

	public Object UserLogin(String username, String password) {

		UserInfo[] users = all.getUsers();
		boolean found = false;
		Integer id = 0;
		for (UserInfo u : users) {
			if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
				found = true;
				id = u.getPlayerID();
			}
		}
		String result = "Failed to login - bad username or password.";
		if (found) {
			currentUsername = username;
			String userInfo = "{\"name\":\"" + username + "\",\"password\":\"" + password + "\",\"playerID\":" + id + "}";
			try {
				result = java.net.URLEncoder.encode(userInfo, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			result = "Successcatan.user=" + result + ";Path=/;";
		}
		return result;
	}

	public Object UserRegister(String username, String password) {

		//TODO: get playerID and figure out authentication
		String userInfo = "{\"authentication\":\"1224085268\",\"name\":\"" + username + "\",\"password\":\"" + password + "\",\"playerID\":13}";
		String result = null;
		try {
			result = java.net.URLEncoder.encode(userInfo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		result = "Successcatan.user=" + result + ";Path=/;";
		//System.out.println(result);

		return result;
	}

	public Object GamesList() {
		//System.out.println("game list in server facade"); 
		GameInfo[] list = all.getGameList();
		return list;
	}

	public Object GamesCreate(String name, boolean numbers, boolean ports, boolean tiles) {
		//System.out.println("game create in server facade");
		return null;
	}

	public Object GamesJoin(Integer id, String color) {

		String response = "The player could not be added to the specified game.";
		Game[] games = all.getGames();
		if (games.length > id) {
			model = games[id];
			ArrayList<Player> players = model.getPlayers();
			boolean foundInGame = false;
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getName().equals(currentUsername)) {
					players.get(i).setColor(color);
					foundInGame = true;
				}
			}
			if (!foundInGame) {
				Player newPlayer = new Player();
				//might need more info here
				newPlayer.setName(currentUsername);
				newPlayer.setColor(color);
				players.add(newPlayer);
				model.setPlayers(players);
			}
			response = "Successcatan.game=" + id + ";Path=/;";
		}
		return response;
	}

	public Object GameModel() {
		// Probably have to do something about the version?
		return model;
	}

	public Object MovesSendChat(Integer id, String content) {
		//System.out.println("moves send chat in server facade"); 

		return model;
	}

	public Game rolldice(Integer index, Integer number) {
		//set state
		if (number == 7) {
			if (model.getPlayers().get(index).getResources().getSize() > 7) {
				model.getTurnTracker().setStatus("Discarding");
			} else {
				model.getTurnTracker().setStatus("Robbing");
			}
		} else {
			model.getTurnTracker().setStatus("Playing");
		}

		//give players resources

		//	find hexes that match this number
		Vector<model.Hex> hexeswithnumber = new Vector<model.Hex>();
		for (model.Hex hex : model.getMap().getHexes()) {
			if (hex.number == number) {
				hexeswithnumber.add(hex);
			}
		}

		//	give those hexes resources to the players owning buildings on the hex
		for (model.Hex hex : hexeswithnumber) {

			//get hex buildings
			//give owner 1 or 2 resources
		}

		//TODO: update log

		updatemodelnumber();
		return model;
	}

	public Object robplayer(Integer index, Integer victimindex, HexLocation location) {
		//set robber location

		//set status to playing

		//if victim index = player index or victim index is out of range
		//	(update log only that no one was robbed)
		//else
		//	rob from victim
		//TODO: update log
		model.getTurnTracker().setStatus("Playing");
		updatemodelnumber();
		return model;
	}

	public Object finishturn(Integer index) {
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(index);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		if (model.getTurnTracker().getStatus().equals("FirstRound")) {
			if (index == 3) {
				model.getTurnTracker().setStatus("SecondRound");
			} else {
				//		model.getTurnTracker().setCurrentTurn(index + 1);
			}
		} else if (model.getTurnTracker().getStatus().equals("SecondRound")) {
			if (index == 0) {
				model.getTurnTracker().setStatus("Rolling");
			} else {
				//	model.getTurnTracker().setCurrentTurn(index - 1);
			}
		} else {
			model.getTurnTracker().setStatus("Rolling");
			//	model.getTurnTracker().setCurrentTurn(index + 1);
		}

		//add new devcards to old devcard list
		DevCardList newList = thePlayer.getNewDevCards();
		DevCardList oldList = thePlayer.getOldDevCards();
		if (newList.getMonopoly() > 0) {
			oldList.setMonopoly(oldList.getMonopoly() + newList.getMonopoly());
		}
		if (newList.getMonument() > 0) {
			oldList.setMonument(oldList.getMonument() + newList.getMonument());
		}
		if (newList.getRoadBuilding() > 0) {
			oldList.setRoadBuilding(oldList.getRoadBuilding() + newList.getRoadBuilding());
		}
		if (newList.getSoldier() > 0) {
			oldList.setSoldier(oldList.getSoldier() + newList.getSoldier());
		}
		if (newList.getYearOfPlenty() > 0) {
			oldList.setYearOfPlenty(oldList.getYearOfPlenty() + newList.getYearOfPlenty());
		}

		thePlayer.setNewDevCards(new DevCardList(0, 0, 0, 0, 0));
		thePlayer.setOldDevCards(oldList);

		MessageLine newLine = new MessageLine(thePlayer.getName() + "'s turn just ended",
				thePlayer.getName());
		model.getLog().getLines().add(newLine);

		updatemodelnumber();
		return model;
	}

	public Object buydevcard(Integer index) {
		//decrement resources of current player
		//randomly select which card from bank's stock
		//remove from bank and add to current player

		//TODO: update log
		updatemodelnumber();
		return model;
	}

	public Object playYOPcard(Integer playerIndex, ResourceType resource1, ResourceType resource2) {
		//player gets first resource from bank
		//player gets second resource from bank
		//remove year of plenty card from player

		//TODO: update log
		updatemodelnumber();
		return model;
	}

	public Object playroadbuildingcard(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		//this.buildroad()
		//remove road building card from player

		//TODO: update log
		updatemodelnumber();
		return model;
	}

	public Object playsoldercard(Integer index, Integer victimindex, HexLocation location) {
		robplayer(index, victimindex, location);
		//remove soldier card from player
		//add soldier to army, determine who has largest army
		//change status?

		//TODO: update log
		updatemodelnumber();
		return model;
	}

	/**
	 * Jesse's methods:
	 */

	/**
	 * play monopoly, gather resources, remove card, add VP, update log.
	 * @param type
	 * @param resource
	 * @param playerIndex
	 * @return
	 */
	public Object playMonopoly(String type, String resource, Integer playerIndex) {
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(playerIndex);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		if (thePlayer != null) {

			thePlayer.getOldDevCards().setMonopoly(thePlayer.getOldDevCards().getMonopoly() - 1);

			ArrayList<Player> allPlayers = model.getPlayers();
			int totalResource = 0;
			for (int i = 0; i < allPlayers.size(); i++) {
				if (allPlayers.get(i).getPlayerIndex() != playerIndex) {
					//acquire all player's resources!!
					totalResource += allPlayers.get(i).getResources().getResourceType(stringTypeToResourceType(resource));
					allPlayers.get(i).getResources().changeResourceTypeWithAmount(stringTypeToResourceType(resource), 0);
				}
			}
			thePlayer.addResource(stringTypeToResourceType(resource), totalResource);
			model.getDeck().setMonopoly(model.getDeck().getMonopoly() + 1);

			MessageLine newLine = new MessageLine(thePlayer.getName() + "monopolized all the " + resource + ".",
					thePlayer.getName());
			model.getLog().getLines().add(newLine);

		}

		updatemodelnumber();
		return model;
	}
	//"Name" used Monopoly and stole all the Resource

	/**
	 * //play monument, remove card, add VP, update log
	 * @param type
	 * @param playerIndex
	 * @return
	 */
	public Object playMonument(String type, Integer playerIndex) {
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(playerIndex);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		if (thePlayer != null) {

			thePlayer.getOldDevCards().setMonument(thePlayer.getOldDevCards().getMonument() - 1);

			thePlayer.setVictoryPoints(thePlayer.getVictoryPoints() + 1);

			model.getDeck().setMonument(model.getDeck().getMonument() + 1);

			MessageLine newLine = new MessageLine(thePlayer.getName() + "built a monument, and gained a Victory Point.",
					thePlayer.getName());
			model.getLog().getLines().add(newLine);

		}

		updatemodelnumber();
		return model;
	}
	//"Name" built a monument and gained a Victory Point

	/**
	 * build road, remove resources, update map and log? (IF FREE)
	 * @param type
	 * @param playerIndex
	 * @param roadLocation
	 * @param free
	 * @return
	 */
	public Object buildRoad(String type, Integer playerIndex, EdgeLocation roadLocation, boolean free) {
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(playerIndex);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		if (thePlayer != null) {

			if (!free) {
				thePlayer.addResource(ResourceType.BRICK, -1);
				thePlayer.addResource(ResourceType.WOOD, -1);

				ResourceList bank = model.getBank();
				bank.changeResourceTypeWithAmount(ResourceType.BRICK, 1);
				bank.changeResourceTypeWithAmount(ResourceType.WOOD, 1);
			}

			try {
				model.getMap().addRoad(roadLocation.getX(), roadLocation.getY(),
						roadLocation.getDir(), playerIndex);
				MessageLine newLine = new MessageLine(thePlayer.getName() + "built a road.", thePlayer.getName());
				model.getLog().getLines().add(newLine);

			} catch (FailureToAddException e) {
				e.printStackTrace();
			}

		}

		updatemodelnumber();
		return model;
	}
	//"Name" built a road

	/**
	 * build settlement, remove resources, update map and log? (IF FREE). Add VP.
	 * @pre Settlement can be built by player (they have enough resources and an open spot) if
	 * the boolean is free and this method is called.
	 * @post the model will have a new settlement owned by the named player, and there
	 * will be a new message in the log to show that the settlement was built. The player's resources will be
	 * decremented and the bank's resources accordingly incremented
	 * @param type
	 * @param playerIndex
	 * @param settlementLocation
	 * @param free
	 * @return a model with the new settlement and a new message
	 */
	public Object buildSettlement(String type, Integer playerIndex, VertexLocation settlementLocation, boolean free) {

		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(playerIndex);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		if (thePlayer != null) {
			if (!free) {
				thePlayer.addResource(ResourceType.BRICK, -1);
				thePlayer.addResource(ResourceType.SHEEP, -1);
				thePlayer.addResource(ResourceType.WOOD, -1);
				thePlayer.addResource(ResourceType.WHEAT, -1);

				ResourceList bank = model.getBank();
				bank.changeResourceTypeWithAmount(ResourceType.WHEAT, 1);
				bank.changeResourceTypeWithAmount(ResourceType.WOOD, 1);
				bank.changeResourceTypeWithAmount(ResourceType.BRICK, 1);
				bank.changeResourceTypeWithAmount(ResourceType.SHEEP, 1);
			}

			try {
				//add settlement to map and add message to log
				model.getMap().addSettlement(settlementLocation.x, settlementLocation.y, settlementLocation.getDir(), playerIndex);
				MessageLine newLine = new MessageLine(thePlayer.getName() + "built a settlement.", thePlayer.getName());
				model.getLog().getLines().add(newLine);

			} catch (FailureToAddException e) {
				e.printStackTrace();
			}

		}

		updatemodelnumber();
		return model;

	}
//"Name" built a settlement

	/**
	 * build city, remove resources, update map and log? (IF FREE). Add VP.
	 * @param type
	 * @param playerIndex
	 * @param cityLocation
	 * @return
	 */
	public Object buildCity(String type, Integer playerIndex, VertexLocation cityLocation) {
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(playerIndex);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		if (thePlayer != null) {

			thePlayer.addResource(ResourceType.WHEAT, -2);
			thePlayer.addResource(ResourceType.ORE, -3);

			ResourceList bank = model.getBank();
			bank.changeResourceTypeWithAmount(ResourceType.WHEAT, 2);
			bank.changeResourceTypeWithAmount(ResourceType.ORE, 3);

			try {
				model.getMap().addCity(cityLocation.x, cityLocation.y, cityLocation.getDir(), playerIndex);
				MessageLine newLine = new MessageLine(thePlayer.getName() + "built a city.", thePlayer.getName());
				model.getLog().getLines().add(newLine);

			} catch (FailureToAddException e) {
				e.printStackTrace();
			}

		}

		updatemodelnumber();
		return model;
	}
	//"Name" built a city

	/**
	 * send trade request with this particular offer to a particular player
	 * @param type
	 * @param playerIndex
	 * @param offer
	 * @param reciever
	 * @return
	 */
	public Object offerTrade(String type, Integer playerIndex, ResourceList offer, Integer reciever) {

		TradeOffer newTrade = new TradeOffer(playerIndex, reciever, offer);
		model.setTradeO(newTrade);

		updatemodelnumber();
		return model;
	}
	//ONLY: Nothing!!

	/**
	 * accept the trade request, coming from acceptor, gotta send to playerIndex and trade resources for both. Notify log.
	 * @param type
	 * @param playerIndex
	 * @param willAccept
	 * @return
	 */
	public Object acceptTrade(String type, Integer playerIndex, boolean willAccept) {

		try {
			Player theOfferPlayer = model.findPlayerbyindex(model.getTradeO().getSender()); //this is the reason for the try/catch
			Player theRecieverPlayer = model.findPlayerbyindex(playerIndex);
			if (willAccept) {

				ResourceList theList = model.getTradeO().getOffer();
				ResourceType theType = ResourceType.BRICK;

				theOfferPlayer.addResource(theType, -theList.getResourceType(theType));
				theRecieverPlayer.addResource(theType, theList.getResourceType(theType));

				theType = ResourceType.ORE;
				theOfferPlayer.addResource(theType, -theList.getResourceType(theType));
				theRecieverPlayer.addResource(theType, theList.getResourceType(theType));

				theType = ResourceType.SHEEP;
				theOfferPlayer.addResource(theType, -theList.getResourceType(theType));
				theRecieverPlayer.addResource(theType, theList.getResourceType(theType));

				theType = ResourceType.WHEAT;
				theOfferPlayer.addResource(theType, -theList.getResourceType(theType));
				theRecieverPlayer.addResource(theType, theList.getResourceType(theType));

				theType = ResourceType.WOOD;
				theOfferPlayer.addResource(theType, -theList.getResourceType(theType));
				theRecieverPlayer.addResource(theType, theList.getResourceType(theType));

				//EMPTY TRADEO
				MessageLine newLine = new MessageLine(theRecieverPlayer.getName() + " accepted the trade.", theOfferPlayer.getName());
				model.getLog().getLines().add(newLine);

			} else {
				MessageLine newLine = new MessageLine(theRecieverPlayer.getName() + " rejected the trade.", theOfferPlayer.getName());
				model.getLog().getLines().add(newLine);
			}
			model.setTradeO(null); //TODO: CHeck if this'll actually work

		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		updatemodelnumber();
		return model;
	}
//ONLY: "The trade was accepted" OR "the trade was not accepted"

	/**
	 * Trades the specified resource with the specified ratio. Tells the bank, updates the log.
	 * @param type
	 * @param playerIndex
	 * @param ratio
	 * @param inputResource
	 * @param outputResource
	 * @return
	 */
	public Object maritimeTrade(String type, Integer playerIndex, Integer ratio, String inputResource, String outputResource) {

		try {
			model.findPlayerbyindex(playerIndex).addResource(stringTypeToResourceType(inputResource), -1 * ratio);
			model.findPlayerbyindex(playerIndex).addResource(stringTypeToResourceType(outputResource), 1);
			ResourceList bank = model.getBank();
			bank.changeResourceTypeWithAmount(stringTypeToResourceType(inputResource), ratio);
			bank.changeResourceTypeWithAmount(stringTypeToResourceType(outputResource), -1);

		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		updatemodelnumber();
		return model;
	}
//Nothing

	/**
	 * will discard the Cards from specified player (??notify log??)
	 * @param type
	 * @param playerIndex
	 * @param discardedCards
	 * @return
	 */
	public Object discardCards(String type, Integer playerIndex, ResourceList discardedCards) {

		model.changePlayerResources(discardedCards, playerIndex);
		model.getTurnTracker().setStatus("Robbing");
		//TODO: How would I check if this is the last person to discard?
		updatemodelnumber();
		return model;
	}
//NOTHING

	public ResourceType stringTypeToResourceType(String theType) {
		String low = theType.toLowerCase();
		if (low.equals("wood")) {
			return ResourceType.WOOD;
		} else if (low.equals("wheat")) {
			return ResourceType.WHEAT;
		} else if (low.equals("brick")) {
			return ResourceType.BRICK;
		} else if (low.equals("ore")) {
			return ResourceType.ORE;
		} else if (low.equals("sheep")) {
			return ResourceType.SHEEP;
		} else {
			System.out.println("Error in stringTypeToResourceType() of ServerFacade!");
			return null;
		}
	}

}
