package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.RobJsonObject;

public class RobPlayerCommand implements ICommand {
	private RobJsonObject robobject;

	/**
	 * robbing actions are done
	 *
	 * @param data
	 *            the robJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: a player is robbed, the robber is moved
	 */

	@Override
	public Object execute(Object data) {
		robobject = (RobJsonObject) data;
		return ServerFacade.getSingleton().robplayer(robobject.getindex(), robobject.getvictimindex(), robobject.getlocation());

	}
}
