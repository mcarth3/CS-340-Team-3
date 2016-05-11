package poller.modeljsonparser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class ClassToJSON {
	public static String OPEN_BRACKET = "[";
	public static String CLOSED_BRACKET = "]";
	public static String OPEN_BRACE = "{";
	public static String CLOSED_BRACE = "}";
	public static String COMMA = ",";
	public static String QUOTE = "\"";
	public static String COLON = ":";
	public static String NULL = "null";
	public static String EMPTY_BRACE = OPEN_BRACE + CLOSED_BRACE;
	
	
	
	/**
	 * contructs a JSON string containing all of the given class's variables and objects
	 * @param classtoconvert: the class desired to be converted into JSON
	 * @pre the class builds propery and contains Integers in place of ints
	 * @post returns a string filled with elements from given class in the form of JSON given to us
	 */
	public static String converttojsonstring(Object classtoconvert) {
		Field[] fields = classtoconvert.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder().append(OPEN_BRACE);

		boolean first = true;

		for (Field field : fields) {
			Object val = getFieldValue(field, classtoconvert);

			if (val == null || field.getModifiers() != Modifier.PRIVATE) {
				continue;
			}

			if (first) {
				first = false;
			} else {
				sb.append(COMMA);
			}

			sb.append(QUOTE).append(field.getName()).append(QUOTE).append(COLON);

			if (val.getClass().isArray()) {
				sb.append(OPEN_BRACKET);

				for (int i = 0; i < Array.getLength(val); i++) {
					if (i > 0) {
						sb.append(COMMA);
					}
					if (val == null || Array.get(val, i) == null) {
						sb.append(NULL);
					} else {
						sb.append(Array.get(val, i).toString());
					}
				}

				sb.append(CLOSED_BRACKET);
			} else if (val.getClass().isEnum()) {
				sb.append(QUOTE).append(val.toString())
						.append(QUOTE);
			} else {
				sb.append(val.toString());
			}
		}

		return sb.append(CLOSED_BRACE).toString();
	}
	
	
	public static Object getFieldValue(Field field, Object context) {
		field.setAccessible(true);
		Object result = null;
		try {
			result = field.get(context);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			
		}
		field.setAccessible(false);
		return result;
	}
	
	protected static Object getFieldValue(String fieldName, Object context) {
		return getFieldValue(getFieldByName(fieldName, context), context);
	}
	
	
	
	private static Field getFieldByName(String fieldName, Object context) {
		Field field = null;
		try {
			field = context.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			try {
				field = context.getClass().getSuperclass().getDeclaredField(fieldName);
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return field;
	}
}