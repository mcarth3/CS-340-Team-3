package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.Game;
import model.Player;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import proxy.MockProxy;
import proxy.RealProxy;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class ServerTests {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	RealProxy rp = new RealProxy();
	MockProxy mp = new MockProxy();
	// ======== REAL PROXY TESTS =========
	// NON-MOVE API
	@Test
	public void userLoginTest() {
		//rp.userRegister("SAM", "sam");
		//String result = rp.userLogin("SAM", "sam");
		//assertEquals("Success", result); 
	}
	@Test
	public void userRegisterTest() {
		//rp.userRegister("SAM", "sam");
	}
	@Test
	public void gamesListTest(){
		//rp.gamesList();
	}
	@Test
	public void gamesCreateTest(){}
	@Test
	public void gameJoinTest(){
		rp.gameJoin(5, "red");
	}
	@Test
	public void gamesSaveTest(){}
	@Test
	public void gamesLoadTest(){}
	@Test
	public void gameModelTest(){}
	@Test
	public void gameResetTest(){}
	@Test
	public void gameCommandsGetTest(){}
	@Test
	public void gameCommandsPostTest(){}
	@Test
	public void gameListAITest(){}
	@Test
	public void gameAddAITest(){}
	@Test
	public void utilChangeLogLevelTest(){}

	// MOVE API 
	@Test
	public void sendChatTest(){
		//rp.sendChat(0, "new message here");
	}
	@Test
	public void acceptTradeTest(){}
	@Test 
	public void discardCardsTest(){}
	@Test 
	public void rollNumberTest(){}
	@Test 
	public void buildRoadTest(){}
	@Test 
	public void buildSettlementTest(){}
	@Test
	public void buildCityTest(){}
	@Test
	public void offerTradeTest(){}
	@Test
	public void maritimeTradeTest(){}
	@Test
	public void robPlayerTest(){}
	@Test
	public void finishTurnTest(){}
	@Test 
	public void buyDevCardTest(){}
	@Test
	public void SoldierTest(){}
	@Test
	public void Year_of_PlentyTest(){}
	@Test
	public void Road_BuildingTest(){}
	@Test 
	public void MonopolyTest(){}
	@Test 
	public void MonumentTest(){}
	
	// ======== MOCK PROXY TESTS =========
	// NON-MOVE API
	@Test
	public void userLoginTestMock() {
		assertFalse(false);
		//mp.userRegister("SAM", "sam");
		//mp.userLogin("SAM", "sam");
	}
	@Test
	public void userRegisterTestMock() {
		//mp.userRegister("SAM", "sam");
	}
	@Test
	public void gamesListTestMock(){
		//rp.gamesList();
	}
	@Test
	public void gamesCreateTestMock(){}
	@Test
	public void gameJoinTestMock(){}
	@Test
	public void gamesSaveTestMock(){}
	@Test
	public void gamesLoadTestMock(){}
	@Test
	public void gameModelTestMock(){}
	@Test
	public void gameResetTestMock(){}
	@Test
	public void gameCommandsGetTestMock(){}
	@Test
	public void gameCommandsPostTestMock(){}
	@Test
	public void gameListAITestMock(){}
	@Test
	public void gameAddAITestMock(){}
	@Test
	public void utilChangeLogLevelTestMock(){}

	// MOVE API 
	@Test
	public void sendChatTestMock(){}
	@Test
	public void acceptTradeTestMock(){}
	@Test 
	public void discardCardsTestMock(){}
	@Test 
	public void rollNumberTestMock(){}
	@Test 
	public void buildRoadTestMock(){}
	@Test 
	public void buildSettlementTestMock(){}
	@Test
	public void buildCityTestMock(){}
	@Test
	public void offerTradeTestMock(){}
	@Test
	public void maritimeTradeTestMock(){}
	@Test
	public void robPlayerTestMock(){}
	@Test
	public void finishTurnTestMock(){}
	@Test 
	public void buyDevCardTestMock(){}
	@Test
	public void SoldierTestMock(){}
	@Test
	public void Year_of_PlentyTestMock(){}
	@Test
	public void Road_BuildingTestMock(){}
	@Test 
	public void MonopolyTestMock(){}
	@Test 
	public void MonumentTestMock(){}

	//	assertTrue(true);
	//	assertFalse(false);
	//	assertEquals("OK", "OK");
	
}
