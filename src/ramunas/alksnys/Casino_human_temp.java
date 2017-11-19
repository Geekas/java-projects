package ramunas.alksnys;

import java.util.ArrayList;
import java.util.List;

public abstract class Casino_human_temp {
	protected String name = "";
	protected List<String> cards;
	protected int points;
	protected boolean status;

	protected abstract void reset();
	
	public Casino_human_temp(String name) {
		points = 0;
		status = false;
		this.name = name;
		cards = new ArrayList<>();
	}

	

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public void addCard(String card) {
		cards.add(card);
	}

	public List<String> getCards() {
		return cards;
	}

	public String getName() {
		return name;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

}
