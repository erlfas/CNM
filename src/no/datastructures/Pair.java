package no.datastructures;

public class Pair<T extends Comparable<T>> {
	private T key;
	private int index;
	
	public Pair(T key, int index) {
		this.key = key;
		this.index = index;
	}
	
	public T key() {
		return key;
	}
	
	public int index() {
		return index;
	}
}
