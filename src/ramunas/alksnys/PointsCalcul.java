package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PointsCalcul {
	private List<Integer> points = new ArrayList<>();
	private boolean overLoad = false;
	private String[] cards = { "2h", "2", "2c", "2", "2s", "2", "2d", "2", "3h", "3", "3c", "3", "3s", "3", "3d", "3",
			"4h", "4", "4c", "4", "4s", "4", "4d", "4", "5h", "5", "5c", "5", "5s", "5", "5d", "5", "6h", "6", "6c",
			"6", "6s", "6", "6d", "6", "7h", "7", "7c", "7", "7s", "7", "7d", "7", "8h", "8", "8c", "8", "8s", "8",
			"8d", "8", "9h", "9", "9c", "9", "9s", "9", "9d", "9", "Th", "10", "Tc", "10", "Ts", "10", "Td", "10", "Jh",
			"10", "Jc", "10", "Js", "10", "Jd", "10", "Qh", "10", "Qc", "10", "Qs", "10", "Qd", "10", "Kh", "10", "Kc",
			"10", "Ks", "10", "Kd", "10", "Ah", "11", "Ac", "11", "As", "11", "Ad", "11" };

	private LinkedList<String> cardListValues = new LinkedList<String>(Arrays.asList(cards));

	public List<Integer> calculatePoints(List<String> cards) {
		if (doesContainA(cards)) {
			for (String card : cards) {
				if (points.isEmpty()) {
					if (card.contains("A")) {
						points.add(1);
						points.add(11);
					} else {
						points.add(getValueOfCard(card));
						points.add(getValueOfCard(card));
					}

				} else {
					if (card.contains("A") && (points.size() == 2) && !overLoad) {
						points.set(0, 1 + points.get(0));
						points.set(1, 11 + points.get(1));
					} else if (card.contains("A") && (points.size() == 1) && !overLoad) {
						points.add(11 + points.get(0));
						points.set(0, 1 + points.get(0));
					} else if (card.contains("A") && (points.size() == 1) && overLoad) {
						points.set(0, 1 + points.get(0));
					} else {
						points.set(0, getValueOfCard(card) + points.get(0));
						points.set(1, getValueOfCard(card) + points.get(1));
					}
				}
			}
		} else {
			for (String card : cards) {
				if (points.isEmpty()) {
					points.add(getValueOfCard(card));
				} else {
					points.set(0, getValueOfCard(card) + points.get(0));
				}
			}

		}
		// System.out.println("Pries: " + Arrays.toString(points.toArray()));
		checkPoints();
		// System.out.println("Po:" + Arrays.toString(points.toArray()));
		return points;
	}

	private void checkPoints() {
		if (points.size() == 2) {
			if ((points.get(0) > 21) && (points.get(1) < 21)) {
				points.remove(0);
				overLoad = true;
			} else if ((points.get(0) < 21) && (points.get(1) > 21)) {
				points.remove(1);
				overLoad = true;
			} else if ((points.get(0) > 21) && (points.get(1) > 21)) {
				points.remove(Collections.max(points));
				overLoad = true;
			}
		}
		if (points.size() == 2) {
			if ((points.get(0) == 21)) {
				points.remove(1);
			} else if ((points.get(1) == 21)) {
				points.remove(0);
			}
		}

	}

	private int getValueOfCard(String card) {
		int cardPlace = cardListValues.indexOf(card);
		return Integer.parseInt(cardListValues.get(cardPlace + 1));
	}

	private boolean doesContainA(List<String> cards) {
		return cards.contains("Ah") || cards.contains("Ac") || cards.contains("Ad") || cards.contains("As");
	}
}
