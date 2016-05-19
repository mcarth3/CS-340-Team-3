package client.join;

import shared.definitions.CatanColor;
import client.GameManager.GameManager;
import client.base.*;
import client.data.*;
import client.misc.*;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import poller.modeljsonparser.ModelParser;
import proxy.RealProxy;



/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	
	public Integer gameChosen;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() {
		
		RealProxy rp = RealProxy.getSingleton();
		String result = rp.gamesList();
		//System.out.println(result); 

		GameInfo[] games = ModelParser.parse4(result); 
	
		PlayerInfo pi = new PlayerInfo();
		GameManager gm = GameManager.getSingleton();
		
		pi.setName(gm.nameTemp);
		pi.setId(gm.playerIdTemp);
		getJoinGameView().setGames(games, pi);
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		
		RealProxy rp = RealProxy.getSingleton();
		String name = getNewGameView().getTitle();
		Boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
		Boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		Boolean randomPorts = getNewGameView().getUseRandomPorts();
		rp.gamesCreate(name, randomTiles, randomNumbers, randomPorts); 
		
//		getJoinGameView().update(); 
		
		getNewGameView().closeModal();
		start(); 
	}

	@Override
	public void startJoinGame(GameInfo game) {

		gameChosen = game.getId();
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
	
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		
		GameManager gm = GameManager.getSingleton();
		gm.setplayercolortemp(color); 
		
		
		try {
			ServerPoller.getSingleton();
		} catch (InvalidMockProxyException e) {
			e.printStackTrace();
		}
		
		
		RealProxy rp = RealProxy.getSingleton();
		String lower = color.toString().toLowerCase();
		System.out.println(gameChosen);
		System.out.println(lower); 
		String result = rp.gameJoin(gameChosen, lower); 
		//System.out.println("RESULT JOIN="+result); 
		if(result != null){
			// If join succeeded
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			joinAction.execute();
		}else{
			System.out.println("Couldn't join a game"); 
		}
	}
	public void update(){
	}

}

