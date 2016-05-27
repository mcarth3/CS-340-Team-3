package server.commands;
import server.ICommand;

public class MovesSendChatCommand implements ICommand {
    /**
     *
     * @pre: ServerModel is initialized and HTTP request is decoded
     * @post: a message is added to the model
     */
	@Override
	public Object execute(Object data) {
		return data; 
		// TODO Auto-generated method stub
		
	}
}
