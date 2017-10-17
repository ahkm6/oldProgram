package h280314;

import java.util.ArrayList;
import java.util.Random;

public class Allocation{

	public ArrayList<Bid> valueMax(ArrayList<ArrayList<Bid>> Bid,Random random){
		ArrayList<Bid> candidate = new ArrayList<Bid>();
		int value = 0;
		for(ArrayList<Bid> task : Bid){
			ArrayList<Bid> Max = new ArrayList<Bid>();
			value = 0;
			for(Bid b : task){
				if(b.allocated() != 0)
					continue;
				if(b.value() > value){
					value = b.value();
					Max.clear();
					Max.add(b);
				}
				else if(b.value() == value){
					if(Max.get(0).preferentialOrder() > b.preferentialOrder()){
						Max.clear();
						Max.add(b);
					}else if(Max.get(0).preferentialOrder() == b.preferentialOrder())
					Max.add(b);
				}
			}
			if(Max.isEmpty()){
				continue;
			}
			candidate.add(Max.get(random.nextInt(Max.size())));
		}
		return candidate;
	}

	public ArrayList<Bid> preferenceMax(ArrayList<Bid> cand){
		ArrayList<Bid> candidate = new ArrayList<Bid>();
		int preferentialNumber = 100;
		for(Bid b : cand){
			if(preferentialNumber > b.preferentialOrder()){
				preferentialNumber = b.preferentialOrder();
				candidate.clear();
				candidate.add(b);
			}
			else if(preferentialNumber == b.preferentialOrder()){
				candidate.add(b);
			}
		}
		return candidate;
	}

	public Bid decition(ArrayList<Bid> candidate,Random random,ArrayList<ArrayList<Bid>> Bid){
		int value = 0;
		ArrayList<Bid> temp = new ArrayList<Bid>();
		for(Bid cand : candidate){
			if(value < cand.value()){
				temp.clear();
				value = cand.value();
				temp.add(cand);
			}
			else if(value == cand.value()){
					temp.add(cand);
			}
		}
		if(temp.size() == 1){
			return temp.get(0);
		}
		else {
			int[] compare = new int[temp.size()];
			ArrayList<Integer> kouho = new ArrayList<Integer>();
			int count = 0;
			for (int i = 0; i < temp.size(); i++) {
				for (ArrayList<Bid> b : Bid) {
					if (b.get(0).taskNumber() == temp.get(i).taskNumber()) {
						compare[i] = b.size();
						if(compare[count] > compare[i]){
							kouho.clear();
							kouho.add(i);
							count = i;
						}
						else if(compare[count] == compare[i]){
							kouho.add(i);
							count = i;
						}
						break;
					}
				}
			}
			if(kouho.size() == 1){
				return temp.get(kouho.get(0));
			}else{
				return temp.get(kouho.get(random.nextInt(kouho.size())));
			}
		}
	}
	public void reallocation(ArrayList<Task> task, ArrayList<ArrayList<Bid>> envyList,ArrayList<Bid> taskList, ArrayList<Task> allocatedTask){
		for(int i = 0; i < envyList.size(); i++){
			find : for(int j = 0; j < envyList.get(i).size(); j++){
				//不満が見つかったら即交換
				for(int m = 0; m < task.size();m++){
					if(task.get(m).taskNumber() == envyList.get(i).get(j).taskNumber()){
						allocatedTask.add(task.get(m));
						task.remove(m);
						int agentNumber = envyList.get(i).get(j).agentNumber();
						int preferentialNumber = envyList.get(i).get(j).originalPreference();
						//解放準備
						for(int k = 0; k < taskList.size(); k++){
							if(agentNumber == taskList.get(k).agentNumber()){

								//タスク戻す
								for(int n = 0; n < allocatedTask.size(); n++){
									if(taskList.get(k).taskNumber() == allocatedTask.get(n).taskNumber()){
										task.add(allocatedTask.get(n));
										break;
									}
								}
								taskList.remove(k);    //解放
								taskList.add(envyList.get(i).get(j));
								//不満リスト削除
								if(preferentialNumber == 1){
									envyList.remove(i);
									break;
								}
								for(int l = 0; l < envyList.get(i).size(); l++){
									if(envyList.get(i).get(l).originalPreference() >= preferentialNumber){
										envyList.get(i).remove(l);
										l--;
									}
								}
								i = -1;
								break find;
							}
						}
						i = -1;
						break find;
					}
				}
			}
		}

	}
}