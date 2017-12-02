package ramunas.alksnys;

public final class Consts {
	
	private Consts(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	  }
	
	public static final int maxPoints = 21;
	public static final int minPoints = 16;
	public static final String noClue = "I have no clue what happended";
	public static final String win = "Laimejo";
	public static final String even = "Lygiosios";
	public static final String lose = "Pralaimejo";
	public static final String errorNegative = "Prideti negalima neigiamu skaiciu.";
	public static final String errorMinus = "Negalima deti neigiamu zenklu";
	public static final String errorPosNum = "Galimi tik teigiami skaiciai";
	public static final String playerWarn = "Tokios komandos, nera. Pagal default you pasalintas is zaidimo!!!";
	public static final String playerNum = "Kiek zaideju zaidzia?";
	public static final String dealerCards = "Dealer cards are:";
	public static final String choiceTake = "imti";
	public static final String choiceStop = "stop";
	public static final String choiceEmpty = "busted";
	public static final String meniu = "Galimi pasirinkimai: IMTI STOP ";
	public static final String mssgWin = "Tu laimejai, surinkai 21";
	public static final String mssgLose = "Tu pralaimejai";
	public static final String wrongComm = "Tokios komandos nera!";
	public static final String contGame = "y";
	public static final String moneyMssg = "Do you want add money? Y/N";
	public static final String errorNegMoney = "Netinkamas skaicius, del laiko trukumo jus statote minimaliai tai yra 1";
	public static final String errorIncText = "Tai nera skaicius, pagal default jus tuomet statote minimuma tai yra 1";
	public static final String whatBet = " kiek norite statyti?";
	public static final String exitMeniu = "Jei norite zaisti dar karta press Y, jei baigti Q";
}
