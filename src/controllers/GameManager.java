package controllers;

import java.util.ArrayList;

import model.Game;
import states.State;


public class GameManager {
	
	private ArrayList<Game> games;
	private int id;

	public GameManager() {
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

	public void update(){
	}
}
