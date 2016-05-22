package model;

import model.bank.ResourceList;
import poller.modeljsonparser.AbstractModelPartition;

/**
 * @author Jesse McArthur
 */

public class TradeOffer extends AbstractModelPartition {
	  
	  //The index of the person offering the trade
	  int sender;
	  //The index of the person the trade was offered to.
	  int receiver;
	  //Positive numbers are resources being offered. Negative are resources being asked for.
	  ResourceList offer;
	//  Resource List of whats being sent (negative)
	  ResourceList sentList = new ResourceList(0);
	 // Resource List of whats being recieved (positive)
	  ResourceList recievedList = new ResourceList(0);
	  
	  public TradeOffer()
	  {
	    sender = 0;
	    receiver = 0;
	    offer = new ResourceList(0);
	  }
	  public TradeOffer(int newsender, int newreciever,ResourceList newoffer, ResourceList newsentlist, ResourceList newrecievedlist)
	  {
		  sender = newsender;
		  receiver = newreciever;
		  offer = newoffer;
		  sentList = newsentlist;
		  recievedList = newrecievedlist;
		  System.out.println("TradeOffer constructor 1, reciever is " + receiver);
	  }
	  public TradeOffer(ResourceList offr)
	  {
	    offer = offr;
	  }

	  public TradeOffer(int pid, int rid, ResourceList o)
	  {
	    sender = pid;
	    receiver = rid;
	    offer = o;
		  System.out.println("TradeOffer constructor 2, reciever is " + receiver);
	  }
	  /*
	  * Separates the given offer into two resource lists, one that is filled with only the positive number
	  * of resources being recieved, and the other a list filled with only the negative number of resources
	  * being sent.
	  */
	  public void separateOffer()
	  {
	    int brick = offer.getBrick();
	    int ore = offer.getOre(); 
	    int sheep = offer.getSheep();
	    int wheat = offer.getWheat();
	    int wood = offer.getWood();
	    if(brick >= 0){recievedList.setBrick(brick);} else sentList.setBrick(brick);
	    if(ore >= 0){recievedList.setOre(ore);} else sentList.setOre(ore);
	    if(sheep >= 0){recievedList.setSheep(sheep);} else sentList.setSheep(sheep);
	    if(wheat >= 0){recievedList.setWheat(wheat);} else sentList.setWheat(wheat);
	    if(wood >= 0){recievedList.setWood(wood);} else sentList.setWood(wood);
	  }
	  
	  public int getSender()
	  {
	    return sender;
	  }
	  public void setSender(int send)
	  {
	    sender = send;
	  }  
	  public int getReciever()
	  {
	    return receiver;
	  }
	  public void setReciever(int recieve)
	  {
	    receiver = recieve;
		  System.out.println("TradeOffer setReciever(), reciever is " + receiver);
	  }  
	  public ResourceList getOffer()
	  {
	    return offer;
	  }
	  public void setOffer(ResourceList off)
	  {
	    offer = off;
	  }
	  public ResourceList getSentList() {return sentList; }
	  public ResourceList getRecievedList() {return recievedList; }
}

  

