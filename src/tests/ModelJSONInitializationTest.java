package tests;

import org.junit.Test;

import model.Game;
import poller.modeljsonparser.ClassToJSON;
import poller.modeljsonparser.ModelParser;
import proxy.MockProxy;

public class ModelJSONInitializationTest {
	@Test
	public void test() {

		Game testgame = new Game(1);
		//PrintWriter out = null;
		//try {
		//	out = new PrintWriter("testmodel.json");
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//}
		//System.out.println("Testing Model Creation From JSON");
		//System.out.println("converting the model into JSON");
		//out.println(ClassToJSON.converttojsonstring(testgame));
		//out.close();

		System.out.println(ClassToJSON.converttojsonstring(testgame));
		System.out.println("---------------------------------------------");

		System.out.println("using mock proxy to retrieve model");
		MockProxy NewMockProxy = MockProxy.getSingleton();
		String stringtoconvert = NewMockProxy.gameModel(1);

		System.out.println("converting from JSON to the model");
		System.out.println("---------------------------------------------");
		try {
			testgame = (Game) ModelParser.parse(stringtoconvert, Game.class);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		System.out.println("---------------------------------------------");
		System.out.println(ClassToJSON.converttojsonstring(testgame));

	}
}
