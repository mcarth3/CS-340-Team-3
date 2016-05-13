package states;

public class State {
	
		public static StateEnum currentState = StateEnum.LOGIN; 
		
		private static State instance = null;
		protected State() {
			// Exists only to defeat instantiation.
		}
		public static State getInstance() {
			if(instance == null) {
				instance = new State();
			}
			return instance;
		}
		public static void setCurrentState(StateEnum s){
			currentState = s; 
		}
		public static StateEnum getCurrentState(){
			return currentState;  
		}
}
