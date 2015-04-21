package no.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;


public class Metagraph {

	private int numCommunities;
	private ArrayList<HashMap<Integer,Boolean>> neighbors;
	private int[] selfEdges;
	private int[] parent;
	private int[] degrees;
	
	public Metagraph(Graph graph) {
		this.numCommunities = graph.numNodes();
		
		this.selfEdges = new int[numCommunities];
		
		this.degrees = new int[numCommunities];
		
		this.parent = new int[numCommunities];
		
		this.neighbors = new ArrayList<HashMap<Integer,Boolean>>();
		
		for (int i = 0; i < numCommunities; ++i) {
			makeSet(i);
			
			degrees[i] = graph.numNeighbors(i);
			
			neighbors.add(i, new HashMap<Integer,Boolean>());
			for (int neighbor : graph.neighborhood(i))
				neighbors.get(i).put(neighbor,true);
		}
	}
	
	public boolean areNeighbors(int i, int j) {
		if (neighbors.get(i).containsKey(j))
			return true;
		else
			return false;
	}
	
	public boolean areInSameCommunity(int i, int j) {
		
		int iRoot = find(i);
		int jRoot = find(j);
		
		if (iRoot == jRoot)
			return true;
		else
			return false;
	}
	
	private void makeSet(int vertex) {
		parent[vertex] = vertex;
	}
	
	private int find(int v) {
		if (parent[v] == v)
			return v;
		else
			return find(parent[v]);
	}

	private void union(int u, int v) {
		
		int uRoot = find(u);
		int vRoot = find(v);

		if (uRoot == vRoot)
			return;

		if (uRoot < vRoot) {
			parent[vRoot] = uRoot;
			parent[uRoot] = uRoot;
		} else {
			parent[uRoot] = vRoot;
			parent[vRoot] = vRoot;
		}
	}
	
	public int numCommunities() {
		return this.numCommunities;
	}
	
	public int communityOf(int v) {
		
		int root = find(v);

		return root;
	}
	
	public int degree(int v) {
		return degrees[v];
	}
	
	/**
	 * Retrieve the neighborhood of community v.
	 * 
	 * @param v
	 * @return
	 */
	public TreeSet<Integer> neighborhood(int v) {	
		TreeSet<Integer> result = new TreeSet<Integer>();
		Set<Integer> neighborhood = neighbors.get(v).keySet();
		for (Integer neighbor : neighborhood)
			result.add(communityOf(neighbor));
		
		return result;
	}
	
	/**
	 * Retrieve the neighborhood of community v but
	 * exclude community of exclude if v and exclude
	 * are neighbors.
	 * 
	 * @param v
	 * @param exclude
	 * @return
	 */
	public Integer[] neighborhood(int v, int exclude) {
		TreeSet<Integer> neighborhood = neighborhood(v);
		neighborhood.remove(v);
		neighborhood.remove(communityOf(v));
		neighborhood.remove(exclude);
		neighborhood.remove(communityOf(exclude));
		Integer[] result = new Integer[neighborhood.size()];
		int i = 0;
		for (Integer n : neighborhood) {
			result[i] = n;
			++i;
		}
		
		return result;
	}
	
	public void merge(int i, int j) {
		union(i,j);
		
		degrees[i] = degrees[i] + degrees[j];
		degrees[j] = 0;
		
		Set<Integer> neighborsofJ = neighbors.get(j).keySet();		
		for (Integer neighborOfJ : neighborsofJ)
			neighbors.get(i).put(neighborOfJ, true);
		
		neighbors.get(i).remove(i);
		neighbors.get(i).remove(j);
		neighbors.get(j).clear();
		
		++selfEdges[i];
		numCommunities = numCommunities - 1;
	}
}
