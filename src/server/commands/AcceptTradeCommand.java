package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.AcceptTradeJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class AcceptTradeCommand implements ICommand {
	private AcceptTradeJsonObject acceptTradeJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: list of resources is traded between two players
	 */

	@Override
	public Object execute(Object data) {
		acceptTradeJsonObject = (AcceptTradeJsonObject) data;
		return ServerFacade.getSingleton().acceptTrade(acceptTradeJsonObject.getType(), acceptTradeJsonObject.getPlayerIndex(),
				acceptTradeJsonObject.isWillAccept());

	}
}
