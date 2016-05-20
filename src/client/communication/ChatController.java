package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.GameManager.GameManager;
import client.base.*;
import model.Game;
import model.ObjectNotFoundException;
import model.Player;
import model.clientModel.MessageLine;
import model.clientModel.MessageList;
import proxy.RealProxy;
import shared.definitions.CatanColor;
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
		
//		System.out.println("the current state is:");
//		System.out.println(State.getCurrentState()); 
		
//		System.out.println("THIS IS THE NEW MESSAGE SENT");
//		System.out.println(message);
		GameManager gm = GameManager.getSingleton();
		//System.out.println(gm.getthisplayer()); 
		
		rp.sendChat(gm.playerIdTemp, message); 
		
	}
	public void update(){
		GameManager gm = GameManager.getSingleton();
		List<LogEntry> ent = new ArrayList<LogEntry>();		
		Game game = gm.getModel();
		//chat
		MessageList cl = game.chat;
		for (MessageLine m : cl.lines){
			//System.out.println(m.message);
			//System.out.println(m.source);
			LogEntry le = new LogEntry(getPlayerColor(m.source), m.message);
			ent.add(le);
		}
		getView().setEntries(ent);
	}
	public CatanColor getPlayerColor(String name){
		CatanColor cc = CatanColor.WHITE;
		GameManager gm = GameManager.getSingleton();
		Game game = gm.getModel();
		try {
			cc = CatanColor.toColor(game.findPlayer(name).color);
		} catch (ObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cc;
	}
	
	
}

