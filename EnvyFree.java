package h280314;

import java.util.ArrayList;
import java.util.Random;

public class EnvyFree{

	public ArrayList<Bid> envyFree(ArrayList<ArrayList<Bid>> envyList, Bid choice){
		ArrayList<Bid> envy = new ArrayList<Bid>();
		int taskNumber = choice.taskNumber();
		int value = choice.value();
		//envyがないかどうか調査
		for(ArrayList<Bid> tempo : envyList){
			for(Bid temp : tempo){
				//発見
				if(taskNumber == temp.taskNumber()){
					if(value < temp.value()){
						envy.add(temp);
					}
				}
			}
		}
		//envyがないかどうか調査
		return envy;
	}

	public Bid change(ArrayList<Bid> envy,ArrayList<Agent> agent,ArrayList<Bid> taskList, ArrayList<ArrayList<Bid>> allocatedTask,ArrayList<ArrayList<Bid>> Bid,Random random,ArrayList<ArrayList<Bid>> envyList){
		Bid change;
	//	System.out.println("不満なんだぞ、と");
		if (envy.size() == 1) {
			change = envy.get(0);
		} else {
			ArrayList<Bid> temp = new ArrayList<Bid>();
			temp.add(envy.get(0));
			int value = envy.get(0).value();
			for (int i = 1; i < envy.size(); i++) {
				if (value < envy.get(i).value()) {
					value = envy.get(i).value();
					temp.clear();
					temp.add(envy.get(i));
				} else if (value == envy.get(i).value()) {
					temp.add(envy.get(i));
				}
			}
			if (envy.size() > 1) {
				change = temp.get(random.nextInt(temp.size()));
			} else {
				change = temp.get(0);
			}
		}
		int taskNumber = change.taskNumber();
		for (int i = 0; i < allocatedTask.size(); i++) {
			if (taskNumber == allocatedTask.get(i).get(0).taskNumber()) {
				Bid.add(allocatedTask.get(i));
				allocatedTask.remove(i);
				break;
			}
		}
		for(int i = 0; i < envyList.size(); i++){
			if(envyList.get(i).get(0).agentNumber() == change.agentNumber()){
				envyList.remove(i);
				break;
			}
		}
		return change;
	}
}