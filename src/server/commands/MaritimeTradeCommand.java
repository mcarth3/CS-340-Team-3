package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.MaritimeTradeJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class MaritimeTradeCommand implements ICommand {
	private MaritimeTradeJsonObject maritimeTradeJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains requested amount of resources and bank trades them
	 */
	@Override
	public Object execute(Object data) {
		maritimeTradeJsonObject = (MaritimeTradeJsonObject) data;
		return ServerFacade.getSingleton().maritimeTrade(maritimeTradeJsonObject.getType(), maritimeTradeJsonObject.getPlayerIndex(),
				maritimeTradeJsonObject.getRatio(), maritimeTradeJsonObject.getInputResource(), maritimeTradeJsonObject.getOutputResource());

	}
}
