package thaWarOnGaza;

import java.util.ArrayList;

public interface Sortable {
	// The sorting base on the number of Martyrs
	ArrayList<Family> sortByMartyrs(ArrayList<Family> families);

	// The sorting base on the number of Martyrs
	ArrayList<Family> sortByOrphans(ArrayList<Family> families);

}
