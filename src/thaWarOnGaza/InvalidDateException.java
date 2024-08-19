package thaWarOnGaza;

/*
 * Class Exception To check the date format
 */
public class InvalidDateException extends Exception {

	public InvalidDateException() {
		// calling the constructor of parent Exception
		super("Invalid! date format: day/month/year ");

	}

}
