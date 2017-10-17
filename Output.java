package taaikes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Output {



	public void agent(String s, String[][] agent) {

		try {
			// 出力先を作成する
			FileWriter fw = new FileWriter("//Users/n.iijima/Desktop/Result/"
					+ s + Allocation.interval + "," + "agent.csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			// 内容を指定する

			//pw.println("");

			int loop = Allocation.LOOP;
			int interval = Allocation.interval;
			pw.println("edf,hrf,sstf,sptf,,edf,hrf,sstf,sptf,,edf,hrf,sstf,sptf,,edf,hrf,sstf,sptf,");
			for (int i = 1; i < agent[0].length; i++) {
				if(i %interval ==0)
				pw.println(i + "," + agent[0][i]+ ","+i+"," + agent[1][i] + ","+i+"," + agent[2][i] + ","+i+"," +agent[3][i]);

			}

			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}
	public void keisoku(String s, ArrayList<Agent> agent) {

		try {
			// 出力先を作成する
			FileWriter fw = new FileWriter("//Users/n.iijima/Desktop/myResult/"
			 	+ "normal.csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			// 内容を指定する

			//pw.println("");

			for(int i = 0; i < agent.size(); i++){
				pw.println(agent.get(i).toString() + "," + agent.get(i).maxQ());
			}


			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}
	public void rast(int g,String s,int number,double[] SUM, double[] DROP, double[][] POLICY, double[] SUCCESS,double[] TP) {


		try {
			String d = null;
			// 出力先を作成する
			switch(g){
			case 0:
				d = "100,0,0,0";
				break;
			case 1:
				d = "25,25,25,25";
				break;
			case 2:
				d = "10,10,10,70";
				break;
			case 3:
				d = "0,0,0,100";
				break;
			}
			FileWriter fw = new FileWriter("/Users/n.iijima/Dropbox/Result/"
					+"ATARASHI-5"+ d + s + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			// 内容を指定する
			int loop = Allocation.LOOP;
			int generate = Allocation.GENERATE;
			int multiple = Allocation.multiple;
			int interval = Allocation.interval;

			pw.println(",sum,sum,drop,success,TP,,EDF,HRF,SSTF,SPTF");

			for (int i = 1; i < Allocation.TIME; i++) {
				if(i % interval == 0){
				pw.print( i  + "," + SUM[i] / loop + "," + (SUM[i] - SUM[i-interval] )/(interval * loop) + "," + (DROP[i] - DROP[i-interval] )/(interval * loop)+ "," + SUCCESS[i] /(loop)+ "," + TP[i]/loop+","+i+",");
				for(int j = 0; j < 4; j++){
					pw.print(POLICY[i/Allocation.interval][j] /(300 * loop) + ",");
				}
				pw.println();
				}
			}
			pw.println();


			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}
	public void output(int g,String s,double[][] SUM, double[][] DROP,double[][] SUCCESS,double[][] TP, double[][][] POLICY) {


		try {
			String d = null;
			// 出力先を作成する
			switch(g){
			case 0:
				d = "100,0,0,0";
				break;
			case 1:
				d = "25,25,25,25";
				break;
			case 2:
				d = "10,10,10,70";
				break;
			case 3:
				d = "0,0,0,100";
				break;
			}
			FileWriter fw = new FileWriter("/Users/n.iijima/Dropbox/Result/"
				+"gensho-5"+ d + s + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			// 内容を指定する
			int loop = Allocation.LOOP;
			int generate = Allocation.GENERATE;
			int multiple = Allocation.multiple;

			double sum[] = new double[Allocation.NUM];
			double drop[] = new double[Allocation.NUM];
			double duration[] = new double[Allocation.NUM];
			double tp[] = new double[Allocation.NUM];
			double policy[][] = new double[Allocation.NUM][4];
			pw.println(",sum,drop,duration,TP,,EDF,HRF,SSTF,SPTF");
			for(int i = 0;i < Allocation.NUM; i++){
				for(int j = 0; j < loop; j++){
					sum[i] += SUM[i][j];
					drop[i] += DROP[i][j];
					duration[i] += SUCCESS[i][j];
					tp[i] += TP[i][j];

				}
				for(int j = 0; j < loop; j++){
					for(int k = 0; k < 4; k++){
						policy[i][k] += POLICY[i][j][k];
					}
				}
			}




			for (int i = 0; i < sum.length; i++) {
				pw.print( i  + "," + (sum[i] / loop) + "," + (drop[i]/ loop)+ "," + duration[i] /(loop)+ "," + tp[i]/loop+"," + i + " ,");
				for(int j = 0; j < 4; j++){
					pw.print(policy[i][j] /(300 * loop) + ",");
				}
				pw.println();
				}

			pw.println();


			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}
	public void rastNoLearning(String s,int number,double[] SUM, double[] DROP,double[][] POLICY, double[] SUCCESS) {

		try {
			// 出力先を作成する
			FileWriter fw = new FileWriter("/Users/n.iijima/Desktop/result/"
					+ s +(20 +  number *10)+ "DecRewNormalQ" + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			// 内容を指定する
			int loop = Allocation.LOOP;
			int generate = Allocation.GENERATE;
			int multiple = Allocation.multiple;

			pw.println(",sum,sum,drop,success,,EDF,HRF,SSTF,SPTF");

			for (int i = 0; i < Allocation.TIME; i++) {
				if(i % 200 == 0){
				pw.print( i  + "," + SUM[i] / loop + "," + SUM[i] / (loop * i )+ "," + DROP[i] /loop+ "," + SUCCESS[i]/loop+ ", ,");
				for(int j = 0; j < 4; j++){
					pw.print(POLICY[i/Allocation.interval][j] / loop + ",");
				}
				pw.println();
				}
			}
			pw.println();


			// ファイルに書き出す
			pw.close();

			// 終了メッセージを画面に出力する
			System.out.println("出力が完了しました。");


		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}
}