package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.BuildRoadJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class BuildRoadCommand implements ICommand {
	private BuildRoadJsonObject buildRoadJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains a road and loses particular resources
	 */
	@Override
	public Object execute(Object data) {
		buildRoadJsonObject = (BuildRoadJsonObject) data;
		return ServerFacade.getSingleton().buildRoad(buildRoadJsonObject.getType(), buildRoadJsonObject.getPlayerIndex(),
				buildRoadJsonObject.getRoadLocation(), buildRoadJsonObject.isFree());

	}
}
