package ramunas.alksnys;

import java.util.List;

public interface PlayerProperties {
	
	public void setPoints(int points);

	public int getPoints();

	public void addCard(String card);

	public List<String> getCards();

	public String getName();

	public void setStatus(boolean status);

	public boolean getStatus();
	
	public List<Integer> calcPoints();
}
