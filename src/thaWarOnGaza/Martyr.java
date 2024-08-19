package thaWarOnGaza;

public class Martyr extends Person {
	// Declaration of variables
	private String dateOfMartyrdom;
	private String causeOfDeath;
	private String placeOfDeath;

	// main argument constructor
	public Martyr(String id, String name, int age, String gender, String address, String contactInfo,
			String dateOfMartyrdom, String causeOfDeath, String placeOfDeath) {
		super(id, name, age, gender, address,
				"Sorry! contact with them has been lost due to the crimes of the occupation...");
		try {
			setDateOfMartyrdom(dateOfMartyrdom);
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
		}
		this.causeOfDeath = causeOfDeath;
		this.placeOfDeath = placeOfDeath;
	}
	
    /*
     *  copy an object of Martyrs
     *  @throws IvalidDateException 
     */
	@Override
	public Object clone() {
		// shallow copy
		Martyr martyrClone = (Martyr) super.clone();
		// Deep copy
		try {
			martyrClone.setDateOfMartyrdom(new String(dateOfMartyrdom));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		martyrClone.setCauseOfDeath(new String(causeOfDeath));
		martyrClone.setPlaceOfDeath(new String(placeOfDeath));
		return martyrClone;

	}

//	 generate the getters and setters:
	public String getDateOfMartyrdom() {
		return dateOfMartyrdom;
	}

	/**
	 * set date of martyrdom.
	 * 
	 * @param dateOfMartyrdom
	 * @return void
	 */
	public void setDateOfMartyrdom(String dateOfMartyrdom) throws InvalidDateException {
		// day/month/hour
		String[] date = dateOfMartyrdom.split("/"); // split dateOfMartyrdom into day , month , year
		if (date.length == 3) {
			// convert the element of date to integer type
			int day = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int year = Integer.parseInt(date[2]);
			if (month > 0 && month <= 12 && year >= 1948 && day > 0 && day <= 31) {
				this.dateOfMartyrdom = dateOfMartyrdom;
			} else
				throw new InvalidDateException();
		} else
			throw new InvalidDateException();

	}

	/**
	 * get date of martyrdom.
	 * 
	 * @return String
	 */
	public String getCauseOfDeath() {
		return causeOfDeath;
	}

	/**
	 * set cause of death.
	 * 
	 * @param String causeOfDeath
	 * @return void
	 */
	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}

	/**
	 * get place of death.
	 * 
	 * @return String
	 */
	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	/**
	 * set place of death.
	 * 
	 * @param placeOfDeath
	 * @return void
	 */
	public void setPlaceOfDeath(String placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}

	/**
	 * retrieve the information about the martyr.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Martyr [id: " + super.getId() + ", name: " + super.getName() + ", age: " + super.getAge()
				+ ", date of martyrdom: " + dateOfMartyrdom + ", cause of death: " + causeOfDeath + ", place of death: "
				+ placeOfDeath + "]";
	}

}
