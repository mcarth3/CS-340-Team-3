package server.commands;

import server.ICommand;

public class RoadBuildingCardCommand implements ICommand {

	/**
	 * road building card is played for player
	 * 
	 * @param data
	 *            the roadBuildingJsonObject containing this method's needed
	 *            info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: road building card is removed and its effects are added to the
	 *        model
	 */
	@Override
	public void execute(Object data) {
		// TODO Auto-generated method stub

	}
}
