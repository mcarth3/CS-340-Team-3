package server.commands;
import server.ICommand;
import server.ServerFacade;

public class UserLoginCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: checks to see if a person in registered and returns a success
    */
	@Override
	public Object execute(Object data) { 
		ServerFacade sf = new ServerFacade(); 
		Object result = sf.UserLogin(data); 
		return result; 
	}
}
