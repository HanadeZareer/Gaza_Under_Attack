package thaWarOnGaza;

/*
 * Exception for ensuring that the parents ArrayList includes only two persons
 */
public class OutOfTwoParentsException extends Exception {

	public OutOfTwoParentsException() {
		// calling the constructor of parent Exception
		super("invalid Adding more than two parents to a family");

	}

}
