package poller.modeljsonparser;
/**
 * @author Mike Towne
 */
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import model.Game;
import model.Hex;
import model.Port;
import model.Road;
import model.Robber;
import model.VertexObject;
import model.bank.ResourceList;
import shared.locations.HexLocation;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.TreeMap;

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
		System.out.println("current: "+Tclass.toString());	
//		System.out.println("element: "+element.get);	

		if (element.isJsonPrimitive()) { //debugging for classes using ints isn't going too well, so I cast to Integer
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isNumber()) {
				Tclass  = (Class<T>) Integer.class;
			}
		}
		
		
		if (element.isJsonNull()) {//if elemnt is not null
			System.out.println("return null 2");
			return null;
		}
		
		if (element.isJsonArray()) {//if element is json array
			JsonArray JArray = element.getAsJsonArray();
			Object[] ObjectArray = new Object[0];
			if(JArray.size() !=0){
				ObjectArray = (Object[]) Array.newInstance(Tclass.getComponentType(), JArray.size());
				for (int i = 0; i < JArray.size(); i++) {
					ObjectArray[i] = parseFromObj(JArray.get(i), Tclass.getComponentType());//parse all array objects
				}
			}
			System.out.println("return array");
			if(ObjectArray.toString().substring(0, 20).equals("[Ljava.lang.Object;@")){
				return null;
			}
			return (T) ObjectArray;
		}
		
		
		if (Tclass.isArray()) {//if class is array
			System.out.println("return array2");
			return (T) (Object[]) Array.newInstance(Tclass.getComponentType(), 0);
		}
		
		if (Tclass.getDeclaredConstructors().length == 0) {//if class has no declared ConstructorArray, assume class is boolean
			System.out.println("return bool?");
			return (T) new Boolean(element.getAsBoolean());
		}

		if (Tclass.isEnum()) {//if class is enum
			for (T enumValue : Tclass.getEnumConstants()) {
				if (enumValue.toString().equalsIgnoreCase((element.getAsString()))) {
					System.out.println("return enum");
					return enumValue;
				}
			}
		}
		if (element.isJsonObject()) {
			JsonObject jsonObject = element.getAsJsonObject();
			if (jsonObject.entrySet().size() == 0){
				System.out.println("returning null 1");
				return null;
			}
			
			Field[] fields = Tclass.getDeclaredFields();
			ArrayList<Object> params = new ArrayList<Object>();
			for (Field field : fields) {	
				JsonElement value = jsonObject.get(field.getName());
				
				System.out.println("field.getName()" + jsonObject.get(field.getName()).toString());
				if (value != null) {
					if (!field.getName().equals("$assertionsDisabled")){
						params.add(parseFromObj(value, field.getType()));
					//	jsonObject.get(field.getName()).
					}
				}
			}
//			System.out.println("name " +Tclass.getName());
			Constructor<T> constructor = null;
			Constructor<?>[] ConstructorArray = Tclass.getDeclaredConstructors();
			for (Constructor<?> c : ConstructorArray) {
				//System.out.println( "param length "+params.toArray().length);
				//System.out.println("contructor params "+c.getParameterTypes().length);
				if (c.getParameterTypes().length == params.toArray().length) {//compares the constructor with the same number of parameters as those required, still not perfect
					System.out.println("constructor elements " + c.toString());//good enough for this project, but should compare the constructor arguments to the parameters
					constructor = (Constructor<T>) c;
					break;
				}
			}
			
			if (constructor == null){
				System.out.println("constructor == null");//YOU NEED A CONSTRUCTOR THAT SETS ALL VARIABLES WITH MATCHING ARGS
			}
			
			System.out.println("returning "+constructor.getName() +" with appropriate constructor");
			System.out.println(params);
	
			return constructor.newInstance(params.toArray());
			

		}else {			
			Constructor<T> constructor = null; 	 	
			Constructor<?>[] ConstructorArray = Tclass.getDeclaredConstructors();
			for (Constructor<?> c : ConstructorArray) {
				if (c.getParameterTypes().length == 1) {
					if (c.toString().equals("public java.lang.Integer(int)")) {
						constructor = (Constructor<T>) c;
						break;
					}else if (c.toString().equals("public java.lang.String(java.lang.String)")) {
						constructor = (Constructor<T>) c;
						break;
					}else if (c.toString().equals("public java.lang.Boolean(java.lang.Boolean)")) {
						constructor = (Constructor<T>) c;
						break;
					}else if (c.toString().equals("public java.lang.Boolean(Boolean)")) {
						constructor = (Constructor<T>) c;
						break;
					}
				}
			}
			
			
			JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
			if (jsonPrimitive.isNumber()) {
				System.out.println("returning int");
				int value = jsonPrimitive.getAsInt();
				return constructor.newInstance(value);
			} else if (jsonPrimitive.isBoolean()) {
				System.out.println("returning bool");
				boolean value = jsonPrimitive.getAsBoolean();
				return constructor.newInstance(value);
			} else {
				String value = jsonPrimitive.getAsString();
				System.out.println("returning string" + value);
				return constructor.newInstance(value);
			}
		}
	}

	
	
	
	
	
	public static Game parse2(String jsonstring) {
		Gson gson = new Gson();
		Game game = gson.fromJson(jsonstring, Game.class);
//		for (Road road : game.getMap().getRoads()) {
//			road.getLocation().convertFromPrimitives();
//		}
//		for (VertexObject settlement : game.getMap().getSettlements()) {
//			settlement.getLocation().convertFromPrimitives();
//		}
//		for (VertexObject city : game.getMap().getCities()) {
//			city.getLocation().convertFromPrimitives();
//		}
//		for (Port port : game.getMap().getPorts()) {
//			port.convertFromPrimitives();
//		}
		return game;
	}

}

	
	


		
		
	 