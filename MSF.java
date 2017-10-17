package taaikes;


import java.util.Comparator;

public class MSF implements Comparator<Bid> {
	public int compare(Bid p1, Bid p2) {

		double number1 = p1.percent();
		double number2 = p2.percent();

		int value1 = p1.value();
		int value2 = p2.value();

		if (number1 > number2)
			return -1;
		else if (number1 == number2){
			if(value1 > value2){
				return -1;
			}
			else if(value1 == value2){
				return 0;
			}
			else
				return 1;
		}
		else
			return 1;
	}
}