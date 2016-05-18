package client.roll;

import client.GameManager.GameManager;
import client.base.*;
import model.Facade;
import model.Player;
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

		SINGLETON = new RollController(view, resultView);
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


		//if(it's the Right State)
		/*
		{

		//TIER: getRollView.isModalShowing()
		//if(State.rolling
		//getRollwView.SetMessage

			resultView.setRollValue(Facade.RollDice());
			//tell gameManager to update()
			//timer on dice
		}


		 */
		/*StateEnum theState = State.getCurrentState();
		if(theState == StateEnum.PLAY)
		{
			Player thePlayer = GameManager.getSingleton().getthisplayer();
			int pid = thePlayer.getPlayerID();
			if(theFacade.canRoll(pid)) {
			int currentRoll = theFacade.roll(pid);
			resultView.setRollValue(currentRoll);
				resultView.showModal();

			}
		}*/


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

		/*if(State.getCurrentState() == StateEnum.PLAY && getRollView().isModalShowing()) {
			setTimer();

		}*/
	}

	public void setTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				if(counter > 0) {
					getRollView().setMessage(counter + " seconds...");
					counter--;
					if(counter == 0)
					{
						rollDice();
						counter = 3;
					}
					else
					{
						setTimer();
					}
				}
				else {
					counter = 3;
				}
			}
		}, 1000);

	}




}

