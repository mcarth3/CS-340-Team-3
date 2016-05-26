package client.join;

import java.util.ArrayList;

import client.GameManager.GameManager;
import client.base.Controller;
import client.base.IAction;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.IMessageView;
import model.Player;
import poller.InvalidMockProxyException;
import poller.ServerPoller;
import poller.modeljsonparser.ModelParser;
import proxy.RealProxy;
import shared.definitions.CatanColor;

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
	 * @param view
	 *            Join game view
	 * @param newGameView
	 *            New game view
	 * @param selectColorView
	 *            Select color view
	 * @param messageView
	 *            Message view (used to display error messages that occur while
	 *            the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}

	public IJoinGameView getJoinGameView() {

		return (IJoinGameView) super.getView();
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
	 * @param value
	 *            The action to be executed when the user joins a game
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
		// System.out.println(result);

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

		// getJoinGameView().update();

		getNewGameView().closeModal();
		start();
	}

	@Override
	public void startJoinGame(GameInfo game) {

		gameChosen = game.getId();

		// ***********************************************************ADDED
		// BELOW FOR COLORS:
		GameManager gm = GameManager.getSingleton();
		// gm.setplayercolortemp(color);

		try {
			ServerPoller.getSingleton();
		} catch (InvalidMockProxyException e) {
			e.printStackTrace();
		}

		RealProxy rp = RealProxy.getSingleton();
		String result = rp.gameJoin(gameChosen, CatanColor.BLUE.name());

		if (GameManager.getSingleton() != null) {
			if (GameManager.getSingleton().getModel() != null) {
				if (GameManager.getSingleton().getModel().getPlayers() != null) {
					ArrayList<Player> allPlayers = GameManager.getSingleton().getModel().getPlayers();

					for (int i = 0; i < allPlayers.size(); i++) {
						if (allPlayers.get(i).getPlayerIndex() != GameManager.getSingleton().getthisplayer().getPlayerIndex()) {
							CatanColor thisPlayersColor = setStringColorToSharedColor(allPlayers.get(i).getColor());
							getSelectColorView().setColorEnabled(thisPlayersColor, false);
						}
					}
				}
			}

		}
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ADDED ABOVE FOR
		// COLORS^^^
		getSelectColorView().showModal();

	}

	public CatanColor setStringColorToSharedColor(String stringColor) {
		String low = stringColor.toLowerCase();
		if (low.equals("blue")) {
			return CatanColor.BLUE;
		}
		if (low.equals("brown")) {
			return CatanColor.BROWN;
		}
		if (low.equals("green")) {
			return CatanColor.GREEN;
		}
		if (low.equals("orange")) {
			return CatanColor.ORANGE;
		}
		if (low.equals("puce")) {
			return CatanColor.PUCE;
		}
		if (low.equals("purple")) {
			return CatanColor.PURPLE;
		}
		if (low.equals("red")) {
			return CatanColor.RED;
		}
		if (low.equals("white")) {
			return CatanColor.WHITE;
		}
		if (low.equals("yellow")) {
			return CatanColor.YELLOW;
		} else {
			System.out.println("\n\nWrong color in JoinGameController's setStringToColor()!!\n\n");
			return CatanColor.BLUE;
		}

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
		// System.out.println(gameChosen);
		// System.out.println(lower);
		String result = rp.gameJoin(gameChosen, lower);
		// System.out.println("RESULT JOIN="+result);
		if (result != null) {
			// If join succeeded
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			GameManager.getSingleton().update(ModelParser.parse2(RealProxy.getSingleton().gameModel(-1)));
			joinAction.execute();
		} else {
			System.out.println("Couldn't join a game");
		}
	}

	@Override
	public void update() {
	}

}
