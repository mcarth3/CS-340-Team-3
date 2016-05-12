package poller;
/**
 * @author Mike Towne
 */
public class InvalidMockProxyException extends Exception {
	
	/**
	 * constructor for the InvalidProxyException.
	 * @param message: a message describing the exception
	 * @pre message is not null
	 * @post a new InvalidProxyException is made with message describing the details of the exception
	 */
	public InvalidMockProxyException(String message) {
		super(message);
	}
}
