package model;
/**
 * @author Jesse R
 */
public class ObjectNotFoundException extends Exception
{
    String message;
    public ObjectNotFoundException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }


}
