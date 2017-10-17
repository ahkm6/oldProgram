package taaikes;

import java.util.Random;

public class Task{

	static int N = 3;

	int task;
	int value = 0;
	int[] resource = new int[N];
	int type;
	int deadline;
	int original;
	int bidNumber;
	double duration;

	public Task(int t, int[] r, int type,int d, Random random){
		this.task = t;
		for(int i = 0; i < N; i++){
			this.resource[i] = r[i];
			this.value += r[i];
		}
		this.duration = 0;
//		if(random.nextInt(2) == 0)
//			this.value = random.nextInt(91) + 60;
		this.type = type;
		this.deadline = d;
		this.original = d;
		this.bidNumber = 0;
	}

	public double duration(){
		return this.duration;
	}

	public void inc(){
		this.bidNumber++;
	}
	public void clear(){
		this.bidNumber = 0;
	}

	public int number(){
		return this.bidNumber;
	}

	public int original(){
		return this.original;
	}

	public int task(){
		return this.task;
	}

	public int resource(int n){
		return this.resource[n];
	}

	public int[] resource(){
		return this.resource;
	}

	public int deadline(){
		return this.deadline;
	}

	public int value(){
		return this.value;
	}

	public int elapse(){
		this.deadline--;
		this.duration++;
		return this.deadline;
	}

	public int type(){
		return this.type;
	}
}