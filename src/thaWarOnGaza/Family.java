package thaWarOnGaza;

import java.util.ArrayList;
import java.util.Collections;

public class Family implements Sortable, Comparable<Family>, Cloneable {
	// Declaration of variables
	private String familyName;
	private ArrayList<Person> parents;
	private ArrayList<Person> members;

	// main argument constructor
	public Family(String familyName) {
		this.familyName = familyName;
		this.parents = new ArrayList<Person>();
		this.members = new ArrayList<Person>();
	}

	// no-argument constructor
	public Family() {
		this.parents = new ArrayList<Person>();
		this.members = new ArrayList<Person>();
	}

	/**
	 * copy an object of family
	 * @throws NullPointerException if the source object is null
	 */
	@Override
	public Object clone() throws NullPointerException { 
		try {
			// shallow copy
			Family familyClone = (Family) super.clone();
			// deep copy
			familyClone.setFamilyName(new String(familyName));
			familyClone.setParents(new ArrayList<Person>(parents));
			familyClone.setMembers(new ArrayList<Person>(members));
			// deep copy of the person in the parents family
			for (int i = 0; i < familyClone.getParents().size(); i++) {
				if (familyClone.getParents().get(i) instanceof LivePerson) {
					LivePerson livePersonClone = (LivePerson) ((LivePerson) familyClone.getParents().get(i)).clone();
					familyClone.getParents().set(i, livePersonClone);
				}
				if (familyClone.getParents().get(i) instanceof Martyr) {
					Martyr martyrClone = (Martyr) ((Martyr) familyClone.getParents().get(i)).clone();
					familyClone.getParents().set(i, martyrClone);
				}
			}
			// deep copy of the person in the members family
			for (int i = 0; i < familyClone.getMembers().size(); i++) {
				if (familyClone.getMembers().get(i) instanceof LivePerson) {
					LivePerson livePersonClone = (LivePerson) ((LivePerson) familyClone.getMembers().get(i)).clone();
					familyClone.getMembers().set(i, livePersonClone);
				}
				if (familyClone.getMembers().get(i) instanceof Martyr) {
					Martyr martyrClone = (Martyr) ((Martyr) familyClone.getMembers().get(i)).clone();
					familyClone.getMembers().set(i, martyrClone);
				}
			}

			return familyClone;
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone not Supported!");
		}

		return null;
	}

