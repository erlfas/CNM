package factory;

import enumerations.EdgeType;
import enumerations.GraphType;
import enumerations.WeightType;
import no.graphs.EdgeI;
import no.graphs.GraphI;
import no.graphs.NodeI;
import no.graphs.Weight;

public abstract class AbstractFactory {
	abstract GraphI getGraph(GraphType graphType, int numVertices);
	abstract EdgeI getEdge(EdgeType edgeType, NodeI u, NodeI v);
	abstract Weight getWeight(WeightType weightType);
}
