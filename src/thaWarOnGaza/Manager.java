package thaWarOnGaza;

import java.util.ArrayList;

public class Manager {
	// Declaration of variables
	private ArrayList<Family> families;

	// main argument constructor
	public Manager() {
		this.families = new ArrayList<>();
	}

	// add a new family to the system
	public boolean addFamily(Family family) {
		for (int i = 0; i < families.size(); i++) {
			if(families.get(i).getFamilyName().equalsIgnoreCase(family.getFamilyName())) {
				System.out.println("failed to add!, "+family.getFamilyName()+" family already exist");
				return false;
			}
		}
			families.add(family);
			System.out.println(family.getFamilyName() + " family added successfully");
			return true;
	}

	/**
	 * update information about a family based on Family name.
	 *
	 * @param String familyName
	 * @param Family updatedFamily
	 * @return boolean
	 */
	public boolean updateFamily(String familyName, Family updatedFamily) {
		boolean found = false;
		for (int i = 0; i < families.size(); i++) {
			if (families.get(i).getFamilyName().equalsIgnoreCase(familyName)) {
				families.set(i, updatedFamily);
				System.out.println("updated successfully");
				found = true;
			}
		}
		if (!found) {
			System.out.println(familyName + " not found.");
		}
		return found;
	}

	/**
	 * delete a family from the system based on Family name.
	 *
	 * @param familyName
	 * @return boolean
	 */
	public boolean deleteFamily(String familyName) {
		boolean found = false;
		for (int i = 0; i < families.size(); i++) {
			if (families.get(i).getFamilyName().equalsIgnoreCase(familyName)) {
				families.remove(i);
				System.out.println("removed successfully");
				found = true;
			}
		}
		if (!found) {
			System.out.println("failed to delete!\n" + familyName + " not found.");
		}
		return found;
	}

	// search for a family based on Family name.
	public Family searchByName(String familyName) {
		boolean found = false;
		for (int i = 0; i < families.size(); i++) {
			if (families.get(i).getFamilyName().equalsIgnoreCase(familyName)) {
				found = true;
				return families.get(i);
			}
		}
		if (!found)
			System.out.println("failed search!\n" + familyName + " not found.");
		return null;
	}

	// search for a person based on their id.
	public Person searchPersonById(String personId) {
		boolean found = false;
		if (families.size() > 0) {
			 // to search on the person object..
			for (int i = 0; i < families.size(); i++) {
				for (int j = 0; j < families.get(i).getMembers().size(); j++) {
					if (families.get(i).getMembers().get(j).getId().equals(personId)) {
						found = true;
						return families.get(i).getMembers().get(j);
					}
				}
				for (int j = 0; j < families.get(i).getParents().size(); j++) {
					if (families.get(i).getParents().get(j).getId().equals(personId)) {
						found = true;
						return families.get(i).getParents().get(j);
					}
				}
			}
			if (found) {
				System.out.println("found successfully!");
			}
			System.out.println("failed to find!");
		}
		return null;
	}

	// return the total number of martyrs in the system.
	public int calculateTotalMartyrs() {
		int totalMartyrs = 0;
		for (int i = 0; i < families.size(); i++) {
			for (int j = 0; j < families.get(i).getParents().size(); j++) {
				if (families.get(i).getParents().get(j) instanceof Martyr) {
					totalMartyrs++;
				}

			}
			for (int j = 0; j < families.get(i).getMembers().size(); j++) {
				if (families.get(i).getMembers().get(j) instanceof Martyr) {
					totalMartyrs++;
				}
			}
		}
		System.out.println("total martyrs: " + totalMartyrs);
		return totalMartyrs;
	}

	// return the total number of orphans in the system.
	public int calculateTotalOrphans() {
		int totalOrphans = 0;
		for (int i = 0; i < families.size(); i++) { // to check if the two parent of each family martyred.
			if (families.get(i).getParents().size() == 2) {
				if (families.get(i).getParents().get(0) instanceof Martyr
						&& families.get(i).getParents().get(1) instanceof Martyr) {
					for (int j = 0; j < families.get(i).getMembers().size(); j++) {
						if (families.get(i).getMembers().get(j) instanceof LivePerson) {
							totalOrphans++;
						}
					}
				}
			}
		}
		System.out.println("total orphans: " + totalOrphans);
		return totalOrphans;
	}

