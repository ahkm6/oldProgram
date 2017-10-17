package taaikes;

import java.util.ArrayList;
import java.util.Random;

public class Allocate {

	public ArrayList<Bid> maxValue(ArrayList<ArrayList<Bid>> cand,Random random) {
		ArrayList<Bid> candidate1 = new ArrayList<Bid>();
		ArrayList<Bid> temp = new ArrayList<Bid>();
		int maxValue = 0;
		int pref = 100;

		for (ArrayList<Bid> a : cand) {
			for (Bid b : a) {

				if (b.myValue() > maxValue) {
					temp.clear();
					temp.add(b);
					pref = b.pref();
					maxValue = b.myValue();
				} else if (b.myValue() == maxValue) {
					if (b.pref() < pref) {
						temp.clear();
						temp.add(b);
						pref = b.pref();
					}
					else if(b.pref() == pref){
						temp.add(b);
					}
				}
			}
			if (temp.size() > 0) {
				candidate1.add(temp.get(random.nextInt(temp.size())));
			}
			temp.clear();
			maxValue = 0;
			pref = 100;
		}
		return candidate1;
	}

	public ArrayList<Bid> maxPref(ArrayList<Bid> candidate1) {
		ArrayList<Bid> candidate2 = new ArrayList<Bid>();

		int pref = 100;
		for (Bid b : candidate1) {
			if (b.pref() < pref) {
				candidate2.clear();
				candidate2.add(b);
				pref = b.pref();
			} else if (b.pref() == pref) {
				candidate2.add(b);
			}
		}

		return candidate2;
	}

	public ArrayList<Bid> value(ArrayList<Bid> cand) {
		ArrayList<Bid> temp = new ArrayList<Bid>();
		int value = cand.get(0).myValue();
		temp.add(cand.get(0));
		for (int i = 1; i < cand.size(); i++) {
			if (value < cand.get(i).myValue()) {
				value = cand.get(i).myValue();
				temp.clear();
				temp.add(cand.get(i));

			} else if (value == cand.get(0).myValue()) {
				temp.add(cand.get(i));
			}
		}
		return temp;
	}

	public Bid SRNF(ArrayList<Bid> cand, ArrayList<ArrayList<Bid>> bid) {
		int[] s = new int[cand.size()];
		int counter = 0;
		for (Bid b : cand) {
			int task = b.task();
			for (int i = 0; i < bid.size(); i++) {
				if (task == bid.get(i).get(0).task()) {
					s[counter] = bid.get(i).size();
					counter++;
					break;
				}
			}
		}
		int t = 0;
		int size = s[0];
		for (int i = 1; i < s.length; i++) {
			if (size < s[i]) {
				t = i;
				size = s[i];
			}
		}
		return cand.get(t);
	}

	public Bid LNVF(ArrayList<Bid> cand, ArrayList<ArrayList<Bid>> bid) {
		int[] s = new int[cand.size()];
		int counter = 0;
		for (Bid b : cand) {
			int task = b.task();
			for (int i = 0; i < bid.size(); i++) {
				if (task == bid.get(i).get(0).task()) {
					s[counter] = bid.get(i).size();
					counter++;
					break;
				}
			}
		}
		int t = 0;
		int size = s[0];
		for (int i = 1; i < s.length; i++) {
			if (size < s[i]) {
				t = i;
				size = s[i];
			}
		}
		return cand.get(t);
	}

	public Bid reallocation1(ArrayList<Bid> exchange,
			ArrayList<ArrayList<Bid>> envy, Bid release, Bid b) {

		for (int i = 0; i < envy.size(); i++) {
			for (int j = 0; j < envy.get(i).size(); j++) {
				if (envy.get(i).get(j).task() == b.task()) {
					if (envy.get(i).get(j).myValue() > b.myValue()) {
						release = b;
						exchange.add(envy.get(i).get(j));
						//System.out.println("....................");
					}
					break;
				}
			}
		}
		return release;
	}

