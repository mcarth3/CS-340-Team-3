package server.commands;

import model.Game;
import server.ICommand;

/**
 * Created by Jesse on 5/26/2016.
 */
public class MonumentCommand implements ICommand {
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player builds a monument, gains VP, and loses monument card
	 */
	@Override
	public Game execute(Object data) {
		// TODO Auto-generated method stub
		return null;
	}
}
