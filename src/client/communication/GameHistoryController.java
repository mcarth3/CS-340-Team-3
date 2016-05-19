package client.communication;

import java.util.*;

import client.GameManager.GameManager;
import client.base.*;
import model.Game;
import model.Player;
import model.clientModel.MessageLine;
import model.clientModel.MessageList;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
//		List<LogEntry> entries = new ArrayList<LogEntry>();
//		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
//		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
//		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
//		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
//		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
//		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
//		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
//		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
//		
//		getView().setEntries(entries);
	
		//</temp>
	}
	
	public void update(){
		GameManager gm = GameManager.getSingleton();
		List<LogEntry> ent = new ArrayList<LogEntry>();
		Game game = gm.getModel();
		// game history
		MessageList ml = game.log;
		for (MessageLine m : ml.lines){
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
		ArrayList<Player> players = game.players; 
		for(Player p : players)
		{
			//System.out.println("|"+p.getName()+"|, |"+name+"|"); 
			if((String)p.getName() == (String)name)
			{
				//System.out.println("BUT ALSO here"); 
				cc = p.getColor();
			}
		}
		return cc;
	}
}

