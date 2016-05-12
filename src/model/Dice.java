package model;
import java.util.Random;

import poller.modeljsonparser.AbstractModelPartition;
/**
 * @author Jesse McArthur
 */

public class Dice extends AbstractModelPartition {
  
  int diceRoll;//2-12
  
  
  
  public void Dice(){
	  diceRoll = 1;
  }
  
  /**
   * @pre 	Starts with a blank die
   * @post chooses a random number
   * Returns a random number from 2-12
   */
  public int rollDice()
  {
    Random r = new Random();
    int n = r.nextInt(2 - 12) + 3;
    return n;
  }
  public boolean canRoll(int playerID)
  { 
    return false;
  }


}
