package model.bank;
/**
 * @author Jesse McArthur
 */

public class Bank {
    ResourceList resources;
    DevCardList dcl;

    public Bank() {
        resources = new ResourceList();
        dcl = new DevCardList();
    }

    public Bank(ResourceList res, DevCardList devs) {
        resources = res;
        dcl = devs;
    }

    public ResourceList getResources() {
        return resources;
    }

    public void setResources(ResourceList list) {
        resources = list;
    }

    public DevCardList getDevCards() {
        return dcl;
    }

    public void setDevCards(DevCardList list) {
        
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addBrick(int brick) throws InsufficientResourcesException {
   
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveBrick(int amount) {
    	return false;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveBrick(int amount) throws InsufficientResourcesException {
     
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addOre(int ore) throws InsufficientResourcesException {

    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveOre(int amount) {
    	return false;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveOre(int amount) throws InsufficientResourcesException {
     
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addSheep(int sheep) throws InsufficientResourcesException {
   
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveSheep(int amount) {
return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveSheep(int amount) throws InsufficientResourcesException {
     
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addWheat(int wheat) throws InsufficientResourcesException {

    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveWheat(int amount) {

        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveWheat(int amount) throws InsufficientResourcesException {
     
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addWood(int wood) throws InsufficientResourcesException {

    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveWood(int amount) {

        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveWood(int amount) throws InsufficientResourcesException {
     
    }

    /**
     * verify that the bank does have dev cards to give
     */
    public boolean canBuyDevCard(ResourceList playerResources) {
        int sheep = playerResources.getSheep();
        int wheat = playerResources.getWheat();
        int ore = playerResources.getOre();
        if (sheep < 1 || wheat < 1 || ore < 1) {
            return false;
        }
        if (dcl.getSize() < 1) {
            return false;
        }
        return true;
    }

    /**
     * purchase of a devcard
     */
    public String BuyDevCard(ResourceList playerResources) throws InsufficientResourcesException {
        //if player doesnt have 1 sheep 1 wheat 1 ore throw exception
        int sheep = playerResources.getSheep();
        int wheat = playerResources.getWheat();
        int ore = playerResources.getOre();
        if (sheep < 1 || wheat < 1 || ore < 1) {
            throw new InsufficientResourcesException();
        }
        if (dcl.getSize() < 1) {
            throw new InsufficientResourcesException();
        }
        return dcl.buyDevCard();
    }

    public boolean canGive(String type, int amount) {

        return false;
    }

    public void add(String type, int amount) {
    }
}