	public Bid[] reallocation2(ArrayList<ArrayList<Bid>> cand,
			ArrayList<ArrayList<Bid>> envy, ArrayList<Task> task,
			ArrayList<Bid> exchange, Bid b, Bid release,
			ArrayList<ArrayList<Bid>> bid, ArrayList<ArrayList<Bid>> save,
			Random random) {

		Bid[] kari = new Bid[2];
		int v = exchange.get(0).myValue();
		int p = exchange.get(0).pref();
		ArrayList<Bid> ex = new ArrayList<Bid>();
		ex.add(exchange.get(0));

		for (int i = 1; i < exchange.size(); i++) {
			if (v < exchange.get(i).myValue()) {
				ex.clear();
				ex.add(exchange.get(i));
				v = exchange.get(i).myValue();
				p = exchange.get(i).pref();
			} else if (v == exchange.get(i).myValue()) {
				if (p > exchange.get(i).pref()) {
					ex.clear();
					ex.add(exchange.get(i));
					v = exchange.get(i).myValue();
					p = exchange.get(i).pref();
				} else if (p == exchange.get(i).pref()) {
					ex.add(exchange.get(i));
				}
			}
		}
		b = ex.get(random.nextInt(ex.size()));
		for (int i = 0; i < envy.size(); i++) {
			if (envy.get(i).get(0).agent() == b.agent()) {
				release = envy.get(i).get(envy.get(i).size() - 1);
				envy.remove(i);
				break;
			}
		}
		exchange.clear();
		ex.clear();

		task.add(release.TASK());
		int t = release.task();

		for (int i = 0; i < save.size(); i++) {
			if (release.task() == save.get(i).get(0).task()) {
				cand.add(save.get(i));
				save.remove(i);
				break;
			}
		}
		int count = 0;
		for (ArrayList<Bid> bi : bid) {
			for (int j = 0; j < bi.size(); j++) {
				if (t == bi.get(j).task()) {
					bi.get(j).deflag();
					for (int k = j + 1; k < bi.size(); k++) {
						if (bi.get(k).getFlag() == 1) {
							continue;
						}
						bi.get(j).setPref(bi.get(k).pref());
						if (bi.get(k).realPref() != bi.get(j).realPref()) {
							for (int i = k; i < bi.size(); i++) {
								bi.get(i).down(1);
							}
						}
						count = 1;
						break;
					}
					if (count == 0) {
						for (int k = j - 1; k > 0; k--) {
							if (bi.get(k).getFlag() == 1) {
								continue;
							}
							if (bi.get(k).realPref() == bi.get(j).realPref()) {
								bi.get(j).setPref(bi.get(k).pref());
							} else {
								bi.get(j).setPref(bi.get(k).pref() + 1);
							}
						}
					}
				}
			}
		}
		kari[0] = b;
		kari[1] = release;
		return kari;
	}

	public Bid[] reallocation3(ArrayList<ArrayList<Bid>> cand,
			ArrayList<ArrayList<Bid>> envy, ArrayList<Task> task,
			ArrayList<Bid> exchange, Bid b, Bid release,
			ArrayList<ArrayList<Bid>> bid, ArrayList<ArrayList<Bid>> save,
			Random random) {
		Bid[] kari = new Bid[2];
		b = exchange.get(0);
		for (int i = 0; i < envy.size(); i++) {
			if (envy.get(i).get(0).agent() == b.agent()) {
				release = envy.get(i).get(envy.get(i).size() - 1);
				envy.remove(i);
				break;
			}
		}
		exchange.clear();

		task.add(release.TASK());

		for (int i = 0; i < save.size(); i++) {
			if (release.task() == save.get(i).get(0).task()) {
				cand.add(save.get(i));
				save.remove(i);
				break;
			}
		}
		int count = 0;
		int t = release.task();
		for (ArrayList<Bid> bi : bid) {
			for (int j = 0; j < bi.size(); j++) {
				if (t == bi.get(j).task()) {
					for (int k = j + 1; k < bi.size(); k++) {
						if (bi.get(k).getFlag() == 1) {
							continue;
						}
						bi.get(j).setPref(bi.get(k).pref());
						if (bi.get(k).realPref() != bi.get(j).realPref()) {
							for (int i = k; i < bi.size(); i++) {
								bi.get(i).down(1);
							}
						}
						count = 1;
						break;
					}
					if (count == 0) {
						for (int k = j - 1; k > 0; k--) {
							if (bi.get(k).getFlag() == 1) {
								continue;
							}
							if (bi.get(k).realPref() == bi.get(j).realPref()) {
								bi.get(j).setPref(bi.get(k).pref());
							} else {
								bi.get(j).setPref(bi.get(k).pref() + 1);
							}
						}
					}
				}
			}
		}
		kari[0] = b;
		kari[1] = release;
		return kari;
	}

