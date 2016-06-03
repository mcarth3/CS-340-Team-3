package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import model.ObjectNotFoundException;
import model.clientModel.MessageLine;
import model.clientModel.MessageList;
import proxy.RealProxy;
import shared.definitions.CatanColor;

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
		return (IChatView) super.getView();
	}

	@Override
	public void sendMessage(String message) {

//		System.out.println("the current state is:");
//		System.out.println(State.getCurrentState()); 

//		System.out.println("THIS IS THE NEW MESSAGE SENT");
//		System.out.println(message);
		//System.out.println(gm.getthisplayer()); 

		rp.sendChat(manager.playerIdTemp, message);

	}

	@Override
	public void update() {
		List<LogEntry> ent = new ArrayList<LogEntry>();
		//chat
		MessageList cl = model.chat;
		for (MessageLine m : cl.lines) {
			//System.out.println(m.message);
			//System.out.println(m.source);
			LogEntry le = new LogEntry(getPlayerColor(m.source), m.message);
			ent.add(le);
		}
		getView().setEntries(ent);
	}

	public CatanColor getPlayerColor(String name) {
		CatanColor cc = CatanColor.WHITE;
		try {
			cc = CatanColor.toColor(model.findPlayer(name).color);
		} catch (ObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cc;
	}

}
