package client.roll;

import client.GameManager.GameManager;
import client.base.*;
import model.Facade;
import model.Player;
import model.TurnTracker;
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

		theFacade = Facade.getFacade();

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


		StateEnum theState = State.getCurrentState();
		if(theState == StateEnum.PLAY)
		{
			Player thePlayer = GameManager.getSingleton().getthisplayer();
			int pid = thePlayer.getPlayerID();
			if(theFacade.canRoll(pid)) {
			int currentRoll = theFacade.roll(pid);
			resultView.setRollValue(currentRoll);
				resultView.showModal();

			}
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
		if(GameManager.getSingleton() != null && theFacade.getGame() != null
				&& theFacade.getGame().getTurnTracker() != null && theFacade.getGame().getTurnTracker().getStatus()!= null) {
			System.out.println("Status by RollController update(): " + theFacade.getGame().getTurnTracker().getStatus());
			if (theFacade.getGame().getTurnTracker().getStatus().equals("ROLL")) {
				setTimer();
				getRollView().showModal();
				counter = 3;

			}
		}
	}

	public void setTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				getRollView().setMessage("3 seconds...");

			}
		}, 1000);


		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				getRollView().setMessage("2 seconds...");

			}
		}, 2000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				getRollView().setMessage("1 seconds...");

			}
		}, 3000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				rollDice();

			}
		}, 4000);
	}




}

