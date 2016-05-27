package server.commands;

import server.ICommand;

public class FinishTurnCommand implements ICommand {

	/**
	 * turn is changed to the next player, state is set to rolling
	 * 
	 * @param data
	 *            the finishJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: turn is changed to the next player, state is set to rolling
	 */
	@Override
	public Object execute(Object data) {
		return data;
		// TODO Auto-generated method stub

	}
}
