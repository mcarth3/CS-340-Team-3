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

}

	
	


		
		
	 