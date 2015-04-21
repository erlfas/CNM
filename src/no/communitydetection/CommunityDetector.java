package no.communitydetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import no.datastructures.IndexMaxPQ;
import no.datastructures.MergePair;
import no.graphs.Graph;
import no.graphs.Metagraph;
import no.utilities.MinMax;
import no.datastructures.Pair;

public class CommunityDetector {

	private Metagraph metagraph;
	private Graph graph;
	private int numVertices;
	private int numEdges;
	private Double modularity;
	private int[] degrees;
	/**
	 * The fraction of ends of edges that are attached
	 * to vertices in a given community.
	 * 
	 * a[i] gives this value for community i.
	 */	
	private Double[] a;
	private ArrayList<TreeMap<Integer,Double>> modularityChange; // (neighbor, modChange)
	private ArrayList<IndexMaxPQ<Double>> modularityChangeMaxHeap; // 	
	private IndexMaxPQ<ModChangeTriple> maxHeap; // *

	public CommunityDetector(Graph graph) {
		this.graph = graph;
		this.metagraph = new Metagraph(graph);
		this.numVertices = graph.numNodes();
		this.numEdges = graph.numEdges();
		this.degrees = new int[numVertices];
		this.modularityChange = new ArrayList<TreeMap<Integer, Double>>(numVertices);

		this.modularityChangeMaxHeap = new ArrayList<IndexMaxPQ<Double>>();
		this.maxHeap = new IndexMaxPQ<ModChangeTriple>(numVertices + 10);
	}

	public void detectCommunities() throws Exception {
		initialize();

		while (maxHeap.size() > 0 && maxHeap.maxKey().modularityChange() > 0) {			
			Pair<ModChangeTriple> amalgamationPair = maxHeap.deleteMax();
			MergePair mergePair = new MergePair(amalgamationPair,metagraph);	
			if (mergePair.i() == mergePair.j()) {
				System.out.printf("***** (%d|%d) = (%d|%d) discarded. *****\n",amalgamationPair.key().i(),
						amalgamationPair.key().j(), mergePair.i(), mergePair.j());
				continue;
			}
			mergeCommunities(mergePair.i(), mergePair.j());
			modularity = modularity + amalgamationPair.key().modularityChange();
		}
		
		printCommunityIDs();
	}

	private void initialize() {
		determineVertexDegrees();
		this.modularity = initialModularity();
		computeInitialFractionOfEdgesIncidentUpon();
		calculateInitialModularityChanges();
		initializeMaxHeap();
	}

	/**
	 * Update the sparse modularity change matrix when communities
	 * i and j are being merged. The method assumes that i < j.
	 * 
	 * @param i one community
	 * @param j another community
	 * @throws Exception 
	 */
	private void mergeCommunities(int i, int j) throws Exception {

		System.out.printf("***** Merging communities %d and %d *****\n",i,j);		

		Integer[] neighborhoodOfI = metagraph.neighborhood(i, j);
		Integer[] neighborhoodOfJ = metagraph.neighborhood(j, i);
		printNeighborhood(i, neighborhoodOfI);
		printNeighborhood(j, neighborhoodOfJ);
		
		int pi = 0;
		int pj = 0;

		metagraph.merge(i, j);

		modularityChange.get(i).remove(j); // O(log n)
		modularityChangeMaxHeap.get(i).delete(j); // O(log n)

		// Handle every neighbor k of i or of j or of both
		while (true) {

			if (pi >= neighborhoodOfI.length && pj < neighborhoodOfJ.length) {

				int k = neighborhoodOfJ[pj];
				updateModularityChangeCase3(i,j,k);
				++pj;

			} else if (pi < neighborhoodOfI.length && pj >= neighborhoodOfJ.length) {

				int k = neighborhoodOfI[pi];
				updateModularityChangeCase2(i,j,k);
				++pi;

			} else if (pi >= neighborhoodOfI.length && pj >= neighborhoodOfJ.length) {

				// all elements are handled and the search is finished
				break;

			} else if (neighborhoodOfI[pi].equals(neighborhoodOfJ[pj])) {

				int k = neighborhoodOfI[pi];
				updateModularityChangeCase1(i, j, k);
				++pi;
				++pj;

			} else if (neighborhoodOfI[pi].compareTo(neighborhoodOfJ[pj]) < 0) {

				int k = neighborhoodOfI[pi];
				updateModularityChangeCase2(i,j,k);
				++pi;

			} else if (neighborhoodOfI[pi].compareTo(neighborhoodOfJ[pj]) > 0) {

				int k = neighborhoodOfJ[pj];
				updateModularityChangeCase3(i,j,k);
				++pj;
				
			} else {
				
				throw new Exception("neighbor not covered by any case");

			}			
		}

		modularityChange.get(j).clear(); // O(1)
		modularityChangeMaxHeap.set(j, null); // O(1)
		maxHeap.delete(j);
		maxHeap.delete(i);
		
		// Update element from row i on max heap
		int maxIndexInRowI = modularityChangeMaxHeap.get(i).maxIndex();
		Double maxModChangeInRowI = modularityChangeMaxHeap.get(i).keyOf(maxIndexInRowI);
		ModChangeTriple newMaxElmFromRowI = new ModChangeTriple(i,maxIndexInRowI,maxModChangeInRowI);		
		maxHeap.insert(i, newMaxElmFromRowI);

		updateA(i,j); // O(1)
	}
	
