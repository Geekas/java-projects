package ramunas.alksnys;

public class Account {
	double suma = 0;

	public Account(double suma) {
		if (suma > 0) {
			this.suma = suma;
		} else {
			System.out.println("Prideti negalima neigiamu skaiciu.");
		}
	}

	public void addMoney(double suma) {
		if (suma > 0) {
			this.suma += suma;
		} else {
			System.out.println("Negalima deti neigiamu zenklu");
		}
	}

	public void discmountMoney(double suma) {
		if (suma > 0) {
			this.suma -= suma;
		} else {
			System.out.println("Galimi tik teigiami skaiciai");
		}
	}

	public double getBalance() {
		return suma;
	}
}
