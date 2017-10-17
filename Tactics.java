package h280314;

import java.util.ArrayList;
import java.util.Random;

public class Tactics {

	final static int EDF = Main.EDF;
	final static int HRF = Main.HRF;
	final static int SPTF = Main.SPTF;
	final static int SSTF = Main.SSTF;
	final static int LEARNING = Main.LEARNING;
	MLearning mlearning = new MLearning();

	public void sort(int flag,int n, int bidNumber, ArrayList<Bid> tempBid,
			ArrayList<ArrayList<Bid>> Bid, ArrayList<ArrayList<Bid>> agentBid,
			Tactics tactics, int value,Random random) {
		ArrayList<Bid> order = new ArrayList<>();
		ArrayList<Bid> temp = new ArrayList<Bid>();
		int agentNumber = tempBid.get(0).agentNumber();
		double bound = -1;
		int count = 0;
		switch (n) {
		case EDF:
			// bidNumber分選ぶ

			while (order.size() < bidNumber && order.size() < tempBid.size()) {
				int deadline = 100;
				for (Bid b : tempBid) {
					// デッドラインが高かったら
					if (deadline > b.deadline() && bound < b.deadline()) {
						deadline = b.deadline();
						temp.clear();
						temp.add(b);
						continue;
					}
					// デッドラインが同じだったら
					if (deadline == b.deadline()) {
						temp.add(b);
					}
				}
				bound = deadline;
				// 一つだったら
				if (temp.size() == 1) {
					temp.get(0).setPreferentialOrder(count);
					order.add(temp.get(0));
					count++;
					if (count == bidNumber)
						break;
					else
						continue;
				}
				// 二つ以上だったら
				if (temp.size() > 1) {
					int thisvalue = temp.get(0).value();
					// 全部に順位をつけるまで
					while (temp.size() != 0) {
						ArrayList<Bid> temp2 = new ArrayList<Bid>();
						temp2.add(temp.get(0));
						for (int i = 1; i < temp.size(); i++) {
							if (thisvalue < temp.get(i).value()) {
								thisvalue = temp.get(i).value();
								temp2.clear();
								temp2.add(temp.get(i));
								continue;
							}
							if (thisvalue == temp.get(i).value()) {
								temp2.add(temp.get(i));
							}
						}
						// 順位づけ
						for (Bid b : temp2) {
							b.setPreferentialOrder(count);
							order.add(b);
							for (int j = 0; j < temp.size(); j++) {
								if (temp.get(j) == b) {
									temp.remove(j);
									break;
								}
							}
						}
						count++;
						temp2.clear();
						// 順位づけ
					}
					temp.clear();
					// 全部に順位をつけるまで
				}
				if (order.size() > bidNumber || order.size() > tempBid.size()) {
					while (order.size() > bidNumber
							|| order.size() > tempBid.size()) {
						order.remove(order.size() - 1);
					}
				}
			}
			// bidNumber分選ぶ
			agentBid.add(order);
			for (Bid insert : order) {
				Bid.get(insert.taskNumber()).add(insert);
			}
			break;
		case HRF:
			// bidNumber分選ぶ
			bound = 10000;
			count = 0;
			while (order.size() < bidNumber && order.size() < tempBid.size()) {
				int reward = 0;
				for (Bid b : tempBid) {
					// デッドラインが高かったら
					if (reward < b.reward() && bound > b.reward()) {
						reward = b.reward();
						temp.clear();
						temp.add(b);
						continue;
					}
					// デッドラインが同じだったら
					if (reward == b.reward()) {
						temp.add(b);
					}
				}
				bound = reward;
				// 一つだったら
				if (temp.size() == 1) {
					temp.get(0).setPreferentialOrder(count);
					order.add(temp.get(0));
					count++;
					if (count == bidNumber)
						break;
					else
						continue;
				}
				// 二つ以上だったら
				if (temp.size() > 1) {
					int thisvalue = temp.get(0).value();
					// 全部に順位をつけるまで
					while (temp.size() != 0) {
						ArrayList<Bid> temp2 = new ArrayList<Bid>();
						temp2.add(temp.get(0));
						for (int i = 1; i < temp.size(); i++) {
							if (thisvalue < temp.get(i).value()) {
								thisvalue = temp.get(i).value();
								temp2.clear();
								temp2.add(temp.get(i));
								continue;
							}
							if (thisvalue == temp.get(i).value()) {
								temp2.add(temp.get(i));
							}
						}
						// 順位づけ
						for (Bid b : temp2) {
							b.setPreferentialOrder(count);
							order.add(b);
							for (int j = 0; j < temp.size(); j++) {
								if (temp.get(j) == b) {
									temp.remove(j);
									break;
								}
							}
						}
						count++;
						temp2.clear();
						// 順位づけ
					}
					temp.clear();
					// 全部に順位をつけるまで
				}
				if (order.size() > bidNumber || order.size() > tempBid.size()) {
					while (order.size() > bidNumber
							|| order.size() > tempBid.size()) {
						order.remove(order.size() - 1);
					}
				}
			}
			// bidNumber分選ぶ
			agentBid.add(order);
			for (Bid insert : order) {
				Bid.get(insert.taskNumber()).add(insert);
			}
			break;
		case SPTF:
			// bidNumber分選ぶ
			bound = 0;
			while (order.size() < bidNumber && order.size() < tempBid.size()) {
				int processingTime = 1000;
				for (Bid b : tempBid) {
					// デッドラインが高かったら
					if (processingTime > b.processingTime()
							&& bound < b.processingTime()) {
						processingTime = b.processingTime();
						temp.clear();
						temp.add(b);
						continue;
					}
					// デッドラインが同じだったら
					if (processingTime == b.processingTime()) {
						temp.add(b);
					}
				}
				bound = processingTime;
				// 一つだったら
				if (temp.size() == 1) {
					temp.get(0).setPreferentialOrder(count);
					order.add(temp.get(0));
					count++;
					if (count == bidNumber)
						break;
					else
						continue;
				}
				// 二つ以上だったら
				if (temp.size() > 1) {
					int thisvalue = temp.get(0).value();
					// 全部に順位をつけるまで
					while (temp.size() != 0) {
						ArrayList<Bid> temp2 = new ArrayList<Bid>();
						temp2.add(temp.get(0));
						for (int i = 1; i < temp.size(); i++) {
							if (thisvalue < temp.get(i).value()) {
								thisvalue = temp.get(i).value();
								temp2.clear();
								temp2.add(temp.get(i));
								continue;
							}
							if (thisvalue == temp.get(i).value()) {
								temp2.add(temp.get(i));
							}
						}
						// 順位づけ
						for (Bid b : temp2) {
							b.setPreferentialOrder(count);
							order.add(b);
							for (int j = 0; j < temp.size(); j++) {
								if (temp.get(j) == b) {
									temp.remove(j);
									break;
								}
							}
						}
						count++;
						temp2.clear();
						// 順位づけ
					}
					temp.clear();
					// 全部に順位をつけるまで
				}
				if (order.size() > bidNumber || order.size() > tempBid.size()) {
					while (order.size() > bidNumber
							|| order.size() > tempBid.size()) {
						order.remove(order.size() - 1);
					}
				}
			}
			// bidNumber分選ぶ
			agentBid.add(order);
			for (Bid insert : order) {
				Bid.get(insert.taskNumber()).add(insert);
			}
			break;
		case SSTF:
			bound = 10000;
			// bidNumber分選ぶ
			while (order.size() < bidNumber && order.size() < tempBid.size()) {
				double match = 0;
				for (Bid b : tempBid) {
					// System.out.println("何が起きてる？" + tempBid.size());
					// U/Tが高かったら
					if (match < b.match() && bound > b.match()) {
						match = b.match();
						temp.clear();
						temp.add(b);
						continue;
					}
					// U/Tが同じだったら
					if (match == b.match()) {
						temp.add(b);
					}
				}
				bound = match;
				// 一つだったら
				if (temp.size() == 1) {
					temp.get(0).setPreferentialOrder(count);
					order.add(temp.get(0));
					count++;
					if (count == bidNumber)
						break;
					else
						continue;
				}
				// 二つ以上だったら
				if (temp.size() > 1) {
					int thisvalue = temp.get(0).value();
					// 全部に順位をつけるまで
					while (temp.size() != 0) {
						ArrayList<Bid> temp2 = new ArrayList<Bid>();
						temp2.add(temp.get(0));
						for (int i = 1; i < temp.size(); i++) {
							if (thisvalue < temp.get(i).value()) {
								thisvalue = temp.get(i).value();
								temp2.clear();
								temp2.add(temp.get(i));
								continue;
							}
							if (thisvalue == temp.get(i).value()) {
								temp2.add(temp.get(i));
							}
						}
						// 順位づけ
						for (Bid b : temp2) {
							b.setPreferentialOrder(count);
							order.add(b);
							for (int j = 0; j < temp.size(); j++) {
								if (temp.get(j) == b) {
									temp.remove(j);
									break;
								}
							}
						}
						count++;
						temp2.clear();
						// 順位づけ
					}
					temp.clear();
					// 全部に順位をつけるまで
				}
				if (order.size() > bidNumber || order.size() > tempBid.size()) {
					while (order.size() > bidNumber
							|| order.size() > tempBid.size()) {
						order.remove(order.size() - 1);
					}
				}
			}
			if(flag == 1){
				mlearning.mLearning(order,random);
				if(order.isEmpty()){
					order.add(new Bid(agentNumber));
					agentBid.add(order);
					break;
				}
			}
			// bidNumber分選ぶ
			agentBid.add(order);
			for (Bid insert : order) {
				Bid.get(insert.taskNumber()).add(insert);
			}
			break;
		}
	}
}