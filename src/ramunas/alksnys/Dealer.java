package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dealer extends Casino_human_temp {
	private Scanner userInput;	
	private CardLog newInstance;
	private List<Player> players;
	private boolean doDealCards;

	public Dealer(String name) {
		super(name);	
		userInput = new Scanner(System.in);
		newInstance = CardLog.getInstance();
		addNullsToDealerCards();
		players = new ArrayList<>();
		doDealCards = false;
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
				reset();
				resetPlayers();
				continue;
			} else {
				break;
			}
		}
	}

	private void resetPlayers() {
		for (Player player : players) {
			player.reset();
		}

	}

	public void reset() {
		points = 0;
		status = false;
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
				if (!player.getStatus() && player.getPoints() > points) {
					System.out.println("Player " + player.getName() + " turi " + player.getPoints()
							+ " surinko daugiau negu dealeris ir laimejo");
				} else if (!player.getStatus() && player.getPoints() == points) {
					System.out.println("Player " + player.getName() + " turi vienodai su dealeriu");
				} else if (!player.getStatus()) {
					System.out.println("Player " + player.getName() + " turi tasku: " + player.getPoints()
							+ " dealeris turi: " + points + " ,todel pralaimejo, dealeris surinko daugiau tasku");
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
						+ Arrays.toString((new PointsCalcul()).calculatePoints(players.get(i).getCards()).toArray()));
				while (true) {
					if ((new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0) == 21) {
						players.get(i).setPoints((new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0));
						System.out.println("Tu laimejai, surinkai 21");
						break;
					}
					System.out.println("Galimi pasirinkimai: IMTI STOP ");
					String choise = userInput.next();
					if (choise.equalsIgnoreCase("imti")) {

						players.get(i).addCard(newInstance.getACard());
						System.out.println(
								"You got: " + players.get(i).getCards().get(players.get(i).getCards().size() - 1));
						System.out.println("You have "
								+ Arrays.toString((new PointsCalcul()).calculatePoints(players.get(i).getCards()).toArray()));
						if ((new PointsCalcul()).calculatePoints(players.get(i).getCards()).size() == 2) {
							continue;
						} else {
							if ((new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0) > 21) {
								System.out.println(Arrays.toString((new PointsCalcul()).calculatePoints(players.get(i).getCards()).toArray()));
								players.get(i).setStatus(true);
								System.out.println("You have lost, you got more than 21 points: "
										+ (new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0));
								break;
							} else if ((new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0) == 21) {
								players.get(i).setPoints(
										(new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0));
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
							players.get(i).setPoints((new PointsCalcul()).calculatePoints(players.get(i).getCards()).get(0));
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
			dealerCardDealing();
		}

	}

	private void dealerCardDealing() {
		while (true) {
			System.out.println("Dealer cards are:" + Arrays.toString(getCards().toArray()) + " tai yra: "
					+ (new PointsCalcul().calculatePoints(getCards())));
			if (Collections.max((new PointsCalcul().calculatePoints(getCards()))) >= 16
					&& Collections.max(new PointsCalcul().calculatePoints(getCards())) < 22) {
				System.out.println("Dealer has: " + Collections.max((new PointsCalcul().calculatePoints(getCards()))));
				points = Collections.max((new PointsCalcul().calculatePoints(getCards())));
				break;
			} else if (Collections.max((new PointsCalcul().calculatePoints(getCards()))) > 21
					&& Collections.min(new PointsCalcul().calculatePoints(getCards())) >= 16
					&& Collections.min(new PointsCalcul().calculatePoints(getCards())) < 22) {
				System.out.println("Dealer has: " + Collections.min((new PointsCalcul().calculatePoints(getCards()))));
				points = Collections.min((new PointsCalcul().calculatePoints(getCards())));
				break;
			} else if (Collections.max((new PointsCalcul().calculatePoints(getCards()))) > 21
					&& Collections.min(new PointsCalcul().calculatePoints(getCards())) > 21) {
				status = true;
				System.out.println("Dealer got more than 21 and lost:"
						+ Collections.min(new PointsCalcul().calculatePoints(getCards())));
				break;
			} else {
				addCard(newInstance.getACard());
				continue;
			}
		}
	}

	private int getHighest(List<String> cards2) {
		return Collections.max((new PointsCalcul()).calculatePoints(cards2));
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
