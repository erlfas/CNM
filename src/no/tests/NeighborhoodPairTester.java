package no.tests;

import no.communitydetection.NeighborhoodPair;
import no.graphs.Graph;
import no.graphs.Metagraph;

import org.junit.Test;

public class NeighborhoodPairTester {

	private Metagraph metagraph;
	private Graph graph;
	
	public NeighborhoodPairTester() {
		this.graph = new Graph(8);
		this.graph.addEdge(0, 1);
		this.graph.addEdge(0, 2);
		this.graph.addEdge(0, 3);
		this.graph.addEdge(1, 2);
		this.graph.addEdge(1, 3);
		this.graph.addEdge(2, 3);
		
		this.graph.addEdge(3, 5);
		
		this.graph.addEdge(4, 5);
		this.graph.addEdge(4, 6);
		this.graph.addEdge(4, 7);
		this.graph.addEdge(5, 6);
		this.graph.addEdge(5, 7);
		this.graph.addEdge(6, 7);
		
		this.metagraph = new Metagraph(graph);
	}
	
	@Test
	public void test() {
		NeighborhoodPair pair1 = new NeighborhoodPair(3, 5, metagraph);
		Integer[] actualI = pair1.neighborhoodOfI();
		Integer[] actualJ = pair1.neighborhoodOfJ();
		Integer[] expectedI = new Integer[] {0,1,2};
		Integer[] expectedJ = new Integer[] {4,6,7};		
		org.junit.Assert.assertArrayEquals(expectedI, actualI);
		org.junit.Assert.assertArrayEquals(expectedJ, actualJ);
		
		NeighborhoodPair pair2 = new NeighborhoodPair(4, 7, metagraph);
		actualI = pair2.neighborhoodOfI();
		actualJ = pair2.neighborhoodOfJ();
		expectedI = new Integer[] {5,6};
		expectedJ = new Integer[] {5,6};
		org.junit.Assert.assertArrayEquals(expectedI, actualI);
		org.junit.Assert.assertArrayEquals(expectedJ, actualJ);
	}

}
