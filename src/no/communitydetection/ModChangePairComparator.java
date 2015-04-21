package no.communitydetection;

import java.util.Comparator;

public class ModChangePairComparator implements Comparator<ModChangePair> {

	@Override
	public int compare(ModChangePair a, ModChangePair b) {
		if (a.modularityChange() < b.modularityChange())
			return -1;
		else if (a.modularityChange() > b.modularityChange())
			return 1;
		else
			return 0;
	}
}
