package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.RoadBuildingJsonObject;

public class RoadBuildingCardCommand implements ICommand {
	private RoadBuildingJsonObject roadbuildingobject;

	/**
	 * road building card is played for player
	 * 
	 * @param data
	 *            the roadBuildingJsonObject containing this method's needed
	 *            info
	 * @return
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: road building card is removed and its effects are added to the
	 *        model
	 */
	@Override
	public Object execute(Object data) {
		System.out.println("roadbuildingcommand " + ((RoadBuildingJsonObject) data).getspot1());
		roadbuildingobject = (RoadBuildingJsonObject) data;
		return ServerFacade.getSingleton().playroadbuildingcard(roadbuildingobject.getindex(), roadbuildingobject.getspot1(), roadbuildingobject.getspot2());

	}
}
