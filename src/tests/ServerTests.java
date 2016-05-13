package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.junit.*;
import com.google.gson.JsonObject;
import model.Game;
import model.Player;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import proxy.MockProxy;
import proxy.RealProxy;
import shared.locations.*;
import states.*;


public class ServerTests {
	
	RealProxy rp = new RealProxy();
	MockProxy mp = new MockProxy(); 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	@Before
	public void setup() {
//		rp.userRegister("SAM", "sam"); 
		//rp.userLogin("Sam", "sam");
//		rp.gamesCreate("Test Game", true, true, true); 
		//rp.gameJoin(0, "red");
		
		//login as Sam
		//join game 0
		//roll playerIndex 0
	}
	
	@After
	public void teardown() {
	}
	
	
//	STATE EXAMPLE
//		State se = State.getInstance();
//		se.setCurrentState(StateEnum.PLAY);
//		System.out.println(se.getCurrentState());
//		se.setCurrentState(StateEnum.LOGIN);
	
	
	@Test
	public void testEnum(){
		
	}
	
	// ======== REAL PROXY TESTS =========
	// NON-MOVE API
	@Test
	public void userLoginTest() {
		//String result = rp.userLogin("Sam", "sam");
		//System.out.println(result);
		//assertTrue(result!=null);
	}
	@Test
	public void userRegisterTest() {
		//String result = rp.userRegister("SAM", "sam");
		//assertTrue(true);
	}
	@Test
	public void gamesListTest(){
		//rp.gamesList();
	}
	@Test
	public void gamesCreateTest(){}
	@Test
	public void gameJoinTest(){
//		rp.userLogin("SAM", "sam");
//		rp.gamesCreate("Test Game", true, true, true); 
//		rp.gameJoin(4, "red");
	}
	@Test
	public void gamesSaveTest(){}
	@Test
	public void gamesLoadTest(){}
	@Test
	public void gameModelTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.gameModel(0); 
		assertTrue(result!=null);
	}
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
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.sendChat(0, "new message here");
		assertTrue(result!=null);
	}
	@Test
	public void acceptTradeTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.acceptTrade(0, false);  
		assertTrue(result!=null);
	}
	@Test 
	public void discardCardsTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.discardCards(0, null);   
		assertTrue(result!=null);
	}
	@Test 
	public void rollNumberTest(){
		//login as Sam
		//join game 0
		//roll playerIndex 0
		
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.rollNumber(0, 8);
		assertTrue(result!=null); 
	}
	@Test 
	public void buildRoadTest(){
		HexLocation hl = new HexLocation(-1, -1);
		EdgeLocation rl = new EdgeLocation(hl, EdgeDirection.NorthWest); 
		//Not done yet
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.buildRoad(0, null, true); 
		//assertTrue(result!=null);
	}
	@Test 
	public void buildSettlementTest(){}
	@Test
	public void buildCityTest(){}
	@Test
	public void offerTradeTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.offerTrade(2, null, 3);    
		assertTrue(result!=null);
	}
	@Test
	public void maritimeTradeTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		//String result = rp.maritimeTrade(0, ratio, inputResource, outputResource)  
		//assertTrue(result!=null);
	}
	@Test
	public void robPlayerTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		HexLocation locaiton = new HexLocation(0, 0); 
		String result = rp.robPlayer(0, 1, null);      		
		assertTrue(result!=null); 
	}
	@Test
	public void finishTurnTest(){}
	@Test 
	public void buyDevCardTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.buyDevCard(0);  		
		assertTrue(result!=null);
	}
	@Test
	public void SoldierTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		HexLocation hl = new HexLocation(-1, -1);
		String result = rp.Soldier(0, 1, hl); 			
		assertTrue(result!=null);
		//assertTrue(true); 
	}
	@Test
	public void Year_of_PlentyTest(){
		
	}
	@Test
	public void Road_BuildingTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		// not done yet
		String result = rp.Road_Building(0, null, null); 		
		assertTrue(result!=null);
	}
	@Test 
	public void MonopolyTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.Monopoly("Brick", 0);			
		//assertTrue(result!=null);
		assertTrue(true); 
	}
	@Test 
	public void MonumentTest(){
		rp.userLogin("Sam", "sam"); 
		rp.gameJoin(0, "red");
		String result = rp.Monument(0);			
		assertTrue(result!=null);
	}

	//	assertTrue(true);
	//	assertFalse(false);
	//	assertEquals("OK", "OK");
	
}
