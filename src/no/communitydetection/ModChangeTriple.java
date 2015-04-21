package no.communitydetection;

public class ModChangeTriple implements Comparable<ModChangeTriple> {

	private int i;
	private int j;
	private Double modularityChange;
	
	public ModChangeTriple(int i, int j, Double modularityChange) {
		this.i = i;
		this.j = j;
		this.modularityChange = modularityChange;
	}
	
	public int i() {
		return this.i;
	}
	
	public int j() {
		return this.j;
	}
	
	public Double modularityChange() {
		return this.modularityChange;
	}
	
	public String toString() {
		return "(" + i + "|" + j + "|" + modularityChange + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof ModChangeTriple))
			return false;
		if (obj == this)
			return true;
		
		ModChangeTriple other = (ModChangeTriple) obj;
		
		if (this.i == other.i && this.j == other.j)
			return true;
		else if (this.i == other.j && this.j == other.i)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.i * 17 + this.j * 31;
	}

	// TODO:
	
	@Override
	public int compareTo(ModChangeTriple o) {
		if (this.modularityChange < o.modularityChange)
			return -1;
		else if (this.modularityChange > o.modularityChange)
			return 1;
//		else if (this.i < o.i)
//			return -1;
//		else if (this.i > o.i)
//			return 1;
//		else if (this.j < o.j)
//			return -1;
//		else if (this.j > o.j)
//			return 1;
		else
			return 0;
	}
}
