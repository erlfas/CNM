import java.util.LinkedList;

import no.communitydetection.CommunityDetector;
import no.communitydetection.Dendrogram;
import no.graphs.Graph;
import no.io.InputReader;

public class Main {

	public static void main(String[] args) {
		
//		Graph graph1 = new Graph(12);
//		graph1.addEdge(0,1);
//		graph1.addEdge(0,2);
//		graph1.addEdge(0,3);
//		graph1.addEdge(1,2);
//		graph1.addEdge(1,3);
//		graph1.addEdge(2,3);
//		
//		graph1.addEdge(4,5);
//		graph1.addEdge(4,6);
//		graph1.addEdge(5,6);
//		
//		graph1.addEdge(7,8);
//		graph1.addEdge(7,9);
//		graph1.addEdge(7,10);
//		graph1.addEdge(7,11);
//		graph1.addEdge(8,9);
//		graph1.addEdge(8,10);
//		graph1.addEdge(8,11);
//		graph1.addEdge(9,10);
//		graph1.addEdge(9,11);
//		graph1.addEdge(10,11);
//		
//		graph1.addEdge(2,10);
//		graph1.addEdge(3,5);
//		graph1.addEdge(6,8);
//		
//		CommunityDetector cd1 = new CommunityDetector(graph1);
//		cd1.detectCommunities();
//		
//		Graph graph2 = new Graph(8);
//		graph2.addEdge(0,1);
//		graph2.addEdge(0,2);
//		graph2.addEdge(0,3);
//		graph2.addEdge(1,2);
//		graph2.addEdge(1,3);
//		graph2.addEdge(2,3);
//		
//		graph2.addEdge(3,5);
//		graph2.addEdge(1, 6);
//		
//		graph2.addEdge(4,5);
//		graph2.addEdge(4,6);
//		graph2.addEdge(4,7);
//		graph2.addEdge(5,6);
//		graph2.addEdge(5,7);
//		graph2.addEdge(6,7);
//		
//		CommunityDetector cd = new CommunityDetector(graph2);
//		cd.detectCommunities();
//		
//		Graph graph3 = new Graph(12);
//		graph3.addEdge(0,1);
//		graph3.addEdge(0,2);
//		graph3.addEdge(0,3);
//		graph3.addEdge(1,2);
//		graph3.addEdge(1,3);
//		graph3.addEdge(2,3);
//		
//		graph3.addEdge(4,5);
//		graph3.addEdge(4,6);
//		graph3.addEdge(4,7);
//		graph3.addEdge(5,6);
//		graph3.addEdge(5,7);
//		graph3.addEdge(6,7);
//		
//		graph3.addEdge(8,9);
//		graph3.addEdge(8,10);
//		graph3.addEdge(8,11);
//		graph3.addEdge(9,10);
//		graph3.addEdge(9,11);
//		graph3.addEdge(10,11);
//		
//		graph3.addEdge(3,8);
//		graph3.addEdge(2,4);
//		graph3.addEdge(7,10);
//		
//		CommunityDetector cd3 = new CommunityDetector(graph3);
//		cd3.detectCommunities();
		
		Integer[] a = new Integer[] {5};
		Integer[] b = new Integer[] {5};
		System.out.println(a.equals(b));
		System.out.println(a[0].equals(b[0]));
		System.out.println(a[0].compareTo(b[0]));
		
		InputReader inputReader = new InputReader("C:\\Users\\Erlend\\Desktop\\karate.net");
		Graph graph4 = inputReader.readPajekInput();
		
//		for (int i = 0; i < graph4.numNodes(); ++i) {
//			System.out.printf("[%d]: ",i+1);
//			for (int neighbor : graph4.neighborhood(i)) {
//				System.out.printf("%d, ",neighbor+1);
//			}
//			System.out.println();
//		}
		
		CommunityDetector cd4 = new CommunityDetector(graph4);
		try {
			cd4.detectCommunities();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
