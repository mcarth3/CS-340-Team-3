package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.UserRegisterJsonObject;

public class UserRegisterCommand implements ICommand {
	private UserRegisterJsonObject input;
	/**
    *
    * @pre: ServerModel is initialized and HTTP request is decoded
    * @post: adds a new player to the list of players
    */
	@Override
	public Object execute(Object data) {
		UserRegisterJsonObject urjo = (UserRegisterJsonObject) data;
		input = urjo;
		ServerFacade sf = ServerFacade.getSingleton();
		return sf.UserRegister(urjo.getUsername(), urjo.getPassword()); 
	}
}