	private void printNeighborhood(int i, Integer[] neighborhood) {
		System.out.printf("[%d = %d]: ", i, i+1);
		for (int n : neighborhood) {
			System.out.printf("(%d = %d),", n, n+1);
		}
		System.out.println();
	}

	/**
	 * Update a[i] to [ai] + a[j].
	 * 
	 * @param i
	 * @param j
	 */
	private void updateA(int i, int j) {
		a[i] = a[i] + a[j];
	}

	private ModChangePair maxElement(TreeMap<Integer,Double> modChangeInRowX) {
		Double maxValue = new Double(Integer.MIN_VALUE);
		Integer maxKey = 0;

		for (Entry<Integer,Double> entry : modChangeInRowX.entrySet()) {
			if (entry.getValue() > maxValue) {
				maxValue = entry.getValue();
				maxKey = entry.getKey();
			}
		}

		return new ModChangePair(maxKey, maxValue);
	}

	private void updateMaxHeap(int i, int j, int k) {
		// If max element in row k were i or j,
		// then there may be a new max element since 
		// the dQ value of both i and j are decreased
		// such that some other community may have gained the
		// max position.

		
		// TODO
		
		ModChangeTriple prevMaxElmFromRowKOnMaxHeap = maxHeap.keyOf(k);
		if (prevMaxElmFromRowKOnMaxHeap != null) {
			int currentMaxIndexInRowK = modularityChangeMaxHeap.get(k).maxIndex();
			Double currentMaxModChangeInRowK = modularityChangeMaxHeap.get(k).keyOf(currentMaxIndexInRowK);
			Double currentMaxModChangeFromRowKOnMaxHeap = maxHeap.keyOf(k).modularityChange();

			if (currentMaxModChangeInRowK > currentMaxModChangeFromRowKOnMaxHeap) {
				ModChangeTriple newMaxElmFromRowK = new ModChangeTriple(k,currentMaxIndexInRowK,currentMaxModChangeInRowK);
				maxHeap.increaseKey(k, newMaxElmFromRowK);
			}
		}
	}

	/**
	 * Case 1: Three communities i,j,k are pairwise connected.
	 * 
	 * @param i a community
	 * @param j a second community
	 * @param k a third community
	 */
	private void updateModularityChangeCase1(int i, int j, int k) {

		Double newModularityChange = modularityChange.get(i).get(k) + modularityChange.get(j).get(k);
		modularityChange.get(i).put(k, newModularityChange); // O(log n)
		modularityChange.get(k).put(i, newModularityChange); // O(log n)
		modularityChange.get(k).remove(j); // O(log n)

		modularityChangeMaxHeap.get(i).increaseKey(k, newModularityChange); // O(log n)
		modularityChangeMaxHeap.get(k).increaseKey(i, newModularityChange); // O(log n)		
		modularityChangeMaxHeap.get(k).delete(j); // 2 * O(log n)

		updateMaxHeap(i,j,k);
	}

	/**
	 * Case 2: i and k are connected but j and k are not connected.
	 * 
	 * @param i a community
	 * @param j a second community
	 * @param k a third community
	 */
	private void updateModularityChangeCase2(int i, int j, int k) {

		Double newModularityChange = modularityChange.get(i).get(k) - 2 * a[j] * a[k];
		modularityChange.get(i).put(k, newModularityChange); // O(log n)
		modularityChange.get(k).put(i, newModularityChange); // O(log n)
		modularityChange.get(k).remove(j);

		modularityChangeMaxHeap.get(i).decreaseKey(k, newModularityChange); // O(log n)
		modularityChangeMaxHeap.get(k).decreaseKey(i, newModularityChange); // O(log n)
		modularityChangeMaxHeap.get(k).delete(j); // 2 * O(log n)

		updateMaxHeap(i,j,k);
	}

