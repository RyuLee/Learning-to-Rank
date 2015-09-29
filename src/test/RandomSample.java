package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class RandomSample {

	HashMap<Integer,MyMetric> hm ;
	
	public RandomSample(HashMap<Integer,MyMetric> hm){
		this.hm = hm;
	}
	
	public HashMap<Integer,MyMetric> sampling(int N){
		HashMap<Integer,MyMetric> subset = new HashMap<Integer,MyMetric>();
		Random rd = new Random();
		ArrayList<Integer> indexOfSubset = new ArrayList<Integer>();
		while(indexOfSubset.size()<N){
			int index = rd.nextInt(hm.size()-1);
			if(!indexOfSubset.contains(index)) indexOfSubset.add(index);
		}
		for(int i :indexOfSubset){
			subset.put(i,hm.get(i));
		}
		return subset;
	}
}
