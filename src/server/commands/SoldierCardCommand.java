package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.SoldierJsonObject;

public class SoldierCardCommand implements ICommand {
	private SoldierJsonObject soldiercardobject;

	/**
	 * soldier card is played for player
	 * 
	 * @param data
	 *            the soldierJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: soldier card is removed and its effects are added to the model
	 */
	@Override
	public Object execute(Object data) {
		soldiercardobject = (SoldierJsonObject) data;
		return ServerFacade.getSingleton().playsoldercard(soldiercardobject.getindex(), soldiercardobject.getvictimindex(), soldiercardobject.getlocation());

	}
}
