package h280314;

import java.util.ArrayList;
import java.util.Random;

public class MakeObject {

	final public static int RANDOM = 0;
	final public static int GOODBAD = 1;
	final public static int VIASGOOD = 2;
	final public static int NORMAL = 3;

	public int deadlineBorder = 15;
	public int deadlineRange = 10;

	public void makeAgent(ArrayList<Agent> agent, Random random, int N,
			int policy, int TYPE) {

		switch (TYPE) {
		case RANDOM:
			for (int i = 0; i < N; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(6) + 2;
				resource[1] = random.nextInt(6) + 2;
				resource[2] = random.nextInt(6) + 2;
				agent.add(new Agent(i, resource, policy, random));
			}
			break;

		case GOODBAD:
			for (int i = 0; i < N / 2; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(3) + 5;
				resource[1] = random.nextInt(3) + 5;
				resource[2] = random.nextInt(3) + 5;
				agent.add(new Agent(i, resource, policy, random));
			}
			for (int i = N / 2; i < N; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(3) + 2;
				resource[1] = random.nextInt(3) + 2;
				resource[2] = random.nextInt(3) + 2;
				agent.add(new Agent(i, resource, policy, random));
			}
			break;

		case VIASGOOD:
			for (int i = 0; i < N / 4; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(3) + 4;
				resource[1] = random.nextInt(3) + 4;
				resource[2] = random.nextInt(3) + 4;
				agent.add(new Agent(i, resource, policy, random));
			}
			for (int i = N / 4; i < N/2; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(3) + 2;
				resource[1] = random.nextInt(3) + 2;
				resource[2] = random.nextInt(3) + 7;
				agent.add(new Agent(i, resource, policy, random));
			}
			for (int i = N / 2; i < (3 * N)/4; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(3) + 2;
				resource[1] = random.nextInt(3) + 7;
				resource[2] = random.nextInt(3) + 2;
				agent.add(new Agent(i, resource, policy, random));
			}
			for (int i = (3 * N)/4; i < N; i++) {
				int[] resource = new int[3];
				resource[0] = random.nextInt(3) + 7;
				resource[1] = random.nextInt(3) + 2;
				resource[2] = random.nextInt(3) + 2;
				agent.add(new Agent(i, resource, policy, random));
			}
			break;
		case NORMAL:
			for (int i = 0; i < N; i++) {
				int[] resource = new int[3];
				if(i<N/5)
					resource = lowAgent(random);
				else if(i<(N/5)*2)
					resource = highAgent(random);
				else if(i<(N/5)*3)
					resource = aBias(random);
				else if(i<(N/5)*4)
					resource = bBias(random);
				else
					resource = cBias(random);
				agent.add(new Agent(i, resource, policy, random));
			}
			break;

		}
	}

	public void makeTask(ArrayList<Task> task, Random random, int load,
			double[] th,int slice) {
		int reward;
		int taskNumber;
		int thresh = 0;
		if (task.isEmpty()) {
			taskNumber = 0;
		} else {
			for (int i = 0; i < task.size(); i++) {
				task.get(i).taskNumber(i);
			}
			taskNumber = task.size();
		}
		for (int i = taskNumber; i < load + taskNumber; i++) {
			double d = random.nextDouble();
			int resource[] = new int[3];
			if (d < th[0]) {
				thresh = 0;
			} else if (d < th[1]) {
				thresh = 1;
			} else if (d < th[2]) {
				thresh = 2;
			} else {
				thresh = 3;
			}

			switch (thresh) {
			case 0:
				resource = normalTask(random);
				reward = resource[0] + resource[1] + resource[2];
				task.add(new Task(i, resource, reward, random
						.nextInt(deadlineRange + 1) + deadlineBorder, 0,slice,random));
				break;
			case 1:
				resource = aBiasTask(random);
				reward = resource[0] + resource[1] + resource[2];
				task.add(new Task(i, resource, reward, random
						.nextInt(deadlineRange + 1) + deadlineBorder, 1,slice,random));
				break;
			case 2:
				resource = bBiasTask(random);
				reward = resource[0] + resource[1] + resource[2];
				task.add(new Task(i, resource, reward, random
						.nextInt(deadlineRange + 1) + deadlineBorder, 2,slice,random));
				break;
			case 3:
				resource = cBiasTask(random);
				reward = resource[0] + resource[1] + resource[2];
				task.add(new Task(i, resource, reward, random
						.nextInt(deadlineRange + 1) + deadlineBorder, 3,slice,random));
				break;
			}
		}
	}

