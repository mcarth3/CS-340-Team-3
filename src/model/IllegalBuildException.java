package model;

import javax.lang.model.util.ElementScanner6;

/**
 * @author Jesse McArthur
 */
public class IllegalBuildException extends Exception {

    String message;
    public IllegalBuildException(String msg)
    {
        message = msg;
    }

    public String getMessage()
    {
        return message;
    }
}
