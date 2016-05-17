package states;

import client.login.LoginController;

//HOW THIS WORKS
//In controller, create SINGLETON of class 
//		public static final LoginController SINGLETON = new LoginController();
//Create private State and input holder for method
//		private State state;
//		private String input;
//In constructor, set State to a state
//		state = State.JOIN;
//In controller, call method
//		this.input = input;
//		state.processInput(this); // this passes in the singleton, I think
//In this file, create method for each state. If your method does nothing for a state, create empty method
//If you try this and it doesn't work, make a note on how you fixed it

public enum State {
	LOGIN {
		@Override
		public void processInput(LoginController parser) throws Exception{
//			if(parser.hasNoInput()) {
//				throw new Exception("No Input OR input not a +, -, or digit");
//			} else {
//				char c = parser.getNextCharacter();
//				if(c == '+'){
//					parser.setState(SawASign); //Different
//				} else if(c == '-') {
//					parser.makeNegative();
//					parser.setState(SawASign); //Different
//				} else if(c >= '0' && c <= '9') {
//					parser.appendDigit(c);
//					parser.setState(SawADigit); //Different
//				} else {
//					throw new Exception("No Input OR input not a +, -, or digit");
//				}
//			}
		}
	},
	
	JOIN {
		@Override
		public void processInput(LoginController parser) throws Exception {
//			if(parser.hasNoInput()) {
//				throw new Exception("No Input OR input not a digit");
//			} else {
//				char c = parser.getNextCharacter();
//				if(c >= '0' && c <= '9') {
//					parser.appendDigit(c);
//					parser.setState(SawADigit); //Different
//				} else {
//					throw new Exception("No Input OR input not a digit");
//				}
//			}
		}
	},
	
	WAIT {
		@Override
		public void processInput(LoginController parser) throws Exception {
//			if(parser.hasNoInput()) {
//				if(parser.isNegative()) {
//					parser.negateResult();
//				}
//				parser.setState(End); //Different
//			} else {
//				char c = parser.getNextCharacter();
//				if(c >= '0' && c <= '9') {
//					parser.appendDigit(c);
//				} else {
//					throw new Exception("Input not a digit");
//				}
//			}
		}
	},
	
	SETUP {
		@Override
		public void processInput(LoginController parser) throws Exception {
//			if(parser.hasNoInput()) {
//				if(parser.isNegative()) {
//					parser.negateResult();
//				}
//				parser.setState(End); //Different
//			} else {
//				char c = parser.getNextCharacter();
//				if(c >= '0' && c <= '9') {
//					parser.appendDigit(c);
//				} else {
//					throw new Exception("Input not a digit");
//				}
//			}
		}
	},
	
	PLAY {
		@Override
		public void processInput(LoginController parser) throws Exception {
//			if(parser.hasNoInput()) {
//				if(parser.isNegative()) {
//					parser.negateResult();
//				}
//				parser.setState(End); //Different
//			} else {
//				char c = parser.getNextCharacter();
//				if(c >= '0' && c <= '9') {
//					parser.appendDigit(c);
//				} else {
//					throw new Exception("Input not a digit");
//				}
//			}
		}
	},
	
	End;

	public void processInput(LoginController parser) throws Exception{};
}



//public class State {
//
//	public static StateEnum currentState = StateEnum.LOGIN; 
//	
//	private static State instance = null;
//	protected State() {
//		// Exists only to defeat instantiation.
//	}
//	public static State getInstance() {
//		if(instance == null) {
//			instance = new State();
//		}
//		return instance;
//	}
//	public static void setCurrentState(StateEnum s){
//		currentState = s; 
//	}
//	public static StateEnum getCurrentState(){
//		return currentState;  
//	}
//}
