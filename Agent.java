package h280314;

import java.util.ArrayList;
import java.util.Random;

public class Agent {

	final int EDF = 0;
	final int HRF = 1;
	final int SPTF = 2;
	final int SSTF = 3;
	final int LEARNING = 4;

	int agentNumber;
	int[] resource;
	int state;
	double[] Q = new double[4];
	int policy;
	int order;
	double reward;
	Task processing;
	ArrayList<Bid> queue = new ArrayList<Bid>();
	int[] coordinate = new int[2];

	public Agent(int agentNumber, int[] resource,int policy,Random random) {
		this.agentNumber = agentNumber;
		this.resource = resource;
		this.state = 0;
		this.policy = policy;
		if(policy == 4){
			this.policy = random.nextInt(4);
		}
		for(int i = 0; i < 4; i++){
			Q[i] = 1;
		}
	}

	public void coordinate(int n, int s){
		this.coordinate[0] = n;
		this.coordinate[1] = s;
	}

	public int agentNumber(){
		return this.agentNumber;
	}

	public int[] resource(){
		return this.resource;
	}
	public int resource(int n){
		return this.resource[n];
	}
	public void state(int n){
		this.state = n;
	}
	public int state(){
		return state;
	}
	public int maxQ(){
		int t = 0;
		double x = Q[0];
		for(int i = 0; i < 4; i++){
			if(x < Q[i]){
				x = Q[i];
				t=i;
			}
		}
		return t;
	}

	public int elapse(){
		this.state--;
		return this.state;
	}
	public int policy(){
		return this.policy;
	}
	public int policy(int n){
		return this.policy = n;
	}
	public void order(int n){
		this.order = n;
	}
	public int order(){
		return this.order;
	}
	public int tactics(){
		return this.policy;
	}
	public void keepReward(double n){
		this.reward = n;
	}
	public double reward(){
		return this.reward;
	}
	public void addQueue(Bid task){
		this.queue.add(task);
	}
	public int queue(){
		return this.queue.size();
	}
	public Bid pop(){
		Bid b =  this.queue.get(0);
		this.queue.remove(0);
		return b;
	}

	public String toString() {
		return this.state + ",agentNum:" + this.agentNumber + ",resource:"
				+ "[" + this.resource[0] + "]" + "[" + this.resource[1] + "]"
				+ "[" + this.resource[2] + "]";
	}
}