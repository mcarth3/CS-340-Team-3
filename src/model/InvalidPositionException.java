package model;

public class InvalidPositionException extends Exception 
{
    String message;
    public InvalidPositionException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }

}
