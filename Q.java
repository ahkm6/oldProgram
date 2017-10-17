package h280314;

import java.util.ArrayList;
import java.util.Random;

public class Q {

	double rate = 0.05;
	double epsilon = 0.05;
	double t = 50;
	double slesh = 0.5;

	int howmany = 4;

	public void update(Agent age, double reward) {
		age.Q[age.policy()] = (1 - rate) * age.Q[age.policy()] + rate * reward;
	}

	public int greedy(Agent age, Random random) {
		int type = 0;

		if (random.nextDouble() < epsilon) {
			type = age.policy(random.nextInt(howmany));
		} else {
			type = age.policy(number(age.Q, random));
		}

		return type;
	}

	public int sftmax(Agent age, Random random) {
		int type = 0;
		double policy[] = new double[howmany];
		double policy_sum = 0;
		for (int i = 0; i < howmany; i++) {
			policy[i] = Math.exp(age.Q[i] / t);
			policy_sum += policy[i];
		}
		for (int i = 0; i < howmany; i++) {
			policy[i] = policy[i] / policy_sum;
		}
		double number = random.nextDouble();
		if (number < policy[0]) {
			type = age.policy(0);
		} else if (number < policy[0] + policy[1]) {
			type = age.policy(1);
		} else if (number < policy[0] + policy[1] + policy[2]) {
			type = age.policy(2);
		} else {
			type = age.policy(3);
		}
		return type;
	}

	public void down() {
		if (this.t > slesh)
			this.t -= 0.1;
	}

	public double t() {
		return this.t;
	}

	public double max(double[] a) {

		double c = a[0];

		for (int i = 1; i < a.length; i++) {
			if (c < a[i]) {
				c = a[i];
			}
		}

		return c;
	}

	public int number(double[] a, Random random) {
		double c = a[0];
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(0);
		for (int i = 1; i < a.length; i++) {
			if (c < a[i]) {
				c = a[i];
				s.clear();
				s.add(i);
			}
			if(c == a[i]){
				s.add(i);
			}
		}
		if(s.size() == 1){
			return s.get(0);
		}else{
			return s.get(random.nextInt(s.size()));
		}
	}
}