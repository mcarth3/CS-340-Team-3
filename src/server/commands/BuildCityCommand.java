package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.BuildCityJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class BuildCityCommand implements ICommand {
	private BuildCityJsonObject buildCityJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains 2 VP and a city, and loses resources.
	 */
	@Override
	public Object execute(Object data) {
		buildCityJsonObject = (BuildCityJsonObject) data;
		return ServerFacade.getSingleton().buildCity(buildCityJsonObject.getType(), buildCityJsonObject.getPlayerIndex(),
				buildCityJsonObject.getVertexLocation());

	}
}
