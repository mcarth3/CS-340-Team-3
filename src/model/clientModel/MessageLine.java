package model.clientModel;

/**
 * Created by Jesse on 5/11/2016.
 */
public class MessageLine {
    private String message;
    private String source;

    public void MessageLine(){
    	message ="default message string";
    	source ="default source string";
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
