import java.util.Date;

public class Rental {
	
	private Date date;
	private String name, phone, type, sub_type, title;
	private int cost, overdue_cost, code_of_rental, days_of_rental, CatAvIndex, overdue_days;
	private boolean returned;
	
	//Constructor
	Rental(int code_of_rental, String title, String type, String sub_type, String name, String phone, Date date, int days_of_rental, 
		   int  cost, int overdue_cost, boolean returned, int CatAvIndex){
		this.code_of_rental = code_of_rental;
		this.name = name;
		this.phone = phone;
		this.date = date;
		this.days_of_rental = days_of_rental;
		this.cost = cost;
		this.overdue_cost = overdue_cost;
		this.CatAvIndex = CatAvIndex;
		this.title = title;
		this.type = type;
		this.sub_type = sub_type;
		this.returned = returned;
	}
	
	//returns Rental's Charecterstics in a String
	public String toString(){
		String line = "Info: " +
					  "\n" + "Code: " + code_of_rental +//first name and phone are printed
					  "\n" + "Name: " + name +
					  "\n" + "Phone: " + phone +
					  "\n" + "Item_type: " + type;
		if(type.equals("movie")){
			line = line + "\n" + "Sub_type: " + sub_type;
		}
		else{
			line = line + "\n" + "Platform: " + sub_type;
		}
		line = line +
				"\n" + "Title: " + title +
 				"\n" + "Date of rental: " + date +//and after that all the other characteristics of a Rental are printed
				"\n" + "Days of rental: " + days_of_rental +
				"\n" + "Overdue days: " + overdue_days +
				"\n" + "Total cost of rental: " + (cost + overdue_cost) +
				"\n" + "Overdue cost of rental: " + overdue_cost +
				"\n" + "Returned: " + returned;
		return line;
	}
	
	//Getters
	public String getTitle(){
		return title;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCode_of_rental(){
		return code_of_rental;
	}
	
	public Date getDate(){
		return date;
	}
	
	public int getDays_of_rental(){
		return days_of_rental;
	}
	
	public boolean getReturned(){
		return returned;
	}
	
	public String getType(){//DVD or blue ray or Playstation or XBOX or Nintendo
		return sub_type;
	}
	
	public String getItemType(){//Game or Movie
		return type;
	}
	
	public int getCatAvIndex(){
		return CatAvIndex;
	}
	
	public int getOverdueDays(){
		return overdue_days;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getOverdueCost(){
		return overdue_cost;
	}
	
	//Setters
	public void setOverdueCost(int ov_cost){
		overdue_cost = ov_cost;
	}
	
	public void setReturned(boolean returned){
		this.returned = returned;
	}
	
	public void setCatAvIndex(int CatAvIndex){
		this.CatAvIndex = CatAvIndex;
	}
	
	public void setOvedueDays(int overdue_days){
		this.overdue_days = overdue_days;
	}
}
