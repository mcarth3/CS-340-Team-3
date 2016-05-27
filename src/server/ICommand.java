package server;

/**
 * Created by Jesse on 5/26/2016. interface for all Commands.
 */
public interface ICommand {

	/**
	 * called by a Handler to execute whatever the Command needs. (e.g. if a
	 * RoadBuilding command came in from the server, one would create a
	 * RoadBuildingCommand and call execute() to cause the ServerModel to
	 * change)
	 * 
	 * @pre: request from HTTP decoded and ServerModel initialized
	 * @post: server model has been changed by a controller.
	 */
	Object execute(Object data);
}
