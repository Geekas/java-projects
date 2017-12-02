package ramunas.alksnys;

public class Account {
	double balance = 0;

	public Account(double suma) {
		if (suma > 0) {
			this.balance = suma;
		} else {
			System.out.println(Consts.errorNegative);
		}
	}

	public void addMoney(double suma) {
		if (suma > 0) {
			this.balance += suma;
		} else {
			System.out.println(Consts.errorMinus);
		}
	}

	public void discmountMoney(double suma) {
		if (suma > 0) {
			this.balance -= suma;
		} else {
			System.out.println(Consts.errorPosNum);
		}
	}

	public double getBalance() {
		return balance;
	}
	
}
