package ramunas.alksnys;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dealer {
	private List<String> cards;
	private String name = "";
	private Scanner userInput = new Scanner(System.in);
	private CardLog newInstance = CardLog.getInstance();
	private List<Player> players;

	public Dealer(String name) {
		this.name = name;
		cards = new ArrayList<>();
		players = new ArrayList<>();
	}

	public void startGame() {
		while (checkGameConditions()) {
			playersRegistration();
			showPlayersStats();
			break;
		}
	}

	private void createPlayerList(int players) {
		for (int i = 0; i < players; i++) {
			userInput.nextLine();
			System.out.println("Koks " + (i + 1) + " zaidejo vardas?");
			this.players.add(new Player(userInput.next()));
		}

	}

	private boolean checkGameConditions() {
		return true;
	}

	private void showPlayersStats() {
		for (Player player : players) {
			System.out.println(player.toString());
		}
	}

	private void playersRegistration() {
		while (true) {
			System.out.println("Kiek zaideju zaidzia?");
			if (userInput.hasNextInt()) {
				int playersNumber = userInput.nextInt();
				if (playersNumber >= 0) {
					createPlayerList(playersNumber);
					userInput.nextLine();
					break;
				}
				else {
					System.out.println("Neigiami skaiciai netinka!");
					userInput.nextLine();
					continue;
				}
			} else {
				System.out.println("Tai ne teigiamas skaicius or w/e!!!");
				userInput.nextLine();
			}
		}
	}

}
