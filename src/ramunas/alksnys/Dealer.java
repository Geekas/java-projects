package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Dealer extends Casino_human_temp implements TableCommander {
	private Scanner userInput;
	private CardLog newInstance;
	private List<Player> players;
	private List<Player> tempPlayers;
	private boolean doDealCards;
	private Winner_searcher findWinner;

	public Dealer(String name) {
		super(name);
		userInput = new Scanner(System.in);
		newInstance = CardLog.getInstance();
		addNullsToDealerCards();
		players = new ArrayList<>();
		tempPlayers = new ArrayList<>();
		doDealCards = false;
	}

	public boolean getDoDeal() {
		return doDealCards;
	}

	public void startGame() {
		playersRegistration();
		while (true) {
			playerCredentionCheck();
			takingBets();
			dealCards();
			findTheWinners();
			showStats();
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

	private void showStats() {
		for (Player player: players){
			showPlayerStatsinDetail(player);
		}
		
	}

	private void takingBets() {
		for (Player player : players){
			StringBuilder sb = new StringBuilder();
			sb.append("Player ").append(player.getName()).append(" kiek norite statyti?");
			System.out.println(sb);
			try{
			double ammount = userInput.nextDouble();
			if (ammount > 0 && ammount <= player.getBalance()){
				player.setBet(ammount);
				userInput.nextLine();
			}
			else {
				System.out.println("Netinkamas skaicius, del laiko trukumo jus statote minimaliai tai yra 1");
				player.setBet(1);
				userInput.nextLine();
			}
			} catch (InputMismatchException e) {
				System.out.println("Tai nera skaicius, pagal default jus tuomet statote minimuma tai yra 1");
				player.setBet(1);
				userInput.nextLine();
			}
		}
		
	}

	private void playerCredentionCheck() {
		List<Player> temp = new ArrayList();
		String choice = "";
		for (Player player : players) {
			if (!player.efficientAccount()) {
				System.out.println("Do you want add money? Y/N");
				choice = userInput.next();
				executeChoice(player, choice);
			}
		}
		players.removeAll(tempPlayers);
	}

	private void executeChoice(Player player, String choice) {
		double ammount = 0;
		switch (choice.toLowerCase()) {
		case "y":
			System.out.println("How much?");
			userInput.nextLine();
			try {
				ammount = userInput.nextDouble();
				if (ammount > 0) {
					player.addMoney(ammount);
					executeChoice(player, "y");
				} else {
					System.out.println("Negatives numbers not allowed");

				}
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}
			break;
		case "n":
			tempPlayers.add(player);
			break;
		default:
			System.out.println("Tokios komandos, nera. Pagal default you pasalintas is zaidimo!!!");
			tempPlayers.add(player);
			break;
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
		tempPlayers.clear();
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
			showHeader2();
			choice = userInput.next();
			switch (choice.toLowerCase()) {
			case "imti":
				giveCardtoPlayer(player);
				if (player.checkIfBusted()) {
					playerBusted(player);
				} else if (player.checkIfWon()) {
					playerWon(player);
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

		checkStatusAndConfirm(player, choice);
	}

	private void checkStatusAndConfirm(Player player, String choice) {

		if (!player.getStatus() && choice.toLowerCase().equals("stop")) {
			showPlayerStats(player);
			player.getHighestPoints();
			doDealCards = true;
		} else {
			player.getHighestPoints();
		}
	}

	private void playerBusted(Player player) {
		player.getHighestPoints();
		player.setStatus(true);
		showPlayerStats(player);
		System.out.println("PRALAIMEJAI");
	}

	private void playerWon(Player player) {
		showPlayerStats(player);
		player.setWinStatus(true);
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
	private void showPlayerStatsinDetail(Player player) {
		System.out.println("Players " + player.getName() + " balance is " + player.getBalance());

	}

	private void giveCardtoPlayer(Player player) {
		player.addCard(newInstance.getACard());
	}

	private void showHeader() {
		System.out.println("Dealer cards are:" + "X" + " " + cards.get(1));
	}

	private void showHeader2() {
		showPlayerCommands();
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
