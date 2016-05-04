package poller;
public class InvalidProxyException extends Exception {
	
	/**
	 * constructor for the InvalidProxyException.
	 * @param message: a message describing the exception
	 * @pre message is not null
	 * @post a new InvalidProxyException is made with message describing the details of the exception
	 */
	public InvalidProxyException(String message) {
		super(message);
	}
}
