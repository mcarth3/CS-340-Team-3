package server.commands;
import server.ICommand;
import server.ServerFacade;

public class GamesListCommand implements ICommand {
	// no data needed
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: grabs a list of games and returns it
    */
	@Override
	public Object execute(Object data) {
		ServerFacade sf = ServerFacade.getSingleton(); 
		return sf.GamesList();  
	}
}
