package poller.modeljsonparser;

import com.google.gson.Gson;

/**
 * @author Mike Towne
 */

public class ModelParser {
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
