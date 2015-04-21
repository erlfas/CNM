package no.tests;

import static org.junit.Assert.*;

import java.util.TreeSet;

import no.graphs.Graph;
import no.graphs.Metagraph;
import no.io.InputReader;

import org.junit.Test;

public class MetagraphTester {
	
	private Metagraph metagraph;
	private Graph graph;
	
	public MetagraphTester() {
		
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
	public void testNeighborhood() {
		
		System.out.println("*** testNeighborhood start ***");
		
		for (int v = 0; v < graph.numNodes(); ++v) {
			TreeSet<Integer> neighborhood = metagraph.neighborhood(v);
			System.out.printf("[%d]: ", v);
			for (Integer n : neighborhood) {
				System.out.print(n + ", ");
			}
			System.out.println();
		}
		
		TreeSet<Integer> neighborhood = metagraph.neighborhood(3);
		TreeSet<Integer> expected = new TreeSet<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(5);
		if (neighborhood.equals(expected)) {
			org.junit.Assert.assertEquals(expected, neighborhood);
		}
		
		System.out.println("*** testNeighborhood start ***");
	}
	
	@Test
	public void testZachary() {
		
		System.out.println("*** testZachary start ***");
		
		InputReader inputReader = new InputReader("C:\\Users\\Erlend\\Desktop\\karate.net");
		Graph graphZachary = inputReader.readPajekInput();
		
		System.out.println("*** GRAPH ***");
		System.out.println("# nodes = " + graphZachary.numNodes());
		for (int v = 0; v < graphZachary.numNodes(); ++v) {
			System.out.printf("[%d]: ",v);
			for (int neighbor : graphZachary.neighborhood(v)) {
				System.out.printf("%d, ",neighbor);
			}
			System.out.println();
		}	
		
		Metagraph metagraphZachary = new Metagraph(graphZachary);
		
		System.out.println("\n*** METAGRAPH ***");
		System.out.println("# communities = " + metagraphZachary.numCommunities());
		for (int c = 0; c < metagraphZachary.numCommunities(); ++c) {			
			TreeSet<Integer> neighborhood = metagraphZachary.neighborhood(c);
			System.out.printf("# neighbors of %d = %d\n",c,neighborhood.size());
			System.out.printf("[%d]: ", c);
			
			for (Integer n : neighborhood) {
				System.out.printf("%d, ", n);
			}
			System.out.println();
		}
		
		Integer[] neighborsOf1 = metagraphZachary.neighborhood(1,17);
		Integer[] neighborsOf17 = metagraphZachary.neighborhood(17,1);
		
		Integer[] expected1 = new Integer[] {0,2,3,7,13,19,21,30};
		Integer[] expected17 = new Integer[] {0};
		
		org.junit.Assert.assertArrayEquals(expected1, neighborsOf1);
		org.junit.Assert.assertArrayEquals(expected17, neighborsOf17);
		
		System.out.println("*** testZachary end ***");
	}

	@Test
	public void test() {
		
	}

}
