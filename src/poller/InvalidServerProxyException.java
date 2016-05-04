public class InvalidServerProxyException extends Exception {
	
	/**
	 * constructor for the InvalidServerProxyException.
	 * @param message: a message describing the exception
	 * @pre message is not null
	 * @post a new InvalidServerProxyException is made with a massage describing the details of the exception
	 */
	public InvalidServerProxyException(String message) {
		super(message);
	}
}
