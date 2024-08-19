package thaWarOnGaza;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
	// the Declaration of variables
	private static Manager manager = new Manager();
	private static File file = new File("family.txt");
	private static int id = 1; // the id is an auto number
	private static int typeCopy = 0; // clone an object
	private static int state = 0;
	private static int age = 0;
	private static String role = "son"; // son or daughter
	private static String date = "";
	private static String name = "";
	private static String address = "";
	private static String gender = "M";
	private static String contactInfo = "";
	private static String causeOfDeath = "";
	private static String placeOfDeath = "";
	private static String familyName = "";

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int choice = 0;
		int choice1 = 0; // file or console
		boolean found = false;
		boolean isFileUploaded = false;
		System.out.println("Do you want read data from:\n 1. text file\n 2. console");
		try {
			choice1 = in.nextInt();
			if (choice1 == 1) {
				isFileUploaded = true;
			} else if (choice1 == 2) {
				isFileUploaded = false;
			} else
				System.out.println("please select 1 or 2");
			do {
				if (isFileUploaded == true) {
					// upload family history (family.txt) to the manager
					uploadDataFile();
					isFileUploaded = false;
				}
				displayMainMenu();
				choice = in.nextInt();
				switch (choice) {
				case 1: // Create a new family
					System.out.println("Enter the family name:");
					familyName = in.next();
					manager.addFamily(new Family(familyName));
					break;
				case 2: // Add parents to a family
					found = false;
					System.out.println("Enter the family name: ");
					familyName = in.next();
					for (int i = 0; i < manager.getFamilies().size(); i++) {
						// check if the familyName exist in the manager
						if (manager.getFamilies().get(i).getFamilyName().equalsIgnoreCase(familyName)) {
							found = true;
							System.out.println("parent's name: ");
							name = in.next();
							System.out.println("age: ");
							age = in.nextInt();
							System.out.println("gender(M/F): ");
							gender = in.next();
							System.out.println("address: ");
							address = in.next();
							System.out.println("Is the parent:\n 1. live \n 2. martyr");
							state = in.nextInt();
							if (state == 1) { // if the parent is a live person
								System.out.println("contact info: ");
								contactInfo = in.next();
								try {
									manager.getFamilies().get(i).addParent(
											new LivePerson(id + "", name, age, gender, address, contactInfo));
								} catch (OutOfTwoParentsException e) {
									System.out.println(e.getMessage());
								} finally {
									id++; // auto increase for the id
								}
							} else if (state == 2) { // if the parent is a martyr
								System.out.println("date of martyrdom: ");
								date = in.next();
								System.out.println("cause of death: ");
								causeOfDeath = in.next();
								System.out.println("place of death: ");
								String placeOfDeath = in.next();
								try {
									manager.getFamilies().get(i).addParent(new Martyr(id + "", name, age, gender,
											address, "", date, causeOfDeath, placeOfDeath));
								} catch (OutOfTwoParentsException e) {
									System.out.println(e.getMessage());
								} finally {
									id++;
								}
							} else
								System.out.println("Invalid choice!");
							// check what the user entered mom or dad
							if (manager.getFamilies().get(i).getParents().size() < 2) {
								if (gender.equalsIgnoreCase("F")) {
									System.out.println("Please! enter the father ,by pressing 2 in the options");
								} else if (gender.equalsIgnoreCase("M")) {
									System.out.println("Please! enter the mother ,by pressing 2 in the options");
								}
							}
						}
					}
					if (!found)
						System.out.println(familyName + "'s family does not exist");
					break;
				case 3: // Add a family members
					System.out.println("Enter the family name: ");
					familyName = in.next();
					// check if the familyName exist in the manager
					for (int i = 0; i < manager.getFamilies().size(); i++) {
						if (manager.getFamilies().get(i).getFamilyName().equalsIgnoreCase(familyName)) {
							found = true;
							System.out.println("name:");
							name = in.next();
							System.out.println("age:");
							age = in.nextInt();
							System.out.println("gender(M/F):");
							gender = in.next();
							if (gender.equalsIgnoreCase("F")) {
								role = "daughter";
							}
							System.out.println("address:");
							address = in.next();
							System.out.println("Is the parent:\n 1. live \n 2. martyr");
							state = in.nextInt();
							if (state == 1) { // if the member is a live person
								System.out.println("contact info:");
								contactInfo = in.next();
								try {
									manager.getFamilies().get(i).addMember(
											new LivePerson(id + "", name, age, gender, address, contactInfo), role);
								} catch (InvalidAddingMemberException e) {
									System.out.println(e.getMessage());
								} finally {
									id++;
								}

							} else if (state == 2) { // if the member is a martyr
								System.out.println("date of martyrdom: ");
								date = in.next();
								System.out.println("cause of death: ");
								causeOfDeath = in.next();
								System.out.println("place of death: ");
								placeOfDeath = in.next();
								try {
									manager.getFamilies().get(i).addMember(new Martyr(id + "", name, age, gender,
											address, "", date, causeOfDeath, placeOfDeath), role);
								} catch (InvalidAddingMemberException e) {
									System.out.println(e.getMessage());
								} finally {
									id++;
								}
							} else
								System.out.println("Invalid choice!");
						}
					}
					if (!found)
						System.out.println(familyName + "'s family does not exist");
					break;
				case 4: // delete a family
					System.out.println("Enter the family name:");
					familyName = in.next();
					manager.deleteFamily(familyName);
					break;
				case 5: // Update a family list
					found = false;
					boolean exist = false;
					// check if the manager isn't empty
					if (manager.getFamilies().isEmpty()) {
						System.out.println("families history is empty!");
					} else {
						System.out.println("Enter the family name: ");
						familyName = in.next();
						// check if the familyName exist in the manager
						for (int i = 0; i < manager.getFamilies().size(); i++) {
							if (manager.getFamilies().get(i).getFamilyName().equalsIgnoreCase(familyName)) {
								Family family = manager.getFamilies().get(i);
								found = true;
								System.out.println("update on a : \n 1. Parent \n 2. Member");
								choice = in.nextInt();
								System.out.println("Enter the person id :");
								id = in.nextInt();
								if (choice == 1) {
									// check if the parent's id is exist
									for (int j = 0; j < family.getParents().size(); j++) {
										if (family.getParents().get(j).getId().equals(id + "")) {
											exist = true;
											Person person = manager.getFamilies().get(i).getParents().get(j);
											change(family, person, j);
										}
									}
									if (!exist)
										System.out.println("Sorry! this parent doesn't exist");
								} else if (choice == 2) {
									// check if the members's id is exist
									for (int j = 0; j < family.getMembers().size(); j++) {
										if (manager.getFamilies().get(i).getMembers().get(j).getId().equals(id + "")) {
											exist = true;
											Person person = manager.getFamilies().get(i).getMembers().get(j);
											change(family, person, j);
										}
									}
									if (!exist)
										System.out.println("Sorry! this person doesn't exist");
								} else
									System.out.println("Invalid choice!");
							}
						}
						if (!found)
							System.out.println(familyName + " doesn't exist");
					}
					break;
				case 6: // Removing a person
					System.out.println("Remove a:\n 1. Parent \n 2. Member");
					choice = in.nextInt();
					found = false;
					if (choice == 1) {
						System.out.println("Enter the parent id:");
						id = in.nextInt();
						for (int i = 0; i < manager.getFamilies().size(); i++) {
							for (int j = 0; j < manager.getFamilies().get(i).getParents().size(); j++) {
								if (manager.getFamilies().get(i).getParents().get(j).getId().equals(id + "")) {
									found = true;
									manager.getFamilies().get(i)
											.removeParent(manager.getFamilies().get(i).getParents().get(j));
								}
							}
						}
						if (!found) {
							System.out.println(id + " doesn't exist!");
						}
					} else if (choice == 2) {
						System.out.println("Enter the member id:");
						id = in.nextInt();
						for (int i = 0; i < manager.getFamilies().size(); i++) {
							for (int j = 0; j < manager.getFamilies().get(i).getMembers().size(); j++) {
								if (manager.getFamilies().get(i).getMembers().get(j).getId().equals(id + "")) {
									found = true;
									manager.getFamilies().get(i)
											.removeMember(manager.getFamilies().get(i).getMembers().get(j));
								}
							}
						}
						if (!found) {
							System.out.println(id + " doesn't exist!");
						}
					} else
						System.out.println("Invalid choice!");
					break;
				case 7: // Searching..
					System.out.println("1. search on a family by name \n2. search on a person by id ");
					choice = in.nextInt();
					if (choice == 1) { // search on a family by name
						System.out.println("Enter the family name:");
						familyName = in.next();
						System.out.println(manager.searchByName(familyName));
					} else if (choice == 2) { // search on a person by id
						System.out.println("Enter the person id:");
						id = in.nextInt();
						System.out.println(manager.searchPersonById(id + ""));
					} else
						System.out.println("Invalid choice!");
					break;
				case 8: // Copy an object
					System.out.println("Copy an object of :\n 1. Parent \n 2. Member \n 3. Family");
					typeCopy = in.nextInt();
					if (typeCopy == 1) {
						try {
							found = false;
							System.out.println("Enter the id : ");
							id = in.nextInt();
							for (int i = 0; i < manager.getFamilies().size(); i++) {
								for (int j = 0; j < manager.getFamilies().get(i).getParents().size(); j++) {
									if (manager.getFamilies().get(i).getParents().get(j).getId().equals(id + "")) {
										found = true;
										Person parent = manager.getFamilies().get(i).getParents().get(j);
										// check the parent live or martyr to to call clone method
										if (parent instanceof LivePerson) {
											LivePerson livePersonClone = (LivePerson) parent.clone();
											System.out.println("live Person copy: \n" + livePersonClone);
										} else if (parent instanceof Martyr) {
											Martyr martyrClone = (Martyr) (parent).clone();
											System.out.println("Martyr Person copy: \n" + martyrClone);
										}
									}
								}
							}
							if (!found)
								System.out.println(id + " doesn't exist");
						} catch (NullPointerException e) {
							System.out.println("Failed to Clone!" + e.toString());
						}
					} else if (typeCopy == 2) {
						try {
							found = false;
							System.out.println("Enter the id : ");
							id = in.nextInt();
							for (int i = 0; i < manager.getFamilies().size(); i++) {
								for (int j = 0; j < manager.getFamilies().get(i).getMembers().size(); j++) {
									if (manager.getFamilies().get(i).getMembers().get(j).getId().equals(id + "")) {
										found = true;
										Person member = manager.getFamilies().get(i).getMembers().get(j);
										// check the member live or martyr to to call clone method
										if (member instanceof LivePerson) {
											LivePerson livePersonClone = (LivePerson) member.clone();
											System.out.println("live Person copy: \n" + livePersonClone);
										} else if (member instanceof Martyr) {
											Martyr martyrClone = (Martyr) (member).clone();
											System.out.println("Martyr Person copy: \n" + martyrClone);
										}
									}

								}
							}
							if (!found)
								System.out.println(id + " doesn't exist");
						} catch (NullPointerException e) {
							System.out.println("Failed to Clone!" + e.toString());
						}
					} else if (typeCopy == 3) {
						found = false;
						System.out.println("Enter the family name: ");
						familyName = in.next();
						for (int i = 0; i < manager.getFamilies().size(); i++) {
							if (manager.getFamilies().get(i).getFamilyName().equalsIgnoreCase(familyName)) {
								found = true;
								// copy the family by using clone 
								Family familyClone = (Family) manager.getFamilies().get(i).clone();
								System.out.println("the copy of " + familyName + " family: " + familyClone);
							}
						}
						if (!found)
							System.out.println(familyName + " doesn't exist");
					} else
						System.out.println("Invalid type!");
					break;
				case 9: // View a family statistics
					System.out.println("Enter the family name: ");
					familyName = in.next();
					manager.calculateFamilyStatistics(familyName);
					break;
				case 10: // View global statistics
					manager.calculateGlobalStatistics();
					break;
				case 11: // Print data of families to the file in sorted order
					System.out.println("sorted in descending order based on the number of:\n 1. martyrs \n 2. orphans");
					choice = in.nextInt();
					if (choice == 1) { // sort the manager by the number of martyrs in descending order
						ArrayList<Family> sortedFamilies = new Family().sortByMartyrs(manager.getFamilies());
						writeSortedFamilies(sortedFamilies, 2); // call the method to write on the file
					} else if (choice == 2) {// sort the manager by the number of orphans in descending order
						ArrayList<Family> sortedFamilies = new Family().sortByOrphans(manager.getFamilies());
						writeSortedFamilies(sortedFamilies, 1);
					} else
						System.out.println("Invalid choice!");

					break;
				case 12: // Exit
					// save the new data on the file
					if (choice1 == 1) {
						updateFile();
					}
					System.out.println("Exit..");
					System.exit(1);
					break;
				default:
					System.out.println("Sorry! opperation not found ");
					break;
				}
			} while (choice != 12);
		} catch (

		InputMismatchException e) {
			System.out.println("ُُError occurred while entering data! \n" + e.toString());
		}
	}

	// update the family.txt file
	public static void updateFile() {
		try (FileWriter output = new FileWriter(file)) {
			for (int i = 0; i < manager.getFamilies().size(); i++) {
				// write Family : family name
				output.write("Family : " + manager.getFamilies().get(i).getFamilyName() + "\n");
				// check whether the parent live or martyr
				for (int j = 0; j < manager.getFamilies().get(i).getParents().size(); j++) {
					Person parent = manager.getFamilies().get(i).getParents().get(j);
					// write the data of the parent
					if (parent instanceof LivePerson) {
						LivePerson livePerson = (LivePerson) parent;
						output.write("Parent : Live Person : " + livePerson.getName() + ", " + livePerson.getAge()
								+ ", " + livePerson.getGender() + ", " + livePerson.getAddress() + ", "
								+ livePerson.getContactInfo() + "\n");
					} else if (parent instanceof Martyr) {
						Martyr martyr = (Martyr) parent;
						
						output.write("Parent : Martyr : " + martyr.getName() + ", " + martyr.getAge() + ", "
								+ martyr.getGender() + ", " + martyr.getAddress() + ", " + martyr.getDateOfMartyrdom()
								+ ", " + martyr.getCauseOfDeath() + ", " + martyr.getPlaceOfDeath() + "\n");
					}
				}
				// check whether the member live or martyr
				for (int j = 0; j < manager.getFamilies().get(i).getMembers().size(); j++) {
					Person member = manager.getFamilies().get(i).getMembers().get(j);
					// write the data of the member
					if (member instanceof LivePerson) {
						LivePerson livePerson = (LivePerson) member;
						output.write("Member : Live Person : " + livePerson.getName() + ", " + livePerson.getAge()
								+ ", " + livePerson.getGender() + ", " + livePerson.getAddress() + ", "
								+ livePerson.getContactInfo() + "\n");
					} else if (member instanceof Martyr) {
						Martyr martyr = (Martyr) member;
						output.write("Member : Martyr : " + martyr.getName() + ", " + martyr.getAge() + ", "
								+ martyr.getGender() + ", " + martyr.getAddress() + ", " + martyr.getDateOfMartyrdom()
								+ ", " + martyr.getCauseOfDeath() + ", " + martyr.getPlaceOfDeath() + "\n");
					}
				}
			}
			System.out.println("Data file updated successfully!");
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	// read data from family.txt file
	public static void uploadDataFile() {
		String role = "son";
		if (file.exists()) {
			try (Scanner s = new Scanner(file);) {
				Family family = new Family();
				while (s.hasNextLine()) {
					// split lines by ':' 
					String[] typeClass = s.nextLine().split(":");
					if (typeClass.length == 2) {
						if ((typeClass[0].trim()).equalsIgnoreCase("Family")) {
							family = new Family((typeClass[1]).trim());
							manager.addFamily(family);
						}
					} else if (typeClass.length == 3) {
						// split lines typeClass[2] by ','
						String[] data = typeClass[2].split(",");
						if ((typeClass[0].trim()).equalsIgnoreCase("Parent")
								&& (typeClass[1].trim()).equalsIgnoreCase("Live Person")) {
							age = Integer.parseInt(data[1].trim());
							// add parent to the family
							try {
								family.addParent(new LivePerson(id + "", data[0].trim(), age, data[2].trim(),
										data[3].trim(), data[4].trim()));
								id++;
							} catch (OutOfTwoParentsException e) {
								System.out.println(e.toString());
							}
						} else if ((typeClass[0].trim()).equalsIgnoreCase("Parent")
								&& (typeClass[1].trim()).equalsIgnoreCase("Martyr")) {
							age = Integer.parseInt(data[1].trim());
							// add parent to the family
							try {
								family.addParent(new Martyr(id + "", data[0].trim(), age, data[2].trim(),
										data[3].trim(), "", data[4].trim(), data[5].trim(), data[6].trim()));
								id++;
							} catch (OutOfTwoParentsException e) {
								System.out.println(e.toString());
							}

						} else if ((typeClass[0].trim()).equalsIgnoreCase("Member")
								&& (typeClass[1].trim()).equalsIgnoreCase("Live Person")) {
							age = Integer.parseInt(data[1].trim());
							gender = data[2].trim();
							if (gender.equalsIgnoreCase("F")) {
								role = "daughter";
							}
							// add member to the family
							family.addMember(new LivePerson(id + "", data[0].trim(), age, gender, data[3].trim(),
									data[4].trim()), role);
							id++;
						} else if ((typeClass[0].trim()).equalsIgnoreCase("Member")
								&& (typeClass[1].trim()).equalsIgnoreCase("Martyr")) {
							age = Integer.parseInt(data[1].trim());
							gender = data[2].trim();
							if (gender.equalsIgnoreCase("F")) {
								role = "daughter";
							}
							// add member to the family
							family.addMember(new Martyr(id + "", data[0].trim(), age, gender, data[3].trim(), "",
									data[4].trim(), data[5].trim(), data[6].trim()), role);
							id++;
						}
					}
				}
				System.out.println("data uploaded successfully!");
			} catch (IOException e) {
				System.out.println(e.toString());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else
			System.out.println("Sorry! File doesn't exist");
	}

	/**
	 *  write families to the file in sorted order based on number of martyrs/orphans
	 *  
	 * @param (ArrayList<Family> sortedFamilies
	 * @param int index : 1/2 index calculateFamilyStatistics to return the number of  martyrs/orphans
	 * familyStatistics.get(1): the number of orphans
	 * familyStatistics.get(2): the number of martyrs
	 */
	public static void writeSortedFamilies(ArrayList<Family> sortedFamilies, int index) {
		String dadName = "", momName = "";
		int num = 0;
		try (FileWriter output = new FileWriter(new File("sort.txt"));) { 
			for (int i = 0; i < sortedFamilies.size(); i++) {
				num++;
				// familyStatistics.get(1): the number of orphans
				if(index == 1){ 
					output.write("Family " + num + ": number of orphans("
							+ manager.calculateFamilyStatistics(sortedFamilies.get(i).getFamilyName()).get(index) + ")\n");
				}
				// familyStatistics.get(2): the number of martyrs
				else {
					output.write("Family " + num + ": number of martyrs("
							+ manager.calculateFamilyStatistics(sortedFamilies.get(i).getFamilyName()).get(index) + ")\n");
				}
				
				// write the parents's name
				if (sortedFamilies.get(i).getParents().size() > 0) {
					for (int j = 0; j < sortedFamilies.get(i).getParents().size(); j++) {
						if (sortedFamilies.get(i).getParents().get(j).getGender().equalsIgnoreCase("M")) {
							dadName = sortedFamilies.get(i).getParents().get(j).getName();
						} else if (sortedFamilies.get(i).getParents().get(j).getGender().equalsIgnoreCase("F")) {
							momName = sortedFamilies.get(i).getParents().get(j).getName();
						}
					}
					output.write("Parents: " + dadName + ", " + momName + "\nMembers: ");
				}
				// write the members's name
				if (sortedFamilies.get(i).getMembers().size() > 0) {
					for (int j = 0; j < sortedFamilies.get(i).getMembers().size() - 1; j++) {
						output.write(sortedFamilies.get(i).getMembers().get(j).getName() + ", ");
					}
					// write the last member's name without ',' at the end
					output.write(sortedFamilies.get(i).getMembers().get(sortedFamilies.get(i).getMembers().size() - 1)
							.getName());
				}
				// write the separator between the families
				output.write("\n..........................................................\n");
			}
			System.out.println("Sorted families added Successfully! to the file");
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
	}

	/**
	 * update on member/parent information
	 * 
	 * @param family
	 * @param person 
	 * @param index
	 */
	public static void change(Family family, Person person, int index) {
		int choice = 0;
		Scanner in = new Scanner(System.in);
		// check the person whether they live or martyr
		if (person instanceof LivePerson) {
			System.out.println("Do you want change: \n 1. name\n 2. age\n "
					+ "3. address\n 4. contact info \n 5. became a martyr\n ");
			choice = in.nextInt();
			switch (choice) {
			case 1: // change the name
				System.out.println("Enter the new name:");
				name = in.next();
				person.setName(name);
				System.out.println("the name changed successfully!");
				break;
			case 2: // change the age
				System.out.println("Enter the new age:");
				age = in.nextInt();
				person.setAge(age);
				System.out.println("the age changed successfully!");
				break;
			case 3: // change the address
				System.out.println("Enter the new address:");
				address = in.next();
				person.setAddress(address);
				System.out.println("the address changed successfully!");
				break;
			case 4: // change the contact info
				System.out.println("Enter the new contact info:");
				contactInfo = in.next();
				person.setContactInfo(contactInfo);
				System.out.println("the contact info changed successfully!");
				break;
			case 5: // the person martyred
				System.out.println("Enter the date of death:");
				date = in.next();
				System.out.println("Enter the cause of death:");
				causeOfDeath = in.next();
				System.out.println("Enter the place of death:");
				placeOfDeath = in.next();
				Martyr m = new Martyr(person.getId(), person.getName(), person.getAge(), person.getGender(),
						person.getAddress(), "null", date, causeOfDeath, placeOfDeath);
				// check whether the person parent or member
				if (family.getParents().contains(person)) {
					family.getParents().set(index, m);
				} else
					family.getMembers().set(index, m);

				System.out.println("State changed Successfully!");
				break;
			}
			manager.updateFamily(familyName, family);
		} else
			System.out.println("Sorry! this parent was martyred");

	}

	// Main menu of choices
	public static void displayMainMenu() {
		System.out.println("\nPlease Select an Operation (1-12):");
		System.out.println("  1) Create a new family\r\n " + " 2) Add parents to a family\r\n"
				+ "  3) Add a family members\r\n" + "  4) Delete a family\r\n" + "  5) Update a family\r\n"
				+ "  6) Removing a person\r\n" + "  7) Searching..\r\n" + "  8) Copy an object\r\n"
				+ "  9) View a family statistics\r\n" + " 10) View global statistics\r\n"
				+ " 11) Print data of families to the file in sorted order\r\n" + " 12) Exit\r\n");
	}
}