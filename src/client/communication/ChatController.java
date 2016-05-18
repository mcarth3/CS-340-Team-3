package client.communication;

import client.GameManager.GameManager;
import client.base.*;
import proxy.RealProxy;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {
	
	public RealProxy rp = new RealProxy(); 

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		System.out.println("THIS IS THE NEW MESSAGE SENT");
		System.out.println(message);
		GameManager gm = GameManager.getSingleton();
		System.out.println(gm.getthisplayer()); 
				
		//rp.sendChat(0, message); 
		
	}
	public void update(){
	}
}

