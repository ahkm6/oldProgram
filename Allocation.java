package taaikes;

import java.util.ArrayList;
import java.util.Random;

class Allocation {

	static int N = 11;

	// task allocation start
	static int AGENT = 300;
	static int TIME = 510000;
	static int haba = 5000;
	static int GENERATE = 28;
	static int interval = 100;
	static int LOOP = 50;
	static int NUMBER = 2;
	static int multiple = 2;
	static int NUM = 1;

	// task allocation end

	public static void main(String[] args) {

		String S = null;
		Agent ppp = new Agent();
		switch(ppp.type()){
		case 0:
			S = "EDF";
			System.out.println("EDF");
			break;
		case 1:
			S = "HRF";
			System.out.println("HRF");
			break;
		case 2:
			S = "SSTF";
			System.out.println("SSTF");
			break;
		case 3:
			S = "SPTF";
			System.out.println("SPTF");
			break;
		default :
			S = "learning";
			System.out.println("LEARN");
		}
		Output out = new Output();
		int generate;


		ForRandom rd = new ForRandom();
		MakeBid bidding = new MakeBid();
		Allocate allocate = new Allocate();

		MakeObject object = new MakeObject();
		Poisson poisson = new Poisson();
		Delete delete = new Delete();

		for(int g = 0; g < 1; g++){
			double th[] = th(g);
			double OOMOTO1[][] = new double[NUM][LOOP];
			double OOMOTO2[][] = new double[NUM][LOOP];
			double OOMOTO3[][] = new double[NUM][LOOP];
			double OOMOTO4[][] = new double[NUM][LOOP];
			double OOMOTO5[][][] = new double[NUM][LOOP][4];
			int coun = 0;

		for (int guruguru = 0; guruguru < NUM; guruguru++) {
			int kakuninn = 0;
			int kekka = 0;
			double[] SUM = new double[TIME];
			double[] DROP = new double[TIME];
			double[] SUCCESS = new double[TIME];
			double[][] POLICY = new double[TIME/interval][4];
			double[] TP = new double[TIME];

			generate = GENERATE + guruguru * multiple;
			int par = generate;
			// for record

			double[] policy = new double[4];
			// for record
			double keisoku[][][] = new double[TIME/interval][4][2];
			for (int guru = 40; guru < 50; guru++) {


				int x  = 0;
				generate = par;
				System.out.println("今は" + guruguru +"で、" + guru+ "週目");


				// in common from
				ArrayList<ArrayList<Bid>> bid = new ArrayList<ArrayList<Bid>>(); // agent
				ArrayList<ArrayList<Bid>> cand = new ArrayList<ArrayList<Bid>>();
				// in common to

				// task allocation from
				ArrayList<Agent> agent = new ArrayList<Agent>();
				ArrayList<Task> task = new ArrayList<Task>();
				int in;
				int taskNumber = 0;

				// task allocation to

				Q q = new Q();
				Random random = new Random(rd.seed(guru));
				ArrayList<ArrayList<Bid>> envy = new ArrayList<ArrayList<Bid>>();
				ArrayList<Bid> exchange = new ArrayList<Bid>();
				ArrayList<ArrayList<Bid>> save = new ArrayList<ArrayList<Bid>>();
				ArrayList<Agent> working = new ArrayList<Agent>();

				double sum = 0;
				double Tsum = 0;
				double tp = 0;
				int drop = 0;
				int processed = 0;
				double duration = 0;

				Bid kari[] = new Bid[2];

				// task allocation from
				agent = object.makeAgent(AGENT, random);
				// task allocation to


				// bidding start
				in = poisson.poisson(generate, random);
				kakuninn += in;
				taskNumber = object.makeTask(task, random, in, taskNumber,th);

				for (int i = 0; i < task.size(); i++) {
					cand.add(new ArrayList<Bid>());
				}
				bidding.biddingResource(task, agent, cand, bid, random,x);
				for (int i = 0; i < cand.size(); i++) {
					if (cand.get(i).isEmpty()) {
						cand.remove(i);
						i--;
					}
				}
				// bidding end

				Bid release = null;


				String[][] people = new String[4][TIME];

				for (int time = 1; time < TIME; time++) {

					while (cand.isEmpty() != true) {

						Bid b = allocate.SRNF(allocate.value(allocate
								.maxPref(allocate.maxValue(cand,random))), cand);

						// reallocation start
					release = allocate.reallocation1(exchange, envy,
								release, b);

						if (exchange.size() > 1) {

							kari = allocate.reallocation2(cand, envy, task,
									exchange, b, release, bid, save, random);
							b = kari[0];
							release = kari[1];
							for (int i = 0; i < working.size(); i++) {
								if (working.get(i).agent() == release.agent()) {
									agent.get(release.agent()).turn(0);
									sum -= release.value();
									duration = duration - release.process() - release.TASK().duration();
									working.remove(i);
									processed--;
									break;
								}
							}
						} else if (exchange.size() == 1) {
							kari = allocate.reallocation3(cand, envy, task,
									exchange, b, release, bid, save, random);
							b = kari[0];
							release = kari[1];
							for (int i = 0; i < working.size(); i++) {
								if (working.get(i).agent() == release.agent()) {
									agent.get(release.agent()).turn(0);
									sum -= release.value();
									duration = duration - release.process() - release.TASK().duration();
									working.remove(i);
									processed--;
									break;
								}
							}
						}
						// reallocation end
						// System.out.println("win" + b.toString());
						int winner = b.agent();
						int item = b.task();
						int p = b.pref();
							duration += b.TASK().duration() + b.process();;
							processed++;
							sum += b.value();
							agent.get(winner).turn(b.process());

							agent.get(winner).allot(b.get(),b.process(),b.deadline());

				//			agent.get(winner).allot(1/(b.TASK().number() * b.process()));
						//	success[b.deadline()]++;
			//			}

						working.add(agent.get(winner));

						// System.out.println("who: " + winner);
						// delete agent start
						delete.agent(envy, bid, b);
						// delete agent end

						// delete agent2 start
						delete.deleteAgent(bid, item);
						// delete agent2 end

						// delete task start
						delete.deleteTask(task, cand, winner, item, save);
						// delete task end

					}

					if (task.size() > 0) {
						double s[] = allocate.lastAllocation(task, agent, envy,
								sum, random, processed,duration);
						sum = s[0];
				//		failure = (int) s[1];
						processed = (int) s[2];
					}

					// time elapse start
					for (int i = 0; i < task.size(); i++) {
						task.get(i).clear();
						if (task.get(i).elapse() == 0) {
		//					dropReward += task.get(i).value();
							task.remove(i);
							drop++;
							i--;
						}
					}
/*
					if(working.isEmpty()!=true){
					//	System.out.println(time);
						for(Agent a : agent){
						if(a.off() == 0){

								q.update(a, a.allot());
								q.greedy(a, random);
								a.reward0();

							}
						}
					}
*/
					tp = (double)working.size() / AGENT;
				for (int i = 0; i < working.size(); i++) {
						if (working.get(i).elapse() == 0) {
/*							q.update(working.get(i), working.get(i).allot());
							q.greedy(working.get(i), random);
							working.get(i).reward0();*/
							working.remove(i);
							i--;
						}
					}
					// time elapse end

					if (time % interval == 0) {
/*
						for (Agent a : agent) {

						//	q.update(a, (sum-Tsum)/interval);
							q.update(a, a.reward()/interval);
							q.greedy(a, random);
						//	System.out.print(q.sftmax(a, random) + ",");

							a.reward0();
						}
*/
						for(Agent a : agent){
							POLICY[time/interval][a.maxQ()]++;
						}
				//		Tsum = sum;
					}

					cand.clear();
					save.clear();
					bid.clear();
					envy.clear();


					// makeing bid start
					in = poisson.poisson(generate, random);
					kakuninn+=in;
					taskNumber = object.makeTask(task, random, in, taskNumber,th);
					// taskNumber = object.makeBiasTask(task, random, in,
					// taskNumber);

					for (int i = 0; i < task.size(); i++) {
						cand.add(new ArrayList<Bid>());
					}
					bidding.biddingResource(task, agent, cand, bid, random,x);
					for (int i = 0; i < cand.size(); i++) {
						if (cand.get(i).isEmpty()) {
							cand.remove(i);
							i--;
						}
					}


					SUM[time-1] += sum;
					DROP[time - 1] += drop;
					SUCCESS[time - 1] += duration / processed;
					TP[time-1] += tp;
					duration = processed = 0;
					if (time % haba == 0){
					//	System.out.println(time);
				//		System.out.println("drop" + drop + ", sum" + (sum - Tsum)/haba);
						Tsum = sum;
						coun++;
							if(time > 10000)
						x++;
				//		x+=5;
/*
						if(time % (haba) == 0){
							coun = 0;
							x++;
					//		generate += 2;
					//		System.out.println("  " + x + "," + time);
						}
					//	th = th(coun);
			//			System.out.println("coun"+coun);
				/*		if(time % (haba*30) == 0){
							x = 0;
							generate += 10;
							System.out.println(time);
						}*/
				//		System.out.println(" " + time + "," + coun +"," + x);
					}

				}

			//	th = th(0);

					for(Agent a : agent){

						policy[a.maxQ()]++;
					}
/*
					for(int i = TIME - 2;i > TIME - 1002; i--){
						OOMOTO1[guruguru][guru] +=  (SUM[i]-SUM[i-1])/1000;
						OOMOTO2[guruguru][guru] +=  (DROP[i]-DROP[i-1])/1000;
						OOMOTO3[guruguru][guru] +=  (SUCCESS[i])/1000;
						OOMOTO4[guruguru][guru] +=  TP[i]/1000;
					}
					for(int i = 0; i < 4; i++){
						OOMOTO5[guruguru][guru][i] += policy[i];
					}
					policy[0]=policy[1]=policy[2]=policy[3]=0;
*/
	//			System.out.println(guru + "sum:"+OOMOTO1[guruguru][guru]);
	//			System.out.println(failure);
	//			System.out.println(guruguru + "捨て:"+OOMOTO2[guruguru][guru]);

	//			out.agent(S,people);
			}


		out.rast(g,S,guruguru,SUM,DROP,POLICY,SUCCESS,TP);
		//	System.out.println("guruguru" + guruguru);
			}
//		out.output(g,S,OOMOTO1,OOMOTO2,OOMOTO3,OOMOTO4,OOMOTO5);

		}

	}

	public static double[] th(int t){
		double th[] = new double[3];
		switch(t){
		case 0:
			th[0] = 1;
			th[1] = 0;
			th[2] = 0;
			break;
		case 1:
			th[0] = 0.25;
			th[1] = 0.5;
			th[2] = 0.75;
			break;
		case 2:
			th[0] = 0.1;
			th[1] = 0.2;
			th[2] = 0.3;
			break;
		case 3:
			th[0] = 0;
			th[1] = 0;
			th[2] = 0;
			break;
		case 4:
			th[0] = 0;
			th[1] = 0;
			th[2] = 0;
			break;
		case 5:
			th[0] = 0;
			th[1] = 0.33;
			th[2] = 0.34;
			break;
		case 6:
			th[0] = 0.1;
			th[1] = 0.1;
			th[2] = 0.1;
			break;
		case 7:
			th[0] = 0.1;
			th[1] = 0.1;
			th[2] = 0.4;
			break;
		}
		return th;
	}
}