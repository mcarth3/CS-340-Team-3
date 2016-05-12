package model;
/**
 * @author Jesse McArthur
 */
public class InsufficientResourcesException extends Exception 
{
    String message;
    public InsufficientResourcesException(String msg)
    {
        message = msg;
    }

    public InsufficientResourcesException() {

    }

    public String getMessage()
    {
        return message;
    }

}
