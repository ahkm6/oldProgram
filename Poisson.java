package taaikes;


import java.util.Random;

public class Poisson{
	public int poisson(int lambda, Random random){
		double xp;
		int k = 0;
		xp = random.nextDouble();
		while (xp >= Math.exp(-lambda)) {
			xp = xp * random.nextDouble();
			k = k + 1;
		}
		return (k);
	}
}