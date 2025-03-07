package client.join;

import java.util.ArrayList;

import client.base.IOverlayView;
import model.Player;

/**
 * Interface for the player waiting view, which is displayed when the user is
 * waiting for other players to join their game
 */
public interface IPlayerWaitingView extends IOverlayView {

	/**
	 * Sets the list of players who have already joined the game
	 * 
	 * @param arrayList
	 *            List of players who have already joined the game
	 */
	void setPlayers(ArrayList<Player> arrayList);

	/**
	 * Sets the list of AI types from which the user may select
	 * 
	 * @param value
	 *            List of AI types from which the user may select
	 */
	void setAIChoices(String[] value);

	/**
	 * Returns the type of AI selected by the user
	 * 
	 * @return The type of AI selected by the user
	 */
	String getSelectedAI();
}
