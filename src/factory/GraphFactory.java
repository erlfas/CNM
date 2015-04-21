package factory;

import enumerations.EdgeType;
import enumerations.GraphType;
import enumerations.WeightType;
import no.graphs.DirectedGraph;
import no.graphs.EdgeI;
import no.graphs.Graph;
import no.graphs.GraphI;
import no.graphs.NodeI;
import no.graphs.UndirectedGraph;
import no.graphs.Weight;

public class GraphFactory extends AbstractFactory {

	@Override
	GraphI getGraph(GraphType graphType, int numVertices) {
		
		switch(graphType) {
		case DIRECTED:
			return new DirectedGraph();
		case UNDIRECTED:
			return new UndirectedGraph();
		default:
			return null;
		}
	}

	@Override
	EdgeI getEdge(EdgeType edgeType, NodeI u, NodeI v) {
		return null;
	}

	@Override
	Weight getWeight(WeightType weightType) {
		return null;
	}
}
