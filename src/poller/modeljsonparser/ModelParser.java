package poller.modeljsonparser;
/**
 * @author Mike Towne
 */
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
	private static <T> T parseFromObj(JsonElement element, Class<T> Tclass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (element.isJsonPrimitive()) { //debugging for classes using ints isn't going too well, so I cast to Integer
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isNumber()) {
				Tclass  = (Class<T>) Integer.class;
			}
		}
		
		
		if (!element.isJsonNull()) {
			if (element.isJsonArray()) {
				JsonArray JArray = element.getAsJsonArray();
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
				return (T) new Boolean(element.getAsBoolean());
			}
			
			if (Tclass.isEnum()) {
				for (T enumVAL : Tclass.getEnumConstants()) {
					if (enumVAL.toString().equalsIgnoreCase((element.getAsString()))) {
						return enumVAL;
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
			
			if (element.isJsonObject()) {
				return parseJobject(element,Tclass,constructor);
				
			} else {
				JsonPrimitive primitive = element.getAsJsonPrimitive();
				//System.out.println(jsonPrimitive.getAsInt());
				//try {
				//	constructor = classOfT.getConstructor();
				//} catch (NoSuchMethodException e) {
				//	e.printStackTrace();
				//} catch (SecurityException e) {
				//	e.printStackTrace();
				//}
				if (primitive.isNumber()) {
					int value = primitive.getAsInt();
					return constructor.newInstance(value);
				} else if (primitive.isBoolean()) {
					boolean value = primitive.getAsBoolean();
					return constructor.newInstance(value);
				} else {
					String value = primitive.getAsString();
					return constructor.newInstance(value);
				}
			}
		}
		return null;

	}


	private static <T> T parseJobject(JsonElement element, Class<T> Tclass, Constructor<T> constructor) {
		JsonObject jsonobject = element.getAsJsonObject();
		if (jsonobject.entrySet().size() == 0){
			return null;
		}
		Field[] fieldarray = Tclass.getDeclaredFields();
		ArrayList<Object> params = new ArrayList<Object>();
		for (Field field : fieldarray) {
			if (field.getModifiers() != Modifier.PRIVATE){
				continue;
			}
			
			JsonElement value = jsonobject.get(field.getName());
			if (value == null) {
				params.add(null);
			} else {
//				System.out.println(value);
				try {
					params.add(parseFromObj(value, field.getType()));
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		//if (constructor==null){
		//	System.out.println("null");
		//}
		
		if (constructor.getParameterTypes().length != params.toArray().length) {
			return null;
		}
		//if (params.size()>0){	System.out.println(params.toArray());}
		try {
			return constructor.newInstance(params.toArray());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}

