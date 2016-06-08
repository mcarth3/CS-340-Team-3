package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.YOPJsonObject;

public class YearOfPlentyCommand implements ICommand {
	private YOPJsonObject yopobject;

	/**
	 * year of plenty card is played for player
	 * 
	 * @param data
	 *            the YOPJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: year of plenty card is removed and its effects are added to the
	 *        model
	 */
	@Override
	public Object execute(Object data) {
		yopobject = (YOPJsonObject) data;

		return ServerFacade.getSingleton().playYOPcard(yopobject.getindex(), yopobject.getresource1(), yopobject.getresource2());

	}
}
