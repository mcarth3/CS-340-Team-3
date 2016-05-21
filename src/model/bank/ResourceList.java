package model.bank;
/**
 * @author Jesse McArthur
 */

import javax.annotation.Resource;

import poller.modeljsonparser.AbstractModelPartition;

public class ResourceList extends AbstractModelPartition {

    int brick;
    int wood;
    int sheep;
    int wheat;
    int ore;
	private int numOfOre;
	private int numOfWood;
	private int numOfBrick = 0;
	private int numOfSheep = 0;
	private int numOfWheat = 0;




    public ResourceList() {
        brick = 0;
        ore = 0;
        sheep = 0;
        wheat = 0;
        wood = 0;
    }

    public ResourceList(int given) {
        if(given > 20 || given < 0){
            given = 19;
        }
        brick = given;
        ore = given;
        sheep = given;
        wheat = given;
        wood = given;
    }

    public ResourceList(int br, int or, int sh, int wh, int wo) {
        brick = br;
        ore = or;
        sheep = sh;
        wheat = wh;
        wood = wo;
    }
    //copy constructor
    public ResourceList(ResourceList resourcesToCopy)
    {
       brick = resourcesToCopy.getBrick();
       ore = resourcesToCopy.getOre();
       sheep = resourcesToCopy.getSheep();
       wheat = resourcesToCopy.getWheat();
      wood = resourcesToCopy.getWood();
    }
    //overload the constructor so that RL can be used for trades, bank, map, players etc.

    public int getBrick() {
        return brick;
    }

    public int getOre() {
        return ore;
    }

    public int getSheep() {
        return sheep;
    }

    public int getWheat() {
        return wheat;
    }

    public int getWood() {
        return wood;
    }

    public void setBrick(int brick) {
        this.brick = brick;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public void setWood(int wood) {
        this.wood = wood;
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

    public int getSize()
    {
        
		int total = numOfBrick + numOfOre + numOfSheep + numOfWheat + numOfWood;
        return total;
    }

	public int getTotal() {
		return getBrick()+getOre()+getSheep()+getWheat()+getWood();
	}

}
