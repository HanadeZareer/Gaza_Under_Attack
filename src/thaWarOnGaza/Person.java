package thaWarOnGaza;

public abstract class Person implements Cloneable { // change person to abstract that prevents to create an object from
													// it
	// Declaration of variables
	private String id;
	private String name;
	private int age;
	private String gender;
	private String address;
	private String contactInfo;

	// main argument constructor
	public Person(String id, String name, int age, String gender, String address, String contactInfo) {
		super();
		setId(id);
		this.name = name;
		this.address = address;
		this.contactInfo = contactInfo;
		try {
			setAge(age);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		setGender(gender);
	}

	/*
	 * deep copy of the object person
	 * 
	 * @return Object
	 */
	@Override
	public Object clone() throws NullPointerException {
		try {
			// shallow copy
			Person personClone = (Person) super.clone();
			// Deep copy
			personClone.setId(new String(id));
			personClone.setName(new String(name));
			personClone.setGender(new String(gender));
			personClone.setAddress(new String(address));
			personClone.setContactInfo(new String(contactInfo));
			return personClone;
		} catch (CloneNotSupportedException e) { // handled if the class not implements Cloneable
			System.out.println("Clone not supported!");
		}
		return null;
	}

	// generate the getters and setters
	public String getId() {
		return id;
	}

	/**
	 * set id.
	 * 
	 * @param String id
	 * @return void
	 */
	public void setId(String id) {
		try {
			// check if the String id is an Integer
			int num = Integer.parseInt(id);
			this.id = id;
		} catch (NumberFormatException e) {
			System.out.println("Invalid id! " + e.getMessage());
		}

	}

	/**
	 * get name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		} else
			throw new IllegalArgumentException("the age can't be less than 0");
	}

	public String getGender() {
		return gender;
	}

	/**
	 * set gender.
	 * 
	 * @param String gender
	 * @return void
	 */
	public void setGender(String gender) {
		if (gender.equalsIgnoreCase("F") || gender.equalsIgnoreCase("M")) { // To check whether the gender entered is M/F
			this.gender = gender;
		} else
			System.out.println("gender can be only F or M");
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * retrieve the information about the person.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Person [id: " + id + ", name: " + name + ", age: " + age + ", gender: " + gender + ", address: "
				+ address + ", contact info: " + contactInfo + "]";
	}
}
