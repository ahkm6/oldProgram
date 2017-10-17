package taaikes;


import java.util.Random;

public class Q{

	double rate = 0.1;
	double discount = 0.9;
	double epsilon = 0.1;
	double t = 50;
	double slesh = 0.5;

	int howmany = 4;

	public void update(Agent age, double reward){

			age.Q[age.type()] =(1-rate) * age.Q[age.type()]  +  rate * reward;

	}

	public double updateType(double type, int process){

		return type = (1-rate) * type + rate * process;

	}

	public double reverse(double type, int process){

		return type = (type-rate*process)/(1-rate);

	}

	public int greedy(Agent age,Random random){
		int type = 0;
		
			if(random.nextDouble() < epsilon){
				type = age.typeChange(random.nextInt(howmany));
			}
			else{
				type =  age.typeChange(number(age.Q,random));
			}

		return type;
	}

	public int sftmax(Agent age,Random random){
		int type = 0;
		double policy[] = new double[howmany];
		double policy_sum = 0;
		for(int i = 0; i < howmany; i++){
			policy[i] = Math.exp(age.Q[i]/t);
			policy_sum += policy[i];
		}
		for(int i = 0; i < howmany; i++){
			policy[i] = policy[i] / policy_sum;
		}
		double number = random.nextDouble();
		if(number < policy[0] ){
			type = age.typeChange(0);
		}else if(number < policy[0] + policy[1]){
			type = age.typeChange(1);
		}else if(number < policy[0] + policy[1] + policy[2]){
			type = age.typeChange(2);
		}else{
			type = age.typeChange(3);
		}

	//	if(age.agent() == 0)
	//	System.out.println(policy[0] + "," + policy[1] + "," + policy[2] + "," + policy[3]);

		return type;
	}

	public void down(){
		if(this.t > slesh)
		this.t -= 0.1;
	}

	public double t(){
		return this.t;
	}

	public double max(double[] a){

		double c = a[0];

		for(int i = 1; i < a.length; i++){
			if(c < a[i]){
				c=a[i];
			}
		}

		return c;
	}

	public int number(double[] a,Random random){
		int b = 0;
		double c = a[0];
		
		for(int i = 1; i < a.length; i++){
			if(c < a[i]){
				c=a[i];
				b = i;
			}
		}
		if(a[0] == 0){
			if(a[1] == 0)
				if(a[2]==0)
					if(a[3]==0)
						b = random.nextInt(4);
		}
		return b;
	}
}