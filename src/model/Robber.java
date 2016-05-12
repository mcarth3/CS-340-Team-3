package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.locations.HexLocation;
/**Robber is a game piece contained in the game. Affects players on each turn. 
 * @author Jesse McArthur
 */
public class Robber extends AbstractModelPartition {
    HexLocation hl;

    public Robber() {
    	hl = new HexLocation(0, 0);
    }

    

    /**
     * @param h - HexLocation - find if robber is in a certain hex
     * @return true/false
     */
    public Boolean isLocated(HexLocation h) {
        return false;
    }

    public HexLocation getHl() {
        return hl;
    }

    public void setHl(HexLocation hl) {
        this.hl = hl;
    }

}

