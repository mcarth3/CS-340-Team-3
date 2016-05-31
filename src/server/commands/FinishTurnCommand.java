package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.FinishJsonObject;

public class FinishTurnCommand implements ICommand {
	private FinishJsonObject finishobject;

	/**
	 * turn is changed to the next player, state is set to rolling
	 * 
	 * @param data the finishJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: turn is changed to the next player, state is set to rolling
	 */
	@Override
	public Object execute(Object data) {
		finishobject = (FinishJsonObject) data;
		return ServerFacade.getSingleton().finishturn(finishobject.getindex());
	}
}
