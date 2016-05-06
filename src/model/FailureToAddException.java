package model;


public class FailureToAddException extends Exception {

    String message;
    public FailureToAddException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }
}
