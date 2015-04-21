package no.datastructures;

import no.communitydetection.ModChangeTriple;
import no.graphs.Metagraph;
import no.utilities.MinMax;

public class MergePair {

	private int i;
	private int j;
	
	public MergePair(Pair<ModChangeTriple> pair, Metagraph metagraph) {
		int a = pair.key().i();
		int b = pair.key().j();
		
		int rootA = metagraph.communityOf(a);
		int rootB = metagraph.communityOf(b);
		
		MinMax minMax = new MinMax(rootA,rootB);
		this.i = minMax.min();
		this.j = minMax.max();
	}
	
	/**
	 * The community with lowest index.
	 * 
	 * @return
	 */
	public int i() {
		return this.i;
	}
	
	/**
	 * The community with highest index.
	 * 
	 * @return
	 */
	public int j() {
		return this.j;
	}
}
