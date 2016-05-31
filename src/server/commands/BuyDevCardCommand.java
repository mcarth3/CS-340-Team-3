package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.DevCardJsonObject;

public class BuyDevCardCommand implements ICommand {
	private DevCardJsonObject devcardobject;

	/**
	 * dev card is bought for the player
	 * 
	 * @param data
	 *            the devCardJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player dev card is given to player, resources are decremented
	 */
	@Override
	public Object execute(Object data) {
		devcardobject = (DevCardJsonObject) data;
		return ServerFacade.getSingleton().buydevcard(devcardobject.getindex());
	}
}
