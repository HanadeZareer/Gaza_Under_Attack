package thaWarOnGaza;

public class LivePerson extends Person {

	// main argument constructor
	public LivePerson(String id, String name, int age, String gender, String address, String contactInfo) {
		super(id, name, age, gender, address, contactInfo);
	}

	@Override
	public String toString() {
		return "Live person [id: " + getId() + ", name: " + getName() + ", age: " + getAge() + ", gender: "
				+ getGender() + ", address: " + getAddress() + ", contact info: " + getContactInfo() + "]";
	}

}
