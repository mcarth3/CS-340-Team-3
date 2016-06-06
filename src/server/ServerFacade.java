package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import client.data.GameInfo;
import model.AllInfo;
import model.City;
import model.FailureToAddException;
import model.Game;
import model.Hex;
import model.ObjectNotFoundException;
import model.Player;
import model.Settlement;
import model.TradeOffer;
import model.UserInfo;
import model.bank.DevCardList;
import model.bank.ResourceList;
import model.clientModel.MessageLine;
import poller.modeljsonparser.ModelParser;
import shared.definitions.DevCardType;
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
		try {
			System.out.println(new File(".").getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(index);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

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
		Vector<Settlement> settlements = new Vector<Settlement>();
		Vector<City> cities = new Vector<City>();
		for (Settlement vo : model.getMap().getsettlements()) {
			settlements.add(vo);
		}
		for (City vo : model.getMap().getcities()) {
			cities.add(vo);
		}
		giveResources(hexeswithnumber, settlements, cities);

		//update log
		MessageLine newLine = new MessageLine(thePlayer.getName() + " rolled a " + number,
				thePlayer.getName());
		model.getLog().getLines().add(newLine);

		updatemodelnumber();
		return model;
	}

	private void giveResources(Vector<model.Hex> hexeswithnumber, Vector<Settlement> settlements, Vector<City> cities) {
		for (Settlement currentbuilding : settlements) {
			//	System.out.println(currentbuilding.getVertextLocation());
			ArrayList<HexLocation> hexLocs = currentbuilding.getVertextLocation().getAdjacentHexes();
			for (HexLocation hexloc : hexLocs) {
				for (Hex rolledHex : hexeswithnumber) {
					if (hexloc.equals(rolledHex.getLocation())) {
						ResourceList resources = model.getPlayers().get(currentbuilding.getOwner()).getResources();
						ResourceList bank = model.getBank();
						try {
							System.out.println("giving " + model.getPlayers().get(currentbuilding.getOwner()).getName() + " 1 " + rolledHex.getResource());
							switch (rolledHex.getResource()) {
							case "brick":
								if (bank.getBrick() > 0) {
									resources.setBrick(resources.getBrick() + 1);
									bank.setBrick(bank.getBrick() - 1);
								}
								break;
							case "ore":
								if (bank.getOre() > 0) {
									resources.setOre(resources.getOre() + 1);
									bank.setOre(bank.getOre() - 1);

								}
								break;
							case "sheep":
								if (bank.getSheep() > 0) {
									resources.setSheep(resources.getSheep() + 1);
									bank.setSheep(bank.getSheep() - 1);

								}
								break;
							case "wheat":
								if (bank.getWheat() > 0) {
									resources.setWheat(resources.getWheat() + 1);
									bank.setWheat(bank.getWheat() - 1);
								}
								break;
							case "wood":
								if (bank.getWood() > 0) {
									resources.setWood(resources.getWood() + 1);
									bank.setWood(bank.getWood() - 1);
								}
								break;
							}
						} catch (Exception e) {
							System.out.println("Num Cards Out Of Range");
						}

					}
				}
			}
		}
		for (City currentbuilding : cities) {
			//	System.out.println(currentbuilding.getVertextLocation());
			ArrayList<HexLocation> hexLocs = currentbuilding.getVertextLocation().getAdjacentHexes();
			for (HexLocation hexloc : hexLocs) {
				for (Hex rolledHex : hexeswithnumber) {
					if (hexloc.equals(rolledHex.getLocation())) {
						ResourceList resources = model.getPlayers().get(currentbuilding.getOwner()).getResources();
						ResourceList bank = model.getBank();

						System.out.println("giving " + model.getPlayers().get(currentbuilding.getOwner()).getName() + " 1 " + rolledHex.getResource());
						switch (rolledHex.getResource()) {
						case "brick":
							if (bank.getBrick() > 0) {
								resources.setBrick(resources.getBrick() + 2);
								bank.setBrick(bank.getBrick() - 2);
							}
							break;
						case "ore":
							if (bank.getOre() > 0) {
								resources.setOre(resources.getOre() + 2);
								bank.setOre(bank.getOre() - 2);

							}
							break;
						case "sheep":
							if (bank.getSheep() > 0) {
								resources.setSheep(resources.getSheep() + 2);
								bank.setSheep(bank.getSheep() - 2);

							}
							break;
						case "wheat":
							if (bank.getWheat() > 0) {
								resources.setWheat(resources.getWheat() + 2);
								bank.setWheat(bank.getWheat() - 2);
							}
							break;
						case "wood":
							if (bank.getWood() > 0) {
								resources.setWood(resources.getWood() + 2);
								bank.setWood(bank.getWood() - 2);
							}
							break;
						}

					}
				}
			}
		}
	}

	public Object robplayer(Integer index, Integer victimindex, HexLocation location) {
		Player thePlayer = null;
		Player victim = null;
		try {
			thePlayer = model.findPlayerbyindex(index);
			victim = model.findPlayerbyindex(victimindex);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		//set robber location
		model.getMap().getRobber().setHl(location);

		//set status to playing
		model.getTurnTracker().setStatus("Playing");

		MessageLine newLine;

		if ((victimindex == index) || (victimindex == -1)) {
			//update log only
			newLine = new MessageLine(thePlayer.getName() + " moved the robber and robbed " + "nobody",
					thePlayer.getName());
		} else {
			ResourceList playerList = model.getPlayers().get(index).getResources();
			ResourceList victimList = model.getPlayers().get(victimindex).getResources();

			//creates an arraylist of the only available stealable resources
			ArrayList<ResourceType> typelist = new ArrayList<ResourceType>();
			if (victimList.getBrick() > 0) {
				typelist.add(ResourceType.BRICK);
			}
			if (victimList.getOre() > 0) {
				typelist.add(ResourceType.ORE);
			}
			if (victimList.getWheat() > 0) {
				typelist.add(ResourceType.WHEAT);
			}
			if (victimList.getSheep() > 0) {
				typelist.add(ResourceType.SHEEP);
			}
			if (victimList.getWood() > 0) {
				typelist.add(ResourceType.WOOD);
			}

			Random rand = new Random();
			ResourceType randomtype = typelist.get(rand.nextInt(((typelist.size() - 1) - 0) + 1) + 0);
			//randomly select a type to steal
			switch (randomtype) {
			case BRICK:
				playerList.setBrick(playerList.getBrick() + 1);
				victimList.setBrick(victimList.getBrick() - 1);
				break;
			case ORE:
				playerList.setOre(playerList.getOre() + 1);
				victimList.setOre(victimList.getOre() - 1);
				break;
			case SHEEP:
				playerList.setSheep(playerList.getSheep() + 1);
				victimList.setSheep(victimList.getSheep() - 1);
				break;
			case WHEAT:
				playerList.setWheat(playerList.getWheat() + 1);
				victimList.setWheat(victimList.getWheat() - 1);
				break;
			case WOOD:
				playerList.setWood(playerList.getWood() + 1);
				victimList.setWood(victimList.getWood() - 1);
				break;
			}

			//update log
			newLine = new MessageLine(thePlayer.getName() + " moved the robber and robbed " + victim.getName(),
					thePlayer.getName());
		}
		model.getLog().getLines().add(newLine);

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
		model.getPlayers().get(index).setPlayedDevCard(false);

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
			if (index == 3) {
				//		model.getTurnTracker().setCurrentTurn(0);
			} else {
				//		model.getTurnTracker().setCurrentTurn(index + 1);
			}
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
		Player thePlayer = null;
		try {
			thePlayer = model.findPlayerbyindex(index);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}

		//moves resources from player back into bank
		model.getPlayers().get(index).getResources().setOre(model.getPlayers().get(index).getResources().getOre() - 1);
		model.getPlayers().get(index).getResources().setWheat(model.getPlayers().get(index).getResources().getWheat() - 1);
		model.getPlayers().get(index).getResources().setSheep(model.getPlayers().get(index).getResources().getSheep() - 1);
		model.getBank().setOre(model.getBank().getOre() + 1);
		model.getBank().setWheat(model.getBank().getWheat() + 1);
		model.getBank().setSheep(model.getBank().getSheep() + 1);

		//randomly select dev card type from bank's stock
		int totaldevcards = model.getDeck().getMonopoly() + model.getDeck().getMonument() + model.getDeck().getSoldier() + model.getDeck().getRoadBuilding() + model.getDeck().getYearOfPlenty();

		ArrayList<DevCardType> devcardlist = new ArrayList<DevCardType>();
		for (int i = 0; i < model.getDeck().getMonopoly(); i++) {
			devcardlist.add(DevCardType.MONOPOLY);
		}
		for (int i = 0; i < model.getDeck().getMonument(); i++) {
			devcardlist.add(DevCardType.MONUMENT);
		}
		for (int i = 0; i < model.getDeck().getSoldier(); i++) {
			devcardlist.add(DevCardType.SOLDIER);
		}
		for (int i = 0; i < model.getDeck().getRoadBuilding(); i++) {
			devcardlist.add(DevCardType.ROAD_BUILD);
		}
		for (int i = 0; i < model.getDeck().getYearOfPlenty(); i++) {
			devcardlist.add(DevCardType.YEAR_OF_PLENTY);
		}

		Random rand = new Random();
		DevCardType card = devcardlist.get(rand.nextInt((((totaldevcards - 1) - 0) + 1) + 0));

		//remove from bank and add to current player
		switch (card) {
		case MONOPOLY:
			model.getDeck().setMonopoly(model.getDeck().getMonopoly() - 1);
			model.getPlayers().get(index).getNewDevCards().setMonopoly(model.getPlayers().get(index).getNewDevCards().getMonopoly() + 1);
			break;
		case MONUMENT:
			model.getDeck().setMonument(model.getDeck().getMonument() - 1);
			model.getPlayers().get(index).getOldDevCards().setMonument(model.getPlayers().get(index).getOldDevCards().getMonument() + 1);
			break;
		case SOLDIER:
			model.getDeck().setSoldier(model.getDeck().getSoldier() - 1);
			model.getPlayers().get(index).getNewDevCards().setSoldier(model.getPlayers().get(index).getNewDevCards().getSoldier() + 1);
			break;
		case ROAD_BUILD:
			model.getDeck().setRoadBuilding(model.getDeck().getRoadBuilding() - 1);
			model.getPlayers().get(index).getNewDevCards().setRoadBuilding(model.getPlayers().get(index).getNewDevCards().getRoadBuilding() + 1);
			break;
		case YEAR_OF_PLENTY:
			model.getDeck().setYearOfPlenty(model.getDeck().getYearOfPlenty() - 1);
			model.getPlayers().get(index).getNewDevCards().setYearOfPlenty(model.getPlayers().get(index).getNewDevCards().getYearOfPlenty() + 1);
			break;

		}

		MessageLine newLine = new MessageLine(thePlayer.getName() + " bought a Development Card",
				thePlayer.getName());
		model.getLog().getLines().add(newLine);

		updatemodelnumber();
		return model;
	}

	public Object playYOPcard(Integer playerIndex, ResourceType resource1, ResourceType resource2) {
		//player gets first resource from bank
		//--------------------------------CHECK SERVER OR CLIENT SIDE?---------------------------------
		switch (resource1) {
		case BRICK:
			if (model.getBank().getBrick() > 0) {
				model.getPlayers().get(playerIndex).getResources().setBrick(model.getPlayers().get(playerIndex).getResources().getBrick() + 1);
				model.getBank().setBrick(model.getBank().getBrick() - 1);
			}
			break;
		case ORE:
			if (model.getBank().getOre() > 0) {
				model.getPlayers().get(playerIndex).getResources().setOre(model.getPlayers().get(playerIndex).getResources().getOre() + 1);
				model.getBank().setOre(model.getBank().getOre() - 1);

			}
			break;
		case SHEEP:
			if (model.getBank().getSheep() > 0) {
				model.getPlayers().get(playerIndex).getResources().setSheep(model.getPlayers().get(playerIndex).getResources().getSheep() + 1);
				model.getBank().setSheep(model.getBank().getSheep() - 1);

			}
			break;
		case WHEAT:
			if (model.getBank().getWheat() > 0) {
				model.getPlayers().get(playerIndex).getResources().setWheat(model.getPlayers().get(playerIndex).getResources().getWheat() + 1);
				model.getBank().setWheat(model.getBank().getWheat() - 1);
			}
			break;
		case WOOD:
			if (model.getBank().getWood() > 0) {
				model.getPlayers().get(playerIndex).getResources().setWood(model.getPlayers().get(playerIndex).getResources().getWood() + 1);
				model.getBank().setWood(model.getBank().getWood() - 1);
			}
			break;
		}

		//player gets second resource from bank
		switch (resource2) {
		case BRICK:
			if (model.getBank().getBrick() > 0) {
				model.getPlayers().get(playerIndex).getResources().setBrick(model.getPlayers().get(playerIndex).getResources().getBrick() + 1);
				model.getBank().setBrick(model.getBank().getBrick() - 1);
			}
			break;
		case ORE:
			if (model.getBank().getOre() > 0) {
				model.getPlayers().get(playerIndex).getResources().setOre(model.getPlayers().get(playerIndex).getResources().getOre() + 1);
				model.getBank().setOre(model.getBank().getOre() - 1);

			}
			break;
		case SHEEP:
			if (model.getBank().getSheep() > 0) {
				model.getPlayers().get(playerIndex).getResources().setSheep(model.getPlayers().get(playerIndex).getResources().getSheep() + 1);
				model.getBank().setSheep(model.getBank().getSheep() - 1);

			}
			break;
		case WHEAT:
			if (model.getBank().getWheat() > 0) {
				model.getPlayers().get(playerIndex).getResources().setWheat(model.getPlayers().get(playerIndex).getResources().getWheat() + 1);
				model.getBank().setWheat(model.getBank().getWheat() - 1);
			}
			break;
		case WOOD:
			if (model.getBank().getWood() > 0) {
				model.getPlayers().get(playerIndex).getResources().setWood(model.getPlayers().get(playerIndex).getResources().getWood() + 1);
				model.getBank().setWood(model.getBank().getWood() - 1);
			}
			break;
		}

		model.getPlayers().get(playerIndex).setPlayedDevCard(true);

		//remove year of plenty card from player
		model.getPlayers().get(playerIndex).getOldDevCards().setYearOfPlenty(model.getPlayers().get(playerIndex).getOldDevCards().getYearOfPlenty() - 1);

		//----------------------------------TODO: update log?---------------------------------------------
		updatemodelnumber();
		return model;

	}

	public Object playroadbuildingcard(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		//-----------------------TODO: this.buildroad()?------------------------

		model.getPlayers().get(playerIndex).setPlayedDevCard(true);

		//remove road building card from player
		model.getPlayers().get(playerIndex).getOldDevCards().setRoadBuilding(model.getPlayers().get(playerIndex).getOldDevCards().getRoadBuilding() - 1);

		//-----------------------------TODO: update log?------------------------
		updatemodelnumber();
		return model;
	}

	public Object playsoldercard(Integer index, Integer victimindex, HexLocation location) {
		//add soldier to army, determine who has largest army
		model.getPlayers().get(index).setSoldiers(model.getPlayers().get(index).getSoldiers() + 1);
		model.getPlayers().get(index).setPlayedDevCard(true);

		setbiggestarmy();

		//--------------TODO: change status? or have the rob view pop up?------------------
		model.getTurnTracker().setStatus("Robbing");

		//remove soldier card from player
		model.getPlayers().get(index).getOldDevCards().setSoldier(model.getPlayers().get(index).getOldDevCards().getSoldier() - 1);

		//---------------------------TODO: update log?--------------------------------
		updatemodelnumber();
		return model;
	}

	private void setbiggestarmy() {
		Map<Integer, Integer> soldiersmap = new HashMap<Integer, Integer>();
		for (int i = 0; i < 4; i++) {
			soldiersmap.put(i, model.getPlayers().get(i).getSoldiers());
			//-------------------TODO: remove VP from prev owner-----------------------
		}
		int mostsoldiers = Collections.max(soldiersmap.values());
		for (Entry<Integer, Integer> entry : soldiersmap.entrySet()) {
			if (entry.getValue().equals(soldiersmap)) {
				if (model.getPlayers().get(mostsoldiers).getSoldiers() > 2) {
					model.getTurnTracker().setLargestArmy(mostsoldiers);
					model.getPlayers().get(mostsoldiers).setVictoryPoints(model.getPlayers().get(mostsoldiers).getVictoryPoints() + 2);
				}

			}
		}
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
		model.getPlayers().get(playerIndex).setPlayedDevCard(true);

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

		model.getPlayers().get(playerIndex).setPlayedDevCard(true);

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
		model.getBank().merge(model.getBank(), discardedCards);

		discardedCards = negatizeResourceList(discardedCards);
		model.changePlayerResources(discardedCards, playerIndex);
		model.getTurnTracker().setStatus("Robbing");
		//TODO: How would I check if this is the last person to discard?
		updatemodelnumber();
		return model;
	}
//NOTHING

	/**
	 * multiplies all values of a resourceList by -1 and returns said list.
	 * Essentially makes all positive numbers negative and vice versa in a ResourceList.
	 * @param theOriginal
	 * @return
     */
	public ResourceList negatizeResourceList(ResourceList theOriginal)
	{
		ResourceList theNew = new ResourceList();
		theNew.setWheat(-1 * theOriginal.getWheat());
		theNew.setOre(-1 * theOriginal.getOre());
		theNew.setWood(-1 * theOriginal.getWood());
		theNew.setBrick(-1 * theOriginal.getBrick());
		theNew.setSheep(-1 * theOriginal.getSheep());



		return theNew;
	}

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
