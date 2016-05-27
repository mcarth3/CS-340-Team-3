package server.commands;

import model.Game;
import server.ICommand;

/**
 * Created by Jesse on 5/26/2016.
 */
public class BuildCityCommand implements ICommand {
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains 2 VP and a city, and loses resources.
	 */
	@Override
	public Game execute(Object data) {
		// TODO Auto-generated method stub
		return null;
	}
}
