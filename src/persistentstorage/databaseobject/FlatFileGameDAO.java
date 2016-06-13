package persistentstorage.databaseobject;

import model.Game;
import server.CommandList;

import java.util.ArrayList;

/**
 * Created by Jesse on 6/12/2016.
 */
public class FlatFileGameDAO implements IGameDao {

    @Override
    public CommandList getCommands(Integer gameID) {
        return null;
    }

    @Override
    public void clearCommands(Integer gameID) {

    }

    @Override
    public void storeGame(Integer gameID, Game theGame) {

    }

    @Override
    public Game getGameByID(Integer gameID) {
        return null;
    }

    @Override
    public ArrayList<Game> getListOfGames(Integer gameID) {
        return null;
    }
}
