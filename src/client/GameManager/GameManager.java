package client.GameManager;

import java.util.ArrayList;
import java.util.Observable;

import model.Facade;
import model.Game;
import model.ObjectNotFoundException;
import model.Player;
import states.State;


public class GameManager extends Observable {
	
	//private ArrayList<Game> games;
	private int id;
	private static GameManager singleton = null;
	private Player player;
	private Facade modelfacade;
	public int playerIdTemp;

	public GameManager() {
		modelfacade = Facade.getSingleton();
	}
	public Game getModel(){
		return modelfacade.gettheGame();
	}
	
	
	public void setfacade(Facade newmodelfacade) {
		modelfacade= newmodelfacade;
	}
	/**
	 * gets game by id
	 * @param id- the game id
	 * @pre id is not null
	 * @post game with appropriate id is returned, null if not found
	 */
	public Game getGameById(Integer id) {
		return null;
	}
	/**
	 * gets every game
	 * @pre none
	 * @post all the games are returned
	 */
	public Game[] getAllGames() {
		return null;

	}
	/**
	 * replaces the current model from the poller, and updates all observers that theres a new model
	 * @param game- the new model to replace the old model
	 * @pre game is not null, and is formatted correctly
	 * @post the current model is replaced with the new one and updates all observers that theres a new model
	 */
	public Game updateGame(Game game) {
		return game;
	}

	public Game createGame(Game gameToCreate) {
		return gameToCreate;

	}

	public boolean removeGame(Integer id) {
		return false;

	}

	private void addTestGames() {
	}
	
	/**
	 * updates the game state to all the observers
	 */
	public void updatestate(State currentstate){
	}
	/**
	 * registers observers
	 */
	public void register(){
	}

	/**
	 * Updates the game. This also notifies all observers of the ModelFacade.
	 * @pre the model is not null and is valid
	 * @post All the observers are notified of the change
	 * @param model the CatanModel
	 */
	public void update(Game model){
		modelfacade.SetGame(model);
		updateLocalPlayer();
		setChanged();
		notifyObservers();
	}

	private void updateLocalPlayer() {
		ArrayList<Player> players = modelfacade.gettheGame().getPlayers();
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i) == null) continue;
			if(players.get(i).getPlayerID() == this.getthisplayer().getPlayerID()) {
				this.setthisplayer(players.get(i));
			} 
		}
	}
	public void setthisplayer(Player player2) {
		this.player = player2;
	}
	
	public void setplayerbyid(int playerid) throws ObjectNotFoundException {
		this.player = modelfacade.getGame().findPlayerbyid(playerid);
	}
	public void setplayerbyidtemp(int playerid){
		this.playerIdTemp = playerid; 
	}
	public static GameManager getSingleton() {
		if(singleton == null) {
			singleton = new GameManager();
		}
		return singleton;
	}
	
	public Player getthisplayer() {
		return player;
	}
	
	
    public int getversion(){
    	return modelfacade.getversion(); 
    }
}
