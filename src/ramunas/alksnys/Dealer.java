package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dealer {
	private List<String> cards;
	private String name = "";
	private Scanner userInput = new Scanner(System.in);
	private CardLog newInstance = CardLog.getInstance();
	private List<Player> players;
	private boolean doDealCards = false;
	private boolean status = false;
	private int dealerPoints = 0;

	public Dealer(String name) {
		this.name = name;
		cards = new ArrayList<>();
		addNullsToDealerCards();
		players = new ArrayList<>();
	}

	public void startGame() {
		playersRegistration();
		while (true) {
			dealCards();
			findTheWinners();
			System.out.println("Jei norite zaisti dar karta press Y, jei baigti Q");
			String answer = userInput.next();
			if (answer.equalsIgnoreCase("Y")) {
				newInstance.prepareNewCards();
				resetDealer();
				resetPlayers();
				continue;
			} else {
				break;
			}
		}
	}

	private void resetPlayers() {
		for (Player player : players) {
			player.resetPlayer();
		}

	}

	private void resetDealer() {
		dealerPoints = 0;
		status = false;
		cards.clear();
		addNullsToDealerCards();

	}

	private void findTheWinners() {
		if (status) {
			for (Player player : players) {
				if (!player.getStatus()) {
					System.out.println("Player " + player.getName() + " laimejo");
				}
			}
		} else {
			for (Player player : players) {
				if (!player.getStatus() && player.getPoints() > dealerPoints) {
					System.out.println("Player " + player.getName() + "turi " + player.getPoints()
							+ "surinko daugiau negu dealeris ir laimejo");
				} else if (!player.getStatus() && player.getPoints() == dealerPoints) {
					System.out.println("Player " + player.getName() + " turi vienodai su dealeriu");
				} else if (!player.getStatus()) {
					System.out.println("Player " + player.getName() + " turi tasku: " + player.getPoints()
							+ " dealeris turi: " + dealerPoints + " ,todel pralaimejo, dealeris surinko daugiau tasku");
				}
			}
		}

	}

	private void dealCards() {
		if (players.size() > 0) {
			for (int i = 0; i < players.size(); i++) {
				if (cards.get(0) == null) {
					cards.set(0, newInstance.getACard());
				}
				players.get(i).addCard(newInstance.getACard());
				if (cards.get(1) == null) {
					cards.set(1, newInstance.getACard());
				}
				players.get(i).addCard(newInstance.getACard());

				System.out.println("Dealer cards are:" + "X" + " " + cards.get(1));
				System.out.println("Players " + players.get(i).getName() + " cards are"
						+ Arrays.toString(players.get(i).getCards().toArray()));
				System.out.println("You have "
						+ Arrays.toString((new DealerBrains()).calculatePoints(players.get(i).getCards()).toArray()));
				while (true) {
					if ((new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0) == 21) {
						players.get(i)
								.setPoints((new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0));
						System.out.println("Tu laimeijai, surinkai 21");
						break;
					}
					System.out.println("Galimi pasirinkimai: IMTI STOP ");
					String choise = userInput.next();
					if (choise.equalsIgnoreCase("imti")) {

						players.get(i).addCard(newInstance.getACard());
						System.out.println(
								"You got: " + players.get(i).getCards().get(players.get(i).getCards().size() - 1));
						System.out.println("You have " + Arrays
								.toString((new DealerBrains()).calculatePoints(players.get(i).getCards()).toArray()));
						if ((new DealerBrains()).calculatePoints(players.get(i).getCards()).size() == 2) {
							continue;
						} else {
							if ((new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0) > 21) {
								players.get(i).setStatus(true);
								System.out.println("You have lost, yout got more than 21 points: "
										+ (new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0));
								break;
							} else if ((new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0) == 21) {
								players.get(i).setPoints(
										(new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0));
								System.out.println("Tu laimeijai, surinkai 21");
								doDealCards = true;
								break;
							}
						}
					} else if (choise.equalsIgnoreCase("stop")) {
						if (players.get(i).getCards().size() == 2) {
							players.get(i).setPoints(getHighest(players.get(i).getCards()));
							System.out.println("You have " + players.get(i).getPoints());
							doDealCards = true;
							break;
						} else {
							players.get(i)
									.setPoints((new DealerBrains()).calculatePoints(players.get(i).getCards()).get(0));
							System.out.println("You have " + players.get(i).getPoints());
							doDealCards = true;
							break;
						}
					} else {
						System.out.println("Tokios komandos nera");
					}
				}
			}

		}
		if (doDealCards) {
			while (true) {
				System.out.println("Dealer cards are:" + Arrays.toString(cards.toArray()) + " tai yra: "
						+ (new DealerBrains().calculatePoints(cards)));
				if (Collections.max((new DealerBrains().calculatePoints(cards))) >= 16
						&& Collections.max(new DealerBrains().calculatePoints(cards)) < 22) {
					System.out.println("Dealer has: " + Collections.max((new DealerBrains().calculatePoints(cards))));
					dealerPoints = Collections.max((new DealerBrains().calculatePoints(cards)));
					break;
				} else if (Collections.max((new DealerBrains().calculatePoints(cards))) > 21
						&& Collections.min(new DealerBrains().calculatePoints(cards)) >= 16
						&& Collections.min(new DealerBrains().calculatePoints(cards)) < 22) {
					System.out.println("Dealer has: " + Collections.min((new DealerBrains().calculatePoints(cards))));
					dealerPoints = Collections.min((new DealerBrains().calculatePoints(cards)));
					break;
				} else if (Collections.max((new DealerBrains().calculatePoints(cards))) > 21
						&& Collections.min(new DealerBrains().calculatePoints(cards)) > 21) {
					status = true;
					System.out.println("Dealer got more than 21 and lost:"
							+ Collections.min(new DealerBrains().calculatePoints(cards)));
					break;
				} else {
					cards.add(newInstance.getACard());
					continue;
				}
			}

		}

	}

	private int getHighest(List<String> cards2) {
		return Collections.max((new DealerBrains()).calculatePoints(cards2));
	}

	private void createPlayerList(int players) {
		for (int i = 0; i < players; i++) {
			userInput.nextLine();
			System.out.println("Koks " + (i + 1) + " zaidejo vardas?");
			this.players.add(new Player(userInput.next()));
		}

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
