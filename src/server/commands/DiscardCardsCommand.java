package server.commands;

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
	public void execute() {

	}

	@Override
	public void execute(Object data) {
		// TODO Auto-generated method stub

	}
}
