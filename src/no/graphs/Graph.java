package no.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import no.utilities.MinMax;

public class Graph {
	private ArrayList<LinkedList<Integer>> graph;
	private ArrayList<Edge> edgeList;
	private int numNodes;
	private int numEdges;
	
	public Graph(ArrayList<LinkedList<Integer>> graph) {
		this.graph = graph;
		this.numNodes = graph.size();
		this.numEdges = 0;
		for (int i = 0; i < graph.size(); ++i)
			this.numEdges += graph.get(i).size();
	}
	
	public Graph(Graph graph) {
		this.graph = graph.graph;
		this.numNodes = graph.numNodes;
		this.numEdges = graph.numEdges;
		this.edgeList = graph.edgeList;
	}
	
	public Graph(int numNodes) {
		this.graph = new ArrayList<LinkedList<Integer>>(numNodes);
		for (int i = 0; i < numNodes; ++i)
			graph.add(i, new LinkedList<Integer>());
		
		this.edgeList = new ArrayList<Edge>(numNodes*numNodes);
		
		this.numNodes = numNodes;
		this.numEdges = 0;
	}
	
	/**
	 * Returns whether the graph is empty or not.
	 * 
	 * The graph is empty if 
	 * 1) it is a null pointer
	 * 2) it has 0 nodes and 0 edges
	 * 
	 * @return true iff the graph is empty
	 */
	public boolean empty() {
		if (this.graph == null)
			return true;
		else if (numNodes == 0 && numEdges == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns the number of nodes in the graph.
	 * 
	 * @return
	 */
	public int numNodes() {
		return this.numNodes;
	}
	
	/**
	 * Returns the number of edges in the graph.
	 * 
	 * @return
	 */
	public int numEdges() {
		return this.numEdges;
	}
	
	/**
	 * Adds the edge uv to the graph.
	 * 
	 * @param u
	 * @param v
	 */
	public void addEdge(int u, int v) {
		if (u < 0 || u >= numNodes)
			throw new IndexOutOfBoundsException();
		if (v < 0 || v >= numNodes())
			throw new IndexOutOfBoundsException();
		
		if (areNeighbors(u, v))
			return;
		
		++this.numEdges;
		
		graph.get(u).add(v);
		graph.get(v).add(u);
		
//		MinMax minMax = new MinMax(u,v);
//		
//		edgeList.add(new Edge(minMax.min(),minMax.max()));
	}
	
	/**
	 * Returns the neighborhood of vertex v, i.e. all vertices adjacent to v.
	 * 
	 * @param v a vertex
	 * @return the neighborhood of vertex v, i.e. all vertices adjacent to v.
	 */
	public Iterable<Integer> neighborhood(int v) {
		if (v < 0 || v >= numNodes())
			throw new IndexOutOfBoundsException();
		
		return graph.get(v);
	}
	
	public boolean areNeighbors(int u, int v) {
		for (int n : neighborhood(u)) {
			if (n == v)
				return true;
		}
		
		return false;
	}
	
	public int numNeighbors(int v) {
		return graph.get(v).size();
	}
	
	public ArrayList<Edge> edgeList() {
		return edgeList;
	}
	
	/**
	 * Removes the specified vertex together with all incident edges.
	 * 
	 * @param vertex
	 * @return
	 */
	public boolean remove(int v) {
		
		Integer vertex = new Integer(v);
		
		for (int i = 0; i < graph.size(); ++i)
			graph.get(i).remove(vertex);
		
		graph.get(v).clear();
		
		for (int i = 0; i < edgeList.size(); ++i)
			if (edgeList.get(i).start() == v || edgeList.get(i).end() == v)
				edgeList.remove(i);
		
		return true;
	}
	
//	public boolean remove(Edge edge) {
//		
//		Edge edgePrime = new Edge(edge.end(), edge.start());
//		
//		edgeList.remove(edge);
//		edgeList.remove(edgePrime); // safeguarding in case of undirected graph
//		
//		graph.get(edge.start()).remove(new Integer(edge.end()));
//		graph.get(edge.end()).remove(new Integer(edge.start()));
//		
//		--numEdges;
//		
//		return true;
//	}
}
