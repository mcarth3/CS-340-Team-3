package model;

/**
 * @author Jesse McArthur
 */
public class FailedCreateGameException extends Exception
{
    String message;
    
    public void FailedCreateGameException(){
    	message = "message";
    }
    public FailedCreateGameException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }
}