	/**
	 * return the total number of live persons in the system.
	 *
	 * @return int
	 */
	public int calculateLivePersons() {
		int totalLivePersons = 0;
		for (int i = 0; i < families.size(); i++) { // to search on the object..
			for (int j = 0; j < families.get(i).getMembers().size(); j++) {
				if (families.get(i).getMembers().get(j) instanceof LivePerson) {
					totalLivePersons++;
				}
			}
			for (int j = 0; j < families.get(i).getParents().size(); j++) {
				if (families.get(i).getParents().get(j) instanceof LivePerson) {
					totalLivePersons++;
				}
			}
		}
		System.out.println("total live persons: " + totalLivePersons);
		return totalLivePersons;
	}

	/**
	 * return statistics for a specific family(the number of martyrs, orphans, and
	 * live persons).
	 *
	 * @param familyName
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> calculateFamilyStatistics(String familyName) {
		ArrayList<Integer> familyStatistics = new ArrayList<>();
		Integer numOfMartyrs = 0;
		Integer numOfOrphans = 0;
		Integer numOfLives = 0;
		boolean found = false;
		for (int i = 0; i < families.size(); i++) {
			if (families.get(i).getFamilyName().equalsIgnoreCase(familyName)) {
				found = true;
				// to calculate number of orphans.
				if (families.get(i).getParents().size() == 2) {
					if (families.get(i).getParents().get(0) instanceof Martyr
							&& families.get(i).getParents().get(1) instanceof Martyr) {
						for (int j = 0; j < families.get(i).getMembers().size(); j++) {
							if (families.get(i).getMembers().get(j) instanceof LivePerson) {
								numOfOrphans++;
							}
						}
					}
				}
				// to calculate number of number of martyrs.
				for (int j = 0; j < families.get(i).getMembers().size(); j++) {
					if (families.get(i).getMembers().get(j) instanceof Martyr) {
						numOfMartyrs++;
					} else if (families.get(i).getMembers().get(j) instanceof LivePerson) {
						numOfLives++;
					}
				}
				// to calculate number of number of live persons.
				for (int j = 0; j < families.get(i).getParents().size(); j++) {
					if (families.get(i).getParents().get(j) instanceof Martyr) {
						numOfMartyrs++;
					} else if (families.get(i).getParents().get(j) instanceof LivePerson) {
						numOfLives++;
					}
				}
				System.out.println("number of lives in " + familyName + " family: " + numOfLives);
				System.out.println("number of orphans in " + familyName + " family: " + numOfOrphans);
				System.out.println("number of martyrs in " + familyName + " family: " + numOfMartyrs);
			}
		}
		if (!found)
			System.out.println("failed! " + familyName + "'s family not found.");
		familyStatistics.add(0, numOfLives); // familyStatistics.get(0): the number of live persons
		familyStatistics.add(1, numOfOrphans); // familyStatistics.get(1): the number of orphans
		familyStatistics.add(2, numOfMartyrs); // familyStatistics.get(2): the number of martyrs
		return familyStatistics;
	}

	/**
	 * return overall statistics for the system.
	 *
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> calculateGlobalStatistics() {
		ArrayList<Integer> globalStatistics = new ArrayList<>();
		Integer allLives = calculateLivePersons();
		Integer allOrphans = calculateTotalOrphans();
		Integer allMartyes = calculateTotalMartyrs();
		globalStatistics.add(0, allLives); // globalStatistics.get(0): the global number of live persons
		globalStatistics.add(1, allOrphans); // globalStatistics.get(1): the global number of orphans
		globalStatistics.add(2, allMartyes); // globalStatistics.get(2): the global number of martyrs
		return globalStatistics;
	}

	// getter
	public ArrayList<Family> getFamilies() {
		return families;
	}

	/**
	 * retrieve all family's information in the manager.
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		return "Manager families: \n" + families;
	}
}
