package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Read {
	public static void main(String args[]) throws IOException{
	BufferedReader bf=new BufferedReader(new InputStreamReader(new FileInputStream(new File("testplace\\movies_train_extra.txt"))));
	String str="";
	int max=0;
	while((str=bf.readLine())!=null){
		String [] regex ;
		regex=str.split(" ");
		
		str = regex[1];
		str=str.substring(str.indexOf(':')+1);
		
		int result =0;
		for(int i=0;i<str.length();i++){
			result=result*10+(str.charAt(i)-'0');
		}
		if(result>max)
			max=result;
		
	}
	System.out.println(max);
	}
}
