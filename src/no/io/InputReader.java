package no.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import no.graphs.Graph;
import no.utilities.MinMax;

public class InputReader {

	private String fileName;
	
	public InputReader(String fileName) {
		this.fileName = fileName;
	}
	
	private class Edge {
		private int start;
		private int end;
		
		public Edge(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
	
	public Graph readPajekInput() {
		
		Graph graph = null;
		
		try {
			
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			StringTokenizer tokenizer = null;
			
			line = bufferedReader.readLine();
			tokenizer = new StringTokenizer(line);
			tokenizer.nextToken();
			int numVertices = Integer.parseInt(tokenizer.nextToken());
			
			for (int i = 0; i < numVertices; ++i)
				line = bufferedReader.readLine();
			
			bufferedReader.readLine();
			
			graph = new Graph(numVertices);
			
			while ((line = bufferedReader.readLine()) != null) {
				tokenizer = new StringTokenizer(line);
				int u = Integer.parseInt(tokenizer.nextToken());
				int v = Integer.parseInt(tokenizer.nextToken());
				
				graph.addEdge(u-1, v-1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return graph;
	}
	
	public Graph readAaronInput() {
		
		Graph graph = null;
		
		try {
			
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			StringTokenizer tokenizer = null;
			
			ArrayList<Edge> edgeList = new ArrayList<Edge>();
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			
			while ((line = bufferedReader.readLine()) != null) {
				tokenizer = new StringTokenizer(line);
				int u = Integer.parseInt(tokenizer.nextToken());
				int v = Integer.parseInt(tokenizer.nextToken());
				
				edgeList.add(new Edge(u,v));
				
				MinMax minMax = new MinMax(u,v);
				int locMin = minMax.min();
				int locMax = minMax.max();
				if (locMax > max) {
					max = locMax;
				}
				if (locMin < min) {
					min = locMin;
				}
			}
			
			int numVertices = 0;
			
			if (min == 0)
				numVertices = max + 1;
			else if (min == 1)
				numVertices = max;
			
			graph = new Graph(numVertices);
			
			if (min == 1) {
				for (Edge e : edgeList) {
					System.out.printf("(%d|%d)\n",e.start-1,e.end-1);
					graph.addEdge(e.start-1, e.end-1);
				}
			} else {
				for (Edge e : edgeList) {
					System.out.printf("(%d|%d)",e.start,e.end);
					graph.addEdge(e.start, e.end);
				}
			}			
			
			return graph;
			
		} catch (Exception e) {
			
		}
		
		return graph;		
	}
}
