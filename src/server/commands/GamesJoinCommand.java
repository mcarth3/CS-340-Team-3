package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.GamesJoinJsonObject;
import server.jsonObjects.UserLoginJsonObject;

public class GamesJoinCommand implements ICommand {
	private GamesJoinJsonObject input; 
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: adds a user to the selected game, a model is returned
    */
	@Override
	public Object execute(Object data) {
		GamesJoinJsonObject gjjo = (GamesJoinJsonObject) data;
		input = gjjo; 
		ServerFacade sf = ServerFacade.getSingleton();
		return sf.GamesJoin(gjjo.getId(), gjjo.getColor()); 
	}
}
