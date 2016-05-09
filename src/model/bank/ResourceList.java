package model.bank;
/**
 * @author Jesse McArthur
 */
import javax.annotation.Resource;

public class ResourceList {

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

   
}
