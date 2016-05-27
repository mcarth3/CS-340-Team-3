package server.commands;
import server.ICommand;

public class GamesCreateCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: creates a new game and adds it to the model
    */
	@Override
	public Object execute(Object data) {
		return data; 
		// TODO Auto-generated method stub
		
	}
}
