package no.communitydetection;

import java.util.LinkedList;
import java.util.Stack;

public class Dendrogram {

	public class Pair {
		private int i;
		private int j;
		
		public Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
		
		public int i() {
			return this.i;
		}
		
		public int j() {
			return this.j;
		}
	}
	
	private Stack<Pair> stack;
	private int numJoins;

	public Dendrogram() {
		this.stack = new Stack<Pair>();
		this.numJoins = 0;
	}
	
	public int numJoins() {
		return this.numJoins;
	}
	
	public boolean add(int i, int j) {
		stack.add(new Pair(i,j));
		
		numJoins = numJoins + 1;
		
		return true;
	}
	
	/**
	 * Returns a LinkedList of joins, beginning with the first join
	 * made.
	 * 
	 * @return
	 */
	public LinkedList<Pair> joins() {		
		LinkedList<Pair> joins = new LinkedList<Pair>();
		for (Pair pair : stack)
			joins.add(pair);
		
		return joins;
	}
}
