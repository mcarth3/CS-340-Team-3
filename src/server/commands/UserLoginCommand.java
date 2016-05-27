package server.commands;
import server.ICommand;

public class UserLoginCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: checks to see if a person in registered and returns a success
    */
	@Override
	public Object execute(Object data) {
		return data; 
		// TODO Auto-generated method stub
		
	}
}
