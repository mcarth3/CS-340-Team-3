package server.commands;
import server.ICommand;
import server.ServerFacade;

public class GamesListCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: grabs a list of games and returns it
    */
	@Override
	public Object execute(Object data) {
		ServerFacade sf = new ServerFacade(); 
		Object result = sf.GamesList();  
		return result;
	}
}
