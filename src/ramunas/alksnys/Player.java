package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends Casino_human_temp implements Wallet {
	private Account account;
	private double bettingSum = 0;

	public void setBet(double ammount){
		bettingSum = ammount;
	}
	public double getBetSum(){
		return bettingSum;
	}
	public Player(String name) {
		super(name);
		account = new Account(100);
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

	public void reset() {
		points = 0;
		win = false;
		status = false;
		cards.clear();
	}

	public boolean checkIfWon() {
		return calcPoints().size() == 1 && calcPoints().get(0) == 21;
	}

	public void getHighestPoints() {
		setPoints(Collections.max(calcPoints()));
	}

	public boolean checkIfBusted() {
		return Collections.min(calcPoints()) > 21;
	}

	public String toString() {
		return ("Zaidejas " + name + " saskaitoje turi " + account.getBalance());
	}
	public boolean canBet(double ammount) {
		return (getBalance() - ammount) >= 0;
	}
	public boolean efficientAccount(){
		return getBalance() >= 1; 
	}

}
