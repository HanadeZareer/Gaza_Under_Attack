package thaWarOnGaza;

/*
 * Class Exception for preventing the addition of family members to the members ArrayList 
 * without including parents in the parents ArrayList
 */
public class InvalidAddingMemberException extends Exception {

	public InvalidAddingMemberException() {
		// calling the constructor of parent Exception
		super("invalid Adding family members to the members List without including parents");

	}

}
