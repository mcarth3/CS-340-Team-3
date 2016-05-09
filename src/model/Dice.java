package model;
import java.util.Random;
/**
 * @author Jesse McArthur
 */

public class Dice {
  
  int diceRoll;//2-12
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