	public void makeBid(int flag, ArrayList<Task> task,
			ArrayList<ArrayList<Bid>> Bid, ArrayList<Agent> agent,
			ArrayList<ArrayList<Bid>> agentBid, int bidNumber, Tactics tactics,
			int value, int ratio, Random random) {
		int timeHigh[];
		int state = 0;
		ArrayList<Bid> tempBid = new ArrayList<Bid>();
		for (Agent bidder : agent) { // エージェントごとに
			state = bidder.state();
			if (bidder.state() > 0)
				continue;            // no queue
			// タスクへ仮入札
	/*		if(bidder.queue() > 0){
				for(Bid qTask : bidder.queue){
					state += qTask.processingTime();
				}
			}*/
			for (Task item : task) {
				timeHigh = calculate(bidder.resource(), item.requiredResource()); // 処理時間、得意度計算
				if (timeHigh[0] + state > item.deadline()) // 処理が間に合わないなら飛ばす
					continue;
				tempBid.add(new Bid(bidder.agentNumber(), item,
					 timeHigh[0]+state, timeHigh[2], 1,
						timeHigh[1], value, ratio, random,timeHigh[0],bidder.tactics())); // 入札候補作成
			}
			// タスクへ仮入札
			// 各戦略ごとに並び替え
			if (tempBid.isEmpty() != true) {
				tactics.sort(flag, bidder.tactics(), bidNumber, tempBid, Bid,
						agentBid, tactics, value,random);
			}
			tempBid.clear();
		}
	}

	public int[] calculate(int[] resource, int[] requiredResource) {
		int time = (int) Math.ceil((double) requiredResource[0] / resource[0]);
		int High = time;
		int resourceSum = resource[0];
		int requiredResourceSum = requiredResource[0];
		int[] timeHigh = { time, High, 0 };

		for (int i = 1; i < resource.length; i++) {
			resourceSum += resource[i];
			requiredResourceSum += requiredResource[i];
			time = (int) Math.ceil((double) requiredResource[i] / resource[i]);
			High = time;
			if (timeHigh[0] < time)
				timeHigh[0] = time;
			if (timeHigh[1] > High)
				timeHigh[1] = High;
		}
		timeHigh[1] = timeHigh[0] - timeHigh[1];
		timeHigh[2] = resourceSum * timeHigh[0] - requiredResourceSum;
		return timeHigh;
	}

	public void changeDeadline(int border, int range){
		this.deadlineBorder = border;
		this.deadlineRange = range;
	}

	public int[] normalAgent(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(4) + 4;
		resource[1] = random.nextInt(4) + 4;
		resource[2] = random.nextInt(4) + 4;
		return resource;
	}
	public int[] highAgent(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(3) + 5;
		resource[1] = random.nextInt(3) + 5;
		resource[2] = random.nextInt(3) + 5;
		return resource;
	}
	public int[] lowAgent(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(3) + 2;
		resource[1] = random.nextInt(3) + 2;
		resource[2] = random.nextInt(3) + 2;
		return resource;
	}
	public int[] normalTask(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(21) + 30;
		resource[1] = random.nextInt(21) + 30;
		resource[2] = random.nextInt(21) + 30;
		return resource;
	}
	public int[] aBias(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(3) + 7;
		resource[1] = random.nextInt(3) + 2;
		resource[2] = random.nextInt(3) + 2;
		return resource;
	}
	public int[] aBiasTask(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(21) + 50;
		resource[1] = random.nextInt(21) + 20;
		resource[2] = random.nextInt(21) + 20;
		return resource;
	}
	public int[] bBias(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(3) + 2;
		resource[1] = random.nextInt(3) + 7;
		resource[2] = random.nextInt(3) + 2;
		return resource;
	}
	public int[] bBiasTask(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(21) + 20;
		resource[1] = random.nextInt(21) + 50;
		resource[2] = random.nextInt(21) + 20;
		return resource;
	}
	public int[] cBias(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(3) + 2;
		resource[1] = random.nextInt(3) + 2;
		resource[2] = random.nextInt(3) + 7;
		return resource;
	}
	public int[] cBiasTask(Random random){
		int resource[] = new int[3];
		resource[0] = random.nextInt(21) + 20;
		resource[1] = random.nextInt(21) + 20;
		resource[2] = random.nextInt(21) +  50;
		return resource;
	}
}
