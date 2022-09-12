import java.util.ArrayList;

public class CatalogueAvailable {
	
	private ArrayList<Item> catalogue = new ArrayList<Item>(); //CatalogueAvailable is a catalogue full of Items, see Item class for more info
	
	//Constructor
	CatalogueAvailable(ArrayList<Item> catalogue){
		this.catalogue = catalogue;
	}
	
	//Getters
	public int getSize(){
		return catalogue.size();
	}
	
	public int getNumAvailable(int i){
		return catalogue.get(i).getNumAvailable();
	}
	
	public String getType(int i){
		return catalogue.get(i).getType();
	}
	
	public String getItemType(int i){
		return catalogue.get(i).getClass().toString().trim().substring(5).trim();
	}
	
	public String getTitle(int i){
		return catalogue.get(i).getTitle();
	}
	
	public boolean getLatestRelease(int i){
		return ((Movie) catalogue.get(i)).getLatestRelease();
	}
	
	public Item getItem(int i){
		return catalogue.get(i);
	}
	
	//Setter
	public void setNumAvailable(int i, int x){ //value of x is either 1 or -1 (that's how available copies are increased/decreased)
		catalogue.get(i).setNumAvailable(catalogue.get(i).getNumAvailable() + x);
	}
	
	//Other Methods
	public boolean MovieOrGame(int i){
		return catalogue.get(i) instanceof Movie; //true4movie false4game
	}
	
	
	public void Print(int i){//Item's characteristics are printed
		System.out.println(catalogue.get(i).toString());
	}
	
	public void PrintTitle(int i){//Item's title is printed
		System.out.println(catalogue.get(i).getTitle());
	}
	
	public void add(Item item){//Adds an item to the Array
		catalogue.add(item);
	}

	public ArrayList<Item> getCatalogueAvailable() {
		return catalogue;
	}
	
}