package no.communitydetection;

public class CommunityPair implements Comparable<CommunityPair> {

	private int i;
	private int j;
	
	public CommunityPair(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public int i() {
		return this.i;
	}
	
	public int j() {
		return this.j;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CommunityPair))
			return false;
		if (obj == this)
			return true;
		
		CommunityPair other = (CommunityPair) obj;
		
		if (this.i == other.i && this.j == other.j)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.i * 17 + this.j * 31;
	}

	@Override
	public int compareTo(CommunityPair o) {
		if (this.i < o.i) // EX: (2,_) < (3,_)
			return -1;
		else if (this.i > o.i) // EX: (3,_) > (2,_)
			return 1;
		else if (this.j < o.j) // EX: (i,2) < (i,3)
			return -1;
		else if (this.j > o.j) // EX: (i,3) > (i,2)
			return 1;
		else // EX: (i,j) = (i,j)
			return 0;
	}
}
