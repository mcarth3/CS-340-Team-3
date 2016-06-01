package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.OfferTradeJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class OfferTradeCommand implements ICommand {
	private OfferTradeJsonObject offerTradeJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: list of resources is offered to a particular player
	 */
	@Override
	public Object execute(Object data) {
		offerTradeJsonObject = (OfferTradeJsonObject) data;
		return ServerFacade.getSingleton().offerTrade(offerTradeJsonObject.getType(), offerTradeJsonObject.getPlayerIndex(),
				offerTradeJsonObject.getOffer(), offerTradeJsonObject.getReceiver());

	}
}
