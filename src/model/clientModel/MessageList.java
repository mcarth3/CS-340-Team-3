package model.clientModel;

import java.util.ArrayList;

import poller.modeljsonparser.AbstractModelPartition;

/**
 * Created by Jesse on 5/11/2016.
 */
public class MessageList extends AbstractModelPartition {
    private ArrayList<MessageLine> lines;
   
    public void MessageList(){
    	lines = new ArrayList<MessageLine>();
    }

    /**
     * GETTERS & SETTERS
     */
    public ArrayList<MessageLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<MessageLine> lines) {
        this.lines = lines;
    }
}
