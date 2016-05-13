package client.base;

import java.util.Observable;

import model.*;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController
{
	
	private IView view;
	
	protected Controller(IView view)
	{
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
		// TODO Auto-generated method stub
		
	}
}

