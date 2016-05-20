package client.roll;

import client.GameManager.GameManager;
import client.base.*;
import model.Facade;
import model.Game;
import model.Player;
import model.TurnTracker;
import proxy.RealProxy;
import states.State;
import states.StateEnum;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	//Singleton
	public static RollController SINGLETON = null;
	private State state;

	private Facade theFacade;
	private Game theGame;
	private String input;

	private IRollResultView resultView;
	//public static final RollController SINGLETON = new RollController();

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {
		super(view);
		//Singleton:
		//state = State.PLAY;
		//SINGLETON = new RollController(view, resultView);
		//Singleton^^^^^^^^^^



		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {

		System.out.println("in the rollDice function!");
		StateEnum theState = State.getCurrentState();
		/*if(theState == StateEnum.PLAY)
		{*/
			System.out.println("State is play!");
			Player thePlayer = GameManager.getSingleton().getthisplayer();
			int pid = thePlayer.getPlayerID();
			if(theGame.canRoll(pid)) {
			int currentRoll = theFacade.roll(pid);
				System.out.println("Current roll: " + currentRoll);
			resultView.setRollValue(currentRoll);
				setResultView(resultView);
				resultView.showModal();
				System.out.println("I set resultView!");


			/*}*/
		}


		getResultView().showModal();


	}

private int counter;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	private boolean chose;

	public void update(){
	System.out.println("You're in the RollController update()!!!");
		GameManager.getSingleton();
		theFacade = GameManager.getSingleton().getModelfacade();
		theGame = GameManager.getSingleton().getModel();
		//if(theFacade.getGame()/*.getTurnTracker().getStatus()*/!= null) {
		if(theGame.getTurnTracker()/*.getStatus()*/!= null) {

			System.out.println("Status by RollController update(): " + theGame.getTurnTracker().getStatus());
			if (theGame.getTurnTracker().getStatus().equals("Rolling")) {
				System.out.println("Rolling!!");
				//if(GameManager.getSingleton().getthisplayer().getPlayerID() == theGame.getTurnTracker().getCurrentPlayer()) {
				System.out.println(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer());
				System.out.println(GameManager.getSingleton().getthisplayer().getPlayerIndex());

				if(GameManager.getSingleton().getModel().getTurnTracker().getCurrentPlayer() == GameManager.getSingleton().getthisplayer().getPlayerIndex()) {

					System.out.println("It's the player!");
					//UNCOMMENT BELOW:
					//setTimer();
					//getRollView().showModal();
					//counter = 3;
					/**
					 * REMOVE BELOW STATEMENT
					 */
					int pid = GameManager.getSingleton().getthisplayer().getPlayerID();
					//theFacade.roll(pid);
					RealProxy.getSingleton().rollNumber(pid, 4);
				}
			}
		}
	}

	public void setTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				getRollView().setMessage("3 seconds...");
				System.out.println("Rolling in 3...");
			}
		}, 1000);


		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				getRollView().setMessage("2 seconds...");
				System.out.println("Rolling in 2...");
			}
		}, 2000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				getRollView().setMessage("1 seconds...");
				System.out.println("Rolling in 1...");
			}
		}, 3000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("calling rollDice()!!!!");
				getRollView().closeModal();
				rollDice();

			}
		}, 4000);
	}




}
