import java.awt.Image;
import java.util.ArrayList;

public abstract class Item {
	private int numAvailable, numAll;
	private String publisher, dateOfProduction, type, title, path;
	
	private ArrayList<String> category = new ArrayList<String>(1);//Arraylist with product's catergory/-ies
	
	//Constructor	
	Item(String title, String publisher, String dateOfProduction, ArrayList<String> category, int numAvailable, int numAll, String type, String path) {
		this.title = title;
		this.publisher = publisher;
		this.dateOfProduction = dateOfProduction;
		this.category = category;
		this.numAll = numAll;
		this.numAvailable = numAvailable;
		this.type = type;
		if(path.equals("") || path==null){
			this.path = "images/Items/noimage.jpg";
		}
		else{
			this.path = path;
		}
	}
	
	//returns Item's Charecterstics in a String
    public String toString () {
		return "<html>" + "INFO: " + "<br>" +
				"Title: " + title + "<br>" +
				"Type: " + type + "<br>" +
				"Publisher: " + publisher + "<br>" +
				"Date of Production: " + dateOfProduction + "<br>" +
				"Category: " + category	;
    }
	
	//Getters
	public String getPublisher(){
		return publisher;
	}
	
	public String getPath(){
		return path;
	}
	
	public String getDate(){
		return dateOfProduction;
	}
	public String getType(){
		return type;
	}
	public int getNumAvailable(){
		return numAvailable;
	}
	public String getTitle(){
		return title;
	}
	
	public int getNumAll(){
		return numAll;
	}
	
	public ArrayList<String> getCategory(){
		return category;
	}
	
	public int getCopies(){
		return numAll;
	}
	
	public int getAvCopies(){
		return numAvailable;
	}
	
	//Setter
	public void setNumAvailable(int numAvailable){
		this.numAvailable = numAvailable;
	}
	
	public void setNumAll(int numAll){
		this.numAll = numAll;
	}
}
