package model.bank;
/**
 * @author Jesse McArthur
 */

import javax.annotation.Resource;

import poller.modeljsonparser.AbstractModelPartition;

public class ResourceList extends AbstractModelPartition {

    int numOfBrick;
    int numOfOre;
    int numOfSheep;
    int numOfWheat;
    int numOfWood;

    public ResourceList() {
        numOfBrick = 0;
        numOfOre = 0;
        numOfSheep = 0;
        numOfWheat = 0;
        numOfWood = 0;
    }

    public ResourceList(int given) {
        if(given > 20 || given < 0){
            given = 19;
        }
        numOfBrick = given;
        numOfOre = given;
        numOfSheep = given;
        numOfWheat = given;
        numOfWood = given;
    }

    public ResourceList(int br, int or, int sh, int wh, int wo) {
        numOfBrick = br;
        numOfOre = or;
        numOfSheep = sh;
        numOfWheat = wh;
        numOfWood = wo;
    }
    //copy constructor
    public ResourceList(ResourceList resourcesToCopy)
    {
       numOfBrick = resourcesToCopy.getBrick();
       numOfOre = resourcesToCopy.getOre();
       numOfSheep = resourcesToCopy.getSheep();
       numOfWheat = resourcesToCopy.getWheat();
      numOfWood = resourcesToCopy.getWood();
    }
    //overload the constructor so that RL can be used for trades, bank, map, players etc.

    public int getBrick() {
        return numOfBrick;
    }

    public int getOre() {
        return numOfOre;
    }

    public int getSheep() {
        return numOfSheep;
    }

    public int getWheat() {
        return numOfWheat;
    }

    public int getWood() {
        return numOfWood;
    }

    public void setBrick(int brick) {
        numOfBrick = brick;
    }

    public void setOre(int ore) {
        numOfOre = ore;
    }

    public void setSheep(int sheep) {
        numOfSheep = sheep;
    }

    public void setWheat(int wheat) {
        numOfWheat = wheat;
    }

    public void setWood(int wood) {
        numOfWood = wood;
    }

    public ResourceList merge(ResourceList first, ResourceList second) {
        int newbrick = first.getBrick() + second.getBrick();
        int newore = first.getOre() + second.getOre();
        int newsheep = first.getSheep() + second.getSheep();
        int newwheat = first.getWheat() + second.getWheat();
        int newwood = first.getWood() + second.getWood();

        ResourceList result = new ResourceList(newbrick, newore, newsheep, newwheat, newwood);
        return result;
    }
}
