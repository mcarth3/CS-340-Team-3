package server.commands;

import server.ICommand;
import server.ServerFacade;

public class GameModelCommand implements ICommand {
	private Integer input; 
	// maybe get version number 
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: a game model is returned 
    */
	@Override
	public Object execute(Object data) {
		input = (Integer) data;
		ServerFacade sf = ServerFacade.getSingleton(); 
		return sf.GameModel(input);  
	}
}
