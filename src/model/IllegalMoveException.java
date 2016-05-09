package model;
/**
 * @author Jesse McArthur
 */
public class IllegalMoveException extends Exception 
{
    String message;
    public IllegalMoveException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }


}
