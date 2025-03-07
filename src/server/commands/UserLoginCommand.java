package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.UserLoginJsonObject;

public class UserLoginCommand implements ICommand {
	private UserLoginJsonObject input;
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: checks to see if a person in registered and returns a success
    */
	@Override
	public Object execute(Object data) { 
		UserLoginJsonObject uljo = (UserLoginJsonObject) data;
		input = uljo;
		ServerFacade sf = ServerFacade.getSingleton(); 
		return sf.UserLogin(uljo.getUsername(), uljo.getPassword()); 
	}
}
