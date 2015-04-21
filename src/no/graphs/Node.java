package no.graphs;

public class Node implements NodeI {

	private int id;
	
	public Node(int id) {
		this.id = id;
	}
	
	@Override
	public int id() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Node))
			return false;
		if (obj == this)
			return true;
		
		Node other = (Node) obj;
		
		if (this.id == other.id)
			return true;
		else
			return false;
	}
}
