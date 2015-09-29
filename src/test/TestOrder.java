package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.*;

import ciir.umass.edu.learning.Evaluator;
import ciir.umass.edu.learning.RANKER_TYPE;

public class TestOrder{
	
	public static void main(String args[]) throws IOException{
		
		BufferedReader bf=new BufferedReader(new InputStreamReader(new FileInputStream(new File("data//movies_test_minus.json"))));
		BufferedReader bf_vu = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data//movies_user_info_vu.json"))));
		BufferedReader bf_fi = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data//movies_item_info_fi.json"))));
		BufferedReader bf_qu = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data//movies_user_info_mymedia.json"))));
		BufferedReader bf_pi = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data//movies_item_info_mymedia.json"))));
		String testPath = "testplace//testset//testorder(0_cosfv_0.2)_1.txt";
		String trainPath = "testplace//trainset//train(0_cosfv_0.2)_1.txt";
		File train_txt = new File(testPath);
		 
		if(!train_txt.exists()){
			
			train_txt.createNewFile();
		}
		
		PrintWriter pw = new PrintWriter(new FileOutputStream(train_txt));
	//	PrintWriter pw = null;
		String line = "";
		
		String line_vu = "";
		
		String line_fi = "";
		
		double result = 0;
		
		String writeLine="";
		
		ArrayList<ArrayList<String>> hs = new ArrayList<ArrayList<String>>();
		
		HashMap<String, Integer> user_id = new HashMap<String,Integer>();
	
		HashMap<String, Integer> hs2 = new HashMap<String, Integer>();
		
		HashMap<String, ArrayList<Double>> hs_vu = new HashMap<String, ArrayList<Double>>(); 
		
		HashMap<String, ArrayList<Double>> hs_fi = new HashMap<String, ArrayList<Double>>();
		
		HashMap<String, ArrayList<Double>> hs_qu = new HashMap<String, ArrayList<Double>>();
		
		HashMap<String, ArrayList<Double>> hs_pi = new HashMap<String, ArrayList<Double>>();

		int countNo = 0;
		
		int countNo2 = 0;
		
		bf_vu.readLine();
		
		
		//存储用户评论因子
		int countID = 0;
		while((line_vu = bf_vu.readLine())!=null){
			
			JSONObject jsonObject_vu =new JSONObject();
			
			JSONArray jsonArray_vu = new JSONArray();
			
			line_vu="["+line_vu+"]";
			
			jsonArray_vu = JSONArray.fromObject(line_vu);
			
			jsonObject_vu = jsonArray_vu.getJSONObject(0);

			if(!hs_vu.containsKey(jsonObject_vu.get("id"))){
				
				
				JSONArray doublearray = new JSONArray();
				
				ArrayList<Double> factors = new ArrayList<Double>();
				
				doublearray = jsonObject_vu.getJSONArray("factors");
				
				Iterator<?> it = doublearray.iterator();
				
				while(it.hasNext()){
					
					
					factors.add((Double)it.next());
					
				}
				
				
				hs_vu.put((String)jsonObject_vu.get("id"),factors );
				
				if(!user_id.containsKey(jsonObject_vu.get("id"))){
					
					user_id.put((String) jsonObject_vu.get("id"), countID);
					
					countID++;
					
				}

			}

		}
		bf_fi.readLine();
		
		while((line_fi = bf_fi.readLine())!=null){
			
			JSONObject jsonObject_fi =new JSONObject();
			
			JSONArray jsonArray_fi = new JSONArray();
			
			line_fi="["+line_fi+"]";
			
			jsonArray_fi = JSONArray.fromObject(line_fi);
			
			jsonObject_fi = jsonArray_fi.getJSONObject(0);
			
			if(!hs_fi.containsKey(jsonObject_fi.get("id"))){
				
				
				JSONArray doublearray = new JSONArray();
				
				ArrayList<Double> factors = new ArrayList<Double>();
				
				doublearray = jsonObject_fi.getJSONArray("factors");
				
				Iterator<?> it = doublearray.iterator();
				
				while(it.hasNext()){
					
					
					factors.add((Double)it.next());
					
				}
				
				
				hs_fi.put((String)jsonObject_fi.get("id"),factors );
				
				
			}
			
			
			
		}
		bf_qu.readLine();
		
		while((line_fi = bf_qu.readLine())!=null){
			
			JSONObject jsonObject_qu =new JSONObject();
			
			JSONArray jsonArray_qu = new JSONArray();
			
			line_fi="["+line_fi+"]";
			
			jsonArray_qu = JSONArray.fromObject(line_fi);
			
			jsonObject_qu = jsonArray_qu.getJSONObject(0);
			
			if(!hs_qu.containsKey(jsonObject_qu.get("id"))){
				
				
				JSONArray doublearray = new JSONArray();
				
				ArrayList<Double> factors = new ArrayList<Double>();
				
				doublearray = jsonObject_qu.getJSONArray("factors");
				
				Iterator<?> it = doublearray.iterator();
				
				while(it.hasNext()){
					
					
					factors.add((Double)it.next());
					
				}
				
				
				hs_qu.put((String)jsonObject_qu.get("id"),factors );
				
				
			}
			
			
			
		}
		bf_pi.readLine();
		
		while((line_fi = bf_pi.readLine())!=null){
			
			JSONObject jsonObject_pi =new JSONObject();
			
			JSONArray jsonArray_pi = new JSONArray();
			
			line_fi="["+line_fi+"]";
			
			jsonArray_pi = JSONArray.fromObject(line_fi);
			
			jsonObject_pi = jsonArray_pi.getJSONObject(0);
			
			if(!hs_pi.containsKey(jsonObject_pi.get("id"))){
				
				
				JSONArray doublearray = new JSONArray();
				
				ArrayList<Double> factors = new ArrayList<Double>();
				
				doublearray = jsonObject_pi.getJSONArray("factors");
				
				Iterator<?> it = doublearray.iterator();
				
				while(it.hasNext()){
					
					
					factors.add((Double)it.next());
					
				}
				
				
				hs_pi.put((String)jsonObject_pi.get("id"),factors );
				
				
			}

		}
		int count=0;
		//进行规整化处理
		HashMap<Integer,MyMetric> rgMap = new HashMap<Integer, MyMetric>();
		while((line=bf.readLine())!=null){
			
			JSONObject jsonObject= new JSONObject();
			
			JSONArray jsonArray = new JSONArray();
			
			line="["+line+"]";
			
			jsonArray=JSONArray.fromObject(line);
			
			jsonObject=jsonArray.getJSONObject(0);
			
			String userid = "";
			
			String itemid = "";
			
			userid = jsonObject.getString("userId");
			
			itemid = jsonObject.getString("itemId");
			
			double m_result = 0;  //余弦相似度
			double m_result_d = 0;//直接矩阵相乘结果
			double Lx=0;
			double Ly=0;
			ArrayList<Double> i = new ArrayList<Double>();
			ArrayList<Double> u = new ArrayList<Double>();
			ArrayList<Double> p = new ArrayList<Double>();
			ArrayList<Double> q = new ArrayList<Double>();
			
			i = hs_fi.get(jsonObject.get("itemId"));
			
			u = hs_vu.get(jsonObject.get("userId"));
			
			p = hs_pi.get(jsonObject.get("itemId"));
			
			q = hs_qu.get(jsonObject.get("userId"));
			
			if(i!=null&&p!=null){
				
			Iterator<Double> it_i = i.iterator();
			Iterator<Double> it_u = u.iterator(); 
			Iterator<Double> it_p = p.iterator();
			Iterator<Double> it_q = q.iterator(); 
			
			while(it_i.hasNext()&&it_u.hasNext()){
				
				
				m_result+=((Double)it_i.next()/*+(Double)it_p.next()*/)*((Double)it_u.next()/*+(Double)it_q.next()*/);
		//		m_result_d +=(/*(Double)it_i.next()+*/(Double)it_p.next())*(/*(Double)it_u.next()+*/(Double)it_q.next());
				
			}
			//计算余弦相似度
			it_i=i.iterator();
			while(it_i.hasNext()){
				double x = it_i.next();
				Lx+=x*x;
			}
			it_u=u.iterator();
			while(it_u.hasNext()){
				double y =it_u.next();
				Ly+=y*y;
			}
			if(Math.sqrt(Lx*Ly)!=0)
				m_result=m_result/Math.sqrt(Lx*Ly);
			else
				m_result=0;
			}
			if(!hs2.containsKey(user_id.get(jsonObject.get("userId")))){
				
				hs2.put((String) jsonObject.get("userId"), 0);
			}
			
			/**************************进行正则化*********************************/
			
			int star = jsonObject.getInt("star");
			
			
			/*if(star>3)
				star=2;
			else if(star>0)
				star=1;
			else 
				star=0;*///进行正则化
			if(i!=null){
			
				if(!rgMap.containsKey(user_id.get(jsonObject.get("userId")))){
					int userId = user_id.get(jsonObject.get("userId"));
					String itemId = (String) jsonObject.get("itemId");
					ArrayList<Double> features= new ArrayList<Double>();
					features.add(m_result);
				//	features.add(m_result_d);
					/* for(double elem :i){
						features.add(elem);
					}
					for(double elem :u){
						features.add(elem);
					}*/
					Tuple tp = new Tuple(star, features, itemId);
					ArrayList<Tuple> tupleList = new ArrayList<Tuple>();
					tupleList.add(tp);
					MyMetric thetuple = new MyMetric(userId, tupleList);
					rgMap.put(userId, thetuple);
				}
				else
				{
					int userId = user_id.get(jsonObject.get("userId"));
					String itemId = (String) jsonObject.get("itemId");
					ArrayList<Double> features= new ArrayList<Double>();
					features.add(m_result);
			//		features.add(m_result_d);
					/*for(double elem :i){
						features.add(elem);
					}
					for(double elem :u){
						features.add(elem);
					}*/
					Tuple tp = new Tuple(star, features, itemId);
					features = null;
					rgMap.get(userId).theTuple.add(tp);
				}
				System.out.println(count);
				jsonArray=null;
				jsonObject=null;
				count++;
			}
			
		}
		
			Iterator<String> it = hs2.keySet().iterator();
			Set<String> fi_key = hs_fi.keySet();
			Object[] item_id_array = (Object[]) fi_key.toArray();
			while(it.hasNext()){
				
				String userID = (String)it.next();
				
				int userId = user_id.get(userID);
				if(rgMap.containsKey(userId)){
					double n = 0.2 ;//设置比例倍数
					//产生不重复的随机数
			//		int[] randomArray = new int [(int) (n*rgMap.get(userId).theTuple.size())];
					int[] randomArray = new int [1000];
					for(int i = 0;i< randomArray.length;i++){
					randomArray[i]	= (int) (item_id_array.length*Math.random());
						for (int j = 0;j < i;j++){
							if(randomArray[i] ==randomArray[j] ){
								i--;break;
							}
						}
					}
					for(int random :randomArray){
					
						
						double m_result = 0;    //余弦相似度
						double m_result_d = 0;  //矩阵相乘结果
						
						double Lx = 0;
						double Ly = 0;
						
						ArrayList<Double> ii = new ArrayList<Double>();					
						ArrayList<Double> uu = new ArrayList<Double>();				
						ArrayList<Double> pi = new ArrayList<Double>();					
						ArrayList<Double> qu = new ArrayList<Double>();					
						ii = hs_fi.get(item_id_array[random]);					
						pi = hs_pi.get(item_id_array[random]);						
						uu = hs_vu.get(userID);
						
						qu = hs_qu.get(userID);
						
						if(ii!=null&&qu!=null){
							
					
						
							Iterator<Double> it_i = ii.iterator();
							Iterator<Double> it_u = uu.iterator(); 
							Iterator<Double> it_p = pi.iterator();
							Iterator<Double> it_q = qu.iterator(); 
							
							while(it_i.hasNext()&&it_u.hasNext()){
								
								//m_result+=((Double)it_i.next()+(Double)it_p.next())*((Double)it_u.next()+(Double)it_q.next());
								m_result+=((Double)it_i.next())*((Double)it_u.next());
							//	m_result_d +=(/*(Double)it_i.next()+*/(Double)it_p.next())*(/*(Double)it_u.next()+*/(Double)it_q.next());
								
							}
							it_i=ii.iterator();
							while(it_i.hasNext()){
								double x = it_i.next();
								Lx+=x*x;
							}
							it_u=uu.iterator();
							while(it_u.hasNext()){
								double y =it_u.next();
								Ly+=y*y;
							}
							if(Math.sqrt(Lx*Ly)!=0)
								m_result=m_result/Math.sqrt(Lx*Ly);
							else
								m_result=0;			
						if(ii!=null&&qu!=null){
						
							
							String itemId = (String) item_id_array[random];
							ArrayList<Double> features= new ArrayList<Double>();
							features.add(m_result);
						//	features.add(m_result_d);
						/*	for(double elem :ii){
								features.add(elem);
							}
							for(double elem :uu){
								features.add(elem);
							}*/
							Tuple tp = new Tuple(0, features, itemId);
							features = null;
							rgMap.get(userId).theTuple.add(tp);
							System.out.println(count);
							count++;
						}	
					}
				}
			}
			}
			RandomSample sampler= new RandomSample(rgMap);
			HashMap<Integer,MyMetric> subset = new HashMap<Integer,MyMetric>();
			subset= sampler.sampling(50);
			Iterator<?> writer_it=subset.entrySet().iterator();
			int detector = 0;
			while(writer_it.hasNext())
			{
				@SuppressWarnings("unchecked")
				Map.Entry<Integer,MyMetric> mm  = (Entry<Integer, MyMetric>) writer_it.next();
				MyMetric theMetric = mm.getValue();
				theMetric.sortMyMetricByLable(theMetric);
				System.out.println(detector);
				ArrayList<Tuple> tuples = theMetric.theTuple;
				for(Tuple tp :tuples){
					if(tp.features.size()!=0){
						String writeLine1 ="";
						writeLine1=tp.label+" "+"qid:"+theMetric.userId+" ";//"#"+jsonObject.get("itemId");
						for(int i=0;i<tp.features.size();i++){
							writeLine1=writeLine1+(i+1)+":"+tp.features.get(i)+" ";
						}
						writeLine1=writeLine1+"#"+tp.itemId;
						pw.println(writeLine1);
						pw.flush();
						
					}
					
				}
				detector ++;
				
			}

			Evaluator el = new Evaluator(RANKER_TYPE.LAMBDAMART, "NDCG@10", "P@10");
			el.score("testplace\\model\\train(0_cosfv0.2)\\lambdaMART_model_1.txt", 
					testPath,"testplace\\order\\LTR\\0_cosfv_0.2\\P@10_1.txt" );
			BufferedReader bf_score = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("testplace\\order\\LTR\\0_cosfv_0.2\\P@10_1.txt"))));
			MyMetric.getScore(subset, bf_score);
			writer_it=subset.entrySet().iterator();
			double con = 0;
			double[] averOrder = new double [10];
			
			while(writer_it.hasNext())
			{
				Map.Entry<Integer,MyMetric> mm  = (Entry<Integer, MyMetric>) writer_it.next();
				MyMetric metric = mm.getValue();
 				//metric.sortMyMetricByfirstValue(metric);
				MyMetric MetricSortByScore = metric.copyMetric(metric);
				MetricSortByScore.sortMyMetricByScore(MetricSortByScore);
				double temp = 0;
				for (Tuple tp:metric.theTuple){
					temp = tp.score;
					tp.score = tp.features.get(0);
					tp.features.set(0, temp);
				}
				metric.sortMyMetricByScore(metric);
				int N = 10;int hit = 0;
				for(int i =0;i<N;i++){
					averOrder[i]+=metric.theTuple.indexOf(MetricSortByScore.theTuple.get(i));				
				}
			}
			for (int i = 0;i<10;i++){ 
				averOrder[i] /= subset.size();
				System.out.println(averOrder[i]);
			}
	
}
	}

