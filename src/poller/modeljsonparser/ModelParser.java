package poller.modeljsonparser;

import com.google.gson.Gson;

import client.data.GameInfo;
import client.join.GameListInfo;
import client.login.PlayerLoginInfo;
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
		Gson gson = new Gson();
		PlayerLoginInfo login = gson.fromJson(jsonstring, PlayerLoginInfo.class);
		return login;
	}
	public static GameInfo[] parse4(String jsonstring) {
		Gson gson = new Gson();
		GameInfo[] login = gson.fromJson(jsonstring, GameInfo[].class);
		return login;
	}
}

	
	


		
		
	 