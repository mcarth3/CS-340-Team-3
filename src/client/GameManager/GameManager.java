package client.GameManager;

import java.util.ArrayList;
import java.util.Observable;

import model.Facade;
import model.Game;
import model.ObjectNotFoundException;
import model.Player;
import shared.definitions.CatanColor;

public class GameManager extends Observable {

	// private ArrayList<Game> games;
	private int id;
	private static GameManager singleton = null;
	private Player player;
	private Facade modelfacade;
	public int playerIdTemp;
	public String nameTemp;
	public CatanColor colorTemp;
	private boolean discardedcheck = false;
	private boolean robbingready = false;
	private boolean begin = false;

	public GameManager() {
		modelfacade = Facade.getSingleton();

	}

	public Game getModel() {
		return modelfacade.gettheGame();
	}

	public void setfacade(Facade newmodelfacade) {
		modelfacade = newmodelfacade;
	}

	public void setdiscardedcheck(boolean newval) {
		discardedcheck = newval;
	}

	public Boolean getdiscardedcheck() {
		return discardedcheck;
	}

	public void setrobbingready(boolean newval) {
		robbingready = newval;
	}

	public Boolean getrobbingready() {
		return robbingready;
	}

	/**
	 * replaces the current model from the poller, and updates all observers
	 * that theres a new model
	 * 
	 * @param game-
	 *            the new model to replace the old model
	 * @pre game is not null, and is formatted correctly
	 * @post the current model is replaced with the new one and updates all
	 *       observers that theres a new model
	 */
	public Game updateGame(Game game) {
		modelfacade.SetGame(game);
		return game;
	}

	/**
	 * Updates the game. This also notifies all observers of the ModelFacade.
	 * 
	 * @pre the model is not null and is valid
	 * @post All the observers are notified of the change
	 * @param model
	 *            the CatanModel
	 */
	public void update(Game model) {
//		System.out.println("thread " + Thread.currentThread().getId() + "- GAMEMANAGERUPDATE");
		modelfacade.SetGame(model);
		updateLocalPlayer();
		setChanged();
		notifyObservers();
	}

	private void updateLocalPlayer() {
		//	System.out.println("thread " + Thread.currentThread().getId() + "- this is the last thing that gets called before it breaks?");
		//System.out.println("PLAYERS " + modelfacade.gettheGame().getPlayers());
		ArrayList<Player> players = modelfacade.gettheGame().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != null) {
				//System.out.println("thread " + Thread.currentThread().getId() + "- player " + i + " is not null");
				System.out.println("thread " + Thread.currentThread().getId() + "- player i id = " + players.get(i).getPlayerID());
				System.out.println("thread " + Thread.currentThread().getId() + "- player i name = " + players.get(i).getName());
				System.out.println("thread " + Thread.currentThread().getId() + "- playerIdTemp = " + playerIdTemp);

				if (players.get(i).getPlayerID() == playerIdTemp) {
					this.setthisplayer(players.get(i));
				}
			}
		}
	}

	public void setthisplayer(Player player2) {
		System.out.println("thread " + Thread.currentThread().getId() + "- LOCAL PLAYER SET: " + player2.getName());
		this.player = player2;
	}

	public void setplayerbyid(int playerid) throws ObjectNotFoundException {
		this.player = modelfacade.getGame().findPlayerbyid(playerid);
	}

	public void setplayerbyidtemp(int playerid) {
		System.out.println("thread " + Thread.currentThread().getId() + "- ID TEMPORARILY SET");
		this.playerIdTemp = playerid;
	}

	public Integer getTempId() {
		return playerIdTemp;
	}

	public void setplayernametemp(String name) {
		this.nameTemp = name;
	}

	public void setplayercolortemp(CatanColor color) {
		this.colorTemp = color;
	}

	public static GameManager getSingleton() {
		if (singleton == null) {
			singleton = new GameManager();
		}
		return singleton;
	}

	public Player getthisplayer() {
		return player;
	}

	public Facade getModelfacade() {
		return modelfacade;
	}

	public int getversion() {
		return modelfacade.getversion();
	}

	public void createdefaultgame() {
		// modelfacade.SetGame(ModelParser.parse2(MockProxy.getSingleton().gameModel(1)));
	}

	public boolean getbegin() {
		return begin;

	}

	public void setbegin(boolean b) {
		begin = b;

	}
}
