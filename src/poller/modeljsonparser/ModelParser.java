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
		//System.out.println("parsing");
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
	
		System.out.println("element "+element.toString());
		System.out.println(Tclass.toString());

		if (element.isJsonPrimitive()) { //debugging for classes using ints isn't going too well, so I cast to Integer
			System.out.println("if element.isJsonPrimitive()");
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isNumber()) {
				System.out.println("if primitive.isNumber()");
				Tclass  = (Class<T>) Integer.class;
			}
		}
		
		
		if (element.isJsonNull()) {//if elemnt is not null
			System.out.println("if element.isJsonNull()");
			return null;
		}
		
		if (element.isJsonArray()) {//if element is json array
			System.out.println("if element.isJsonArray()");
			JsonArray JArray = element.getAsJsonArray();
			System.out.println("");
			Object[] ObjectArray = new Object[0];
			if(JArray.size() !=0){
				ObjectArray = (Object[]) Array.newInstance(Tclass.getComponentType(), JArray.size());
				for (int i = 0; i < JArray.size(); i++) {
					System.out.println("for (int i = 0; i < JArray.size(); i++) {");
					ObjectArray[i] = parseFromObj(JArray.get(i), Tclass.getComponentType());//parse all array objects
				}
			}
			return (T) ObjectArray;
		}
		
		
		if (Tclass.isArray()) {//if class is array
			System.out.println("if (Tclass.isArray()) {");
			return (T) (Object[]) Array.newInstance(Tclass.getComponentType(), 0);
		}
		
		if (Tclass.getDeclaredConstructors().length == 0) {//if class has no declared ConstructorArray, assume class is boolean
			System.out.println("if Tclass.getDeclaredConstructors().length == 0");
			return (T) new Boolean(element.getAsBoolean());
		}

		if (Tclass.isEnum()) {//if class is enum
			System.out.println("if Tclass.isEnum()");
			for (T enumValue : Tclass.getEnumConstants()) {
				System.out.println("for (T enumValue : Tclass.getEnumConstants()) {");
				if (enumValue.toString().equalsIgnoreCase((element.getAsString()))) {
					System.out.println("if enumValue.toString().equalsIgnoreCase((element.getAsString()))");
					return enumValue;
				}
			}
		}
		
		Constructor<T> constructor = null;//get all constructor objects
		Constructor<?>[] ConstructorArray = Tclass.getDeclaredConstructors();
		for (Constructor<?> c : ConstructorArray) {
			System.out.println("for (Constructor<?> c : ConstructorArray) {");
			if (c.getParameterTypes().length > 0) {
				System.out.println("if c.getParameterTypes().length > 0");
				System.out.println("constructor elements " + c.toString());
				constructor = (Constructor<T>) c;
				break;
			}
		}
		

		
		if (element.isJsonObject()) {
			System.out.println("if element.isJsonObject()");
			JsonObject jsonObject = element.getAsJsonObject();
			if (jsonObject.entrySet().size() == 0){
				System.out.println("jsonObject.entrySet().size() == 0");
				return null;
			}
			
			Field[] fields = Tclass.getDeclaredFields();
			ArrayList<Object> params = new ArrayList<Object>();
			for (Field field : fields) {
				System.out.println("field: " + field.toString());				
				JsonElement value = jsonObject.get(field.getName());
				if (value == null) {
					System.out.println("Field = null");
					params.add(null);
				} else {
					params.add(parseFromObj(value, field.getType()));
				}
			}
			
			if (constructor == null){
				System.out.println("if (constructor == null){");
				System.out.println("YOU NEED A CONSTRUCTOR THAT SETS ALL VARIABLES WITH MATCHING ARGS");
			}
			
			if (constructor.getParameterTypes().length != params.toArray().length) {
				System.out.println("if (constructor.getParameterTypes().length != params.toArray().length) {");
				return null;
			}
			System.out.println(constructor.getName());
			System.out.println(element);
			//JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
			//boolean value = jsonPrimitive.getAsBoolean();
			//return constructor.newInstance(value);
			
			
			if(false){
				
			}
			
			System.out.println(params);
			return constructor.newInstance( params.toArray());
		}else {
			System.out.println("not (element.isJsonObject()) {");
			
			JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
			if (jsonPrimitive.isNumber()) {
				System.out.println("if (jsonPrimitive.isNumber()) {");
				int value = jsonPrimitive.getAsInt();
				return constructor.newInstance(value);
			} else if (jsonPrimitive.isBoolean()) {
				System.out.println("} else if (jsonPrimitive.isBoolean()) {");
				boolean value = jsonPrimitive.getAsBoolean();
				return constructor.newInstance(value);
			} else {
				System.out.println("else");
				String value = jsonPrimitive.getAsString();
				System.out.println(constructor.getName());
				System.out.println(value);
				//System.out.println(String.class.getDeclaredConstructors());
				return (T) value;			}
		}
	}
	
}

	
	


		
		
	 