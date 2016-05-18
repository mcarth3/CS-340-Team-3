package client.login;

import client.base.*;
import client.misc.*;
import proxy.RealProxy;
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
	public static LoginController SINGLETON = new LoginController(null, null); 
		
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
		 
		
		//SINGLETON = new LoginController(view, messageView);	  
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
		String username = this.getLoginView().getLoginUsername();
		String password = this.getLoginView().getLoginPassword();
		
		RealProxy rp = new RealProxy();
		
		String result = null;
		if(loginCanDo(username, password)){
			result = rp.userLogin(username, password);
		}
		
		if(result != null){
			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		}else{
			System.out.println("Login didn't work"); 
		}
	}
	public boolean loginCanDo(String username, String password){
		boolean result = true;
		// input not null
		// input not ""
		// user at least length 3
		if(username == null || password == null){
			result = false;
		}
		if(username == "" || password == ""){
			result = false;
		}
		if(username.length() < 3 || password.length() < 3)
		{
			result = false;
		}
		return result; 
	}

	@Override
	public void register() {

		// TODO: register new user (which, if successful, also logs them in)		
		String username = this.getLoginView().getRegisterUsername();
		String password = this.getLoginView().getRegisterPassword();
		String passwordRepeat = this.getLoginView().getRegisterPassword();
		
		RealProxy rp = new RealProxy();
		
		String result = null;
		if(registerCanDo(username, password, passwordRepeat)){
			result = rp.userRegister(username, password);
		}
		if(result != null)
		{
			rp.userLogin(username, password);
			// If register succeeded
			getLoginView().closeModal();
			loginAction.execute();
			
		}else{
			System.out.println("Register didn't work"); 
		}
		
	}
	public boolean registerCanDo(String username, String password, String passwordRepeat){
		boolean result = true;
		// input not null
		// input not ""
		// input at least length 5	
		// user at least length 3
		// pass and passRepeat the same
		if(username == null || password == null){
			result = false;
		}
		if(username == "" || password == ""){
			result = false;
		}
		if(username.length() < 3 || password.length() < 5)
		{
			result = false;
		}
		if(password != passwordRepeat){
			result = false;
		}
		
		return result; 
	}
	public void update(){
	}

}

