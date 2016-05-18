package poller.modeljsonparser;

import com.google.gson.Gson;

import model.Game;

/**
 * @author Mike Towne
 */

public class ModelParser {	
	public static Game parse2(String jsonstring) {
		Gson gson = new Gson();
		Game game = gson.fromJson(jsonstring, Game.class);
		return game;
	}
	public static PlayerLoginInfo parse3(String jsonstring) {
		PlayerLoginInfo gson = new Gson();
		PlayerLoginInfo login = gson.fromJson(jsonstring, Game.class);
		return login;
	}

}

	
	


		
		
	 