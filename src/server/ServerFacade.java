package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Vector;

import model.Game;
import model.ObjectNotFoundException;
import model.bank.ResourceList;
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

	public static ServerFacade getSingleton() {
		if (singleton == null) {
			singleton = new ServerFacade();
		}
		return singleton;
	}

	public ServerFacade() {
		String data;

		File file = new File("testmodel.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO IMPORT MODEL");
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Scanner scanner = new Scanner(bufferedReader);
		data = scanner.useDelimiter("\\Z").next();
		scanner.close();

	}

	public Object UserLogin(String username, String password) {

		//TODO: get playerID 
		String userInfo = "{\"name\":\"" + username + "\",\"password\":\"" + password + "\",\"playerID\":0}";
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
		return null;
	}

	public Object GamesCreate(String name, boolean numbers, boolean ports, boolean tiles) {
		//System.out.println("game create in server facade");
		return null;
	}

	public Object GamesJoin(Integer id, String color) {
		//System.out.println("game join in server facade"); 
		return null;
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

		return model;
	}

	public Object finishturn(Integer index) {
		if (model.getTurnTracker().getStatus().equals("FirstRound")) {
			if (index == 3) {
				model.getTurnTracker().setStatus("SecondRound");
			} else {
				model.getTurnTracker().setPlayerIndex(index++);
			}
		} else if (model.getTurnTracker().getStatus().equals("SecondRound")) {
			if (index == 0) {
				model.getTurnTracker().setStatus("Rolling");
			} else {
				model.getTurnTracker().setPlayerIndex(index--);
			}
		} else {
			model.getTurnTracker().setStatus("Rolling");
			model.getTurnTracker().setPlayerIndex(index++);
		}

		//TODO: transfer dev cards from new to old

		//TODO: update log

		return model;
	}

	public Object buydevcard(Integer index) {
		//decrement resources of current player
		//randomly select which card from bank's stock
		//remove from bank and add to current player

		//TODO: update log
		return model;
	}

	public Object playYOPcard(Integer playerIndex, ResourceType resource1, ResourceType resource2) {
		//player gets first resource from bank
		//player gets second resource from bank
		//remove year of plenty card from player

		//TODO: update log
		return model;
	}

	public Object playroadbuildingcard(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		//this.buildroad()
		//remove road building card from player

		//TODO: update log
		return model;
	}

	public Object playsoldercard(Integer index, Integer victimindex, HexLocation location) {
		robplayer(index, victimindex, location);
		//remove soldier card from player
		//add soldier to army, determine who has largest army
		//change status?

		//TODO: update log
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

		return model;
	}
	//"Name" built a road

	/**
	 * build settlement, remove resources, update map and log? (IF FREE). Add VP.
	 * @param type
	 * @param playerIndex
	 * @param settlementLocation
	 * @param free
	 * @return
	 */
	public Object buildSettlement(String type, Integer playerIndex, VertexLocation settlementLocation, boolean free) {

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

			//still gotta add to playerIndex, and take away and add to Bank
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
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

		//TODO: How would I check if this is the last person to discard?
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
