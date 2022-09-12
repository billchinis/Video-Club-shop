import java.util.ArrayList;
import java.util.Date;

public class CatalogueRented {
	
	private ArrayList<Rental> catalogue = new ArrayList<Rental>(1); //CatalogueRented is a catalogue full of Rentals, see Rental class for more info
	
	//Constructor
	CatalogueRented(ArrayList<Rental> catalogue){
		this.catalogue = catalogue;
	}
	
	//Getters
	public int getSize(){
		return catalogue.size();
	}
	
	public int getDays_of_rental(int i){
		return catalogue.get(i).getDays_of_rental();
	}
	
	public Date getDate(int i){
		return catalogue.get(i).getDate();
	}
	
	public String getTitle(int i){
		return catalogue.get(i).getTitle();
	}	
	
	public boolean getReturned(int i){
		return catalogue.get(i).getReturned();
	}
	
	public int getCode_of_rental(int i){
		return catalogue.get(i).getCode_of_rental();
	}
	
	public String getType(int i){
		return catalogue.get(i).getType();
	}
	
	public String getItemType(int i){
		return catalogue.get(i).getItemType();
	}
	
	public String getName(int i){
		return catalogue.get(i).getName();
	}
	
	public int getCatAvIndex(int i){
		return catalogue.get(i).getCatAvIndex();
	}
	
	public ArrayList<Rental> getCatalogueRented(){
		return catalogue;
	}
	
	public Rental getRental(int i){
		return catalogue.get(i);
	}
	
	//Setters
	public void setOverdueCost(int i, int ov_cost){
		catalogue.get(i).setOverdueCost(ov_cost);
	}
	
	public void setReturned(int i, boolean returned){//true4returned false4still-rented
		catalogue.get(i).setReturned(returned);
	}
	
	public void setCatAvIndex(int i, int CatAvIndex){
		catalogue.get(i).setCatAvIndex(CatAvIndex);
	}
	
	public void setOverdueDays(int i, int overdue_days){
		catalogue.get(i).setOvedueDays(overdue_days);
	}
	
	//Other Methods
	public void Print(int i){//Rental's characteristics are printed
		System.out.println(catalogue.get(i).toString());
	}
	
	public void PrintRent(int i){//code of rental, title of rented product, name of user and state of Rental (Retured: true or false) are printed 
		System.out.println("[" + getCode_of_rental(i) + "] " + "Title: " +  getTitle(i) + ", Type: " + getType(i) + ", Name: " + getName(i) + ", Returned: " + getReturned(i));
	}
	
	public void add(Rental rent){//Adds a rental to the Array
		catalogue.add(rent);
	}
}
