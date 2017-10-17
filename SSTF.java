package taaikes;


import java.util.Comparator;

public class SSTF implements Comparator<Bid> {
	public int compare(Bid p1, Bid p2) {

		int number1 = p1.subst();
		int number2 = p2.subst();

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