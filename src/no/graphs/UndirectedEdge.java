package no.graphs;

public class UndirectedEdge implements EdgeI {
	
	private NodeI head;
	private NodeI tail;
	
	public UndirectedEdge(NodeI head, NodeI tail) {
		this.head = head;
		this.tail = tail;
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
