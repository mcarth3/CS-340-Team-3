package client.roll;

import client.base.*;
import states.State;
import states.StateEnum;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
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
		StateEnum theState = State.getCurrentState();
		if(theState == StateEnum.PLAY)
		{

		}


		getResultView().showModal();


	}


	public void update(){
		//timer, check if rolling
	}

}

