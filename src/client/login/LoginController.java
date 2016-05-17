package client.login;

import client.base.*;
import client.misc.*;
import states.State;
import states.StateEnum;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	//Singleton
	public static LoginController SINGLETON = null;
		
	//Domain Implementation THESE 4 ARE FROM THE EXAMPLE
	private String input;
	private int inputIndex;
	private boolean negative;
	private int result;
	
	private State state; 

	private IMessageView messageView;
	private IAction loginAction;
	
	//Constructors	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
		
		state = State.LOGIN;
		
		SINGLETON = new LoginController(view, messageView);	  
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		
		// TODO: log in user
		

		// If log in succeeded
		getLoginView().closeModal();
		loginAction.execute();
	}

	@Override
	public void register() {
		
		// TODO: register new user (which, if successful, also logs them in)
		
		// If register succeeded
		getLoginView().closeModal();
		loginAction.execute();
	}
	public void update(){
	}

}

