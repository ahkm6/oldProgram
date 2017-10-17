package h280314;

import java.util.ArrayList;

public class Delete {

	public void deleteAgentBid(ArrayList<ArrayList<Bid>> agentBid, ArrayList<ArrayList<Bid>> agentList,Bid choice,
			ArrayList<ArrayList<Bid>> envyList, ArrayList<Agent> agent) {
		int agentNumber = choice.agentNumber();
		int taskNumber = choice.taskNumber();
		int preferentialNumber;
		for (int i = 0; i < agentBid.size(); i++) {
			if(agentBid.get(i).get(0).agentNumber() == agentNumber){
	//			System.out.print("こいつ:");
				for(Bid a : agentBid.get(i)){
		//			System.out.print(a.toString()+" , ");
					if(a.allocated() == 0)
						a.allocated(2);
				}
			//	System.out.println();
				if(choice.originalPreference() != 1){
					preferentialNumber = choice.originalPreference();
					envyList.add(new ArrayList<Bid>());
					//不満解消とリスト作成
					for(Bid BID : agentBid.get(i)){
						if(BID.originalPreference() == preferentialNumber)
							break;
						envyList.get(envyList.size()-1).add(BID);
					}
					//不満解消とリスト作成
				}
				agentList.add(agentBid.get(i));
				agentBid.remove(i);
				i--;
				continue;
			}
			for (int j = 0; j < agentBid.get(i).size(); j++) {
				if (agentBid.get(i).get(j).taskNumber() == taskNumber) { // 割り当てフラグ
					agentBid.get(i).get(j).allocated(1);
					preferentialNumber = agentBid.get(i).get(j)
							.originalPreference();
					// 第一位かつ一番前の時
					if (j == 0) {
						for (int m = 1; m < agentBid.get(i).size(); m++) {
							if(agentBid.get(i).get(m).allocated() > 0)
								continue;
							if (agentBid.get(i).get(m).originalPreference() == preferentialNumber) {
								break;
							}
							agentBid.get(i).get(m).rankUp(preferentialNumber);
						}
					}

					// 二番目以降の時
					else{
						for(int t = j-1; t >-1; t--){
							if(agentBid.get(i).get(t).allocated() > 0){
								if(t == 0 ){
									for (int m = j + 1; m < agentBid.get(i).size(); m++) {
										if(agentBid.get(i).get(m).allocated() > 0)
											continue;
										if (agentBid.get(i).get(m).originalPreference() == preferentialNumber) {
											break;
										}
										agentBid.get(i).get(m).rankUp(preferentialNumber);
									}
									break;
								}
								continue;
							}
							else{
								if(agentBid.get(i).get(t).originalPreference() == preferentialNumber){
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	public void deleteTask(ArrayList<ArrayList<Bid>> allocatedBid, ArrayList<ArrayList<Bid>> Bid, Bid choice,ArrayList<Task> task,ArrayList<Task> allocatedTask){
		int taskNumber = choice.taskNumber();
		for(int j = 0; j < task.size();j++){
			if (taskNumber == task.get(j).taskNumber()) {
				allocatedTask.add(task.get(j));
				task.remove(j);
				for (int i = j; i < Bid.size(); i++) {
					if (taskNumber == Bid.get(i).get(0).taskNumber()) {
						allocatedBid.add(Bid.get(i));
						Bid.remove(i);
						break;
					}
				}
				break;
			}
		}
	}
}