package taaikes;

import java.util.Comparator;

public class HRF implements Comparator<Bid> {
	public int compare(Bid p1, Bid p2) {

		int number1 = p1.value();
		int number2 = p2.value();

		if (number1 > number2)
			return -1;
		else if (number1 == number2) {
			return 0;
		} else
			return 1;
	}
}