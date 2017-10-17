package h280314;

public class Name {
	public String PrintTactics(int n) {
		String S = null;
		switch (n) {
		case 0:
			S = "EDF";
			System.out.println("EDF");
			break;
		case 1:
			S = "HRF";
			System.out.println("HRF");
			break;
		case 2:
			S = "SPTF";
			System.out.println("SPTF");
			break;
		case 3:
			S = "SSTF";
			System.out.println("SSTF");
			break;
		default:
			S = "learning";
			System.out.println("LEARN");
		}
		return S;
	}

	public String PrintAgent(int n) {
		String S = null;
		switch (n) {
		case 0:
			S = "RANDOM";
			System.out.println("RANDOM");
			break;
		case 1:
			S = "GOODBAD";
			System.out.println("GOODBAD");
			break;
		case 2:
			S = "VIASGOOD";
			System.out.println("VIASGOOD");
			break;
		case 3:
			S = "NORMAL";
			System.out.println("NORMAL");
			break;
		}
		return S;
	}

	public String PrintValue(int n) {
		String S = null;
		switch (n) {
		case 0:
			S = "PROCESSTIME";
			System.out.println("PROCESSTIME");
			break;
		case 1:
			S = "REWARD";
			System.out.println("REWARD");
			break;
		}
		return S;
	}

	public String PrintLearn(int n) {
		String S = null;
		switch (n) {
		case 0:
			S = "NoLearm";
			System.out.println("NoLearn");
			break;
		case 1:
			S = "MLearn";
			System.out.println("MLearn");
			break;
		}
		return S;
	}

	public double[] vias(int n) {
		double th[] = new double[3];
		switch (n) {
		case 0:
			th[0] = 1;
			th[1] = 0;
			th[2] = 0;
			break;
		case 1:
			th[0] = 0.25;
			th[1] = 0.5;
			th[2] = 0.75;
			break;
		case 2:
			th[0] = 0.1;
			th[1] = 0.2;
			th[2] = 0.3;
			break;
		case 3:
			th[0] = 0;
			th[1] = 0;
			th[2] = 0;
			break;
		}
		return th;
	}
}