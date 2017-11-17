package ramunas.alksnys;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardLog {
	private static CardLog firstInstance = null;
	private String[] cards = { "2h", "2c", "2s", "2d", "3h", "3c", "3s", "3d", "4h", "4c", "4s", "4d", "5h", "5c", "5s",
			"5d", "6h", "6c", "6s", "6d", "7h", "7c", "7s", "7d", "8h", "8c", "8s", "8d", "9h", "9c", "9s", "9d", "Th",
			"Tc", "Ts", "Td", "Jh", "Jc", "Js", "Jd", "Qh", "Qc", "Qs", "Qd", "Kh", "Kc", "Ks", "Kd", "Ah", "Ac", "As",
			"Ad" };

	private LinkedList<String> cardList = new LinkedList<String>(Arrays.asList(cards));

	private CardLog() {
	};

	public static CardLog getInstance() {
		if (firstInstance == null) {
			firstInstance = new CardLog();
			// Shuffle the letters in the list
			Collections.shuffle(firstInstance.cardList);
		}
		return firstInstance;
	}

	public void prepareNewCards() {
		cardList = new LinkedList<String>(Arrays.asList(cards));
		Collections.shuffle(firstInstance.cardList);
	}

	public String getACard() {
		String card = cardList.getFirst();
		cardList.removeFirst();
		return card;
	}
}
