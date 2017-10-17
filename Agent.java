package taaikes;

import java.util.Random;

public class Agent {

	static int N = 3;

	final static int EDF = 0;
	final static int HRF = 1;
	final static int MSF = 4;
	final static int SSTF = 2;
	final static int SPTF = 3;

	int agent;
	int[] resource = new int[N];
	int type= EDF;
	int keep;
	int keeptype;
	int off;
	int allReward;
	double allot;
	double reward;
//	double[] processTime = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	double[] Q = { 0,0,0,0 };

	public Agent(){

	}

	public Agent(int t, int[] r,Random random) {
		this.agent = t;
		for (int i = 0; i < N; i++) {
			this.resource[i] = r[i];
		}
		off = 0;
		this.type = EDF;
		this.reward = 0;
		this.allReward = 0;
	}

	public int agent() {
		return this.agent;
	}

	public int resource(int n) {
		return this.resource[n];
	}

	public void allot(double n,int t,int deadline){
		this.allot = n;
	}

	public int[] resource() {
		return this.resource;
	}

	public double reward() {
		return this.reward;
	}

	public void clear(Random random){
		this.Q[0] = this.Q[1] = this.Q[2] = this.Q[3] = 0;
		type = random.nextInt(4);
	}

	public void reward(double n) {
		this.reward += this.allot;
	}

	public void reward0() {
		this.allot = 0;
	}

	public int type() {
		return this.type;
	}

	public int off() {
		return this.off;
	}

	public void turn(int n) {
		this.off = n;
	}
/*
	public double processTime(int n) {
		return processTime[n];
	}

	public void processTime(int n, int s) {
		this.processTime[n] = (this.processTime[n] + s) / 2;
		this.keep = s;
		this.keeptype = n;
	}

	public void reallocation(int n,int s){
		this.processTime[this.keeptype] = 2*this.processTime[this.keeptype] - keep;
		this.processTime[n] = (this.processTime[n] + s) / 2;
		this.keep = s;
		this.keeptype = n;
	}
*/
	public double allot(){
		return this.allot;
	}

	public int typeChange(int n) {
		return this.type = n;
	}

	public int elapse() {

		off--;
		if (off == 0){

			return 0;
		}else {
			return 1;
		}
	}

	public int maxQ(){
		int q = 0;
		for(int i = 1; i < Q.length;i++){
			if(Q[q] < Q[i]){
				q = i;
			}
		}
		return q;
	}

	public String toString() {
		return this.agent + "," + resource[0] + "," + resource[1] + ","
				+ resource[2];
	}

	public String showQ(){
		StringBuilder s = new StringBuilder();
		s.append(Q[0]);
		for(int i =  1; i < Q.length;i++){
			s.append("," + Q[i]);
			}

		return s.toString();
	}
}