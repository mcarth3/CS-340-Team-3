package poller.modeljsonparser;

import com.google.gson.Gson;

import client.data.GameInfo;
import client.login.PlayerLoginInfo;
import model.Game;
import server.input.UserLoginInput;

/**
 * @author Mike Towne
 */

public class ModelParser {
	public static Game parse2(String jsonstring) {
		System.out.println(jsonstring);
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

	public static UserLoginInput parseLogin(String jsonstring) {
		Gson gson = new Gson();
		UserLoginInput uli = gson.fromJson(jsonstring, UserLoginInput.class);
		return uli;
	}

	public static <T> Object parse(String jsondata, Class<T> givenclass) {
		Gson gson = new Gson();
		Object returnedobject = gson.fromJson(jsondata, givenclass);
		return returnedobject;
	}

	public static String toJson(Object givenclass) {
		Gson gson = new Gson();
		return gson.toJson(givenclass);
	}

}
