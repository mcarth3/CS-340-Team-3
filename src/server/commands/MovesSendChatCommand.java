package server.commands;
import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.GamesJoinJsonObject;
import server.jsonObjects.MovesSendChatJsonObject;

public class MovesSendChatCommand implements ICommand {
	private MovesSendChatJsonObject input; 
    /**
     *
     * @pre: ServerModel is initialized and HTTP request is decoded
     * @post: a message is added to the model
     */
	@Override
	public Object execute(Object data) {
		MovesSendChatJsonObject mscjo = (MovesSendChatJsonObject) data;
		input = mscjo; 
		ServerFacade sf = ServerFacade.getSingleton();
		return sf.MovesSendChat(mscjo.getPlayerIndex(), mscjo.getContent());  	
	}
}
