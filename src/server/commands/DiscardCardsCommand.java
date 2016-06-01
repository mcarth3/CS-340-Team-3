package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.DiscardCardsJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class DiscardCardsCommand implements ICommand {
	private DiscardCardsJsonObject discardCardsJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: list of cards is taken away from player
	 */
	@Override
	public Object execute(Object data) {
		discardCardsJsonObject = (DiscardCardsJsonObject) data;
		return ServerFacade.getSingleton().discardCards(discardCardsJsonObject.getType(),
				discardCardsJsonObject.getPlayerIndex(), discardCardsJsonObject.getDiscardedCards());

	}
}
