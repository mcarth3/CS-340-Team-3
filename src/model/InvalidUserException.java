package model;
/**
 * @author Jesse McArthur
 */
public class InvalidUserException extends Exception
{
    String message;
    public InvalidUserException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }
}
