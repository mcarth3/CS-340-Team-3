package poller.modeljsonparser;

import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ModelParser {
	private static JsonParser parser = new JsonParser();
	/**
	 * constructs a catan model from the given JSON (creates json elements from json using jsonparser and calls parse from obj)
	 * @param jsonstring: the JSON to be parsed
	 * @param Tclass: generic class
	 * @pre given json is formatted correctly
	 * @post returns a model filled with elements from given json
	 */
	public static <T> T parse(String jsonstring, Class<T> Tclass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		JsonElement element = parser.parse(jsonstring);
		return parseFromObj(element, Tclass);
	}

	/**
	 * cycles through json elements and adds the values to the model
	 * @param jsonString: the JSON to be parsed
	 * @param Tclass: generic class
	 * @pre given json is formatted correctly
	 * @post returns a model filled with elements from given json
	 */
	private static <T> T parseFromObj(JsonElement Element, Class<T> Tclass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (Element.isJsonNull()) {
			return null;
		}
		if (Element.isJsonArray()) {
			JsonArray JArray = Element.getAsJsonArray();
			Object[] ObjectArray = (Object[]) Array.newInstance(Tclass.getComponentType(), JArray.size());
			for (int i = 0; i < JArray.size(); i++) {
				ObjectArray[i] = parseFromObj(JArray.get(i), Tclass.getComponentType());
			}
			return (T) ObjectArray;
		}
		
		if (Tclass.isArray()) {
			return (T) (Object[]) Array.newInstance(Tclass.getComponentType(), 0);
		}
		
		if (Tclass.getDeclaredConstructors().length == 0) {
			return (T) new Boolean(Element.getAsBoolean());
		}
		
		if (Tclass.isEnum()) {
			for (T enumValue : Tclass.getEnumConstants()) {
				if (enumValue.toString().equalsIgnoreCase((Element.getAsString()))) {
					return enumValue;
				}
			}
		}
		
		Constructor<T> constructor = null;
		Constructor<?>[] ConstructorArray = Tclass.getDeclaredConstructors();
		for (Constructor<?> c : ConstructorArray) {
			if (c.getParameterTypes().length > 0) {
				constructor = (Constructor<T>) c;
				break;
			}
		}
		
		if (Element.isJsonObject()) {
			JsonObject JObject = Element.getAsJsonObject();
			if (JObject.entrySet().size() == 0) return null;
			Field[] FieldArray = Tclass.getDeclaredFields();
			ArrayList<Object> params = new ArrayList<Object>();
			for (Field field : FieldArray) {
				if (field.getModifiers() != Modifier.PRIVATE) continue;
				
				JsonElement value = JObject.get(field.getName());
				if (value == null) {
					params.add(null);
				} else {
					params.add(parseFromObj(value, field.getType()));
				}
			}
			
			if (constructor.getParameterTypes().length != params.toArray().length) {
				return null;
			}
			
			return constructor.newInstance(params.toArray());
		} else {
			JsonPrimitive jsonPrimitive = Element.getAsJsonPrimitive();
			if (jsonPrimitive.isNumber()) {
				int value = jsonPrimitive.getAsInt();
				return constructor.newInstance(value);
			} else if (jsonPrimitive.isBoolean()) {
				boolean value = jsonPrimitive.getAsBoolean();
				return constructor.newInstance(value);
			} else {
				String value = jsonPrimitive.getAsString();
				return constructor.newInstance(value);
			}
		}

	}


	
	

}
