package no.graphs;

public class Edge implements EdgeI {
	
	protected NodeI head;
	protected NodeI tail;
	
	public Edge(NodeI head, NodeI tail) {
		this.head = head;
		this.tail = tail;
	}
	
	public int start() {
		return this.head.id();
	}
	
	public int end() {
		return this.tail.id();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Edge))
			return false;
		
		if (this == obj)
			return true;
		
		Edge other = (Edge) obj;
		
		if (this.head == other.head() && this.tail == other.tail())
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.start() * 17 + this.end() * 31;
	}

	@Override
	public NodeI head() {
		return this.head;
	}

	@Override
	public NodeI tail() {
		return this.tail;
	}
}
