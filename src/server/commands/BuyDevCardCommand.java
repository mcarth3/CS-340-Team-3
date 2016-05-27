package server.commands;

import server.ICommand;

public class BuyDevCardCommand implements ICommand {

	/**
	 * dev card is bought for the player
	 * 
	 * @param data
	 *            the devCardJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player dev card is given to player, resources are decremented
	 */
	@Override
	public void execute(Object data) {
		// TODO Auto-generated method stub

	}
}
