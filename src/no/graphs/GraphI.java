package no.graphs;

public interface GraphI {
	int numNodes();
	int numEdges();
	void addEdge(EdgeI e);
	boolean areNeighbors(NodeI i, NodeI j);
	Iterable<NodeI> neighborhood(NodeI i);
	int numNeighbors(NodeI i);
}
