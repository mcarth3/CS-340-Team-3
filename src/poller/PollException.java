package poller;
//thrown when polling fails
public class PollException extends Exception {
	/**
	 * constructor for the PollException.
	 * @param message: a message describing the exception
	 * @pre message is not null
	 * @post a new PollException is made with message describing the details of the exception
	 */
	public PollException(String message) {
		super(message);
	}

}
