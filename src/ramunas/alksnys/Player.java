package ramunas.alksnys;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	Account account;
	List<String> cards;

	public Player(String name) {
		this.name = name;
		account = new Account(100);
		cards = new ArrayList<>();
	}
}
