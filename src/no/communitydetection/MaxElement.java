package no.communitydetection;

class MaxElement {
	private Integer key;
	private Double value;

	public MaxElement(Integer key, Double value) {
		this.key = key;
		this.value = value;
	}

	public Integer key() {
		return this.key;
	}

	public Double value() {
		return this.value;
	}
}