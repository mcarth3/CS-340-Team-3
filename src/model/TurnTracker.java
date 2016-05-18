package model;

import poller.modeljsonparser.AbstractModelPartition;

/**
 * @author Jesse McArthur
 */
public class TurnTracker extends AbstractModelPartition {
    String status;
	int currentTurn;
    int longestRoad = -1;
    int largestArmy = -1;
    //must be at least 3 to claim largest army, therefore if player army >  currLA they earn largest army and currLA = player army size

    public TurnTracker() {
    	currentTurn = 0;
        status = "default";
    }

    public TurnTracker(int currentP, String stat,int newlongestroad, int newlargestarmy) {
    	currentTurn = currentP;
        status = stat;
        longestRoad = newlongestroad;
        largestArmy = newlargestarmy;
    }
    public TurnTracker(int currentP, String stat) {
    	currentTurn = currentP;
        status = stat;
    }

    /**
     * Gets the id of the current player
     */
    public int getCurrentPlayer() {
        return currentTurn;
    }

    /**
     * Sets the id of the current player
     */
    public void setCurrentPlayer(int pid) {
    	currentTurn = pid;
    }

    /**
     * Updates status of the current player.
     * If the player is in the last part of their turn, when called, this method moves on to the next player
     * and resets the status to zero.
     */
    public void updateStatus(String givenStatus) {
        status = givenStatus;
    }

    /**
     * returns the current status of the current players game
     * 0 = ROLL
     * 1 = TRADE
     * 2 = BUILD
     * 3 = FINISH
     */
    public String getStatus() {
        return status;
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public void setLongestRoad(int road) {
        longestRoad = road;
    }

    public int getLargestArmy() {
        return largestArmy;
    }

    public void setLargestArmy(int army) {
        largestArmy = army;
    }

    /**
     * @param pid - player id of player wishing to try for largest army
     *            might return bool in future, not a necessity at the moment though
     */
    public void calcLargestArmy(int pid) {
    }

    /**
     * @param pid = player id of player wishing to try for longest road
     *            again might return bool in future, not a necessity at the moment
     */
    public void calcLongestRoad(int pid) {
    }

}
