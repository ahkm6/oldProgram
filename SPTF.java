package taaikes;


import java.util.Comparator;

public class SPTF implements Comparator<Bid> {
	public int compare(Bid p1, Bid p2) {

		double number1 = p1.process();
		double number2 = p2.process();

		int value1 = p1.myValue();
		int value2 = p2.myValue();

		if (number1 < number2)
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