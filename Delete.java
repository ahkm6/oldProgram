package taaikes;

import java.util.ArrayList;

public class Delete{

	public void agent(ArrayList<ArrayList<Bid>> envy, ArrayList<ArrayList<Bid>> bid,Bid b){  // reduce envy

		int winner = b.agent();
		int p = b.pref();

		for (int i = 0; i < bid.size(); i++) {
			if (bid.get(i).get(0).agent() == winner) {
				if (p > 1) {
					ArrayList<Bid> temp = new ArrayList<Bid>();
					for (Bid d : bid.get(i)) {
						if (d.pref() == p) {
							break;
						}
						temp.add(d);
					}
					envy.add(temp);
					envy.get(envy.size()-1).add(b);
				}
				bid.remove(i);
				break;
			}
		}
	}

	public void deleteAgent(ArrayList<ArrayList<Bid>> bid,int item){
		int pref;
		for (ArrayList<Bid> f : bid) {
			for (int i = 0; i < f.size(); i++) {
				if (f.get(i).task() == item) {
					f.get(i).flag();
					pref = f.get(i).pref();
					if (f.size() - 1 != i) {
						if (pref == 1) {
							for (int j = i + 1; j < f.size(); j++) {
								if (f.get(j).getFlag() == 0) {
									if (f.get(j).pref() == 2) {
										for (int k = j; k < f.size(); k++) {
											f.get(k).up();
										}
									}
									break;
								}
							}
						} else {
							for (int j = i + 1; j < f.size(); j++) {
								if (f.get(j).getFlag() == 0) {
									if (f.get(j).pref() > pref + 1) {
										for (int k = j; k < f.size(); k++) {
											f.get(k).up();
										}
									}
									break;
								}
							}
						}
					}
					break;
				}
			}
		}
	}

	public void deleteTask(ArrayList<Task> task, ArrayList<ArrayList<Bid>> cand, int winner,int item,ArrayList<ArrayList<Bid>> save){
		for(int i = 0; i < task.size(); i++){
			if(task.get(i).task() == item){
				task.remove(i);
				break;
			}
		}

		for (int i = 0; i < cand.size(); i++) {
			for (int j = 0; j < cand.get(i).size(); j++) {
				if(cand.get(i).get(j).task() == item){
					save.add(cand.get(i));
					cand.remove(i);
					i--;
					break;
				}
				if (cand.get(i).get(j).agent() == winner) {
					cand.get(i).remove(j);
					if (cand.get(i).isEmpty()) {
						cand.remove(i);
						i--;
					}
					break;
				}
			}
		}
		for(int i = 0; i < save.size(); i++){
			for (int j = 0; j < save.get(i).size(); j++) {
				if (save.get(i).get(j).agent() == winner) {
					save.get(i).remove(j);
					if (save.get(i).isEmpty()) {
						save.remove(i);
						i--;
					}
					break;
				}
			}
		}
	}
}