	/**
	 * Case 3: j and k are connected but i and k are not connected.
	 * 
	 * @param i a community
	 * @param j a second community
	 * @param k a third community
	 */
	private void updateModularityChangeCase3(int i, int j, int k) {

		Double newModularityChange = modularityChange.get(j).get(k) - 2 * a[i] * a[k];
		modularityChange.get(i).put(k, newModularityChange); // O(log n)
		modularityChange.get(k).put(i, newModularityChange); // O(log n)
		modularityChange.get(k).remove(j); // O(log n) remove row j altogether

		// TODO If-testene nedenfor skal være unødvendige
		// Kanskje det var behov for dem pga. at algoritmen ikke 
		// brukte find-prosedyren til å finne de faktiske community ID-ene 
		// på et visst tidspunkt i utviklingen av algoritmen.
		
		if (modularityChangeMaxHeap.get(i).contains(k)) {
			
			Double current = modularityChangeMaxHeap.get(i).keyOf(k);
			
			if (newModularityChange > current)		
				modularityChangeMaxHeap.get(i).increaseKey(k, newModularityChange);
			else
				modularityChangeMaxHeap.get(i).decreaseKey(k, newModularityChange);
		}
		else
			modularityChangeMaxHeap.get(i).insert(k, newModularityChange); // O(log n)

		if (modularityChangeMaxHeap.get(k).contains(i)) {
			
			Double current = modularityChangeMaxHeap.get(k).keyOf(i);
			
			if (newModularityChange > current)
				modularityChangeMaxHeap.get(k).increaseKey(i, newModularityChange);
			else if (newModularityChange < current)
				modularityChangeMaxHeap.get(k).decreaseKey(i, newModularityChange);
		}
		else
			modularityChangeMaxHeap.get(k).insert(i, newModularityChange); // O(log n)
		
		
		modularityChangeMaxHeap.get(k).delete(j); // 2 * O(log n)

		updateMaxHeap(i,j,k);
	}

	/**
	 * Retrieves that neighborhood of i excluding j in ascending order.
	 * 
	 * @param i one community
	 * @param j another community that is a neighboring community of i
	 * @return
	 */
	public Integer[] neighborhoodOf(int i, int j) {
		Integer[] neighborhood = new Integer[modularityChange.get(i).size() - 1];
		NavigableSet<Integer> neighbors = modularityChange.get(i).navigableKeySet();

		int k = 0;
		for (Integer neighbor : neighbors) {
			if (neighbor != j) {
				neighborhood[k] = neighbor;
				++k;
			}
		}

		return neighborhood;
	}

	private void printCommunityIDs() {
		System.out.println("*** Community IDs ***");
		for (int i = 0; i < numVertices; ++i) {
			System.out.printf("Community ID of vertex %d: %d\n",i+1,metagraph.communityOf(i)+1);
		}
		System.out.println("*********************");
	}

	private void printMaxHeap() {
		System.out.println("***MAX HEAP***");
		for (Integer index : maxHeap) {
			ModChangeTriple key = maxHeap.keyOf(index);
			System.out.printf("(%d|%d|%f)\n",key.i(),key.j(),key.modularityChange());
		}
	}

	private void printDeltaQ() {
		System.out.println("***MODULARITY CHANGE ***");
		for (int i = 0; i < modularityChange.size(); ++i) {
			if (modularityChange.get(i) != null && !modularityChange.get(i).isEmpty()) {
				System.out.print(i + ": ");
				TreeMap<Integer,Double> deltaQIJ = modularityChange.get(i);
				Set<Integer> neighbors = deltaQIJ.keySet();
				for (int neighbor : neighbors) {
					System.out.printf("(%d|%f) - ",neighbor,deltaQIJ.get(neighbor));
				}
				System.out.println();
			}
		}
	}

	private Double initialModularity() {

		Double initialModularity = (-1.0)/(4.0 * numEdges * numEdges);
		Double sum = 0.0;

		for (int v = 0; v < numVertices; ++v)
			sum = sum + (degrees[v] * degrees[v]);

		initialModularity = initialModularity * sum;

		return initialModularity;
	}

	private void initializeMaxHeap() {
		for (int i = 0; i < numVertices; ++i) {
			int maxIndexNeighbor = modularityChangeMaxHeap.get(i).maxIndex();
			Double maxModChange = modularityChangeMaxHeap.get(i).keyOf(maxIndexNeighbor);
			ModChangeTriple triple = new ModChangeTriple(i, maxIndexNeighbor, maxModChange);
			maxHeap.insert(i, triple);
			
//			System.out.printf("H.add([%d|%d|%f] = [%d|%d|%f])\n",i,maxIndexNeighbor,maxModChange,
//					i+1,maxIndexNeighbor+1,maxModChange);
		}
	}

	private void calculateInitialModularityChanges() {

		for (int i = 0; i < numVertices; ++i) {
			modularityChange.add(i, new TreeMap<Integer,Double>());
			modularityChangeMaxHeap.add(i, new IndexMaxPQ<Double>(numVertices));
		}

//		System.out.println("*** Making initial DeltaQ ***");
		for (int i = 0; i < numVertices; ++i) {
			for (int j : graph.neighborhood(i)) {
				double numerator = 2.0 * numEdges - degrees[i] * degrees[j];
				double denominator = 4.0 * numEdges * numEdges;
				Double fraction = numerator/denominator;
				
//				System.out.printf("(%d|%d|%f) = (%d|%d|%f)\n",i,j,fraction,i+1,j+1,fraction);

				modularityChange.get(i).put(j, fraction);
				modularityChangeMaxHeap.get(i).insert(j, fraction); // O(log n)
			}
		}
	}

	private void computeInitialFractionOfEdgesIncidentUpon() {

		a = new Double[numVertices];

		for (int v = 0; v < numVertices; ++v)
			a[v] = degrees[v]/(2.0 * numEdges);
	}

	private void determineVertexDegrees() {
		for (int v = 0; v < numVertices; ++v)
			degrees[v] = graph.numNeighbors(v);
	}
}
