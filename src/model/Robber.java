package model;

import shared.locations.HexLocation;
/**Robber is a game piece contained in the game. Affects players on each turn. 
 * @author Jesse McArthur
 */
public class Robber {
    HexLocation h;

    public Robber() {
    }

    

    /**
     * @pre Look for robber
     * @post robber found
     * @param h - HexLocation - find if robber is in a certain hex
     * @return true/false
     */
    public Boolean isLocated(HexLocation h) {
        return false;
    }

    public HexLocation getH() {
        return h;
    }

    public void setH(HexLocation h) {
        this.h = h;
    }

  

}
