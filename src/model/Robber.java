package model;

import shared.locations.HexLocation;
/**Robber is a game piece contained in the game. Affects players on each turn. 
 * @author Jesse McArthur
 */
public class Robber {
    HexLocation hl;

    public Robber() {
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

