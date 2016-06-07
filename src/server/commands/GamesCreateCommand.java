package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.GamesCreateJsonObject;

public class GamesCreateCommand implements ICommand {
	private GamesCreateJsonObject input;
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: creates a new game and adds it to the model
    */
	@Override
	public Object execute(Object data) {
		GamesCreateJsonObject gcjo = (GamesCreateJsonObject) data;
		input = gcjo;
		ServerFacade sf = ServerFacade.getSingleton(); 
		return sf.GamesCreate(gcjo.getName(), gcjo.isRandomNumbers(), gcjo.isRandomPorts(), gcjo.isRandomTiles()); 
	}
}
