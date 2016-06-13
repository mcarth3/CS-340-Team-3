package server;

import java.util.ArrayList;

public class CommandList {

	private static CommandList singleton = null;
	private ArrayList<ICommand> commands;
	private Integer cutoff = 5; 
	
	
	public static CommandList getSingleton() {
		if (singleton == null) {
			singleton = new CommandList();
		}
		return singleton;
	}
	public CommandList(){
		//System.out.println("new command list is created");
		commands = new ArrayList<ICommand>(); 
	}
	
	/**
	 * 
	 * @param command
	 * @pre it takes in a command
	 * @post takes a comand and adds it to the list of commands. 
	 * it also saves the command to the database/file
	 * then checks the size of the list and if the size of the list is above or equal
	 * the set cutoff, a method is called in the server facade that saves the model.
	 */
	public void addCommand(ICommand command){
		commands.add(command); 
		
		if(getListSize() >= cutoff){
			//System.out.println("Command list needs to be emptied and model saved"); 
			commands.clear();
		}
	}
	
	/**
	 * 
	 * @pre none, there is already a list created
	 * @post executes all the saved commands in the list and deletes the list
	 */
	public void executeAll(){
		
	}
	
	/**
	 * 
	 * @pre none, there is already a list created
	 * @post gets and returns the size of the command list
	 */
	public Integer getListSize(){
		return commands.size();
	}
	
	/**
	 * 
	 * @pre takes in a valid command
	 * @post the command is saved to the database
	 */
	public void saveCommandToDB(ICommand command){
		
	}
	
	/**
	 * 
	 * @pre takes in a valid command
	 * @post the commands are deleted from the database
	 */
	public void deleteCommandsinDB(ICommand command){
		
	}
	
	
}
