package model.clientModel;

import poller.modeljsonparser.AbstractModelPartition;

/**
 * Created by Jesse on 5/11/2016.
 */
public class MessageLine extends AbstractModelPartition {
    public String message;
    public String source;

    public MessageLine(){
    	message ="default message string";
    	source ="default source string";
    }
    public MessageLine(String newmessage, String newsource){
    	message = newmessage;
    	source = newsource;
    }
    /**
    GETTERS & SETTERS:
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
