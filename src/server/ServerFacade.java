package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import model.Game;
import model.ObjectNotFoundException;
import model.Player;
import model.bank.ResourceList;
import server.input.UserLoginInput;
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

	public Object UserLogin(Object input) {
		// TODO: This is where the magic happens
		UserLoginInput uli = (UserLoginInput) input;
		System.out.println(uli.getUsername());
		System.out.println(uli.getPassword());

		// TODO: Maybe make UserLoginOutput to return?

		return input;
	}

	public Game rolldice(Integer index, Integer number) {
		model.getTurnTracker().setStatus("Playing");
		//give resources to players
		return model;
	}

	public Object robplayer(Integer index, Integer victimindex, HexLocation location) {
		//rob player
		return model;
	}

	public Object finishturn(Integer index) {
		model.getTurnTracker().setStatus("Rolling");
		model.getTurnTracker().setPlayerIndex(index);
		return model;
	}

	public Object buydevcard(Integer index) {
		//buy dev card
		return model;
	}

	public Object playYOPcard(Integer playerIndex, ResourceType resource1, ResourceType resource2) {
		//play year of plenty card
		return model;
	}

	public Object playroadbuildingcard(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		//play road building card
		return model;
	}

	public Object playsoldercard(Integer index, Integer victimindex, HexLocation location) {
		//rob player and remove card
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

	/**
	 * //play monument, remove card, add VP, update log
	 * @param type
	 * @param playerIndex
     * @return
     */
	public Object playMonument(String type, Integer playerIndex) {

		return model;
}

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

		return model;
	}


	/**
	 * will discard the Cards from specified player (??notify log??)
	 * @param type
	 * @param playerIndex
	 * @param discardedCards
     * @return
     */
	public Object discardCards(String type, Integer playerIndex, ResourceList discardedCards) {

		model.changePlayerResources(discardedCards, playerIndex);

		//TODO: update the log? How would I check if this is the last person to discard?
		return model;
	}


}
