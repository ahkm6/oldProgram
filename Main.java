package h280314;

import java.util.ArrayList;
import java.util.Random;

public class Main {
	final static int PROCESSTIME = 0;
	final static int REWARD = 1;
	final static int EDF = 0;
	final static int HRF = 1;
	final static int SPTF = 2;
	final static int SSTF = 3;
	final static int LEARNING = 4;
	final public static int RANDOM = 0;
	final public static int GOODBAD = 1;
	final public static int VIASGOOD = 2;
	final public static int NORMAL = 3;
	final static int all = 0;
	final static int piece = 1;
	final static int NoLEARN = 0;
	final static int MLEARN = 1;
	// output
	final static int getAll = 0;
	final static int getAgent = 1;
	final static int getOneAgent = 2;
	final static int getProcess = 3;
	final static int getProcessAll = 4;
	final static int noOutput = -1;
	final static int CPLEX = 0;
	final static int LNVF = 1;

	static int AGENT = 300; // エージェントの数
	static int SimulationTime = 5000; // シミュレーション時間3
	static int TASKLOAD = 6; // １tickに追加するタスクの数
	static int times = 13; // 試行回数
	static int interval = 2; // いくつずつ増やすか
	static int LOOP = 10; // 繰り返しの数
	static int SLICE = 1; // いくつず進めるか
	static int tactics = LEARNING; //  戦略
	static int agentType = RANDOM; // エージェントのリソース値
	static int bidNumber = 5; // いくつまで入札できるか
	static int VALUE = REWARD; // 価値
	static int record = all;
	static int flag = NoLEARN; // managerの学習は?
	static int vias = 4;
	static int last = 1000;
	static int OUTPUT = getAll; // 何を出力する？
	static int AgentNumber = 25; // どのエージェントのデータを集める？
	static int METHOD = LNVF;

