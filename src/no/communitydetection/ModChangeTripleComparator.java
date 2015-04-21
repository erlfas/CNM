package no.communitydetection;

import java.util.Comparator;

public class ModChangeTripleComparator implements Comparator<ModChangeTriple> {

	@Override
	public int compare(ModChangeTriple a, ModChangeTriple b) {
		if (a.modularityChange() < b.modularityChange())
			return 1;
		else if (a.modularityChange() > b.modularityChange())
			return -1;
		else if (a.i() < b.i())
			return -1;
		else if (a.i() > b.i())
			return 1;
		else if (a.j() < b.j())
			return -1;
		else if (a.j() > b.j())
			return 1;
		else
			return 0;
	}

}
