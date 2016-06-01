package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.GamesCreateJsonObject;

public class GamesCreateCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: creates a new game and adds it to the model
    */
	@Override
	public Object execute(Object data) {
		GamesCreateJsonObject gcjo = (GamesCreateJsonObject) data; 
		ServerFacade sf = new ServerFacade(); 
		Object result = sf.GamesCreate(gcjo.getName(), gcjo.isRandomNumbers(), gcjo.isRandomPorts(), gcjo.isRandomTiles()); 
		return result;
	}
}
