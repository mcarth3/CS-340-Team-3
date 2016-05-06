package model;


import java.util.ArrayList;

/**
 * Created by Jesse on 5/2/2016.
 */
public class Game {


//    private ArrayList<Dice> twoDice;
    private ArrayList<Person> people;
//    private Robber robber;
//    private ArrayList<Hex> board;

    /**
     * This will be called after a player completes their turn. The main data will then
     * be changed on the server so it is equal to the player's turn
     * PRE: A player's turn has completed, or the Game has initialized and is ready for the first turn.
     * POST: A player's turn completes (possibly leading to the nextTurn() or endGame()
     */
    public void nextTurn()
    {

    }

    /**
     * This will be called to ensure that the Game grabs all the updated info from the
     * server.
     * PRE: initialize has to have been called before this, but once the Poller grabs a new model
     * from the server, it will call this.
     * POST: Game has all new info/model data from the server
     */
    public void syncBoard()
    {

    }

    /**
     * this is called at the very start of the game to initialize the singleton and
     * create all the classes needed to function.
     *
     * PRE: Game just started (or created)
     * POST: Game has all necessary classes created to function (2-4 players, all necessary dice and hexes are created, etc)
     */
    public void initialize()
    {

    }


    /**

     * PRE: Game just ended because a player earned 10 victory points
     * POST: victory message has been displayed (could return to a Menu?)
     */
    public void endGame()
    {

    }



}
