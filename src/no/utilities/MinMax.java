package no.utilities;

public class MinMax {

	private int min;
	private int max;
	
	public MinMax(int a, int b) {
		if (a <= b) {
			min = a;
			max = b;
		} else {
			min = b;
			max = a;
		}
	}
	
	public int min() {
		return this.min;
	}
	
	public int max() {
		return this.max;
	}
}
