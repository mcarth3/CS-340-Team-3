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
        //devs = list;
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addBrick(int brick) throws InsufficientResourcesException {
        //throws if trying to add negative number
        if (brick < 0) {
            throw new InsufficientResourcesException();
        } else {
            int prevRsrcBrick = resources.getBrick();
            resources.setBrick((prevRsrcBrick + brick));
        }
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveBrick(int amount) {
        if (amount < 0) {
            return false;
        }

        int prevRsrcBrick = resources.getBrick();
        if ((prevRsrcBrick - amount) < 0) {
            return false;
        }
        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveBrick(int amount) throws InsufficientResourcesException {
        //throws if there arent enough resources to give
        if (amount < 0) {
            throw new InsufficientResourcesException();
        }
        int prevRsrcBrick = resources.getBrick();
        if ((prevRsrcBrick - amount) < 0) {
            throw new InsufficientResourcesException();
        }

        int newRsrc = (prevRsrcBrick - amount);
        resources.setBrick(newRsrc);
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addOre(int ore) throws InsufficientResourcesException {
        //throws if trying to add negative number
        if (ore < 0) {
            throw new InsufficientResourcesException();
        } else {
            int prevRsrcOre = resources.getOre();
            resources.setOre((prevRsrcOre + ore));
        }
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveOre(int amount) {
        if (amount < 0) {
            return false;
        }

        int prevRsrcOre = resources.getOre();
        if ((prevRsrcOre - amount) < 0) {
            return false;
        }
        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveOre(int amount) throws InsufficientResourcesException {
        //throws if there arent enough resources to give
        if (amount < 0) {
            throw new InsufficientResourcesException();
        }
        int prevRsrcOre = resources.getOre();
        if ((prevRsrcOre - amount) < 0) {
            throw new InsufficientResourcesException();
        }

        int newRsrc = (prevRsrcOre - amount);
        resources.setOre(newRsrc);
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addSheep(int sheep) throws InsufficientResourcesException {
        //throws if trying to add negative number
        if (sheep < 0) {
            throw new InsufficientResourcesException();
        } else {
            int prevRsrcSheep = resources.getSheep();
            resources.setSheep((prevRsrcSheep + sheep));
        }
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveSheep(int amount) {
        if (amount < 0) {
            return false;
        }

        int prevRsrcSheep = resources.getSheep();
        if ((prevRsrcSheep - amount) < 0) {
            return false;
        }
        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveSheep(int amount) throws InsufficientResourcesException {
        //throws if there arent enough resources to give
        if (amount < 0) {
            throw new InsufficientResourcesException();
        }
        int prevRsrcSheep = resources.getSheep();
        if ((prevRsrcSheep - amount) < 0) {
            throw new InsufficientResourcesException();
        }

        int newRsrc = (prevRsrcSheep - amount);
        resources.setSheep(newRsrc);
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addWheat(int wheat) throws InsufficientResourcesException {
        //throws if trying to add negative number
        if (wheat < 0) {
            throw new InsufficientResourcesException();
        } else {
            int prevRsrcWheat = resources.getWheat();
            resources.setWheat((prevRsrcWheat + wheat));
        }
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveWheat(int amount) {
        if (amount < 0) {
            return false;
        }

        int prevRsrcWheat = resources.getWheat();
        if ((prevRsrcWheat - amount) < 0) {
            return false;
        }
        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveWheat(int amount) throws InsufficientResourcesException {
        //throws if there arent enough resources to give
        if (amount < 0) {
            throw new InsufficientResourcesException();
        }
        int prevRsrcWheat = resources.getWheat();
        if ((prevRsrcWheat - amount) < 0) {
            throw new InsufficientResourcesException();
        }

        int newRsrc = (prevRsrcWheat - amount);
        resources.setWheat(newRsrc);
    }

    /**
     * adds proper resource to resourcelist
     */
    public void addWood(int wood) throws InsufficientResourcesException {
        //throws if trying to add negative number
        if (wood < 0) {
            throw new InsufficientResourcesException();
        } else {
            int prevRsrcWood = resources.getWood();
            resources.setWood((prevRsrcWood + wood));
        }
    }

    /**
     * @param amount - number of resources needed to give out
     *               checks to make sure the bank can give the proper resource amount
     */
    public boolean canGiveWood(int amount) {
        if (amount < 0) {
            return false;
        }

        int prevRsrcWood = resources.getWood();
        if ((prevRsrcWood - amount) < 0) {
            return false;
        }
        return true;
    }

    /**
     * gives proper resource from resourcelist
     */
    public void giveWood(int amount) throws InsufficientResourcesException {
        //throws if there arent enough resources to give
        if (amount < 0) {
            throw new InsufficientResourcesException();
        }
        int prevRsrcWood = resources.getWood();
        if ((prevRsrcWood - amount) < 0) {
            throw new InsufficientResourcesException();
        }

        int newRsrc = (prevRsrcWood - amount);
        resources.setWood(newRsrc);
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
        if (type.equals("brick")) {
            return canGiveBrick(amount);
        }
        if (type.equals("ore")) {
            return canGiveOre(amount);
        }
        if (type.equals("sheep")) {
            return canGiveSheep(amount);
        }
        if (type.equals("wheat")) {
            return canGiveWheat(amount);
        }
        if (type.equals("wood")) {
            return canGiveWood(amount);
        }
        return false;
    }

    public void add(String type, int amount) {
        if (type.equals("brick")) {
            try {
                addBrick(amount);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("ore")) {
            try {
                addOre(amount);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("sheep")) {
            try {
                addSheep(amount);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("wheat")) {
            try {
                addWheat(amount);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("wood")) {
            try {
                addWood(amount);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
            }
        }
    }
}
