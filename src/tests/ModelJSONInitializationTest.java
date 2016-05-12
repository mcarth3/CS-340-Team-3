package tests;
/**
 * @author Mike Towne
 */
import static org.junit.Assert.fail;

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

		try {
			Game testgame = new Game(1);
			System.out.println("Testing Model Creation From JSON");
			//converting the model into JSON
			System.out.println(ClassToJSON.converttojsonstring(testgame));
			//using mock proxy to retrieve model
			//MockProxy NewMockProxy= MockProxy.getSingleton();
			//String stringtoconvert = NewMockProxy.gameModel(1);
			//testgame = ModelParser.parse(stringtoconvert, Game.class);

		} catch (Exception e) {
			fail("ServerPoller getSingleton was null");
		}
	}
}
