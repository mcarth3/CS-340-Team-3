package model;


import java.util.ArrayList;

/**
 * Created by Jesse on 5/2/2016.
 */
public class Game {


//    private ArrayList<Dice> twoDice;
    private ArrayList<Player> people;
//    private Robber robber;
//    private ArrayList<Hex> board;

    /**
     * This will be called after a player completes their turn. The main data will then
     * be changed on the server so it is equal to the player's turn
     * @pre A player's turn has completed, or the Game has initialized and is ready for the first turn.
     * @post A player's turn completes (possibly leading to the nextTurn() or endGame()
     */
    public void nextTurn()
    {

    }

    /**
     * This will be called to ensure that the Game grabs all the updated info from the
     * server.
     * @pre initialize has to have been called before this, but once the Poller grabs a new model
     * from the server, it will call this.
     * @post Game has all new info/model data from the server
     */
    public void syncBoard()
    {

    }

    /**
     * this is called at the very start of the game to initialize the singleton and
     * create all the classes needed to function.
     *
     * @pre Game just started (or created). This could be called by the constructor since there may be so much
     * to
     * @post Game has all necessary classes created to function (2-4 players, all necessary dice and hexes are created, etc)
     */
    public void initialize()
    {

    }


    /**

     * @pre Game just ended because a player earned 10 victory points
     * @post victory message has been displayed (could return to a Menu?)
     */
    public void endGame()
    {

    }



}
