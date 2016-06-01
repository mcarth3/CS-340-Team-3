package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.UserRegisterJsonObject;

public class UserRegisterCommand implements ICommand {
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: adds a new player to the list of players
    */
	@Override
	public Object execute(Object data) {
		UserRegisterJsonObject urjo = (UserRegisterJsonObject) data;
		ServerFacade sf = new ServerFacade(); 
		Object result = sf.UserRegister(urjo.getUsername(), urjo.getPassword()); 
		return result;
	}
}
