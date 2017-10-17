package h280314;

import java.util.ArrayList;
import java.util.Random;

class geography {

	static int grid = 30;

	int[][] map = new int[grid][grid];

	public geography(ArrayList<Agent> agent, Random random) {
		int x, y = 0;
		map[14][14] = -1;
		for (Agent age : agent) {
			while (true) {
				x = random.nextInt(grid);
				y = random.nextInt(grid);
				if (map[x][y] == 0) {
					age.coordinate(x, y);
					map[x][y] = age.agentNumber + 1;
					break;
				}
			}
		}
	}
}