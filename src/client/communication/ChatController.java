package client.communication;

import client.GameManager.GameManager;
import client.base.*;
import proxy.RealProxy;
import states.State;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {
	
	public RealProxy rp = RealProxy.getSingleton(); 

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		
		System.out.println("the current state is:");
		System.out.println(State.getCurrentState()); 
		
		System.out.println("THIS IS THE NEW MESSAGE SENT");
		System.out.println(message);
		GameManager gm = GameManager.getSingleton();
		//System.out.println(gm.getthisplayer()); 
				
		// GET THE PLAYER ID FROM THE THE LOGIN INFO
		
		rp.sendChat(0, message); 
		
	}
	public void update(){
	}
}

