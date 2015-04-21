package no.tests;

import static org.junit.Assert.*;
import no.datastructures.IndexMaxPQ;

import org.junit.Test;

public class IndexMaxPQTest {

	@Test
	public void test() {
		IndexMaxPQ<Double> maxHeap = new IndexMaxPQ<Double>(50);
		maxHeap.insert(3, new Double(20));
		maxHeap.insert(19, new Double(5));
		maxHeap.insert(37, new Double(16));
		
		System.out.println(maxHeap.deleteMax());
		System.out.println(maxHeap.deleteMax());
		System.out.println(maxHeap.deleteMax());
	}

}
