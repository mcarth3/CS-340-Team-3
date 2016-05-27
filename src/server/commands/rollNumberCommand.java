package server.commands;

import server.ICommand;

public class rollNumberCommand implements ICommand {

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	/**
	 * dice roll actions are done
	 *
	 * @param data
	 *            the rollJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: players recieve resources if they're on the number that was
	 *        rolled, status is changed to playing
	 */

	@Override
	public void execute(Object data) {
		// TODO Auto-generated method stub

	}
}
