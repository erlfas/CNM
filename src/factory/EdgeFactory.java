package factory;

import enumerations.EdgeType;
import enumerations.GraphType;
import enumerations.WeightType;
import no.graphs.DirectedEdge;
import no.graphs.DirectedWeightedEdge;
import no.graphs.EdgeI;
import no.graphs.GraphI;
import no.graphs.NodeI;
import no.graphs.UndirectedEdge;
import no.graphs.UndirectedWeightedEdge;
import no.graphs.Weight;

public class EdgeFactory extends AbstractFactory {

	@Override
	GraphI getGraph(GraphType graphType, int numVertices) {
		return null;
	}

	@Override
	EdgeI getEdge(EdgeType edgeType, NodeI u, NodeI v) {
		
		switch (edgeType) {
		case UNDIRECTED:
			return new UndirectedEdge(u,v);
		case DIRECTED:
			return new DirectedEdge(u,v);
		case DIRECTED_WEIGHTED:
			return new DirectedWeightedEdge(u, v);
		case UNDIRECTED_WEIGHTED:
			return new UndirectedWeightedEdge(u, v);
		default:
			return null;
		}
	}

	@Override
	Weight getWeight(WeightType weightType) {
		return null;
	}
}
