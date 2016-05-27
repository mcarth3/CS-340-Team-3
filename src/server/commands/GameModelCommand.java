package server.commands;

import server.ICommand;

public class GameModelCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: a game model is returned 
    */
	@Override
	public Object execute(Object data) {
		return data; 
		// TODO Auto-generated method stub
		
	}
}