	public double[] lastAllocation(ArrayList<Task> task,
			ArrayList<Agent> agent, ArrayList<ArrayList<Bid>> envy, double sum,
			Random random, int processed, double duration) {
		double[] s = new double[5];
		Q q = new Q();
		ArrayList<Bid> temp = new ArrayList<Bid>();
		ArrayList<ArrayList<Bid>> minus = new ArrayList<ArrayList<Bid>>();
		for (int i = 0; i < task.size(); i++) {
			temp.clear();
			for (ArrayList<Bid> e : envy) {
				for (Bid a : e) {
					if (task.get(i).task() == a.task()) {
						temp.add(a);
						minus.add(e);
						break;
					}
				}
			}
			if (temp.size() == 1) {

				agent.get(temp.get(0).agent()).turn(temp.get(0).process());
				agent.get(temp.get(0).agent()).allot(temp.get(0).get(),temp.get(0).process(),temp.get(0).deadline());
				duration = duration + temp.get(0).TASK().duration() + temp.get(0).process();
		//		type[temp.get(0).type()] = q.updateType(type[temp.get(0).type()],temp.get(0).process());
			//	agent.get(temp.get(0).agent()).allot(1/(temp.get(0).TASK().number() * temp.get(0).process()));
	/*			agent.get(temp.get(0).agent()).reallocation(temp.get(0).type(),
						temp.get(0).process());*/
//				if (temp.get(0).judge() == 1) {
					processed++;
/*					success[temp.get(0).deadline()]++;
				} else {
					failure++;
					miss[temp.get(0).deadline()]++;
				}*/
				task.remove(i);
				i--;
				sum = sum + temp.get(0).value()
						- minus.get(0).get(minus.get(0).size() - 1).value();
				task.add(minus.get(0).get(minus.get(0).size() - 1).TASK());
//				if (minus.get(0).get(minus.get(0).size() - 1).judge() == 1) {
					processed--;

//					success[minus.get(0).get(minus.get(0).size() - 1).deadline]--;
/*				} else {
					failure--;
					miss[minus.get(0).get(minus.get(0).size() - 1).deadline]--;
				}*/
				int pr = temp.get(0).realPref();
				if (pr == 1) {
					minus.get(0).clear();
				}
				for (int k = 0; k < minus.get(0).size(); k++) {
					if (minus.get(0).get(k).realPref() == pr) {
						for (int l = k; l < minus.get(0).size(); l++) {
							minus.get(0).remove(l);
							l--;
						}
					}
					minus.get(0).add(temp.get(0));
				}
				minus.clear();
			} else if (temp.size() > 1) {
				int tvalue = temp.get(0).myValue();
				int tp = temp.get(0).realPref();
				ArrayList<Integer> count = new ArrayList<Integer>(temp.size());
				count.add(0);
				for (int k = 1; k < temp.size(); k++) {
					if (tvalue < temp.get(k).myValue()) {
						count.clear();
						count.add(k);
						tvalue = temp.get(k).myValue();
						tp = temp.get(k).realPref();
					} else if (tvalue == temp.get(k).myValue()) {
						if (tp < temp.get(k).realPref()) {
							count.clear();
							count.add(k);
							tp = temp.get(k).realPref();
						} else if (tp == temp.get(k).realPref()) {
							count.add(k);
						}
					}
				}
				if (count.size() == 1) {
/*					agent.get(temp.get(count.get(0)).agent()).turn(
							temp.get(count.get(0)).process());
					agent.get(temp.get(count.get(0)).agent()).reallocation(
							temp.get(count.get(0)).type(),
							temp.get(count.get(0)).process());*/
//					if (temp.get(count.get(0)).judge() == 1) {
						agent.get(temp.get(count.get(0)).agent()).allot(temp.get(count.get(0)).get(),temp.get(count.get(0)).process(),temp.get(count.get(0)).deadline());
						duration = duration + temp.get(count.get(0)).TASK().duration() + temp.get(count.get(0)).process();
	//					type[temp.get(count.get(0)).type()] = q.updateType(type[temp.get(count.get(0)).type()],temp.get(count.get(0)).process());
					//	agent.get(temp.get(count.get(0)).agent()).allot(1/(temp.get(count.get(0)).TASK().number() * temp.get(count.get(0)).process()));
						processed++;
//						success[temp.get(count.get(0)).deadline()]++;
/*					} else {
						failure++;
						miss[temp.get(count.get(0)).deadline()]++;
					}*/
					task.remove(i);
					i--;
					sum = sum
							+ temp.get(count.get(0)).value()
							- minus.get(count.get(0))
									.get(minus.get(count.get(0)).size() - 1)
									.value();
					task.add(minus.get(count.get(0))
							.get(minus.get(count.get(0)).size() - 1).TASK());

//					if (minus.get(count.get(0))
//							.get(minus.get(count.get(0)).size() - 1).judge() == 1) {
						processed--;
/*						success[minus.get(count.get(0))
								.get(minus.get(count.get(0)).size() - 1)
								.deadline()]--;
					} else {
						failure--;
						miss[minus.get(count.get(0))
								.get(minus.get(count.get(0)).size() - 1)
								.deadline()]--;
					}*/
					int pr;
					if ((pr = temp.get(count.get(0)).realPref()) == 1) {
						minus.get(count.get(0)).clear();
					}
					for (int k = 0; k < minus.get(count.get(0)).size(); k++) {
						if (minus.get(count.get(0)).get(k).realPref() == pr) {
							for (int l = k; l < minus.get(count.get(0)).size(); l++) {
								minus.get(count.get(0)).remove(l);
								l--;
							}
						}
					}
					minus.get(count.get(0)).add(temp.get(count.get(0)));
				} else {
					int a = count.get(random.nextInt(count.size()));
					agent.get(temp.get(a).agent()).turn(temp.get(a).process());
					agent.get(temp.get(a).agent()).allot(temp.get(a).get(),temp.get(a).process(),temp.get(a).deadline());
					duration = duration + temp.get(a).TASK().duration() + temp.get(a).process();
	//				type[temp.get(a).type()] = q.updateType(type[temp.get(a).type()],temp.get(a).process());
		//			agent.get(temp.get(a).agent()).allot(1/ (temp.get(a).TASK().number() * temp.get(a).process()));
	/*				agent.get(temp.get(a).agent()).reallocation(
							temp.get(a).type(), temp.get(a).process());*/
//					if (temp.get(a).judge() == 1) {
						processed++;
/*					} else {
						failure++;
					}*/
					task.remove(i);
					i--;
					sum = sum + temp.get(a).value()
							- minus.get(a).get(minus.get(a).size() - 1).value();
					task.add(minus.get(a).get(minus.get(a).size() - 1).TASK());
//					if (minus.get(a).get(minus.get(a).size() - 1).judge() == 1) {
						processed--;
/*						success[minus.get(a).get(minus.get(a).size() - 1)
								.deadline()]--;
					} else {
						failure--;
						miss[minus.get(a).get(minus.get(a).size() - 1)
								.deadline()]--;
					}*/
					int pr;
					if ((pr = temp.get(a).realPref()) == 1) {
						minus.get(a).clear();
					}
					for (int k = 0; k < minus.get(a).size(); k++) {
						if (minus.get(a).get(k).realPref() == pr) {
							for (int l = k; l < minus.get(a).size(); l++) {
								minus.get(a).remove(l);
								l--;
							}
						}
					}
					minus.get(a).add(temp.get(a));
				}
				minus.clear();
			}
		}
		s[0] = sum;
//		s[1] = failure;
		s[2] = processed;
		return s;
	}
}