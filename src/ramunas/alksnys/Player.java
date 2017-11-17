package ramunas.alksnys;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private Account account;
	private List<String> cards;

	public Player(String name) {
		this.name = name;
		account = new Account(100);
		cards = new ArrayList<>();
	}
	public String toString(){
		return ("Zaidejas " + name + " saskaitoje turi " + account.getBalance());
	}
	
}
