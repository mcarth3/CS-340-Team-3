package model;

/**
 * @author Jesse McArthur
 */
public class FailureToAddException extends Exception {

    String message;
    public void FailureToAddException(){
    	message = "message";
    }
    
    
    public FailureToAddException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }
}
