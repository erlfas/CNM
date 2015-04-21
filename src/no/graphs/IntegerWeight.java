package no.graphs;

public class IntegerWeight extends Weight {

	private Integer weight;
	
	public IntegerWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Integer weight() {
		return this.weight;
	}
}
