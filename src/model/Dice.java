package model;
import java.util.Random;

import poller.modeljsonparser.AbstractModelPartition;
/**
 * @author Jesse McArthur
 */

public class Dice extends AbstractModelPartition {
  
  int diceRoll;//2-12
  
  
  
  public Dice(){
	  diceRoll = 1;
  }
  public Dice(int newdiceRoll){
	  diceRoll = newdiceRoll;
  }
  
  /**
   * @pre 	Starts with a blank die
   * @post chooses a random number
   * Returns a random number from 2-12
   * CHANGES DICEROLL TO RETURNED VALUE
   */
  public int rollDice()
  {
    Random r = new Random();
    int n = r.nextInt(2 - 12) + 3;
    diceRoll = n;
    return n;
  }
  public boolean canRoll(int playerID)
  { 
    return false;
  }


}
