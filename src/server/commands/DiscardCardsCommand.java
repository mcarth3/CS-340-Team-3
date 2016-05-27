package server.commands;

import model.Game;
import server.ICommand;

/**
 * Created by Jesse on 5/26/2016.
 */
public class DiscardCardsCommand implements ICommand {
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: list of cards is taken away from player
	 */
	@Override
	public Game execute(Object data) {
		// TODO Auto-generated method stub
		return null;
	}
}
