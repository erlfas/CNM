package no.graphs;

public class DirectedWeightedEdge extends DirectedEdge implements WeightedI {

	private Weight weight;
	
	public DirectedWeightedEdge(NodeI head, NodeI tail) {
		super(head, tail);
		this.weight = null;
	}
	
	public DirectedWeightedEdge(NodeI head, NodeI tail, Weight weight) {
		super(head, tail);
		this.weight = weight;
	}

	@Override
	public Weight weight() {
		return this.weight;
	}

	@Override
	public void setWeight(Weight weight) {
		this.weight = weight;
	}
}
