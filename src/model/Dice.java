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

    Random rand = new Random();

    int  n = rand.nextInt(6) + 1;
    //System.out.println("n result: "+ n);

    int  n2 = rand.nextInt(6) + 1;
    //System.out.println("n2 result: "+ n2);

    diceRoll = n + n2;
    return diceRoll;
  }
  public boolean canRoll(int playerID)
  { 
    return false;
  }

  public int getDiceRoll() {
    return diceRoll;
  }

  public void setDiceRoll(int diceRoll) {
    this.diceRoll = diceRoll;
  }
}
