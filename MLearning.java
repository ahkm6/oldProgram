package h280314;

import java.util.ArrayList;
import java.util.Random;

public class MLearning {
	final static int EDF = 0;
	final static int HRF = 1;
	final static int SPTF = 2;
	final static int SSTF = 3;
	double rate = 0.1;

	int typeA = 20;
	int typeB = 20;
	int typeC = 20;
	int typeD = 20;

	double pTime;
	double reward;
	double match;
	double deadline;
	double pTimeStd;
	double rewardStd;
	double matchStd;
	double deadlineStd;

	double discount = 0.1;

	public void mLearning(ArrayList<Bid> order, Random random) {
		for (int i = 0; i < order.size(); i++) {
			if(order.get(i).match() == 0){
				i++;
				continue;
			}
			switch (order.get(i).type) {
			case 0:
				if (typeA / order.get(i).match() < (random.nextDouble()*0.5 + 0.5)) {
					order.remove(i);
					i--;
				}
				break;
			case 1:
				if (typeB / order.get(i).match() < (random.nextDouble()*0.5 + 0.5)) {
					order.remove(i);
					i--;
				}
				break;
			case 2:
				if (typeC / order.get(i).match() < (random.nextDouble()*0.5 + 0.5)) {
					order.remove(i);
					i--;
				}
				break;
			case 3:
				if (typeD / order.get(i).match() < (random.nextDouble()*0.5 + 0.5)) {
					order.remove(i);
					i--;
				}
				break;
			}
		}
	}
	public void calculate(ArrayList<Bid> Bid){
		double a=0;
		double b=0;
		double c=0;
		double d=0;
		this.pTime=this.reward=this.match=this.deadline=0;
		for(Bid bid : Bid){
			a+=Math.pow(bid.processingTime(),2);
			b+=Math.pow(bid.reward(),2);
			c+=Math.pow(bid.match(),2);
			d+=Math.pow(bid.deadline(),2);
			this.pTime+=bid.processingTime();
			this.reward += bid.reward();
			this.match += bid.match();
			this.deadline += bid.deadline();
		}
		this.pTime = this.pTime / Bid.size();
		this.reward = this.reward / Bid.size();
		this.match = this.match / Bid.size();
		this.deadline = this.deadline / Bid.size();
		this.pTimeStd = Math.sqrt(a-Math.pow(this.pTime,2));
		this.rewardStd = Math.sqrt(b-Math.pow(this.reward,2));
		this.matchStd = Math.sqrt(c-Math.pow(this.match,2));
		this.deadlineStd = Math.sqrt(d-Math.pow(this.deadline,2));

	}
	public void update(int type, int processingTime){
		switch(type){
		case 0:
			typeA = (int)(typeA*(1- discount) + discount*processingTime)/2;
			break;
		case 1:
			typeB = (int)(typeB*(1- discount) + discount*processingTime)/2;
			break;
		case 2:
			typeC = (int)(typeC*(1- discount) + discount*processingTime)/2;
			break;
		case 3:
			typeD = (int)(typeD*(1- discount) + discount*processingTime)/2;
			break;
		}
	}
	public void average(double[][] average){
		this.pTime = (1-rate)*this.pTime + rate * average[0][0];
		this.reward = (1-rate)*this.reward + rate * average[1][0];
		this.match = (1-rate)*this.match + rate * average[2][0];
		this.deadline = (1-rate)*this.deadline + rate * average[3][0];
	}
	public void std(double[][] average){
		this.pTimeStd = (1-rate)*this.pTimeStd + rate * Math.sqrt(average[0][1]-Math.pow(average[0][0],2));
		this.rewardStd = (1-rate)*this.rewardStd + rate * Math.sqrt(average[1][1]-Math.pow(average[1][0],2));
		this.matchStd = (1-rate)*this.matchStd + rate * Math.sqrt(average[2][1]-Math.pow(average[2][0],2));
		this.deadlineStd = (1-rate)*this.deadlineStd + rate * Math.sqrt(average[3][1]-Math.pow(average[3][0],2));
	}

	public void firstAverage(double[][] average){
		this.pTime = average[0][0];
		this.reward = average[1][0];
		this.match = average[2][0];
		this.deadline = average[3][0];
	}
	public void firstStd(double[][] average){
		this.pTimeStd =  Math.sqrt(average[0][1]-Math.pow(pTime,2));
		this.rewardStd =  Math.sqrt(average[1][1]-Math.pow(reward,2));
		this.matchStd =  Math.sqrt(average[2][1]-Math.pow(match,2));
		this.deadlineStd =  Math.sqrt(average[3][1]-Math.pow(deadline,2));
	}
	public double vias(Bid bid){
		double result = 0;
		switch(bid.strategy()){
		case EDF:
			result = -(bid.deadline()-deadline)/deadlineStd;
			break;
		case HRF:
			result = (bid.reward()-reward)/rewardStd;
			break;
		case SPTF:
			result = -(bid.processingTime()-pTime)/pTimeStd;
			break;
		case SSTF:
			result = (bid.match()-match)/matchStd;
		}
		return result;
	}
	public double vias2(Bid bid){
		double result = 0;
		switch(bid.strategy()){
		case EDF:
			result = (bid.deadline()-5)/15;
			break;
		case HRF:
			if(bid.property == 0)
			result = (bid.reward()-90)/60;
			else
				result = (bid.reward()-45)/78;
			break;
		case SPTF:
			result =1- (bid.processingTime()-5)/15;
			break;
		case SSTF:
			result = bid.match()/17;
		}
		return result;
	}
}