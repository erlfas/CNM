package no.communitydetection;

import java.util.TreeSet;

import no.graphs.Metagraph;

public class NeighborhoodPair {
	
	private Integer[] neighborsOfI;
	private Integer[] neighborsOfJ;
	
	private Metagraph metagraph;
	
	public NeighborhoodPair(int i, int j, Metagraph metagraph) {
		this.metagraph = metagraph;
		this.neighborsOfI = buildNeighborhood(i,j);
		this.neighborsOfJ = buildNeighborhood(j,i);
	}
	
	/**
	 * Retrieves the neighborhood of community x but excludes
	 * community exclude.
	 * 
	 * @param x			the community whose neighborhood is to be retrieved
	 * @param exclude	if this community is a neighbor of x, then it is not in
	 * 					the neighborhood returned by this method
	 * @return			the neighborhood of x exluding exclude.
	 */
	private Integer[] buildNeighborhood(int x, int exclude) {
		TreeSet<Integer> neighborhoodUnclean = metagraph.neighborhood(x);
		TreeSet<Integer> neighborhoodClean = new TreeSet<Integer>();
		
		for (Integer n : neighborhoodUnclean)
			neighborhoodClean.add(metagraph.communityOf(n));
		
		neighborhoodClean.remove(exclude);
		
		Integer[] result = new Integer[neighborhoodClean.size()];
		int i = 0;
		for (Integer n : neighborhoodClean) {
			result[i] = n;
			++i;
		}
		
		return result;
	}
	
	public Integer[] neighborhoodOfJ() {
		return this.neighborsOfJ;
	}
	
	public Integer[] neighborhoodOfI() {
		return this.neighborsOfI;
	}
}
