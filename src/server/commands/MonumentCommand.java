package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.MonumentJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class MonumentCommand implements ICommand {
	private MonumentJsonObject monumentJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player builds a monument, gains VP, and loses monument card
	 */
	@Override
	public Object execute(Object data) {
		monumentJsonObject = (MonumentJsonObject) data;
		return ServerFacade.getSingleton().playMonument(monumentJsonObject.getType(), monumentJsonObject.getPlayerIndex());

	}
}
