package server;

import server.input.UserLoginInput;

/**
 * Created by Jesse on 5/26/2016.
 * This Facade will be called upon by the commands and will be the only 
 * class to modify the model on the Server's side.
 */
public class ServerFacade {
	
	
	public Object UserLogin(Object input){
		//TODO: This is where the magic happens
		UserLoginInput uli = (UserLoginInput) input;
		System.out.println(uli.getUsername());
		System.out.println(uli.getPassword());
		
		//TODO: Maybe make UserLoginOutput to return?
		
		return input; 
	}
	
}
