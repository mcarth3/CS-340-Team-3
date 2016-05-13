package proxy;

import java.util.ArrayList;

import javax.annotation.Resource;

import model.Game;
import model.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface IServer {
	
	// NON-MOVE API
	/**
	 * /user/login
	 * description: logs the caller in to the server, and sets their catan.user HTTP cookie. 
	 * @param username -name of the user
	 * @pre username is not null, password is not null
	 * @post
	 * If username and password are valid:
	 * 1. The server return an HTTP 200 with â€œSuccessâ€� in the body
	 * 2. The HTTP response headers set the catan.user cookie to contain the identity of the logged-in player. The cookie uses â€œPath=/â€�, and its value contains a url-encoded JSON object of the following form {â€œnameâ€�:STRING, â€œpasswordâ€�:STRING, â€œplayerIDâ€�:INTEGER }. For example, {â€œnameâ€�:â€�Rickâ€�, â€œpasswordâ€�:â€�secretâ€�, â€œplayerIDâ€�:14}
	 * 
	 * If the passedÂ­ in (username, password) pair is not valid, or the operation fails for any other reason:
	 * 1. The server returns an HTTP 400 error response, and the body contains an error message.
	 * Notes: The passedÂ­in username and password may correspond to the credentials of any registered user. The server starts with four users: Sam, Brooke, Pete and Mark. Their passwords are sam, brooke, pete and mark respectively. Any additional registered users should also work with this call
	 *
	 * public void userLogin(username, password){}
	 */
	public String userLogin(String username, String password); 
	
	/**
	 * /user/register
	 * description: This method does two things:
	 * Creates a new user account
	 * Logs the caller in to the server as the new user, and sets their catan.user HTTP cookie. (See the doc â€œHow the Catan Server Uses HTTP Cookiesâ€�)
	 * @param username - player's username to register
	 * @param password - player's password
	 * @pre username is not null, password is not null, the specified username is not already in use
	 * @post 
	 * If there is no existing user with the specified username:
	 * 1. A new user account has been created with the specified username and password.
	 * 2. The server returns an HTTP 200 success response with â€œSuccessâ€� in the body.
	 * 3. The HTTP response headers set the catan.user cookie to contain the identity of the loggedÂ­in player. The cookie uses â€�Path=/â€�, and its value contains a urlÂ­encoded JSON object of the following form: { â€œnameâ€�: STRING, â€œpasswordâ€�: STRING, â€œplayerIDâ€�: INTEGER }. For example, { â€œnameâ€�: â€œRickâ€�, â€œpasswordâ€�: â€œsecretâ€�, â€œplayerIDâ€�: 14 }.
	 * If there is already an existing user with the specified name, or the operation fails for any other reason:
	 * 1. The server returns an HTTP 400 error response, and the body contains an error message.
	 * Notes: You should be able to register any username via this call, unless that username is already registered with another user. There is no method for changing passwords.
	 * 
	 * public void userRegister(username, password){}
	 */
	public String userRegister(String username, String password);

	/**
	 * /games/list
	 * description: returns information about all of the current games on the server
	 * @pre none
	 * @post
	 * If the operation succeeds, 
	 * 1. The server returns an HTTP 200 success response. 
	 * 2. The body contains a JSON array containing a list of objects that contain information about the serverâ€™s games
	 * 
	 * If the operation fails, 
	 * 1. The server returns an HTTP 400 error response, and the body contains an error message.
	 * 
	 * Output JSON format:
	 * The output is a JSON array of game objects. Each game object contains the title and ID of a game, and an array of four player objects containing information about players who have
	 * joined the game. Each player object contains the color, name and ID of a player who has
	 * joined the game. Players who have not yet joined the game are represented as empty JSON
	 * objects. The idâ€™s are integers, and colors are one of the following values: red, green,
	 * blue, yellow, puce, brown, white, purple, orange.
	 * [
	 * 	{
	 * 		"title":"GameName",
	 * 		"id":0,
	 * 		"players":[
	 * 			{
	 * 				"color":"orange",
	 * 				"name":"PlayerName",
	 * 				"id":0
	 * 			},
	 * 			...
	 * 		]
	 * 	},
	 * 	...
	 * ]
	 */
	public String gamesList(); 
	
	/**
	 * /games/create
	 * Description: Creates a new game on the server.
	 * @pre name != null, randomTiles, randomNumbers, and randomPorts contain valid boolean values
	 * @post
	 * If the operation succeeds,
	 * 1. A new game with the specified properties has been created
	 * 2. The server returns an HTTP 200 success response.
	 * 3. The body contains a JSON object describing the newly created game
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * Output JSON format:
	 * The output is a JSON object containing information about the newly created game, including
	 * its title, ID, and an array of four empty player objects.
	 * {
	 * 	"title":"GameName",
	 * 	"id":3,
	 * 	"players":[
	 * 		{},
	 * 		{},
	 * 		{},
	 * 		{}
	 * 	]
	 * }
	 * 
	 * public void gamesCreate(name, randomTiles, randomNumbers, randomPorts);
	 */ 
	public String gamesCreate(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts);
	
	/**
	 * /games/join
	 * Description: Adds the player to the specified game and sets their catan.game cookie. (See the document
	 * titled â€œHow the Catan Server Uses HTTP Cookiesâ€� for more details on cookies.)
	 * @pre
	 * 1. The user has previously logged in to the server (i.e., they have a valid catan.user HTTP cookie).
	 * 2. The player may join the game because
	 * 	2.a They are already in the game, OR
	 * 	2.b There is space in the game to add a new player
	 * 3. The specified game ID is valid
	 * 4. The specified color is valid (red, green, blue, yellow, puce, brown, white, purple,
	 * orange)
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response with â€œSuccessâ€� in the body.
	 * 2. The player is in the game with the specified color (i.e. calls to /games/list method will
	 * show the player in the game with the chosen color).
	 * 3. The server response includes the â€œSetÂ­cookieâ€� response header setting the catan.game
	 * HTTP cookie
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * public void gameJoin(catan.user, gameID, color){}
	 */
	public String gameJoin(Integer gameID, String color);
	
	/**
	 * /games/save
	 * Description: This method is for testing and debugging purposes. When a bug is found, you can use the /games/save method to save the state of the game to a file, and attach the file to a bug report. A developer can later restore the state of the game when the bug occurred by loading the previously saved file using the /games/load method. Game files are saved to and loaded from the server's saves/ directory.
	 * @pre
	 * 1. The specified game ID is valid
	 * 2. The specified file name is valid (i.e., not null or empty)
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response with â€œSuccessâ€� in the body.
	 * 2. The current state of the specified game (including its ID) has been saved to the
	 * specified file name in the serverâ€™s saves/ directory
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * public void gamesSave(gameID, fileName){}
	 */
	public String gamesSave(Integer gameID, String fileName); 
	
	/**
	 * /games/load
	 * Description: This method is for testing and debugging purposes. When a bug is found, you can use the /games/save method to save the state of the game to a file, and attach the file to a bug report. A developer can later restore the state of the game when the bug occurred by loading the previously saved file using the /games/load method. Game files are saved to and loaded from the server's saves/ directory.
	 * @pre 
	 * 1. A previously saved game file with the specified name exists in the serverâ€™s saves/
	 * directory.
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response with â€œSuccessâ€� in the body.
	 * 2. The game in the specified file has been loaded into the server and its state restored
	 * (including its ID).
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 *
	 *public void gamesLoad(fileName);
	 */
	public String gamesLoad(String fileName);
	
	/**
	 * /game/model?version=N
	 * Description: Returns the current state of the game in JSON format.
	 * In addition to the current game state, the returned JSON also includes a version number for the client model. The next time /game/model is called, the version number from the
	 * previously retrieved model may optionally be included as a query parameter in the request
	 * (/game/model?version=N). The server will only return the full JSON game state if its version number is not equal to N. If it is equal to N, the server returns true to indicate that the caller already has the latest game state. This is merely an optimization. If the version number is not included in the request URL, the server will return the full game state.
	 * @pre
	 * 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 * valid catan.user and catan.game HTTP cookies).
	 * 2. If specified, the version number is included as the â€œversionâ€� query parameter in the
	 * request URL, and its value is a valid integer.
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response.
	 * 2. The response body contains JSON data
	 * 	a. The full client model JSON is returned if the caller does not provide a version
	 * number, or the provide version number does not match the version on the server
	 * 	b. â€œtrueâ€� (true in double quotes) is returned if the caller provided a version number,
	 * and the version number matched the version number on the server
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * The format of the returned JSON can be found on the serverâ€™s Swagger page, or in the document titled â€œClient Model JSON Documentationâ€�.
	 * 
	 * public void gameModel(catan.user, versionNumber){}
	 */
	public String gameModel(Integer versionNumber); 
	
	/**
	 * /game/reset
	 * void gameReset(catan.user){} ?
	 * Description: Clears out the command history of the current game.
	 * For the default games created by the server, this method reverts the game to the state
	 * immediately after the initial placement round. For userÂ­created games, this method reverts
	 * the game to the very beginning (i.e., before the initial placement round). This method returns the client model JSON for the game after it has been reset. You must login and join a game before calling this method.
	 * @pre
	 * 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 * valid catan.user and catan.game HTTP cookies).
	 * @post
	 * If the operation succeeds,
	 * 1. The gameâ€™s command history has been cleared out
	 * 2. The gameâ€™s players have NOT been cleared out
	 * 3. The server returns an HTTP 200 success response.
	 * 4. The body contains the gameâ€™s updated client model JSON
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * Note: When a game is reset, the players in the game are maintained
	 * 
	 * public void gameReset(catan.user, catan.game){}
	 */
	public String gameReset(Player user, Game game); 
	
	/**
	 * /game/commands [GET]
	 * Description: Returns a list of commands that have been executed in the current game.
	 * This method can be used for testing and debugging. The command list returned by this
	 * method can be passed to the /game/command (POST) method to reÂ­execute the commands
	 * in the game. This would typically be done after calling /game/reset to clear out the gameâ€™s command history. 
	 * This is one way to capture the state of a game and restore it later. 
	 * (See the /games/save and /games/load methods which provide another way to save and restore the state of a game.)
	 * 
	 * For the default games created by the server, this method returns a list of all commands that have been 
	 * executed after the initial placement round. For userÂ­created games, this method returns a list of all commands 
	 * that have been executed since the very beginning of the game (i.e., before the initial placement round).
	 * 
	 * You must login and join a game before calling this method.
	 * 
	 * @pre
	 * 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 * valid catan.user and catan.game HTTP cookies).
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response.
	 * 2. The body contains a JSON array of commands that have been executed in the game.
	 * This command array is suitable for passing back to the /game/command [POST] method to
	 * restore the state of the game later (after calling /game/reset to revert the game to its initial state).
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * public void gameCommandsGet(catan.user, catan.game){}
	 */
	public String gameCommandsGet(Player user, Game game);
	
	/**
	 * /game/commands [POST]
	 * Description: Executes the specified command list in the current game.
	 * This method can be used for testing and debugging. The command list returned by the /game/command [GET] method is suitable for passing to this method.
	 * 
	 * This method returns the client model JSON for the game after the command list has been
	 * applied.
	 * 
	 * You must login and join a game before calling this method.
	 * 
	 * @pre
	 * 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 * valid catan.user and catan.game HTTP cookies).
	 * @post
	 * If the operation succeeds,
	 * 1. The passedÂ­in command list has been applied to the game.
	 * 2. The server returns an HTTP 200 success response.
	 * 3. The body contains the gameâ€™s updated client model JSON
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * public void gameCommandsPost(catan.user, catan.game){}
	 */
	public String gameCommandsPost(Player user, Game game); 
	
	/**
	 * /game/listAI
	 * Description: Returns a list of supported AI player types. Currently, LARGEST_ARMY is the only supported type.
	 * 
	 * @pre None
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response.
	 * 2. The body contains a JSON string array enumerating the different types of AI players.
	 * These are the values that may be passed to the /game/addAI method.
	 * 
	 * public void gameListAI(){}
	 */
	public String gameListAI(); 
	
	/**
	 * /game/addAI
	 * Description: Adds an AI player to the current game. You must login and join a game before calling this method.
	 * @pre
	 * 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 * valid catan.user and catan.game HTTP cookies).
	 * 2. There is space in the game for another player (i.e., the game is not â€œfullâ€�).
	 * 3. The specified â€œAITypeâ€� is valid (i.e., one of the values returned by the /game/listAI
	 * method).
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response with â€œSuccessâ€� in the body.
	 * 2. A new AI player of the specified type has been added to the current game. The server
	 * selected a name and color for the player.
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * public void gameAddAI(catan.user, catan.game, AIType){}
	 */
	public String gameAddAI(String AIType); 
	
	/**
	 * /util/changeLogLevel
	 * Description: Sets the serverâ€™s logging level.
	 * @pre
	 * 1.The caller specifies a valid logging level. Valid values include: SEVERE, WARNING,
	 * INFO, CONFIG, FINE, FINER, FINEST
	 * @post
	 * If the operation succeeds,
	 * 1. The server returns an HTTP 200 success response with â€œSuccessâ€� in the body.
	 * 2. The Server is using the specified logging level
	 * 
	 * If the operation fails,
	 * 1. The server returns an HTTP 400 error response, and the body contains an error
	 * message.
	 * 
	 * public void utilChangeLogLevel(logLevel){}
	 */
	public String utilChangeLogLevel(String logLevel);
	

	// MOVE API 
	/**
	 * Anytime Commands
	 * Properties:
	 * content: string [the message you want to send]
	 * @pre None (this command may be executed at any time by any player)
	 * @post The chat contains your message at the end
	 * 
	 * "type": "sendChat"
	 * public void sendChat(content){}
	 */
	public String sendChat(Integer playerIndex, String content); 

	/**
	 * Miscellaneous Commands
	 * Properties: willAccept: boolean [Whether or not you accept the offered trade]
	 * @pre
	 * You have been offered a domestic trade. To accept the offered trade, you have the required resources
	 * @post
	 * If you accepted, you and the player who offered swap the specified resources
	 * If you declined no resources are exchanged
	 * The trade offer is removed
	 * 
	 * "type": "acceptTrade"
	 * public void acceptTrade(willAccept){}
	 */
	public String acceptTrade(Integer playerIndex, Boolean willAccept); 

	/**
	 * Miscellaneous Commands
	 * Properties: discardedCards: ResourceHand [the cards you are discarding]
	 * @pre
	 * The status of the client model is 'Discarding'
	 * You have over 7 cards
	 * You have the cards you're choosing to discard.
	 * @post
	 * You gave up the specified resources
	 * If you're the last one to discard, the client model status changes to 'Robbing'
	 * 
	 * {
	 *   "type": "discardCards",
	 *   "playerIndex": "integer",
	 *   "discardedCards": {
	 *     "brick": "integer",
	 *     "ore": "integer",
	 *     "sheep": "integer",
	 *     "wheat": "integer",
	 *     "wood": "integer"
	 *   }
	 * }
	 * public void discardCards(discardedCards){}
	 */
	public String discardCards(Integer playerIndex, ArrayList discardedCards); 
	
	/**
	 * ?
	 * â€˜Rollingâ€™ Commands
	 * Properties: number: integer in the range 2Â­12 [the number you rolled]
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is â€˜Rollingâ€™
	 * @post
	 * The client modelâ€™s status is now in â€˜Discardingâ€™ or â€˜Robbingâ€™ or â€˜Playingâ€™
	 * 
	 * "type": "rollNumber
	 * public void rollNumber(number){}
	 */
	public String rollNumber(Integer playerIndex, Integer number); 

	/**
	 * â€˜Playingâ€™ Commands
	 * Properties:
	 * free: boolean [whether or not you get this piece for free (i.e., in setup)]
	 * roadLocation: EdgeLocation [the new roadâ€™s location]
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is 'Playing'
	 * The road location is open
	 * The road location is connected to another road owned by the player
	 * The road location is not on water
	 * You have the required resources (1 wood, 1 brick, 1 road)
	 * Setup round: Must be placed by settlement owned by the player with no adjacent
	 * road
	 * @post
	 * You lost the resources required to build a road (1 wood, 1 brick, 1 road)
	 * The road is on the map at the specified location
	 * If applicable, longest road has been awarded to the player with the longest road
	 * 
	 * {
	 *   "type": "buildRoad",
	 *   "playerIndex": "integer",
	 *   "roadLocation": {
	 *     "x": "integer",
	 *     "y": "integer",
	 *     "direction": "string"
	 *   },
	 *   "free": "Boolean"
	 * }
	 * public void buildRoad(free, roadLocation){}
	 */
	public String buildRoad(Integer playerIndex, EdgeLocation roadLocation, Boolean free); 

	/**
	 * Playing Commands
	 * Properties:
	 * free: boolean [whether or not you get this piece for free (i.e. in setup)]
	 * vertexLocation: VertexLocation [the location of the settlement]
	 * @pre
	 * It is your turn
	 * The client model's status is 'Playing'
	 * The settlement location is open
	 * The settlement location is not on water
	 * The settlement location is connected to one of your roads except during setup
	 * You have the required resources (1 wood, 1 brick, 1 wheat, 1 sheep, 1 settlement)
	 * The settlement cannot be placed adjacent to another settlement
	 * @post
	 * You lost the resources required to build a settlement (1 wood, 1 brick, 1 wheat, 1
	 * sheep, 1 settlement)
	 * 	The settlement is on the map at the specified location
	 * 	
	 * 	"type": "buildSettlement",
	 *   "playerIndex": "integer",
	 *   "vertexLocation": {
	 *     "x": "integer",
	 *     "y": "integer",
	 *     "direction": "string"
	 *   },
	 *   "free": "Boolean"
	 * }
	 * 	public void buildSettlement(free, vertexLocation){}
	*/
	public String buildSettlement(Integer playerIndex, VertexLocation vertexLocation, Boolean free);

	/**
	 * Playing Commands
	 * Properties: 
	 * vertexLocation: VertexLocation [the location of the city]
	 * @pre
	 * It is your turn
	 * The client model's status is 'Playing'
	 * The city location is where you currently have a settlement
	 * You have the required resources (2 wheat, 3 ore, 1 city)
	 * @post
	 * You lost the resources required to build a city (2 wheat, 3 ore, 1 city)
	 * The city is on the map at the specified location
	 * You got a settlement back
	 * 
	 * {
	 *   "type": "buildCity",
	 *   "playerIndex": "integer",
	 *   "vertexLocation": {
	 *     "x": "integer",
	 *     "y": "integer",
	 *     "direction": "string"
	 *   }
	 * }
	 * public void buildCity(vertexLocation){}
	 */
	public String buildCity(Integer playerIndex, VertexLocation vertexLocation);

	/**
	 * â€˜Playingâ€™ Commands
	 * Properties:
	 * of er: ResourceHand [negative numbers mean you get those cards]
	 * receiver: playerIndex [the recipient of the trade offer]
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is 'Playing'
	 * You have the resources you are offering
	 * @post
	 * The trade is offered to the other player (stored in the server model)
	 * 
	 * {
	 *   "type": "offerTrade",
	 *   "playerIndex": "integer",
	 *   "offer": {
	 *     "brick": "integer",
	 *     "ore": "integer",
	 *     "sheep": "integer",
	 *     "wheat": "integer",
	 *     "wood": "integer"
	 *   },
	 *   "receiver": "integer"
	 * }
	 * public void offerTrade(of_er, receiver){}
	 */
	public String offerTrade(Integer playerIndex, ArrayList offer, Integer receiver);

	/**
	 * â€˜Playingâ€™ Commands
	 * Properties:
	 * ratio: integer [2,3, or 4]
	 * inputResource: Resource [what you are giving]
	 * outputResource: Resource [what you are getting]
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is 'Playing'
	 * You have the resources you are giving
	 * For ratios less than 4, you have the correct port for the trade
	 * @post
	 * The trade has been executed (the offered resources are in the bank, and the
	 * requested resource has been received)
	 * 
	 * "type": "maritimeTrade",
	 * public void maritimeTrade(ratio, inputResource, outputResource){}
	 */
	public String maritimeTrade(Integer playerIndex, Integer ratio, String inputResource, String outputResource);

	/**
	 * â€˜Playingâ€™ Commands
	 * Properties:
	 * location: HexLocation [the new robber location]
	 * victimIndex: playerIndex, or Â­1 if you are not robbing anyone [the player you are
	 * robbing]
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is 'Playing'
	 * The robber is not being kept in the same location
	 * If a player is being robbed (i.e., victimIndex != Â­1), the player being robbed has resource cards
	 * @post
	 * The robber is in the new location
	 * The player being robbed (if any) gave you one of his resource cards (randomly
	 * selected)
	 * 
	 * "type": "robPlayer",
	 * "location": {
	 * "x": "string",
	 * "y": "string" 
	 * }
	 * public void robPlayer(location, victimIndex){}
	 */
	public String robPlayer(Integer playerIndex, Integer victimIndex, HexLocation location);

	/**
	 * â€˜Playingâ€™ Commands
	 * Properties: None (except the universal properties)
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is 'Playing'
	 * @post
	 * The cards in your new dev card hand have been transferred to your old dev card
	 * hand
	 * It is the next playerâ€™s turn
	 * 
	 * "type": "finishTurn"
	 * public void finishTurn(){}
	 */
	public String finishTurn(Integer playerIndex); 

	/**
	 * â€˜Playingâ€™ Commands
	 * Properties: None (except the universal properties)
	 * @pre
	 * It is your turn
	 * The client modelâ€™s status is 'Playing'
	 * You have the required resources (1 ore, 1 wheat, 1 sheep)
	 * There are dev cards left in the deck
	 * @post
	 * You have a new card
	 * If it is a monument card, it has been added to your old devcard hand
	 * If it is a nonÂ­monument card, it has been added to your new devcard hand
	 * (unplayable this turn)
	 * 
	 * "type": "buyDevCard"
	 * public void buyDevCard(){}
	 */
	public String buyDevCard(Integer playerIndex);

	/**
	 * Dev Card Commands
	 * Properties:
	 * location: HexLocation [the new robber location]
	 * victimIndex: playerIndex, or Â­1 if you are not robbing anyone [the player you are
	 * robbing]
	 * @pre
	 * It is your turn
	 * The client model status is 'Playing'
	 * You have the specific card you want to play in your old dev card hand
	 * You have not yet played a nonÂ­monument dev card this turn
	 * The robber is not being kept in the same location
	 * If a player is being robbed (i.e., victimIndex != Â­1), the player being robbed has
	 * resource cards
	 * @post
	 * The robber is in the new location
	 * The player being robbed (if any) gave you one of his resource cards (randomly
	 * selected)
	 * If applicable, â€œlargest armyâ€� has been awarded to the player who has played the
	 * most soldier cards
	 * You are not allowed to play other development cards during this turn (except for
	 * monument cards, which may still be played)
	 * 
	 * "type": "Soldier"
	 * "location":{
	 * "x": string,
	 * "y": string
	 * }
	 * public void Soldier(location, victimIndex){}
	 */
	public String Soldier(Integer playerIndex, Integer victimIndex, HexLocation location);

	/**
	 * Dev Card Commands
	 * Properties:
	 * resource1: Resource [the first resource you want to receive]
	 * resource2: Resource [the second resource you want to receive]
	 * @pre
	 * It is your turn
	 * The client model status is 'Playing'
	 * You have the specific card you want to play in your old dev card hand
	 * You have not yet played a nonÂ­monument dev card this turn
	 * The two specified resources are in the bank
	 * @post
	 * You gained the two specified resources
	 * 
	 * "type": "Year_of_Plenty"
	 * public void Year_of_Plenty(resource1, resource2){}
	 */
	public String Year_of_Plenty(Integer playerIndex, Resource resource1, Resource resource2);

	/**
	 * Dev Card Commands
	 * Properties:
	 * spot1: EdgeLocation
	 * spot2: EdgeLocation
	 * @pre
	 * It is your turn
	 * The client model status is 'Playing'
	 * You have the specific card you want to play in your old dev card hand
	 * You have not yet played a nonÂ­monument dev card this turn
	 * The first road location (spot1) is connected to one of your roads.
	 * The second road location (spot2) is connected to one of your roads or to the first
	 * road location (spot1)
	 * Neither road location is on water
	 * You have at least two unused roads
	 * @post
	 * You have two fewer unused roads
	 * Two new roads appear on the map at the specified locations
	 * If applicable, â€œlongest roadâ€� has been awarded to the player with the longest road
	 * 
	 * "type": "Road_Building"
	 * "spot1": {
     * "x": "integer",
     * "y": "integer",
     * "direction": "string"
  	 * }
	 * public void Road_Building(spot1, spot2){}
	 */
	public String Road_Building(Integer playerIndex, EdgeLocation spot1, EdgeLocation spot2); 

	/**
	 * Dev Card Commands
	 * Properties:
	 * resource: Resource [the resource being taken from the other players]
	 * @pre
	 * It is your turn
	 * The client model status is 'Playing'
	 * You have the specific card you want to play in your old dev card hand
	 * You have not yet played a nonÂ­monument dev card this turn
	 * @post
	 * All of the other players have given you all of their resource cards of the specified
	 * type
	 * 
	 * "type": "Monopoly"
	 * public void Monopoly(resource){}
	 */
	public String Monopoly(String resource, Integer playerIndex); 

	/**
	 * Dev Card Commands
	 * Properties: None (except the universal properties)
	 * @pre
	 * It is your turn
	 * The client model status is 'Playing'
	 * You have the specific card you want to play in your old dev card hand
	 * You have not yet played a nonÂ­monument dev card this turn
	 * You have enough monument cards to win the game (i.e., reach 10 victory points)
	 * @post
	 * You gained a victory point
	 * 
	 * public void Monument(){}
	 * 
	 * {
  	 * "type": "Monument",
  	 * "playerIndex": "4"
	 * }
	 */
	public String Monument(Integer playerIndex);

 
}
