package taaikes;

import java.util.ArrayList;

public class renshu{

	public static void main(String args[]){
		ArrayList<ArrayList<Integer>> aaa = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> ccc = new ArrayList<Integer>();
		
		for(int i = 0; i < 10; i++){
			ArrayList<Integer> bbb = new ArrayList<Integer>();
			bbb.add(i + 2);
			bbb.add(i + 4);
			bbb.add(i + 6);
			aaa.add(bbb);
		}
		
		ccc = aaa.get(3);
		
		ccc.clear();
		for(int i = 0; i < aaa.size(); i++){
			for(int j = 0; j < aaa.get(i).size(); j++){
				System.out.print(aaa.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
}