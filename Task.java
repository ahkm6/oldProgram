package h280314;

import java.util.Random;

public class Task{

	int taskNumber;
	int requiredResource[];
	int reward;
	int deadline;
	int originalDeadline;
	int type;
	int popularity;
	int property;

	public Task(){
		this.taskNumber = -1;
	}

	public Task(int taskNumber, int[] requiredResource, int reward, int deadline,int type,int slice,Random random){
		if(random.nextInt(100) > slice)
			this.property = 0;
		else {
			this.property = 1;
		}
		this.taskNumber = taskNumber;
		this.requiredResource = requiredResource;
		this.reward = reward;
		//this.reward = random.nextInt(91)+90;
		this.deadline = deadline;
		this.originalDeadline = deadline;
		this.type = type;
	}

	public int popularity(){
		return this.popularity;
	}
	public void popularity(int n){
		this.popularity = n;
	}
	public int taskNumber(){
		return this.taskNumber;
	}
	public void taskNumber(int n){
		 this.taskNumber = n;
	}

	public int[] requiredResource(){
		return this.requiredResource;
	}
	public int requiredResource(int n){
		return this.requiredResource[n];
	}
	public int deadline(){
		return this.deadline;
	}
	public int originalDeadline(){
		return this.originalDeadline;
	}
	public int reward(){
		return this.reward;
	}
	public int elapse(){
		this.deadline--;
		return this.deadline;
	}
	public int type(){
		return this.type;
	}
	public String toString(){
		return "taskNum:" + this.taskNumber + ",resource:"
				+ "[" + this.requiredResource[0] + "]" + "[" + this.requiredResource[1] + "]"
	 			+ "[" + this.requiredResource[2] + "]" + ",deadline:"+this.deadline;
	}
}