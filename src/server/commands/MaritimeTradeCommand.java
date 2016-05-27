package server.commands;

import model.Game;
import server.ICommand;

/**
 * Created by Jesse on 5/26/2016.
 */
public class MaritimeTradeCommand implements ICommand {
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains requested amount of resources and bank trades them
	 */
	@Override
	public Game execute(Object data) {
		// TODO Auto-generated method stub
		return null;
	}
}
