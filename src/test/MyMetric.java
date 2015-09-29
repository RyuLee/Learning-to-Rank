package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.math.*;

import ciir.umass.edu.utilities.SimpleMath;

public class MyMetric {
	
	
	public int userId;
	
	public ArrayList<Tuple> theTuple;
	
	public MyMetric(){
		
	}
	
	public MyMetric(int userId,ArrayList<Tuple> theTuple){
		this.userId=userId;
		this.theTuple=theTuple;	
	}

	
	public void sortMyMetricByLable(MyMetric mm){
		
		Comparator<Tuple> c = new Comparator<Tuple>() {
			
			@Override
			public int compare(Tuple o1, Tuple o2) {
				
				// TODO Auto-generated method stub
				return o2.label-o1.label;
			}
		};
		mm.theTuple.sort(c);
	}
	public void sortMyMetricByScore(MyMetric mm){
		
		Comparator<Tuple> c = new Comparator<Tuple>() {
			
			@Override
			public int compare(Tuple o1, Tuple o2) {
				if((o2.score-o1.score)<0.0)   
                    return -1;  
                else if((o2.score-o1.score)>0.0)  
                    return 1;  
                else return 0;  
			}
		};
		mm.theTuple.sort(c);
	}
	public void sortMyMetricByfirstValue(MyMetric mm){
		Comparator<Tuple> c = new Comparator<Tuple>(){
			@Override
			public int compare(Tuple o1, Tuple o2) {
				if((o2.features.get(0)-o2.features.get(0))<0)   
                    return -1;  
                else if((o2.features.get(0)-o2.features.get(0))>0)  
                    return 1;  
                else return 0;  
			}
		};
		mm.theTuple.sort(c);
	}
	public static void getScore(HashMap<Integer,MyMetric> rgMap,BufferedReader bf) throws IOException{
		Iterator writer_it=rgMap.entrySet().iterator();
		while(writer_it.hasNext())
		{
			
			Map.Entry<Integer,MyMetric> mm  = (Entry<Integer, MyMetric>) writer_it.next();
			MyMetric theMetric = mm.getValue();
			ArrayList<Tuple> tuples = theMetric.theTuple;
			for(Tuple tp :tuples){
				String score = bf.readLine();
				tp.score = Double.valueOf(score);
			}

		}

	}
	public  double getPercision_1(MyMetric mmSortByScore,int N){
		double precision =0;
		int hit=0;
	for(int j=0;j<this.theTuple.size();j++){//修改数组界以适应训练集
		String targetId = mmSortByScore.theTuple.get(j).itemId;
			for(int i=0;i<this.theTuple.size();i++){
				String theId = this.theTuple.get(i).itemId;
				if(theId == targetId){
					hit++;
					break;
				}
			}
		
	}
	precision=(double)hit/(double)this.theTuple.size();
		return precision;
	}
	public double getPercision_2(MyMetric mmSortByScore,int N){
		double percision = 0;
		int hit=0;
	for(int j=0;j<N;j++){
		
		if(mmSortByScore.theTuple.get(j).label>=3)
			hit++;
		}
		percision=(double)hit/(double)N;
		return percision;
	}
	
	public MyMetric copyMetric(MyMetric mm){
		ArrayList<Tuple> tp = new ArrayList<Tuple>();
		for(Tuple tuple :theTuple){
			tp.add(tuple);
		}
		MyMetric newMm = new MyMetric(mm.userId,tp);
		return newMm;
	}
	
	public double getNDCG(MyMetric mm,int N ){
		double NDCGScore = 0;
		double DCGScore = 0;
		double IDCGScore= 0;
		for(int i = 0;i<mm.theTuple.size();i++){
			DCGScore += Math.pow(2.0, mm.theTuple.get(i).label-1.0)/SimpleMath.logBase2(i+2);
			IDCGScore += Math.pow(2.0, this.theTuple.get(i).label-1.0)/SimpleMath.logBase2(i+2);
		}
		
		
		NDCGScore = DCGScore/IDCGScore;
		
		return NDCGScore;
		
	}


}
