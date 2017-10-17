package taaikes;

import java.util.ArrayList;
import java.util.Random;

public class MakeObject {

	static int least = 10;
	static int var = 20;
	static int VIAS = 1;
	static int NORMAL = 0;

	int s = NORMAL;

	public ArrayList<Agent> makeAgent(int N, Random random) {
		ArrayList<Agent> agent = new ArrayList<Agent>();

		int[] resource = new int[3];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < resource.length; j++) {
				resource[j] = random.nextInt(6) + 2;
			}
			agent.add(new Agent(i, resource, random));
		}
	/*	for (int i = 0; i < 75; i++) {
			for (int j = 0; j < resource.length; j++) {
				resource[j] = random.nextInt(3) + 3;
			}
			agent.add(new Agent(i, resource, random));
		}
		for (int i = 75; i < 150; i++) {
			for (int j = 0; j < resource.length - 1; j++) {
				resource[j] = random.nextInt(2) + 2;
			}
			resource[2] = random.nextInt(3) + 5;
			agent.add(new Agent(i, resource, random));
		}
		for (int i = 150; i < 225; i++) {
			resource[2] = random.nextInt(2) + 2;
			resource[1] = random.nextInt(2) + 2;
			resource[0] = random.nextInt(3) + 5;
			agent.add(new Agent(i, resource, random));
		}
		for (int i = 225; i < N; i++) {
			resource[0] = random.nextInt(2) + 2;
			resource[2] = random.nextInt(2) + 2;
			resource[1] = random.nextInt(3) + 5;
			agent.add(new Agent(i, resource, random));
		}
*/
		return agent;
	}

	public int makeTask(ArrayList<Task> task, Random random, int in,
			int number, double[] th) {

		int[] resource = new int[3];
		int i;
		int t;

		for (i = number; i < in + number; i++) {
			double d = random.nextDouble();

			if (d < th[0]) {
				t = 0;
			} else if (d < th[1]) {
				t = 1;
			} else if (d < th[2]) {
				t = 2;
			} else {
				t = 3;
			}

			switch (t) {
			case 0:
				resource[0] = random.nextInt(21) + 30;
				resource[1] = random.nextInt(21) + 30;
				resource[2] = random.nextInt(21) + 30;
				task.add(new Task(i, resource, 9, random.nextInt(var + 1)
						+ least, random));
				break;
			case 1:
				resource[1] = random.nextInt(21) + 20;
				resource[0] = random.nextInt(21) + 50;
				resource[2] = random.nextInt(21) + 20;
				task.add(new Task(i, resource, 9, random.nextInt(var + 1)
						+ least, random));
				break;
			case 2:
				resource[0] = random.nextInt(21) + 20;
				resource[1] = random.nextInt(21) + 50;
				resource[2] = random.nextInt(21) + 20;
				task.add(new Task(i, resource, 9, random.nextInt(var + 1)
						+ least, random));
				break;
			case 3:
				resource[0] = random.nextInt(21) + 20;
				resource[2] = random.nextInt(21) + 50;
				resource[1] = random.nextInt(21) + 20;
				task.add(new Task(i, resource, 9, random.nextInt(var + 1)
						+ least, random));
				break;
			}
		}
		return i;
	}

	public int makeBiasTask(ArrayList<Task> task, Random random, int in,
			int number) {

		int[] resource = new int[3];
		int i;
		int x = 0;
		int y = 3;
		if ((x = random.nextInt(10)) == 0) {
			y = 0;
		} else if (x == 1) {
			y = 1;
		} else if (x == 2) {
			y = 2;
		}

		for (i = number; i < in + number; i++) {
			switch (y) {
			case 0:
				resource[0] = random.nextInt(41) + 50;
				resource[1] = random.nextInt(21) + 20;
				resource[2] = random.nextInt(21) + 20;
				task.add(new Task(i, resource, 0, random.nextInt(var + 1)
						+ least));
				break;
			case 1:
				resource[0] = random.nextInt(21) + 20;
				resource[1] = random.nextInt(41) + 50;
				resource[2] = random.nextInt(21) + 20;
				task.add(new Task(i, resource, 1, random.nextInt(var + 1)
						+ least));
				break;
			case 2:
				resource[0] = random.nextInt(21) + 20;
				resource[1] = random.nextInt(21) + 20;
				resource[2] = random.nextInt(41) + 50;
				task.add(new Task(i, resource, 2, random.nextInt(var + 1)
						+ least));
				break;
			case 3:
				resource[0] = random.nextInt(31) + 30;
				resource[1] = random.nextInt(31) + 30;
				resource[2] = random.nextInt(31) + 30;
				task.add(new Task(i, resource, 3, random.nextInt(var + 1)
						+ least));
				break;
			}
		}
		return i;
	}
}