	public static void main(String[] args) {
for(int qqq = 0; qqq < 3; qqq++){
		int taskLoad = TASKLOAD;
		// いろいろ
		MLearning mlearning = new MLearning();
		Q q = new Q();
		Output output = new Output();
		EnvyFree envyFree = new EnvyFree();
		Delete delete = new Delete();
		MakeObject makeObject = new MakeObject();
		ForRandom seed = new ForRandom();
		Name name = new Name();
		Poisson poisson = new Poisson();
		Tactics TACTICS = new Tactics();
		Allocation allocation = new Allocation();
		// いろいろ
		// 取得パラメータ設定
		Double[][] rewardAll = new Double[vias][times];
		Double[][] dropAll = new Double[vias][times];
		Double[][] rateAll = new Double[vias][times];
		Double[][] durationAll = new Double[vias][times];
		Double[][] pTimeAll = new Double[vias][times];
		Double[][] uselessAll = new Double[vias][times];
		Double[][] processedAll = new Double[vias][times];
		Double[][][] Qvalue = new Double[vias][times][4];

		int[][][] AgentQ = new int[4][AGENT][times];
		int[][] ProcessQ = new int[AGENT][SimulationTime / 10];
		double oneAgentQ[][][][] = new double[4][vias][times][SimulationTime];
		// 取得パラメータ設定
		String wTactics = name.PrintTactics(tactics);
		String wAgent = name.PrintAgent(agentType);
		String wValue = name.PrintValue(VALUE);
		String wLearn = name.PrintLearn(flag);

		// タスクの分散ごとに
		for (int Vias = 0; Vias < vias; Vias++) { // タスクの偏りの数だけ

			double[] vias = name.vias(Vias); // タスクの分散
			Double[] rewardPiece = new Double[times];
			Double[] dropPiece = new Double[times];
			Double[] ratePiece = new Double[times];
			Double[] durationPiece = new Double[times];
			Double[] pTimePiece = new Double[times];
			Double[] uselessPiece = new Double[times];
			Double[] processedPiece = new Double[times];
			Double[][] QvaluePiece = new Double[times][4];
			// 追加タスクの増加
			for (int add = 0; add < times; add++) {
				taskLoad = TASKLOAD + add * interval;
				// 集計用
				double sumPart = 0; // 報酬
				double dropPart = 0; // 廃棄タスク
				double ratePart = 0; // 稼働率
				double durationPart = 0; // 処理までにかかった時間
				double pTimePart = 0; // 処理までにかかった時間
				double uselessPart = 0; // 余分なリソース
				double processedPart = 0; // 処理したタスク数
				double QvaluePart[] = new double[4];
				// 集計用

				// 報酬が下がるタスクの割合ごとに
				for (int slice = 0; slice < SLICE; slice++) { // いくつ進んだか

					double Psum[] = new double[SimulationTime / 10]; // 報酬
					double Pdrop[] = new double[SimulationTime / 10]; // 廃棄タスク
					double Prate[] = new double[SimulationTime / 10]; // 稼働率
					double Pduration[] = new double[SimulationTime / 10]; // 処理までにかかった時間
					double PpTime[] = new double[SimulationTime / 10]; // 処理までにかかった時間
					double Puseless[] = new double[SimulationTime / 10]; // 余分なリソース
					double Pprocessed[] = new double[SimulationTime / 10]; // 処理したタスク数
					double PQvalue[][][] = new double[SimulationTime / 10][4][5]; // Qvalue

					for (int loop = 0; loop < LOOP; loop++) { // 繰り返し
						slice = qqq*50;

						// epsilon initialize
						// q.epsilon();
						// 集計用
						double sum[] = new double[2]; // 報酬
						double drop[] = new double[2]; // 廃棄タスク
						double rate[] = new double[2]; // 稼働率
						double duration[] = new double[2]; // 処理までにかかった時間
						double pTime[] = new double[2]; // 処理までにかかった時間
						double useless[] = new double[2]; // 余分なリソース
						double processed[] = new double[2]; // 処理したタスク数
						ArrayList<Bid> KEEP = new ArrayList<Bid>();
						// 集計用
						// 必要不可欠
						int generation = 0;
						ArrayList<Agent> active = new ArrayList<Agent>(); // 稼働中のエージェント
						ArrayList<Agent> agent = new ArrayList<Agent>(); // 全体のエージェント
						ArrayList<Task> task = new ArrayList<Task>(); // 現在あるタスク
						ArrayList<Task> allocatedTask = new ArrayList<Task>(); // 割り当て済みのタスク
						Bid choice; // 選ばれたのは...
						ArrayList<Bid> confirm = new ArrayList<Bid>(); // 選ぶ途中経過
						ArrayList<ArrayList<Bid>> Bid = new ArrayList<ArrayList<Bid>>(); // 入札をタスクごとに管理
						ArrayList<ArrayList<Bid>> allocatedBid = new ArrayList<ArrayList<Bid>>(); // 割り当てたタスクの入札管理
						ArrayList<ArrayList<Bid>> agentBid = new ArrayList<ArrayList<Bid>>(); // 入札をエージェントごとに管理
						ArrayList<ArrayList<Bid>> agentList = new ArrayList<ArrayList<Bid>>(); // 割当て済み
						ArrayList<ArrayList<Bid>> envyList = new ArrayList<ArrayList<Bid>>(); // 不満候補
						ArrayList<Bid> taskList = new ArrayList<Bid>(); // 割り当てが決まった入札
						Random random = new Random(seed.seed(loop));
						// 必要不可欠
						// 下準備
						makeObject.makeAgent(agent, random, AGENT, tactics,
								agentType);
						// 下準備
						for (int elapsedTime = 0; elapsedTime < SimulationTime; elapsedTime++) {

							// タスク発生フェーズ
							generation = poisson.poisson(taskLoad, random);
							makeObject.makeTask(task, random, generation, vias,
									slice);
							// タスク発生フェーズ
							// 入札フェーズ
							for (int i = 0; i < task.size(); i++) {
								Bid.add(new ArrayList<Bid>());
							}
							makeObject.makeBid(flag, task, Bid, agent,
									agentBid, bidNumber, TACTICS, VALUE, slice,
									random);
							for (int i = 0; i < Bid.size(); i++) {
								if (Bid.get(i).isEmpty()) {
									Bid.remove(i);
									i--;
									continue;
								}
							}
							for (ArrayList<Bid> bidNumber : Bid) {
								task.get(bidNumber.get(0).taskNumber())
										.popularity(bidNumber.size());
							}
							// 入札フェーズ
							if (METHOD == CPLEX) {
								try {
									Cplex.cplex(task, Bid, agentBid, taskList);
								} catch (Throwable e) {
									// TODO 自動生成された catch ブロック
									e.printStackTrace();
								}
							} else {
								while (true) {
									// 割り当てフェーズ
									if ((confirm = allocation.valueMax(Bid,
											random)).isEmpty()) {
										break;
									}
									choice = allocation.decition(
											allocation.preferenceMax(confirm),
											random, Bid);
									// 割り当てフェーズ
									// 不満調査
									ArrayList<Bid> d = envyFree.envyFree(
											envyList, choice);
									if (d.size() > 0)
										choice = envyFree.change(d, agent,
												taskList, allocatedBid, Bid,
												random, envyList);
									// 不満調査
									taskList.add(choice);
									// 割り当て済みの入札の削除フェーズ
									delete.deleteAgentBid(agentBid, agentList,
											choice, envyList, agent);
									delete.deleteTask(allocatedBid, Bid,
											choice, task, allocatedTask);
									// 割り当て済みの入札の削除フェーズ
								}
								// 割り当てフェーズ終了
								// 際割り当てフェーズ
								allocation.reallocation(task, envyList,
										taskList, allocatedTask);
								// 際割り当てフェーズ
							}
							// 割り当て実行
							for (Bid allocate : taskList) {
								KEEP.add(allocate);
								// agent.get(allocate.agentNumber()).keepReward(
								// allocate.reward());
								if (elapsedTime > 1)
							//		agent.get(allocate.agentNumber()).keepReward(allocate.reward());
									agent.get(allocate.agentNumber())
											.keepReward(
													mlearning.vias(allocate));

								sum[0] += allocate.reward();
								duration[0] += allocate.duration();

								pTime[0] += allocate.processingTime();
								useless[0] += allocate.useless();
								// mlearning.update(allocate.type,
								// allocate.match());
								if (agent.get(allocate.agentNumber()).state() == 0) {
									active.add(agent.get(allocate.agentNumber()));
									agent.get(allocate.agentNumber()).state(
											allocate.originalProcessingTime());
								} else {
									agent.get(allocate.agentNumber()).addQueue(
											allocate);
								}
							}

							while (KEEP.size() > 51) {
								KEEP.remove(0);
							}

							processed[0] += taskList.size();
							// 割り当て実行
							rate[0] = (double) active.size() / AGENT;
							// 時間経過フェーズ
							if (tactics == LEARNING) {
								mlearning.calculate(KEEP);
								for (ArrayList<Bid> rest : agentBid) {
									if (agent.get(rest.get(0).agentNumber())
											.state() == 0) {
										q.update((agent.get(rest.get(0)
												.agentNumber())), -1);
										q.greedy((agent.get(rest.get(0)
												.agentNumber())), random);
									}
								}
							}
							for (int i = 0; i < active.size(); i++) {
								if (active.get(i).elapse() == 0) {
									if (tactics == LEARNING) {
										q.update(active.get(i), active.get(i)
												.reward());
										q.greedy(active.get(i), random);
									}
									if (active.get(i).queue() == 0) {
										active.remove(i);
										i--;
									} else {
										active.get(i)
												.state(active
														.get(i)
														.pop()
														.originalProcessingTime());
									}
								}
							}
							for (int j = 0; j < task.size(); j++) {
								if (task.get(j).elapse() == 0) {
									task.remove(j);
									drop[0]++;
									j--;
								}
							}

							// 時間経過フェーズ
							// レコーディング
							if (OUTPUT != getProcess) {
								if (elapsedTime > SimulationTime - last) {
									if(taskList.isEmpty()!=true){
									sumPart += (sum[0] - sum[1]);
									dropPart += drop[0] - drop[1];
									ratePart += rate[0];
									processedPart += processed[0]
											- processed[1];
									durationPart += (duration[0] - duration[1])
											/ (double)taskList.size();

									pTimePart += (pTime[0] - pTime[1])
											/ (double)taskList.size();
									uselessPart += (useless[0] - useless[1])
											/ (double)taskList.size();
									if (elapsedTime == SimulationTime - 1) {
										for (Agent a : agent) {
											QvaluePart[a.policy()]++;
										}
										}
									}
								}
							}
							if (OUTPUT == getProcess || OUTPUT == getProcessAll) {
								/*
								 * if (elapsedTime % ((SimulationTime - 10000) /
								 * 100) == 0 && elapsedTime >= 10000) { //
								 * slice++; // System.out.println(elapsedTime);
								 * }
								 */
								/*
								 * if(elapsedTime % (SimulationTime/4) == 0 &&
								 * elapsedTime > 0){ Vias++; name.vias(Vias);
								 * System.out.println(elapsedTime + "," + Vias);
								 * }
								 */
								/*
								 * if(elapsedTime % (SimulationTime/3) == 0 &&
								 * elapsedTime > 0){
								 * makeObject.changeDeadline(10,10);
								 * System.out.println(elapsedTime + "," + Vias);
								 * }
								 */
								sumPart += (sum[0] - sum[1]);
								dropPart += drop[0] - drop[1];
								ratePart += rate[0];
								durationPart += (duration[0] - duration[1])
										/ (double)taskList.size();
								pTimePart += (pTime[0] - pTime[1])
										/ (double)taskList.size();
								uselessPart += (useless[0] - useless[1])
										/ (double)taskList.size();
								processedPart += processed[0] - processed[1];
								if (elapsedTime % 10 == 0) {
									Psum[elapsedTime / 10] += (sumPart / 10)
											/ LOOP;
									Pdrop[elapsedTime / 10] += (dropPart / 10)
											/ LOOP;
									Prate[elapsedTime / 10] += ratePart
											/ (10 * LOOP);
									Pduration[elapsedTime / 10] += (durationPart / (10 * LOOP));
									PpTime[elapsedTime / 10] += (durationPart / (10 * LOOP));
									Puseless[elapsedTime / 10] += (uselessPart / (10 * LOOP));
									Pprocessed[elapsedTime / 10] += (processedPart / (10 * LOOP));

									int c = 0;
									for (Agent a : agent) {
										if (agentType == GOODBAD || agentType == RANDOM) {
											if (c < AGENT/2)
												PQvalue[elapsedTime / 10][a
														.policy()][0]++;
											else
												PQvalue[elapsedTime / 10][a
														.policy()][1]++;
										} else if (agentType == NORMAL) {
											if (c < AGENT/5)
												PQvalue[elapsedTime / 10][a
														.policy()][0]++;
											else if(c<(AGENT/5)*2)
												PQvalue[elapsedTime / 10][a
														.policy()][1]++;
											else if(c<(AGENT/5)*3)
												PQvalue[elapsedTime / 10][a
														.policy()][2]++;
											else if(c<(AGENT/5)*4)
												PQvalue[elapsedTime / 10][a
														.policy()][3]++;
											else
												PQvalue[elapsedTime / 10][a
														.policy()][4]++;
										}
										c++;
									}
									sumPart = dropPart = ratePart = durationPart = pTimePart = uselessPart = processedPart = 0;
								}
							}
							sum[1] = sum[0];
							drop[1] = drop[0];

							duration[1] = duration[0];
							pTime[1] = pTime[0];
							useless[1] = useless[0];
							processed[1] = processed[0];
							// レコーディング
							// いろいろ消す
							System.out.println(task.size());
							allocatedBid.clear();
							Bid.clear();
							agentBid.clear();
							agentList.clear();
							envyList.clear();
							taskList.clear();
							allocatedTask.clear();
							// いろいろ消す
							if (OUTPUT == getOneAgent) {
								for (int j = 0; j < 4; j++) {
									oneAgentQ[j][Vias][add][elapsedTime] = agent
											.get(AgentNumber).Q[j];
								}
							}
							// 何か追加用
							if (OUTPUT == getProcessAll) {
								if (elapsedTime % 10 == 0)
									for (int i = 0; i < agent.size(); i++) {
										ProcessQ[i][elapsedTime / 10] = agent
												.get(i).maxQ();
									}
							}
							// 何か追加用
						}
						if (OUTPUT == getProcess || OUTPUT == getAgent) {
							for (int i = 0; i < agent.size(); i++) {
								AgentQ[Vias][i][add] = agent.get(i).policy();
							}
						}
						// 時間内
						System.out.println("繰り返し" + loop);
					}
					// LOOP
					System.out.println("次の追加タスク数"
							+ (TASKLOAD + (add + 1) * interval));
					if (OUTPUT == getProcess) {
						output.changeRatio(wLearn, wTactics, wAgent, wValue,
								Psum, Pdrop, Prate, Pduration, Puseless,
								PQvalue, Vias, PpTime,taskLoad);
					}
				}
				if (OUTPUT == getProcessAll) {
					Random rand = new Random(seed.agentSeed());
					ArrayList<Agent> agent = new ArrayList<Agent>();
					makeObject
							.makeAgent(agent, rand, AGENT, tactics, agentType);
					output.outProcess(wLearn, wTactics, wAgent, wValue,
							ProcessQ, agent, Vias);
				}
				// 報酬が下がるタスクの割合ごとに
				rewardPiece[add] = sumPart / (last * LOOP);
				dropPiece[add] = dropPart / (last * LOOP);
				ratePiece[add] = ratePart / (last * LOOP);
				durationPiece[add] = durationPart / (last * LOOP);
				pTimePiece[add] = pTimePart / (last * LOOP);
				uselessPiece[add] = uselessPart / (last * LOOP);
				processedPiece[add] = processedPart / (last * LOOP);
				if (tactics == 4) {
					for (int i = 0; i < QvaluePart.length; i++) {
						QvaluePiece[add][i] = QvaluePart[i] / LOOP;
					}
				}
			}
			rewardAll[Vias] = rewardPiece;
			dropAll[Vias] = dropPiece;
			rateAll[Vias] = ratePiece;
			durationAll[Vias] = durationPiece;
			pTimeAll[Vias] = pTimePiece;
			processedAll[Vias] = processedPiece;
			uselessAll[Vias] = uselessPiece;
			Qvalue[Vias] = QvaluePiece;
			// 少しずつ追加タスク増加
		}
		// タスクの分散ごとに
		if (OUTPUT == getAll) {
			output.taskLoad(wLearn, wTactics, wAgent, wValue, rewardAll,
					dropAll, rateAll, durationAll, uselessAll, Qvalue,
					pTimeAll, processedAll,qqq);
		} else {
			Random rand = new Random(seed.agentSeed());
			ArrayList<Agent> agent = new ArrayList<Agent>();
			makeObject.makeAgent(agent, rand, AGENT, tactics, agentType);
			if (OUTPUT == getAgent) {
				output.outAgent(wLearn, wTactics, wAgent, wValue, AgentQ, agent);
			} else if (OUTPUT == getOneAgent) {
				output.oneAgent(wLearn, wTactics, wAgent, wValue, oneAgentQ,
						agent, AgentNumber);
			}
		}
	}
		System.out.println("終わり");

	}
}