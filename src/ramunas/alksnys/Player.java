package ramunas.alksnys;

import java.util.ArrayList;
import java.util.List;

public class Player extends Casino_human_temp {
	private Account account;

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
		status = false;
		cards.clear();
	}

	public String toString() {
		return ("Zaidejas " + name + " saskaitoje turi " + account.getBalance());
	}

}
