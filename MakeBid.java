package taaikes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MakeBid {

	final static int EDF = 0;
	final static int HRF = 1;
	final static int MSF = 4;
	final static int SSTF = 2;
	final static int SPTF = 3;
	Q q = new Q();
	final static int order = 4;

	public void biddingRandom(ArrayList<ArrayList<Bid>> cand,
			ArrayList<ArrayList<Bid>> bid, Random random, int N) {
		int[] order = new int[3];
		int center;
		int preference;
		int value;
		int left;
		int right;
		for (int i = 0; i < N; i++) {

			ArrayList<Bid> temp = new ArrayList<Bid>();

			value = random.nextInt(301) * 10 + 2000;
			preference = 1;
			center = random.nextInt(N);
			right = left = center;
			Bid b = null;

			for (int k = 0; k < order.length; k++) {
				order[k] = random.nextInt(3);
			}
			b = new Bid(i, center, preference, value);
			temp.add(b);
			cand.get(center).add(b);
			if (order[0] != 0) {
				for (int j = 0; j < order[0]; j++) {
					if ((left -= 1) < 0) {
						b = new Bid(i, N + left, preference, value);
						temp.add(b);
						cand.get(N + left).add(b);
					} else {
						b = new Bid(i, left, preference, value);
						temp.add(b);
						cand.get(left).add(b);
					}
					if ((right += 1) > N - 1) {
						b = new Bid(i, right - N, preference, value);
						temp.add(b);
						cand.get(right - N).add(b);
					} else {
						b = new Bid(i, right, preference, value);
						temp.add(b);
						cand.get(right).add(b);
					}
				}
				preference++;
			} else {
				preference++;
			}

			for (int j = 1; j < order.length; j++) {
				if (order[j] > 0) {
					value = random.nextInt(301) * 10 + 2000;
					for (int k = 0; k < order[j]; k++) {
						if ((left -= 1) < 0) {
							b = new Bid(i, N + left, preference, value);
							temp.add(b);
							cand.get(N + left).add(b);
						} else {
							b = new Bid(i, left, preference, value);
							temp.add(b);
							cand.get(left).add(b);
						}
						if ((right += 1) > N - 1) {
							b = new Bid(i, right - N, preference, value);
							temp.add(b);
							cand.get(right - N).add(b);
						} else {
							b = new Bid(i, right, preference, value);
							temp.add(b);
							cand.get(right).add(b);
						}
					}
					preference++;
				}
			}
			value = random.nextInt(301) * 10 + 2000;
			if (left < 0) {
				left += N;
			}
			if ((right += 1) > N - 1) {
				right -= N;
				while (right != left) {
					b = new Bid(i, right, preference, value);
					temp.add(b);
					cand.get(right).add(b);

					right++;

				}
			} else {

				while (right != left) {
					b = new Bid(i, right, preference, value);
					temp.add(b);
					cand.get(right).add(b);
					if (++right > N - 1) {
						right -= N;

					}
				}
			}
			bid.add(temp);
		}
	}

	public void biddingResource(ArrayList<Task> task, ArrayList<Agent> agent,
			ArrayList<ArrayList<Bid>> cand, ArrayList<ArrayList<Bid>> bid,
			Random random,int x) {
		int counter;
		int N = agent.size();

		for (Agent a : agent) {

			if (a.off() > 0)
				continue;
			counter = 0;
			ArrayList<Bid> temp = new ArrayList<Bid>();
			ArrayList<Bid> OK = new ArrayList<Bid>();
			for (Task t : task) {

				double[] s = process(a.resource(), t.resource());
				if (t.deadline() < s[0]) {
					counter++;
					continue;
				}
				temp.add(new Bid(a.agent(), t.task(), 1, t.value(), (int) s[0],
						t.deadline(), counter, t.value(), t, (s[0] - s[1]),random,x));
				counter++;

			}
			if (temp.isEmpty()) {
				continue;
			}
			int preference = 0;
			int value = 0;
			int myValue = 0;
			double multi =1.0;
			switch (a.type) {
			case EDF:
				Collections.sort(temp, new EDF());
				int deadline = temp.get(0).deadline();
				myValue = temp.get(0).myValue();
				for (Bid b : temp) {
					if (b.deadline() == deadline) {
						if (b.myValue() == myValue) {
							b.down(preference);
							b.rdown(preference,multi);
							OK.add(b);
							b.TASK().inc();
							continue;
						}
					}
					if (++preference > order)
						break;
					multi-=0.1;
					b.down(preference);
					b.rdown(preference,multi);
					OK.add(b);
					myValue = b.myValue();
					deadline = b.deadline();
					b.TASK().inc();
				}
				break;
			case HRF:

				Collections.sort(temp, new HRF());
				value = temp.get(0).value();
				myValue = temp.get(0).myValue();
				for (Bid b : temp) {
					if (b.value() == value) {
						if(b.myValue() == myValue){
						b.down(preference);
						b.rdown(preference,multi);
						OK.add(b);
						b.TASK().inc();
						continue;
					}
					}
					if (++preference > order)
						break;
					multi -= 0.1;
					b.down(preference);
					b.rdown(preference,multi);
					OK.add(b);
					value = b.value();
					myValue = b.myValue();
					b.TASK().inc();
				}
				break;
			case MSF:
				Collections.sort(temp, new MSF());
				double percent = temp.get(0).percent();
				myValue = temp.get(0).myValue();
				for (Bid b : temp) {
					if (b.percent() == percent) {
						if (b.myValue() == myValue) {
							b.rdown(preference,multi);
							b.down(preference);
							OK.add(b);
							continue;
						}
					}
					if (++preference > order)
						break;
					multi-=0.1;
					b.down(preference);
					b.rdown(preference,multi);
					OK.add(b);
					myValue = b.myValue();
					percent = b.percent();
				}
				break;
			case SSTF:

				Collections.sort(temp, new SSTF());
				int subst = temp.get(0).subst();
				myValue = temp.get(0).myValue();
				for (Bid b : temp) {
					if (b.subst() == subst)
						if (b.myValue() == myValue) {
							b.down(preference);
							b.rdown(preference,multi);
							OK.add(b);
							b.TASK().inc();
							continue;
						}
					if (++preference > order)
						break;
					multi-=0.1;
					b.down(preference);
					b.rdown(preference,multi);
					OK.add(b);
					myValue = b.myValue();
					subst = b.subst();
					b.TASK().inc();
				}
				break;
			case SPTF:

				Collections.sort(temp, new SPTF());
				int time = temp.get(0).process();
				myValue = temp.get(0).myValue();
				for (Bid b : temp) {
					if (b.process() == time)
						if (b.myValue() == myValue) {
							b.down(preference);
							b.rdown(preference,multi);
							OK.add(b);
							b.TASK().inc();
							continue;
						}
					if (++preference > order)
						break;
					multi-=0.1;
					b.down(preference);
					b.rdown(preference,multi);
					OK.add(b);
					myValue = b.myValue();
					time = b.process();
					b.TASK().inc();
				}
				break;
			}
			bid.add(OK);
			for (Bid o : OK) {
				cand.get(o.temp()).add(o);
			}
		}
	}

	public double[] process(int[] a, int[] t) {
		double c = 0;
		double d = 0;
		double[] e = new double[2];
		c = d = Math.ceil((double) t[0] / a[0]);
		for (int i = 1; i < a.length; i++) {
			if (c < Math.ceil((double) t[i] / a[i])) {
				c = Math.ceil((double) t[i] / a[i]);
			} else if (d > Math.ceil((double) t[i] / a[i])) {
				d = Math.ceil((double) t[i] / a[i]);
			}
		}
		e[0] = c;
		e[1] = d;
		return e;
	}

}