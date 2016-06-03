package client.base;

import java.util.Observable;
import java.util.Observer;

import client.GameManager.GameManager;
import model.Game;
import model.Player;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController, Observer {

	private IView view;
	protected GameManager manager = GameManager.getSingleton();;
	protected Player thisplayer;
	protected Game model;
	protected String state;
	protected int currentplayer;

	protected Controller(IView view) {
		manager = GameManager.getSingleton();
		if (manager.getthisplayer() != null) {
			thisplayer = manager.getthisplayer();
		}
		if (manager.getModel() != null) {
			model = manager.getModel();
			if (model.getTurnTracker() != null) {
				state = model.getTurnTracker().getStatus();
				currentplayer = model.getTurnTracker().getCurrentPlayer();
			}
		}
		manager.addObserver(this);
		setView(view);
	}

	private void setView(IView view) {
		this.view = view;
	}

	@Override
	public IView getView() {
		return this.view;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		manager = GameManager.getSingleton();
		if (manager.getthisplayer() != null) {
			thisplayer = manager.getthisplayer();
		}
		if (manager.getModel() != null) {
			model = manager.getModel();
			if (model.getTurnTracker() != null) {
				state = model.getTurnTracker().getStatus();
				currentplayer = model.getTurnTracker().getCurrentPlayer();
			}
		}
		update();
	}

	public void update() {

	}
}
