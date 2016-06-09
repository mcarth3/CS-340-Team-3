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
	public void addCommand(ICommand command){
		commands.add(command); 
		//System.out.println(command.toString()); 
		//System.out.println("command added. size: "+getListSize()); 
		if(getListSize() >= cutoff){
			//System.out.println("Command list needs to be emptied and model saved"); 
			commands.clear();
		}
	}
	public Integer getListSize(){
		return commands.size();
	}
	
}
