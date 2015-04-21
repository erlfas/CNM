package no.graphs;

import java.util.ArrayList;
import java.util.LinkedList;

import no.utilities.MinMax;

public class UndirectedGraph implements GraphI {

	private ArrayList<LinkedList<NodeI>> graph;
	private ArrayList<EdgeI> edgeList;
	private int numNodes;
	private int numEdges;
	
	public UndirectedGraph() {
		// TODO Auto-generated constructor stub
	}
	
	public UndirectedGraph(int numNodes) {
		this.graph = new ArrayList<LinkedList<NodeI>>(numNodes);
		for (int i = 0; i < numNodes; ++i)
			graph.add(i, new LinkedList<NodeI>());
		
		this.edgeList = new ArrayList<EdgeI>(numNodes*numNodes);
		
		this.numNodes = numNodes;
		this.numEdges = 0;
	}
	
	@Override
	public int numNodes() {
		return this.numNodes;
	}

	@Override
	public int numEdges() {
		return this.numEdges;
	}

	@Override
	public void addEdge(EdgeI e) {
		
		int u = e.head().id();
		int v = e.tail().id();
		
		if (u < 0 || u >= numNodes)
			throw new IndexOutOfBoundsException();
		if (v < 0 || v >= numNodes())
			throw new IndexOutOfBoundsException();
		
		if (areNeighbors(e.head(), e.tail()))
			return;
		
		++this.numEdges;
		
		graph.get(u).add(e.tail());
		graph.get(v).add(e.head());
		
		MinMax minMax = new MinMax(u,v);
		
		if (minMax.min() == u) {
			edgeList.add(new Edge(e.head(),e.tail()));
		} else {
			edgeList.add(new Edge(e.tail(),e.head()));
		}
	}

	@Override
	public boolean areNeighbors(NodeI i, NodeI j) {
		for (NodeI n : neighborhood(i))
			if (n.equals(j))
				return true;
		
		return false;
	}

	@Override
	public Iterable<NodeI> neighborhood(NodeI node) {
		if (node.id() < 0 || node.id() >= numNodes())
			throw new IndexOutOfBoundsException();
		
		return graph.get(node.id());
	}

	@Override
	public int numNeighbors(NodeI i) {
		return graph.get(i.id()).size();
	}
}
