package persistentstorage.databaseobject;

import model.Game;
import server.CommandList;
import shared.definitions.CatanColor;

import java.util.ArrayList;

/**
 * interface implemented by FlatFileGameDAO and RDBGameDAO
 * includes functions getCommands, clearCommands, storeGame,  getGameByID, getListOfGames
 */
public interface IGameDao {

    /**
     *
     * @param gameID
     * @pre gameID is valid
     * @post the CommandList of the specified game is returned
     */
    public CommandList getCommands(Integer gameID);

    /**
     *
     * @param gameID
     * @pre gameID is valid
     * @post the CommandList of the specified game is cleared
     */
    public void clearCommands(Integer gameID);

    /**
     *
     * @param gameID
     * @param theGame
     * @pre gameID is valid, theGame is valid Game object
     * @post the specified Game is saved in the specified form of file (RDB or FlatFile, depending on the object implementing this interface)
     */
    public void storeGame(Integer gameID, Game theGame);

    /**
     *
     * @param gameID
     * @pre gameID is valid
     * @post the specified Game is returned to the caller.
     */
    public Game getGameByID(Integer gameID);

    /**
     *
     * @pre a list of Games has already been established here
     * @post the list of all saved Games is returned to the caller.
     */
    public ArrayList<Game> getListOfGames(Integer gameID);




}
