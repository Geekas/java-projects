package ramunas.alksnys;

public class Account {
	double balance = 0;

	public Account(double suma) {
		if (suma > 0) {
			this.balance = suma;
		} else {
			System.out.println("Prideti negalima neigiamu skaiciu.");
		}
	}

	public void addMoney(double suma) {
		if (suma > 0) {
			this.balance += suma;
		} else {
			System.out.println("Negalima deti neigiamu zenklu");
		}
	}

	public void discmountMoney(double suma) {
		if (suma > 0) {
			this.balance -= suma;
		} else {
			System.out.println("Galimi tik teigiami skaiciai");
		}
	}

	public double getBalance() {
		return balance;
	}
	
}
