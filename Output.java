package h280314;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Output {
	public void taskLoad(String learn, String tactics, String agent,
			String value, Double[][] reward, Double[][] drop, Double[][] rate,
			Double[][] duration, Double[][] useless, Double[][][] Q,
			Double[][] pTime, Double[][] processed,int slice) {
		try {
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Dropbox/resultFrom0719/" + "kakuninnotame,"+slice * 50 + ","
							+ Main.SimulationTime + tactics + "R," + agent + ","
							+ value + "," + learn + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			if (tactics.equals("learning")) {

					pw.println(",reward,pTime,drop,rate,duration,useless,,EDF,HRF,SPTF,CEF");
					for (int i = 0; i < reward.length; i++) {
						for (int j = 0; j < reward[i].length; j++) {
							pw.println((Main.TASKLOAD + j * Main.interval)
									+ "," + reward[i][j] + "," + pTime[i][j]
									+ "," + drop[i][j] + "," + rate[i][j] + ","
									+ duration[i][j] + "," + useless[i][j]
									+ ",," + Q[i][j][0] + "," + Q[i][j][1]
									+ "," + Q[i][j][2] + "," + Q[i][j][3]);
						}
						pw.println();
					}

			} else {
				pw.println(",reward,pTime,drop,rate,duration,useless");
				for (int i = 0; i < reward.length; i++) {
					for (int j = 0; j < reward[i].length; j++) {
						pw.println((Main.TASKLOAD + j * Main.interval) + ","
								+ reward[i][j] + "," + pTime[i][j]
								 + "," + drop[i][j] + ","
								+ rate[i][j] + "," + duration[i][j] + ","
								+ useless[i][j]);
					}
					pw.println();
				}
			}
			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void changeRatio(String learn, String tactics, String agent,
			String value, double[] reward, double[] drop, double[] rate,
			double[] duration, double[] useless, double[][][] Q, int bias,
			double[] pTime,int taskLoad) {
		try {
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Dropbox/resultFrom0719/kondokoso,"
							+ taskLoad + "," + tactics + "," + agent + ","
							+ value + "," + bias + ",15~25" + learn + ".csv",
					false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			if (tactics.equals("learning")) {
				if (agent.equals("NORMAL")) {
				pw.println(",reward,pTime,drop,rate,duration,useless,,EDF,HRF,SPTF,SSTF,,EDF,HRF,SPTF,CEF,,EDF,HRF,SPTF,SSTF,,EDF,HRF,SPTF,CEF,,EDF,HRF,SPTF,SSTF,,EDF,HRF,SPTF,CEF");
				for (int i = 0; i < reward.length; i++) {
					pw.println((i * 10) + "," + reward[i] + "," + pTime[i] + ","
							+ drop[i] + "," + rate[i] + "," + duration[i] + ","
							+ useless[i] + ",," + (Q[i][0][0]+Q[i][0][1]+Q[i][0][2]+Q[i][0][3]+Q[i][0][4]) + "," + (Q[i][1][0]+Q[i][1][1]+Q[i][1][2]+Q[i][1][3]+Q[i][1][4])
							+ "," + (Q[i][2][0]+Q[i][2][1]+Q[i][2][2]+Q[i][2][3]+Q[i][2][4]) + "," + (Q[i][3][0]+Q[i][3][1]+Q[i][3][2]+Q[i][3][3]+Q[i][3][4]) + ",,"
							+ Q[i][0][0] + "," + Q[i][1][0] + "," + Q[i][2][0]
							+ "," + Q[i][3][0] + ",," + Q[i][0][1] + "," + Q[i][1][1] + "," + Q[i][2][1]
									+ "," + Q[i][3][1]+",,"+ Q[i][0][2] + "," + Q[i][1][2] + "," + Q[i][2][2]
											+ "," + Q[i][3][2]+",,"+ Q[i][0][3] + "," + Q[i][1][3] + "," + Q[i][2][3]
													+ "," + Q[i][3][3]+",,"+ Q[i][0][4] + "," + Q[i][1][4] + "," + Q[i][2][4]
															+ "," + Q[i][3][4]);

				}

				} else {
					pw.println(",reward,pTime,drop,rate,duration,useless,,EDF,HRF,SPTF,SSTF,,EDF,HRF,SPTF,CEF");
					for (int i = 0; i < reward.length; i++) {
						pw.println((i * 10) + "," + reward[i] + "," + pTime[i] + ","
								+ drop[i] + "," + rate[i] + "," + duration[i] + ","
								+ useless[i] + ",," + Q[i][0][0] + "," + Q[i][1][0]
								+ "," + Q[i][2][0] + "," + Q[i][3][0] + ",,"
								+ Q[i][0][1] + "," + Q[i][1][1] + "," + Q[i][2][1]
								+ "," + Q[i][3][1]);

					}
				}
			} else {
				pw.println(",reward,pTime,drop,rate,duration,useless");
				for (int i = 0; i < reward.length; i++) {
					pw.println((i * 10) + "," + reward[i] + "," + pTime[i] +","
							+ drop[i] + "," + rate[i] + "," + duration[i] + ","
							+ useless[i]);
				}
			}

			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void outAgent(String learn, String tactics, String age,
			String value, int[][][] agent, ArrayList<Agent> Agent) {
		try {
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Dropbox/resultFrom0606/"
							+ +Main.SimulationTime + "agent" + tactics + ","
							+ age + "," + value + "," + learn + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.print("number,R1,R2,R3,");
			for (int i = 0; i < agent[0][0].length; i++) {
				pw.print((Main.TASKLOAD + i * Main.interval) + ",");
			}
			pw.println();
			for (int i = 0; i < agent.length; i++) {
				for (int j = 0; j < agent[i].length; j++) {
					pw.print(j + "," + Agent.get(j).resource(0) + ","
							+ Agent.get(j).resource(1) + ","
							+ Agent.get(j).resource(2) + ",");
					for (int k = 0; k < agent[i][j].length; k++) {
						pw.print(agent[i][j][k] + ",");
					}
					pw.println();
				}
				pw.println();
			}
			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void outProcess(String learn, String tactics, String age,
			String value, int[][] agent, ArrayList<Agent> Agent, int bias) {
		try {
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Dropbox/resultFrom0606/R" + bias + ","
							+ Main.SimulationTime + "agent" + tactics + ","
							+ age + "," + value + "," + learn + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.print("number,R1,R2,R3,");

			pw.println();

			for (int j = 0; j < agent.length; j++) {
				pw.print(Agent.get(j).resource(0) + +Agent.get(j).resource(1)
						+ +Agent.get(j).resource(2) + ",");
			}
			pw.println();
			for (int i = 0; i < agent[0].length; i++) {
				for (int j = 0; j < agent.length; j++) {
					pw.print(agent[j][i] + ",");
				}
				pw.println();
			}

			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void oneAgent(String learn, String tactics, String age,
			String value, double[][][][] oneAgent, ArrayList<Agent> Agent,
			int number) {
		try {
			FileWriter fw = new FileWriter(
					"//Users/n.iijima/Dropbox/resultFrom0322/100"
							+ Agent.get(number).resource(0) + ","
							+ Agent.get(number).resource(1) + ","
							+ Agent.get(number).resource(2) + "," + tactics
							+ "," + age + "," + value + ".csv", false); // ※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.println("number,R1,R2,R3,");

			for (int i = 0; i < oneAgent[0][0].length; i++) {
				pw.print((Main.TASKLOAD + i * Main.interval) + ",,,,,,");
			}
			pw.println();
			for (int i = 0; i < oneAgent[0][0].length; i++) {
				pw.print("number,EDF,HRF,SPTF,SSTF,,");
			}
			pw.println();
			for (int j = 0; j < oneAgent[0][0][0].length; j++) {
				for (int i = 0; i < oneAgent[0][0].length; i++) {
					pw.print(j + "," + oneAgent[0][0][i][j] + ","
							+ oneAgent[1][0][i][j] + "," + oneAgent[2][0][i][j]
							+ "," + oneAgent[3][0][i][j] + ",,");
				}
				pw.println();
			}
			System.out.println("終了");
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}