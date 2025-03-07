package client.roll;

import java.util.Timer;
import java.util.TimerTask;

import client.base.Controller;
import model.Facade;
import model.Player;
import states.State;
import states.StateEnum;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	//Singleton
	public static RollController SINGLETON = null;

	private boolean shownResult;
	private Facade theFacade;
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
		shownResult = false;

		setResultView(resultView);
	}

	public IRollResultView getResultView() {
		return resultView;
	}

	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView) getView();
	}

	@Override
	public void rollDice() {
		shownResult = true;
		getRollView().setMessage("3 seconds...");
		System.out.println("in the rollDice function!");
		StateEnum theState = State.getCurrentState();

		Player thePlayer = thisplayer;
		int index = thePlayer.getPlayerIndex();
		if (model.canRoll(index)) {
			System.out.println("can roll");
			int currentRoll = model.rollGameDice();

			//COPIED TO THE ROLLRESULTVIEW
			//			theFacade.rollThisInt(thePlayer.getPlayerIndex(), currentRoll);

			//System.out.println("Current roll: " + currentRoll);
			resultView.setRollValue(currentRoll);
			setResultView(resultView);
			resultView.showModal();
			//getResultView().showModal()
		} else {
			System.out.println("cannot roll");
		}

	}

	private int counter;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	private boolean chose;

	@Override
	public void update() {
		//make this the only uncommented part of the method in order to auto roll
//		if(thisplayer.getPlayerIndex() == currentplayer){	
//			if (state.equals("Rolling")){
//				RealProxy.getSingleton().rollNumber(thisplayer.getPlayerIndex(), 5);
//			}
//		}

		//System.out.println("You're in the RollController update()!!!");

		theFacade = manager.getModelfacade();
		//if(theFacade.getGame()/*.getTurnTracker().getStatus()*/!= null) {
		if (model.getTurnTracker()/*.getStatus()*/ != null) {

			System.out.println("thread " + Thread.currentThread().getId() + "- Status by RollController update(): " + state);
			if (state.equals("Rolling")) {
				System.out.println("Rolling!!");
				//if(thisplayer.getPlayerID() == theGame.getTurnTracker().getCurrentPlayer()) {
				//System.out.println(currentplayer);
				//System.out.println(thisplayer.getPlayerIndex());

				if (currentplayer == thisplayer.getPlayerIndex()) {

					System.out.println("It's the player!");
					//UNCOMMENT BELOW:

					if (!getRollView().isModalShowing()) {
						System.out.println("not showin");
						setTimer();
						getRollView().showModal();
						counter = 3;
						shownResult = false;
					} else {
						System.out.println("thread " + Thread.currentThread().getId() + "- FAILED TO ROLL: getRollView().isModalShowing()");
					}
					/**
						* REMOVE BELOW STATEMENT
						
						int pid = thisplayer.getPlayerID();
						//theFacade.roll(pid);
						RealProxy.getSingleton().rollNumber(pid, 4);*/
				} else {
					System.out.println("thread " + Thread.currentThread().getId() + "- FAILED TO ROLL: currentplayer != thisplayer.getPlayerIndex()");
				}
			} else {
				System.out.println("thread " + Thread.currentThread().getId() + "- FAILED TO ROLL: state != rolling");
			}
		} else {
			System.out.println("thread " + Thread.currentThread().getId() + "- FAILED TO ROLL: turn tracker == null");
		}
	}

	public void setTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!shownResult) {
					getRollView().setMessage("3 seconds...");
					//System.out.println("Rolling in 3...");
				}
			}
		}, 1000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!shownResult) {
					getRollView().setMessage("2 seconds...");
					//System.out.println("Rolling in 2...");
				}

			}
		}, 2000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!shownResult) {
					getRollView().setMessage("1 seconds...");
					//System.out.println("Rolling in 1...");
				}

			}
		}, 3000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//System.out.println("calling rollDice()!!!!");
				if (getRollView().isModalShowing()) {
					getRollView().closeModal();
				}
				if (!shownResult) {
					rollDice();
				}

			}
		}, 4000);
	}

}
