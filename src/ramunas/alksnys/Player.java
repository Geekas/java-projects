package ramunas.alksnys;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private Account account;
	private List<String> cards;
	private int points = 0;
	private boolean status = false; //grazina ar zaidejas jau pralose ar ne

	public void setPoints(int points){
		this.points = points;
	}
	public void resetPlayer(){
		points = 0;
		status = false;
		cards.clear();
	}
	public Player(String name) {
		this.name = name;
		account = new Account(100);
		cards = new ArrayList<>();
	}

	public void discmountMoney(double suma) {
		account.discmountMoney(suma);
	}

	public void addMoney(double suma) {
		account.addMoney(suma);
	}

	public double getBalance() {
		return account.getBalance();
	}

	public void addCard(String card){
		cards.add(card);
	}
	
	public List<String> getCards(){
		return cards;
	}
	public String getName(){
		return name;
	}
	public boolean getStatus(){
		return status;
	}
	public void setStatus(boolean status){
		this.status = status;
	}
	public void resetStatus(){
		status = false;
	}
	public String toString() {
		return ("Zaidejas " + name + " saskaitoje turi " + account.getBalance());
	}
	public int getPoints() {		
		return points;
	}
	

}
