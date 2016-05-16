package tests;
/**
 * @author Mike Towne
 */
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import model.Game;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import poller.modeljsonparser.ClassToJSON;
import poller.modeljsonparser.ModelParser;
import proxy.MockProxy;

public class ModelJSONInitializationTest {
	@Test
	public void test() {

		Game testgame= null;// = new Game(1);
		PrintWriter out = null;
		//try {
		//	out = new PrintWriter("testmodel.json");
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//}
		//System.out.println("Testing Model Creation From JSON");
		//System.out.println("converting the model into JSON");
		//out.println(ClassToJSON.converttojsonstring(testgame));
		//out.close();
		
		//System.out.println("the model's title is '" + testgame.title + "'");
		//System.out.println(ClassToJSON.converttojsonstring(testgame));
		System.out.println("---------------------------------------------");

		System.out.println("using mock proxy to retrieve model");
		MockProxy NewMockProxy= MockProxy.getSingleton();
		String stringtoconvert = NewMockProxy.gameModel(1);
		
		System.out.println("converting from JSON to the model");
		try {
			testgame = ModelParser.parse(stringtoconvert, Game.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		System.out.println("the model's title is '" + testgame.title + "'");
		System.out.println("---------------------------------------------");
		System.out.println(ClassToJSON.converttojsonstring(testgame));

	}
}
