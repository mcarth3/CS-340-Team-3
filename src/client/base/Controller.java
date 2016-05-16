package client.base;

import java.util.Observable;
import java.util.Observer;

import controllers.GameManager;
import model.*;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController, Observer
{
	
	private IView view;
	private GameManager manager = GameManager.getSingleton();
	
	protected Controller(IView view)
	{
		manager.addObserver(this);
		setView(view);
	}
	
	private void setView(IView view)
	{
		this.view = view;
	}
	
	@Override
	public IView getView()
	{
		return this.view;
	}
	
	public void UpdateModel(Game currentmodel){
		
	}

	public void update(Observable arg0, Object arg1) {
		update();
	}
	public void update() {
	}
}