	/**
	 * compare depend on number of Martyrs
	 * 
	 * @param Family family
	 */
	public int compareTo(Family family) {
		int numOfMartyrs = 0; // number Of Martyrs of this.family
		int numOfFamilyMartyrs = 0; // number Of Martyrs of family
		// calculate the number Of Martyrs of this.family:
		for (int i = 0; i < parents.size(); i++) {
			if (parents.get(i) instanceof Martyr) {
				numOfMartyrs++;
			}
		}
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i) instanceof Martyr) {
				numOfMartyrs++;
			}
		}
		// calculate the number Of Martyrs of family:
		for (int i = 0; i < family.parents.size(); i++) {
			if (family.parents.get(i) instanceof Martyr) {
				numOfFamilyMartyrs++;
			}
		}
		for (int i = 0; i < family.members.size(); i++) {
			if (family.members.get(i) instanceof Martyr) {
				numOfFamilyMartyrs++;
			}
		}
		// check which have more Martyrs:
		if (numOfMartyrs > numOfFamilyMartyrs) {
			return 1;
		} else if (numOfMartyrs < numOfFamilyMartyrs) {
			return -1;
		} else
			return 0;
	}

	/**
	 * The sorting in descending order base on the number of Martyrs. without
	 * changing the original families list.
	 * 
	 * @param ArrayList<Family> families
	 */
	@Override
	public ArrayList<Family> sortByMartyrs(ArrayList<Family> families) { 
		 // check if Family list has families
		if (families.size() > 0) {
			// add families's element to sortMartyrs list
			ArrayList<Family> sortMartyrs = new ArrayList<>(families);
			Family temp = new Family();
			if (sortMartyrs.size() > 0) {
//				Collections.sort(sortMartyrs); // this method to sort the families in ascending order 
				for (int i = 0; i < families.size(); i++) {
					for (int j = i + 1; j < families.size(); j++) {
						// sort families in descending order
						if (sortMartyrs.get(j).compareTo(sortMartyrs.get(i)) == 1) {
							temp = sortMartyrs.get(i); // to save the family
							sortMartyrs.set(i, sortMartyrs.get(j));
							sortMartyrs.set(j, temp);
							j--;
						}
					}
				}
			}
			return sortMartyrs;
		}
		System.out.println("Sorry! families history is empty");
		return null; // if Family list is empty
	}

	/**
	 * compare depend on number of Orphans
	 * 
	 * @param Family family
	 */
	public int orphanCompareTo(Family family) {
		int numOfOrphans = 0; // number Of Orphans of this.family
		int numOfFamilyOrphans = 0; // number Of Orphans of family
		// calculate the number Of Orphans of this.family:
		if (parents.size() == 2) {
			if (parents.get(0) instanceof Martyr && parents.get(1) instanceof Martyr) { // check if both parents
																						// martyred
				for (int i = 0; i < members.size(); i++) {
					if (members.get(i) instanceof LivePerson) {
						numOfOrphans++;
					}
				}
			}
		}
		if (family.parents.size() == 2) {
			// calculate the number Of Orphans of family:
			if (family.parents.get(0) instanceof Martyr && family.parents.get(1) instanceof Martyr) {
				for (int i = 0; i < family.members.size(); i++) {
					if (family.members.get(i) instanceof LivePerson) {
						numOfFamilyOrphans++;
					}
				}
			}
		}
		// check which family have more Orphans:
		if (numOfOrphans > numOfFamilyOrphans) {
			return 1;
		} else if (numOfOrphans < numOfFamilyOrphans) {
			return -1;
		} else
			return 0;
	}

	/**
	 * The sorting in descending order base on the number of Orphans. without
	 * changing the original families list.
	 * 
	 * @param ArrayList<Family> families
	 */
	@Override
	public ArrayList<Family> sortByOrphans(ArrayList<Family> families) {
		 // check if Family list has families
		if (families.size() > 0) {
			// add families's element to sortOrphans list
			ArrayList<Family> sortOrphans = new ArrayList<>(families); 
			Family temp = sortOrphans.get(0); // to save the family
			for (int i = 0; i < families.size(); i++) {
				for (int j = i + 1; j < families.size(); j++) {
					// sort families in descending order
					if (sortOrphans.get(j).orphanCompareTo(sortOrphans.get(i)) == 1) { 
						temp = sortOrphans.get(i);
						sortOrphans.set(i, families.get(j));
						sortOrphans.set(j, temp);
						j--;
					}
				}
			}
			return sortOrphans;
		}
		System.out.println("Sorry! families history is empty");
		return null; // if Family list is empty
	}

	/**
	 * add a person to the family with a specified role
	 * 
	 * @param member
	 * @param role
	 * @return boolean
	 * @throws InvalidAddingMemberException
	 */
	public boolean addMember(Person member, String role) throws InvalidAddingMemberException {
		boolean add = false;
		if (parents.size() == 2) {
			if (role.equalsIgnoreCase("son") || role.equalsIgnoreCase("daughter")) {
				members.add(member);
				System.out.println(member.getId() + " added successfully");
				add = true;
			} else {
				System.out.println("failed addition!\nthe permitted roles include son or daughter");
				add = false;
			}
		} else
			throw new InvalidAddingMemberException();
		return add;
	}

	// remove a person from the family
	public boolean removeMember(Person member) {
		boolean removed = false;
		if (members.size() > 0) {
			members.remove(member);
			removed = true;
			System.out.println(member.getId() + " removed successfully");
		} else {
			removed = false;
			System.out.println("null!");
		}
		return removed;
	}

	// add a parent to the family
	public void addParent(Person parent) throws OutOfTwoParentsException {
		if (parents.size() < 2) { // to avoid adding more than a parent(mom and dad)
			parents.add(parent);
			System.out.println(parent.getId() + " added successfully");
		} else
			throw new OutOfTwoParentsException();

	}

	// removes a parent from the family
	public boolean removeParent(Person parent) {
		boolean removed = false;
		if (parents.size() > 0) {
			parents.remove(parent);
			removed = true;
			System.out.println(parent.getId() + " removed successfully");
		} else {
			removed = false;
			System.out.println("null!");
		}
		return removed;
	}

	/**
	 * Override this method to consider two families as equal if they have the same
	 * number of martyrs
	 *
	 * @param Object obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Family) {
			if (this.compareTo((Family) obj) == 0) {
				return true;
			}
		}
		return false;
	}

//  generating the getters and setters:

	/**
	 * retrieve the Family Name.
	 *
	 * @return String
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * set a name for family.
	 *
	 * @param familyName
	 * @return void
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * getParents() retrieve the list of family parents
	 *
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getParents() {
		return parents;
	}

	public void setParents(ArrayList<Person> parents) {
		this.parents = parents;
	}

	/**
	 * getMembers() retrieve the list of family members.
	 *
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getMembers() {
		return (ArrayList<Person>) members;
	}

	public void setMembers(ArrayList<Person> members) {
		this.members = members;
	}

	/**
	 * retrieve family's information.
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		return "\nFamily name: " + familyName + "\nparents: " + parents + "\nmembers: " + members;
	}
}