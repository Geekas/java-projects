package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dealer extends Casino_human_temp implements TableCommander {
	private Scanner userInput;
	private CardLog newInstance;
	private List<Player> players;
	private boolean doDealCards;
	private Winner_searcher findWinner;

	public Dealer(String name) {
		super(name);
		userInput = new Scanner(System.in);
		newInstance = CardLog.getInstance();
		addNullsToDealerCards();
		players = new ArrayList<>();
		doDealCards = false;
	}

	public boolean getDoDeal() {
		return doDealCards;
	}

	public void startGame() {
		playersRegistration();
		while (true) {
			dealCards();
			findTheWinners();
			System.out.println("Jei norite zaisti dar karta press Y, jei baigti Q");
			String answer = userInput.next();
			if (answer.equalsIgnoreCase("Y")) {
				prepareForNewGame();
				continue;
			} else {
				break;
			}
		}
	}

	private void prepareForNewGame() {
		newInstance.prepareNewCards();
		reset();
		resetPlayers();
	}

	private void resetPlayers() {
		for (Player player : players) {
			player.reset();
		}

	}

	public void reset() {
		points = 0;
		status = false;
		doDealCards = false;
		addNullsToDealerCards();
	}

	private void findTheWinners() {
		findWinner = new Winner_searcher(this, players);
		findWinner.showWinner();

	}

	private void dealCards() {
		if (players.size() > 0) {
			for (int i = 0; i < players.size(); i++) {
				if (cards.get(0) == null) {
					cards.set(0, newInstance.getACard());
				}
				giveCardtoPlayer(players.get(i));

				if (cards.get(1) == null) {
					cards.set(1, newInstance.getACard());
				}
				giveCardtoPlayer(players.get(i));

				showHeader();

				showPlayerStats(players.get(i));

				checkPlayerWinStatus(players.get(i));

				if (!players.get(i).getWinStatus()) {
					dealPlayerCards(players.get(i));
				}
			}

		}
		if (getDoDeal()) {
			dealerCardDealing();
		}

	}

	private void dealPlayerCards(Player player) {
		String choice = "";
		do {
			showPlayerCommands();
			choice = userInput.next();
			switch (choice.toLowerCase()) {
			case "imti":
				giveCardtoPlayer(player);
				if (player.checkIfBusted()) {
					player.getHighestPoints();
					player.setStatus(true);
					showPlayerStats(player);
					System.out.println("PRALAIMEJAI");
				} else if (player.checkIfWon()) {
					showPlayerStats(player);
					player.setWinStatus(true);
				} else {
					showPlayerStats(player);
				}
				break;
			case "stop":
				break;
			default:
				System.out.println("Tokios komandos nera!");
				break;
			}
			userInput.nextLine();
		} while (!player.getWinStatus() && !player.getStatus()
				&& (choice.toLowerCase().equals("imti") || !choice.toLowerCase().equals("stop")));

		if (!player.getStatus() && choice.toLowerCase().equals("stop")) {
			showPlayerStats(player);
			player.getHighestPoints();
			doDealCards = true;
		} else {
			player.getHighestPoints();
		}

	}

	private void checkPlayerWinStatus(Player player) {
		if (player.checkIfWon()) {
			player.setPoints(21);
			player.setWinStatus(true);
			System.out.println("Tu laimejai, surinkai 21");
		}

	}

	private void showPlayerCommands() {
		System.out.println("Galimi pasirinkimai: IMTI STOP ");
	}

	private void showPlayerStats(Player player) {
		System.out.println("Players " + player.getName() + " cards are" + Arrays.toString(player.getCards().toArray()));
		System.out.println("You have " + player.calcPoints());

	}

	private void giveCardtoPlayer(Player player) {
		player.addCard(newInstance.getACard());
	}

	private void showHeader() {
		System.out.println("Dealer cards are:" + "X" + " " + cards.get(1));
	}

	private void dealerCardDealing() {
		calculPoints();
		String command = getCommand();
		showDealerStats();
		while (command.equals("imti")) {
			addCard(newInstance.getACard());
			calculPoints();
			showDealerStats();
			command = getCommand();
		}
	}

	private String getCommand() {
		if (getPoints() > 21) {
			setStatus(true);
			return "busted";
		} else if (getPoints() >= 16) {
			return "stop";
		}
		return "imti";
	}

	private void showDealerStats() {
		System.out.println("Dealer cards are:" + Arrays.toString(getCards().toArray()) + " tai yra: "
				+ Collections.max(calcPoints()));
	}

	private void calculPoints() {
		List<Integer> dPoints = new ArrayList<>(calcPoints());
		if (dPoints.size() == 2 && Collections.max(calcPoints()) <= 21) {
			setPoints(Collections.max(calcPoints()));
		} else if (dPoints.size() == 2 && Collections.min(calcPoints()) <= 21) {
			setPoints(Collections.min(calcPoints()));
		} else {
			setPoints(calcPoints().get(0));
		}
	}

	private void createPlayerList(int players) {
		for (int i = 0; i < players; i++) {
			userInput.nextLine();
			System.out.println("Koks " + (i + 1) + " zaidejo vardas?");
			this.players.add(new Player(userInput.next()));
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
				} else {
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

	private void addNullsToDealerCards() {
		cards.clear();
		cards.add(null);
		cards.add(null);
	}

}
