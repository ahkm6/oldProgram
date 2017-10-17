package taaikes;

import java.util.Random;

class Bid{

	int agent;
	int task;
	int pref;
	int realPref;
	int value;
	int flag = 0;
	int process = 0;
	int deadline;
	int myValue;
	int temp = 0;
	int subst = 0;
	double percent;
	Task TASK;
	double get;
	int type;

	public Bid(int a,int t, int p, int v){
		this.agent = a;
		this.task = t;
		this.pref = p;
		this.value = v;
	}

	public Bid(int a,int t, int p, int v,int process,int deadline,int fp,int mv,Task task,double d,Random random, int x){
		this.agent = a;
		this.task = t;
		this.pref = p;
		this.realPref = p;

		this.value = v;
		if(random.nextInt(100)<x){
			this.value = (int)(v/2 + v * (deadline - process) / (2 * task.original()));
		}
		this.process = process;
		this.deadline = deadline;
		this.temp = fp;
		this.TASK = task;
	//	this.percent = deadline / d;

		this.myValue = 100 - process;
		//this.myValue = 100 - deadline + process;
//		this.myValue = value;
		this.subst = (int)d;
		this.get = v;

	}

	public Task TASK(){
		return this.TASK;
	}

	public void flag(){  //  get
		this.flag = 1;
	}
	public void deflag(){   // release
		this.flag = 0;
	}
	public void setPref(int s){
		this.pref = s;
	}
	public void up(){
		this.pref--;
	}
	public void down(int n ){
		this.pref += n;
	}
	public void rdown(int n, double s){
		this.realPref += n;
		this.get = s;
	}
	public int type(){
		return this.TASK.type();
	}

	public int agent(){
		return this.agent;
	}
	public int task(){
		return this.task;
	}
	public int pref(){
		return this.pref;
	}
	public int realPref(){
		return this.realPref;
	}
	public int value(){
		return this.value;
	}
	public double get(){
		return this.value;
	}
	public int getFlag(){
		return this.flag;
	}
	public double percent(){
		return this.percent;
	}
	public int subst(){
		return this.subst;
	}

	public int myValue(){
		return this.myValue;
	}

	public int process(){
		return this.process;
	}

	public int deadline(){
		return this.deadline;
	}

	public int judge(){
		if((this.deadline - this.process) < 0){
			return 1;
		}else{
			return -1;
		}
	}

	public int temp(){
		return this.temp;
	}
	public String toString(){
		if(flag == 0){
		return "○a" + this.agent + ",t" +this.task + ",p" + this.realPref + ",v" + this.subst+ "," + this.process;
		}
		else{
			return "×a" +this.agent + ",t" +this.task + ",p" + this.realPref + ",v" + this.subst + "," + this.process;
		}
	}
}