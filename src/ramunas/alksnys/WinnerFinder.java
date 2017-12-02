package ramunas.alksnys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WinnerFinder {
	Dealer dealer;
	List<Player> players;
	List<Player> realplayers;
	List<String> messages;

	public WinnerFinder(final Dealer dealer, List<Player> players) {
		this.dealer = dealer;
		this.players = new ArrayList(players);
		this.realplayers = players;
		messages = new ArrayList<>();
		fillterPlayers();
		findTheWinners();
	}

	private void fillterPlayers() {
		List<Player> chosenPlayers = new ArrayList<>();
		for (Player player : players) {
			if (player.getStatus()) {
				chosenPlayers.add(player);
				minusMoneyToPlayer(player);
			}
		}
		players.removeAll(chosenPlayers);

	}

	private void findTheWinners() {
		for (Player player : players) {
			messages.add(getMessage(player));
		}
	}

	private String getMessage(Player player) {
		StringBuilder stringBuilder = new StringBuilder();
		switch (getStatus(player).toLowerCase()) {
		case "laimejo":
			if (dealer.getDoDeal()) {
				stringBuilder.append("Player ").append(player.getName()).append(" laimejo su ")
						.append(player.getPoints()).append(" taskais ").append(" dealeris turi ")
						.append(dealer.getPoints()).append(" tasku");
				addMoneyToPlayer(player);
			} else {
				stringBuilder.append("Player ").append(player.getName()).append(" laimejo su ")
						.append(player.getPoints()).append(" taskais ");
				addMoneyToPlayer(player);
			}
			return stringBuilder.toString();
		case "lygiosios":
			stringBuilder.append("Player ").append(player.getName())
					.append(" gavo vienodai tasku su dealeriu. Dealerio taskai ").append(dealer.getPoints())
					.append(" tavo taskai ").append(player.getPoints());
			return stringBuilder.toString();
		case "pralaimejo":
			stringBuilder.append("Player ").append(player.getName()).append(" pralaimejo. Dealerio taskai ")
					.append(dealer.getPoints()).append(" tavo taskai ").append(player.getPoints());
			minusMoneyToPlayer(player);
			return stringBuilder.toString();
		default:
			stringBuilder.append(Consts.noClue);
		}
		return stringBuilder.toString();
	}

	private void addMoneyToPlayer(Player player) {
		int index = realplayers.indexOf(player);
		realplayers.get(index).addMoney(realplayers.get(index).getBetSum());
	}
	private void minusMoneyToPlayer(Player player) {
		int index = realplayers.indexOf(player);
		realplayers.get(index).discmountMoney(realplayers.get(index).getBetSum());
	}
	public String getStatus(Player player) {
		if (dealer.getStatus()) {
			return Consts.win;
		} else if (dealer.getPoints() == player.getPoints()) {
			return Consts.even;
		} else if (dealer.getPoints() > player.getPoints()) {
			return Consts.lose;
		}
		return "Laimejo";
	}

	public void showWinner() {
		for (String message : messages) {
			System.out.println(message);
		}
	}
}
