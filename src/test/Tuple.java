package test;

import java.util.ArrayList;

public class Tuple {

	public int label;
	public ArrayList<Double> features;
	public double score;
	public String itemId ;
	
	public Tuple(int label,ArrayList<Double> features,String itemId){
		this.label=label;
		this.features=features;
		this.itemId=itemId;
	}
}
