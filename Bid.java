package h280314;

import java.util.Random;

public class Bid {

	int agentNumber;
	Task task;
	int reward;
	int deadline;
	int processingTime;
	int preferentialOrder;
	int originalPreference;
	int originalDeadline;
	int originalProcessingTime;
	double match;
	int value;
	int allocated;
	int duration;
	int useless;
	int type;
	int strategy;
	int property;

	public Bid(int agentNumber) {
		this.agentNumber = agentNumber;
		this.task = new Task();
	}

	public Bid(int agent, Task task, int processingTime, int useless,
			int preferentialOrder, int match, int value, int ratio,
			Random random, int originalProcessingTime, int strategy) {

		this.agentNumber = agent;
		this.task = task;
		if (task.property == 0) {
			this.reward = task.reward();
			this.property = 0;
		} else {
			this.reward = (int) (task.reward() / 2 + task.reward()
					* (task.deadline() - processingTime)
					/ (2 * task.originalDeadline()));
			this.property = 1;
		}
		this.originalDeadline = task.originalDeadline();
		this.deadline = task.deadline();
		this.processingTime = processingTime;
		this.preferentialOrder = preferentialOrder;
		this.originalPreference = preferentialOrder;
		this.originalProcessingTime = originalProcessingTime;
		this.match = (double)(reward)/processingTime;
		switch (value) {
		case 0:
			this.value = 100 - this.processingTime;
			break;
		case 1:
			this.value = this.reward;
		}
		this.duration = originalDeadline - deadline + processingTime;
		this.allocated = 0;
		this.useless = useless;
		this.type = task.type();
		this.strategy = strategy;
	}

	public int strategy() {
		return this.strategy;
	}

	public double match() {
		return this.match;
	}

	public int processingTime() {
		return this.processingTime;
	}

	public int originalProcessingTime() {
		return this.originalProcessingTime;
	}

	public int reward() {
		return this.reward;
	}

	public int allocated() {
		return this.allocated;
	}

	public void allocated(int n) {
		this.allocated = n;
	}

	public void rankUp(int n) {
		if (this.originalPreference > n) {
			this.preferentialOrder--;
		}
	}

	public void release() {
		this.allocated = 1;
	}

	public int agentNumber() {
		return this.agentNumber;
	}

	public int deadline() {
		return this.deadline;
	}

	public int preferentialOrder() {
		return this.preferentialOrder;
	}

	public int originalPreference() {
		return this.originalPreference;
	}

	public void setPreferentialOrder(int n) {
		this.preferentialOrder += n;
		this.originalPreference += n;
	}

	public int value() {
		return this.value;
	}

	public int taskNumber() {
		return this.task.taskNumber();
	}

	public int duration() {
		return this.duration;
	}

	public int useless() {
		return this.useless;
	}

	public int popularity() {
		return this.task.popularity();
	}

	public String toString() {
		return "flag:" + this.allocated + ",aNum:" + this.agentNumber
				+ ",tNum:" + this.task.taskNumber() + ",順位:"
				+ this.originalPreference + ",時間" + this.processingTime
				+ ",合う:" + this.match;
	}
}