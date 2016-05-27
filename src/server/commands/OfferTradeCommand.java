package server.commands;

import model.Game;
import server.ICommand;

/**
 * Created by Jesse on 5/26/2016.
 */
public class OfferTradeCommand implements ICommand {
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: list of resources is offered to a particular player
	 */
	@Override
	public Game execute(Object data) {
		// TODO Auto-generated method stub
		return null;
	}
}
