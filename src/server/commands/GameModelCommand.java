package server.commands;

import server.ICommand;
import server.ServerFacade;

public class GameModelCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: a game model is returned 
    */
	@Override
	public Object execute(Object data) {
		ServerFacade sf = new ServerFacade(); 
		Object result = sf.GameModel();  
		return result;
	}
}
