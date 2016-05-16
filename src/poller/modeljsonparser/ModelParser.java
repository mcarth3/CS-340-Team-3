package poller.modeljsonparser;
/**
 * @author Mike Towne
 */
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import model.Hex;
import model.Port;
import model.Road;
import model.Robber;
import model.VertexObject;
import model.bank.ResourceList;
import shared.locations.HexLocation;

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
		System.out.println("current: "+Tclass.toString());	
	//	System.out.println("current element "+element.toString());


		if (element.isJsonPrimitive()) { //debugging for classes using ints isn't going too well, so I cast to Integer
			//System.out.println("if element.isJsonPrimitive()");
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isNumber()) {
			//	System.out.println("if primitive.isNumber()");
				Tclass  = (Class<T>) Integer.class;
			}
		}
		
		
		if (element.isJsonNull()) {//if elemnt is not null
			System.out.println("return null 2");
			return null;
		}
		
		if (element.isJsonArray()) {//if element is json array
//			System.out.println("if element.isJsonArray()");
			JsonArray JArray = element.getAsJsonArray();
	//		System.out.println("");
			Object[] ObjectArray = new Object[0];
			if(JArray.size() !=0){
				ObjectArray = (Object[]) Array.newInstance(Tclass.getComponentType(), JArray.size());
				for (int i = 0; i < JArray.size(); i++) {
		//			System.out.println("for (int i = 0; i < JArray.size(); i++) {");
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
		//	System.out.println("if Tclass.isEnum()");
			for (T enumValue : Tclass.getEnumConstants()) {
	//			System.out.println("for (T enumValue : Tclass.getEnumConstants()) {");
				if (enumValue.toString().equalsIgnoreCase((element.getAsString()))) {
					System.out.println("return enum");
					return enumValue;
				}
			}
		}
		
		//Constructor<T> constructor = null;//get all constructor objects
		//Constructor<?>[] ConstructorArray = Tclass.getDeclaredConstructors();
		//for (Constructor<?> c : ConstructorArray) {
	//		System.out.println("for (Constructor<?> c : ConstructorArray) {");
		//	if (c.getParameterTypes().length > 0) {
//				System.out.println("if c.getParameterTypes().length > 0");
//				System.out.println("constructor elements " + c.toString());
		//		System.out.println("constructor elements " + c.toString());
		//		constructor = (Constructor<T>) c;
		//		break;
		//	}
		//}
		

		
		if (element.isJsonObject()) {
			
			
			
//			System.out.println("object is Json object");
			JsonObject jsonObject = element.getAsJsonObject();
			if (jsonObject.entrySet().size() == 0){
//				System.out.println("jsonObject.entrySet().size() == 0");
				System.out.println("returning null 1");
				return null;
			}
			
			Field[] fields = Tclass.getDeclaredFields();
			ArrayList<Object> params = new ArrayList<Object>();
			for (Field field : fields) {
		//		System.out.println("field: " + field.toString());				
				JsonElement value = jsonObject.get(field.getName());
				if (value == null) {
//					System.out.println("Field = null");
		//			System.out.println("adding null param");
	//				params.add(null);
				} else {
					//System.out.println("parsing param " +field.getName());
					if (!field.getName().equals("$assertionsDisabled")){
						params.add(parseFromObj(value, field.getType()));
					}
				}
			}
			
			Constructor<T> constructor = null;
			Constructor<?>[] ConstructorArray = Tclass.getDeclaredConstructors();
			for (Constructor<?> c : ConstructorArray) {
				System.out.println( "param length "+params.toArray().length);
				System.out.println("contructor params "+c.getParameterTypes().length);
				//System.out.println("for "+ Tclass.getName());
				//System.out.println(params);
				if (c.getParameterTypes().length == params.toArray().length) {//compares the constructor with the same number of parameters as those required, still not perfect
					System.out.println("constructor elements " + c.toString());
					constructor = (Constructor<T>) c;
					break;
				}
			}
			
			if (constructor == null){
				System.out.println("constructor == null");
				//System.out.println("YOU NEED A CONSTRUCTOR THAT SETS ALL VARIABLES WITH MATCHING ARGS");
			}
			

			//if (constructor.getName().equals("model.Map")){
			//	TreeMap<HexLocation,Hex> hexes = new TreeMap<HexLocation,Hex>();
			//	ArrayList<Port> ports = new ArrayList<Port>();
			//	ArrayList<Road> roads = new ArrayList<Road>();
			//	Robber robber = new Robber();
			//	ArrayList<VertexObject> buildings = new ArrayList<VertexObject>();
			//	ArrayList<ResourceList> resources = new ArrayList<ResourceList>();
			//	int radius = -1;
			//	System.out.println("returning "+constructor.getName() +" with appropriate constructor");
			//	System.out.println(params);
			//	return constructor.newInstance(hexes, ports , roads, buildings, resources, radius, robber);
			//}
			System.out.println("returning "+constructor.getName() +" with appropriate constructor");
			System.out.println(params);
			return constructor.newInstance( params.toArray());
			

		}else {
			//System.out.println("element isnt a json object");
			
			
			
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
	
}

	
	


		
		
	 