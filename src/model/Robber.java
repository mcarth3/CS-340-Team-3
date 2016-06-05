package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.locations.HexLocation;

/**Robber is a game piece contained in the game. Affects players on each turn. 
 * @author Jesse McArthur
 */
public class Robber extends AbstractModelPartition {
	int x;
	int y;

	public Robber() {
		x = 0;
		y = 0;
	}

	public Robber(HexLocation newh1) {
		x = newh1.getX();
		y = newh1.getX();
	}

	/**
	 * @param h - HexLocation - find if robber is in a certain hex
	 * @return true/false
	 */
	public Boolean isLocated(HexLocation h) {
		return false;
	}

	public HexLocation getHl() {
		HexLocation hl = new HexLocation(x, y);
		return hl;
	}

	public void setHl(HexLocation hl) {
		x = hl.getX();
		y = hl.getY();
	}

}
