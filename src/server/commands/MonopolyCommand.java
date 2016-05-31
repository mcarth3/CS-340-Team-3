package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.MonopolyJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class MonopolyCommand implements ICommand {
	private MonopolyJsonObject monopolyJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains all players' amounts of particular resources
	 */
	@Override
	public Object execute(Object data) {
		monopolyJsonObject = (MonopolyJsonObject) data;
		return ServerFacade.getSingleton().playMonopoly(monopolyJsonObject.getType(), monopolyJsonObject.getResource(), monopolyJsonObject.getPlayerIndex());

	}
}
