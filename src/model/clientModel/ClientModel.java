package model.clientModel;

import model.Map;
import model.Player;
import model.TradeOffer;
import model.TurnTracker;
import model.bank.ResourceList;
import poller.modeljsonparser.AbstractModelPartition;

import java.util.ArrayList;

/**
 * Created by Jesse on 5/11/2016.
 */
public class ClientModel extends AbstractModelPartition {

    private ResourceList bank;
    private MessageList chat;
    private MessageList log;
    private Map map;
    private ArrayList<Player> players;
    private TradeOffer tradeOffer;
    private TurnTracker turnTracker;
    private int version;
    private int winner;
    
    public ClientModel(ResourceList newbank, MessageList newchat, MessageList newlog, Map newmap, ArrayList<Player> newplayers, TradeOffer newtradeOffer, TurnTracker newturnTracker, int newversion, int newwinner){
        bank = newbank;
        chat = newchat;
        log = newlog;
        map = newmap;
        players = newplayers;
        tradeOffer = newtradeOffer;
        turnTracker = newturnTracker;
        version = newversion;
        winner = newwinner;
    }
    
    public ClientModel(){
        bank = new ResourceList();
        chat = new MessageList();
        log = new MessageList();
        map = new Map();
        players = new ArrayList<Player>();
        tradeOffer = new TradeOffer();
        turnTracker = new TurnTracker();
        version = 0;
        winner = 0;
    }

    /**
     * GETTERS & SETTERS
     */

    public ResourceList getBank() {
        return bank;
    }

    public void setBank(ResourceList bank) {
        this.bank = bank;
    }

    public MessageList getChat() {
        return chat;
    }

    public void setChat(MessageList chat) {
        this.chat = chat;
    }

    public MessageList getLog() {
        return log;
    }

    public void setLog(MessageList log) {
        this.log = log;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map Map) {
        this.map = Map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    public void setTradeOffer(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public void setTurnTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
