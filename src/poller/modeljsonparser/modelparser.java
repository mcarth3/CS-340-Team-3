package poller.modeljsonparser;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ModelParser {
	public static String NULL = "null";
	public static String QUOTE = "\"";
	public static String COMMA = ",";
	public static String COLON = ":";
	public static String OPEN_BRACKET = "[";
	public static String CLOSED_BRACKET = "]";
	public static String OPEN_BRACE = "{";
	public static String CLOSED_BRACE = "}";
	public static String EMPTY_BRACE = OPEN_BRACE + CLOSED_BRACE;
	
	private static JsonParser parser = new JsonParser();
	
	
	/**
	 * constructs a catan model from the given JSON (creates json elements from json using jsonparser and calls parse from obj)
	 * @param jsonString: the JSON to be parsed
	 * @param Tclass: generic class
	 * @pre given json is formatted correctly
	 * @post returns a model filled with elements from given json
	 */
	public static <T> T parse(String jsonString, Class<T> Tclass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return null;
	}

	/**
	 * cycles through json elements and adds the values to the model
	 * @param jsonString: the JSON to be parsed
	 * @param Tclass: generic class
	 * @pre given json is formatted correctly
	 * @post returns a model filled with elements from given json
	 */
	private static <T> T parseFromObj(JsonElement jsonElement, Class<T> Tclass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return null;

	}
	/**
	 * retrieves the field of a given name from the given object
	 * @pre field and object are not null
	 * @post returns the field associated with the given name from the object
	 */
	private static Field getFieldByName(String fieldName, Object context) {
		return null;
	}

	/**
	 * retrieves the fieldvalue of the given field
	 * @pre field and object are not null
	 * @post returns the value of the given field
	 */
	public static Object getFieldValue(Field field, Object context) {
		return null;

	}
	
	

}
