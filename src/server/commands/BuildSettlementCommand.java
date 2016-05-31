package server.commands;

import model.Game;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.BuildSettlementJsonObject;

/**
 * Created by Jesse on 5/26/2016.
 */
public class BuildSettlementCommand implements ICommand {
	private BuildSettlementJsonObject buildSettlementJsonObject;
	/**
	 *
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: player gains settlement and VP
	 */
	@Override
	public Object execute(Object data) {
		buildSettlementJsonObject = (BuildSettlementJsonObject) data;
		return ServerFacade.getSingleton().buildSettlement(buildSettlementJsonObject.getType(), buildSettlementJsonObject.getPlayerIndex(),
				buildSettlementJsonObject.getVertexLocation(), buildSettlementJsonObject.isFree());

	}
}
