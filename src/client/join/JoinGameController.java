package client.join;

import client.GameManager.GameManager;
import client.base.Controller;
import client.base.IAction;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.IMessageView;
import model.Facade;
import model.Game;
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
	private GameInfo gametemp;
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
		// System.out.println("thread " + Thread.currentThread().getId() + "- "+result);

		GameInfo[] games = (GameInfo[]) ModelParser.parse(result, GameInfo[].class);

		PlayerInfo pi = new PlayerInfo();

		pi.setName(manager.nameTemp);
		pi.setId(manager.playerIdTemp);
		getJoinGameView().setGames(games, pi);
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		if (getJoinGameView().isModalShowing()) {
			getJoinGameView().closeModal();
		}
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {

		getNewGameView().closeModal();
		start();

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
		gametemp = game;

		for (PlayerInfo playerInfo : game.getPlayers()) {
			if (!(playerInfo.getId() == manager.playerIdTemp)) {
				if (playerInfo.getColor() != null) {
					System.out.println("COLOR FOR ID " + playerInfo.getId() + " = " + playerInfo.getColor());
					getSelectColorView().setColorEnabled(setStringColorToSharedColor(playerInfo.getColor()), false);
				} else {
					System.out.println("PROBLEM WITH ID " + playerInfo.getId());
				}
			}
		}
		if (getJoinGameView().isModalShowing()) {
			getJoinGameView().closeModal();
		}
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
			System.out.println("thread " + Thread.currentThread().getId() + "- \n\nWrong color in JoinGameController's setStringToColor()!!\n\n");
			return CatanColor.BLUE;
		}

	}

	@Override
	public void cancelJoinGame() {
		for (PlayerInfo playerInfo : gametemp.getPlayers()) {
			if (!(playerInfo.getId() == manager.playerIdTemp)) {
				if (playerInfo.getColor() != null) {
					System.out.println("COLOR FOR ID " + playerInfo.getId() + " = " + playerInfo.getColor());
					getSelectColorView().setColorEnabled(setStringColorToSharedColor(playerInfo.getColor()), true);
				} else {
					System.out.println("PROBLEM WITH ID " + playerInfo.getId());
				}
			}
		}
		getJoinGameView().closeModal();
		start();
	}

	@Override
	public void joinGame(CatanColor color) {

		GameManager gm = manager;
		gm.setplayercolortemp(color);
		gameChosen = gametemp.getId();
		RealProxy rp = RealProxy.getSingleton();
		String lower = color.toString().toLowerCase();

		//System.out.println("thread " + Thread.currentThread().getId() + "- game chosen " + gameChosen);
		//System.out.println("thread " + Thread.currentThread().getId() + "- game chosen " + lower);
		String result = rp.gameJoin(gameChosen, lower);
		// System.out.println("thread " + Thread.currentThread().getId() + "- RESULT JOIN="+result);
		if (result != null) {
			// If join succeeded

			if (getSelectColorView().isModalShowing()) {
				getSelectColorView().closeModal();
			}

			if (getJoinGameView().isModalShowing()) {
				getJoinGameView().closeModal();
			}

			Facade.getSingleton().SetGame((Game) ModelParser.parse(RealProxy.getSingleton().gameModel(-1), Game.class));

			joinAction.execute();

		} else {
			System.out.println("thread " + Thread.currentThread().getId() + "- Couldn't join a game");
		}

	}

	@Override
	public void update() {
		// getSelectColorView();

	}

}
