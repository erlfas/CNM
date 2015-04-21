package no.communitydetection;

public class ModChangePair implements Comparable<ModChangePair> {

	private int neighbor;
	private Double modularityChange;
	
	public ModChangePair(int neighbor, Double modularityChange) {
		this.neighbor = neighbor;
		if (modularityChange != null)
			this.modularityChange = modularityChange;
		else
			this.modularityChange = new Double(0);
	}
	
	public int neighbor() {
		return this.neighbor;
	}
	
	public Double modularityChange() {
		return this.modularityChange;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof ModChangeTriple))
			return false;
		if (obj == this)
			return true;
		
		ModChangePair other = (ModChangePair) obj;
		
		if (this.neighbor == other.neighbor)
			return true;
		else
			return false;
		
	}

	@Override
	public int compareTo(ModChangePair o) {
		if (this.modularityChange < o.modularityChange)
			return 1;
		else if (this.modularityChange > o.modularityChange)
			return -1;
		else
			return 0;
	}
}
