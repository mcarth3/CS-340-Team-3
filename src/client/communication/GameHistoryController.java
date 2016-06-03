package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import model.ObjectNotFoundException;
import model.clientModel.MessageLine;
import model.clientModel.MessageList;
import shared.definitions.CatanColor;

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

		return (IGameHistoryView) super.getView();
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

	@Override
	public void update() {
		List<LogEntry> ent = new ArrayList<LogEntry>();
		// game history
		MessageList ml = model.log;
		for (MessageLine m : ml.lines) {
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
