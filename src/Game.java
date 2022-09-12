import java.util.ArrayList;

public class Game extends Item{
	private int numAll, numAvailable;
		 
	//Construstor
	Game(String title, String type, ArrayList<String> category,String publisher, String date_of_production, int numAll, int numAvailable, String path){
		super(title, publisher, date_of_production, category, numAvailable, numAll, type, path);
		this.numAll = numAll;
		this.numAvailable = numAvailable;
	}
	
	//A game's characteristics are returned in a String
    public String toString () {
		return super.toString() +  "<br>" +
				"Number of all copies: "+ numAll + "<br>" +
				"Number of available copies: "+ numAvailable +"</html>";
    }
}
