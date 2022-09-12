import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StoreFileR {
	private static ArrayList<Item> items = new ArrayList<Item>(1);
	private ArrayList<Rental> rents = new ArrayList<Rental>(1);
	int code=1;
	
	StoreFileR(String pathOfItems, String pathOfRents){
		loadItems(pathOfItems);
		loadRental(pathOfRents);
		checkCopies();
	}
	
	public void loadItems(String path){
		int counter = 0;
		
		File f = null;
		BufferedReader reader = null;
		
		String line, type, sub_type, title, year, director, script_writer, time, company, pathOfImage;
		
		boolean latest_release;
		
		int available_copies, copies;
		
		try{//creates a new File
			f = new File(path);
		} catch (NullPointerException e) {
			System.err.println("File not found");
		}
		
		try{//creates a new BufferedReader
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			System.err.println("Error opening file!");
		}
		
		try{//starts reading the file and creating objects for ArrayList<Items> only if their format is right
			line = reader.readLine();
			
			if(!line.trim().equals(" ")){
				if(line.trim().equalsIgnoreCase("ITEM_LIST")){
					line = reader.readLine();
					
					if(line != null) {
						if(line.trim().equals("{")){
							line = reader.readLine();
							
							if(line != null) {
								while(line!=null){
									ArrayList<String> cast = new ArrayList<String>(1);
									ArrayList<String> category = new ArrayList<String>(1);
									type = null;
									sub_type = null;
									title = null;
									year = null;
									director = "NOT AVAILABLE";
									script_writer = "NOT AVAILABLE";
									time = "NOT AVAILABLE";
									latest_release = false;
									copies = 2;
									available_copies = 2;
									company = "NOT AVAILABLE";
									pathOfImage = "";
									Item item = null;
									
									if (line.trim().equalsIgnoreCase("ITEM")) {										
										line = reader.readLine();
										
										reader.mark(300);//marks the point where ITEM begins so that it can return if it's info* is valid in order to actually create it 
														 //(info* only 3 elements are necessary for the object to be created! These are: item_type, title, year
										
										if(line != null){//check for the 3 necessary elements is made
											if(line.trim().equals("{")){
												line = reader.readLine();
												boolean item_type_found = false, title_found = false, year_found = false;
												
												if(line != null){
													while(line.trim().equals("}") == false && line!=null){
														
														if(line != null){
															if(line.trim().toLowerCase().startsWith("item_type")){																
																
																if(line != null){	
																	if(line.trim().substring(9).trim().replaceAll("\"", "").equalsIgnoreCase("movie")){
																		item_type_found = true;
																		type = "movie";
																	}
																	else if(line.trim().substring(9).trim().replaceAll("\"", "").equalsIgnoreCase("game")){
																		item_type_found = true;
																		type = "game";
																	}
																}
															}
															if(line.trim().toLowerCase().startsWith("title")){
																title_found = true;
																
																title = line.replaceAll("\"","").trim().substring(5).trim();
															}
															
															if(line.trim().toLowerCase().startsWith("year")){
																year_found = true;
																
																year = line.replaceAll("\"","").trim().substring(4).trim();
															}												
															
															line = reader.readLine();															
														}
													}
													if(line != null){//returns (with reset's help) to find every other (not necessary) element if the 3 necessary elements are found
														if(item_type_found && title_found && year_found){
															reader.reset();
															
															
															line = reader.readLine();
															
															if(line != null){
																	while(line.trim().equals("}") == false && line!=null){//searches for other elements																																
																		if(line != null){
																			if(type.equals("movie")){
																				if(line.trim().toLowerCase().startsWith("sub_type")){
																					if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("dvd")){
																						sub_type = "DVD";
																					}
																					else if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("blueray")){
																						sub_type = "blue ray";
																					}
																				}
																				
																				if(line.trim().toLowerCase().startsWith("cast")){
																					String new_line = line.trim().substring(4).trim();
																					
																					int sizeOfLine = new_line.length();
																					int i = 0, k;
																					String actor;
																					
																					while(i<=sizeOfLine-1){
																						k=i;
																						
																						if(new_line.charAt(k) == '\"'){
																							k++;
																							while(new_line.charAt(k) != '\"' && k<=sizeOfLine-1){
																								k++;
																							}																							

																							actor = new_line.substring(i,k+1).replaceAll("\"", "");
																							cast.add(actor);
																							i=k;
																						}
																						
																						i++;
																					}																				
																				}
																				
																				if(line.trim().toLowerCase().startsWith("director")){
																					director = line.trim().replaceAll("\"","").substring(8).trim();
																				}
																				
																					
																				if(line.trim().toLowerCase().startsWith("script writer")){
																					script_writer = line.trim().replaceAll("\"","").substring(13).trim();
																				}
																					
																				if(line.trim().toLowerCase().startsWith("latest release")){
																					latest_release = Boolean.parseBoolean(line.trim().replaceAll("\"","").substring(14).trim());
																				}
																				
																				if(line.trim().toLowerCase().startsWith("time")){
																					time = line.trim().replaceAll("\"","").substring(4).trim();
																				}
																			}else{
																				if(line.trim().toLowerCase().startsWith("platform")){
																					if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("playstation")){
																						sub_type = "Playstation";
																					}
																					else if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("nintendo")){
																						sub_type = "Nintendo";
																					}
																					else if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("xbox")){
																						sub_type = "XBOX";
																					}
																				}
																			}
																			
																			if(line.trim().toLowerCase().startsWith("category")){
																				String new_line = line.trim().substring(8).trim();
																				
																				int sizeOfLine = new_line.length();
																				int i = 0, k;
																				String cat;
																				
																				while(i<=sizeOfLine-1){
																					k=i;
																					
																					if(new_line.charAt(k) == '\"'){
																						k++;
																						while(new_line.charAt(k) != '\"' && k<=sizeOfLine-1){
																							k++;
																						}																							

																						cat = new_line.substring(i,k+1).replaceAll("\"", "");
																						
																						category.add(cat);
																						i=k;
																					}
																					
																					i++;
																				}																			
																			}
																			
																			if(line.trim().toLowerCase().startsWith("path")){
																				pathOfImage = line.trim().substring(4).replaceAll("\"","").trim();
																			}
																			
																			if(line.trim().toLowerCase().startsWith("company")){
																				company = line.trim().replaceAll("\"","").substring(7).trim();
																			}
																			
																			if(line.trim().toLowerCase().startsWith("copies")){
																				copies = Integer.parseInt(line.replaceAll("\\s","").replaceAll("\"","").substring(6).trim());
																			}
																			
																			if(line.trim().toLowerCase().startsWith("available copies")){
																				available_copies = Integer.parseInt(line.replaceAll("\\s","").replaceAll("\"","").substring(15));
																			}
																		}
																	line = reader.readLine();
																}													
															}
															if(sub_type==null){
																if(type.equalsIgnoreCase("movie")){
																	sub_type = "DVD";
																}
																else if(type.equalsIgnoreCase("movie")){
																	sub_type = "Playstation";
																}
															}
															
															if(type=="game"){//an Item object is created and added to ArrayList<Items> 
																item = new Game(title, sub_type, category, company, year, copies, available_copies, pathOfImage);
															}
															else if(type=="movie"){//an Item object is created and added to ArrayList<Items>
																item = new Movie(title, director, category, script_writer, cast, company, year, time, sub_type, latest_release, available_copies, copies, pathOfImage);
															}
															
															items.add(item);
														}
													}
												}
											}
										}
									}
									line = reader.readLine();
								}
							}					
						}		
					}
				}
			}		
			
		}catch (IOException e) {
			System.out.println("Line " + counter + ": Sudden end.");
		}
		
		try {
			reader.close();
		}  catch (IOException e) {
			System.err.println("Error closing file.");	
		}
	}
	
	public void loadRental(String path){//the same process is user to load the rentals' txt file
		int counter = 0;
		
		File f = null;
		BufferedReader reader = null;
		
		String line = null;
		
		try{
			f = new File(path);
		} catch (NullPointerException e) {
			System.err.println("File not found");
		}
		
		try{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			System.err.println("Error opening file!");
		}
		

		String type, sub_type, title, name, phone;
		int cost, overdue_cost, daysOfRental, overdue_days;
		Date date;
		String d;
		boolean returned = false;
		
		try{
			line = reader.readLine();
			
			if(!line.trim().equals(" ")){
				if(line.trim().equalsIgnoreCase("RENTAL_LIST")){
					line = reader.readLine();
					
					if(line != null) {
						if(line.trim().equals("{")){
							line = reader.readLine();
							
							if(line != null) {
								while(line!=null){
									type = null;
									sub_type = null;
									title = null;
									name = null;
									phone = null;
									cost=0;
									overdue_cost=0;
									daysOfRental=0;
									overdue_days = 0;
									date = null;
									returned = false;
									d = null;
									
									if (line.trim().equalsIgnoreCase("RENTAL")){
										line = reader.readLine();
										
										reader.mark(300);										
										if(line != null){
											if(line.trim().equals("{")){
												line = reader.readLine();
												boolean item_type_found = false;
												
												if(line != null){
													while(line.trim().equals("}") == false && line!=null){
													
														if(line != null){
																if(line.trim().toLowerCase().startsWith("item_type")){
																if(line != null){	
																	if(line.trim().substring(9).trim().replaceAll("\"", "").equalsIgnoreCase("movie")){
																		item_type_found = true;
																		type = "movie";
																	}
																	else if(line.trim().substring(9).trim().replaceAll("\"", "").equalsIgnoreCase("game")){
																		item_type_found = true;
																		type = "game";
																	}
																}
															}
															line = reader.readLine();
														}
													}
													
													if(line != null){
														if(item_type_found){
															reader.reset();
															
															line = reader.readLine();
															while(line.trim().equals("}") == false && line!=null){
																if(line != null){		
																	if(line.trim().toLowerCase().startsWith("title")){
																		title = line.trim().replaceAll("\"","").substring(5).trim();
																	}
																	
																	if(type.equals("movie")){
																		if(line.trim().toLowerCase().startsWith("sub_type")){
																			if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("dvd")){
																				sub_type = "DVD";
																			}
																			else if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("blueray")){
																				sub_type = "blue ray";
																			}
																		}
																	}
																	else if(type.equals("game")){
																		if(line.trim().toLowerCase().replaceAll("\\s","").startsWith("platform")){
																			if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("playstation")){
																				sub_type = "Playstation";
																			}
																			else if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("nintendo")){
																				sub_type = "Nintendo";
																			}
																			else if(line.replaceAll("\\s","").replaceAll("\"","").substring(8).equalsIgnoreCase("xbox")){
																				sub_type = "XBOX";
																			}
																		}
																	}
																	
																	if(line.toLowerCase().trim().startsWith("cost")){
																		cost = new Integer(line.trim().replaceAll("\"","").substring(4).trim());
																	}															
																	
																	if(line.trim().toLowerCase().startsWith("overdue cost") && !line.trim().substring(12).trim().equals("")){
																		overdue_cost = Integer.parseInt(line.trim().replaceAll("\"","").substring(12).trim());
																	}
																	
																	if(line.trim().toLowerCase().startsWith("returned") && !line.trim().substring(8).trim().equals("")){
																		returned = Boolean.valueOf(line.trim().replaceAll("\"","").substring(8).trim());
																	}
																		
																	if(line.trim().toLowerCase().startsWith("days")){
																		daysOfRental = Integer.parseInt(line.trim().replaceAll("\"","").substring(4).trim());
																	}

																	if(line.trim().toLowerCase().startsWith("overdue days") && !line.trim().substring(12).trim().equals("")){
																		overdue_days = Integer.parseInt(line.trim().replaceAll("\"","").substring(12).trim());
																	}
																		
																	if(line.trim().toLowerCase().startsWith("name")){
																		name = line.trim().replaceAll("\"","").substring(4).trim();
																	}
																	
																	if(line.trim().toLowerCase().startsWith("phone")){
																		phone = line.trim().replaceAll("\"","").substring(5).trim();
																	}
																	
																	if(line.trim().toLowerCase().startsWith("date")){
																		d = line.trim().replaceAll("\"","").substring(4).trim();
																	}
																	
																	try{
																		DateFormat formatter;
																		formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
																		date = formatter.parse(d);
																	}catch (Exception ex) {
																		
																	}																
																}
																line = reader.readLine();
															}
															
															
															int i = 0;
															for(i=0; i<=items.size()-1; i++){
																if(items.get(i).getTitle().equalsIgnoreCase(title) && items.get(i).getType().equalsIgnoreCase(sub_type) && items.get(i).getClass().toString().trim().replaceAll("\\s","").replaceAll("\"","").substring(5).trim().equalsIgnoreCase(type)){
																	break;
																}
															}												
															
															Rental rent = new Rental(code, title, type, sub_type, name, phone, date, daysOfRental, cost, overdue_cost, returned, i);
															
															rents.add(rent);
															code++;
														}
													}
												}
											}
										}
									}
									line = reader.readLine();
								}
							}
						}
					}
				}				
			}			
		}catch (IOException e) {
			System.out.println("Line " + counter + ": Sudden end.");
		}
		
		try {
			reader.close();
		}  catch (IOException e) {
			System.err.println("Error closing file.");	
		}
		
		
	}
	
	public void saveItems(String path, ArrayList<Item> item_list){//Saves ArrayList<Items> content in the items' txt file (in order to save any changes, e.g. in the number of copies)
																  //in the format that was given in the assignment
		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(path);
		}
		catch (NullPointerException e) {
			System.err.println ("File not found.");
		}

		try	{
			writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(f)));
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file for writing!");
		}
		    
		try	{
			writer.write("ITEM_LIST");
			writer.newLine();
			writer.write("{");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}

		for(Item item : item_list){
			try	{
				writer.newLine();
				writer.write("\t" + "ITEM");
				writer.newLine();
				writer.write("\t" + "{");
				writer.newLine();
				writer.write("\t" + "\t" + "ITEM_TYPE \"" + item.getClass().toString().trim().substring(5).trim() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "TITLE \"" + item.getTitle() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "YEAR \"" + item.getDate() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "COMPANY \"" + item.getPublisher() + "\"");
				writer.newLine();
				if(item.getPath().equals("") || item.getPath()==null){
					writer.write("\t" + "\t" + "PATH \" " + "images/Items/noimage.jpg" + "\"");
				}
				else{
					writer.write("\t" + "\t" + "PATH \"" + item.getPath() + "\"");
				}
				
				writer.newLine();
				writer.write("\t" + "\t" + "CATEGORY");
				
				for(int i = 0; i<=item.getCategory().size()-1; i++){
					writer.write(" \"" + item.getCategory().get(i) + "\"");
				}
				
				if(item instanceof Movie){
					writer.newLine();
					writer.write("\t" + "\t" + "SUB_TYPE \"" + item.getType() + "\"");
					writer.newLine();
					writer.write("\t" + "\t" + "TIME \"" + ((Movie) item).getTime() + "\"");
					writer.newLine();
					writer.write("\t" + "\t" + "CAST");
					for(int i = 0; i<=((Movie) item).getCast().size()-1; i++){
						writer.write(" \"" + ((Movie) item).getCast().get(i) + "\"");
					}
					writer.newLine();
					writer.write("\t" + "\t" + "DIRECTOR \"" + ((Movie) item).getDirector() + "\"");
					writer.newLine();
					writer.write("\t" + "\t" + "SCRIPT WRITER \"" + ((Movie) item).getScriptWriter() + "\"");
					
					if(item.getType().equals("DVD")){
						writer.newLine();
						writer.write("\t" + "\t" + "LATEST RELEASE \"" + ((Movie) item).getLatestRelease() + "\"");
					}
				}
				else if(item instanceof Game){
					writer.newLine();
					writer.write("\t" + "\t" + "PLATFORM \"" + item.getType() + "\"");
				}
				writer.newLine();
				writer.write("\t" + "\t" + "COPIES \"" + item.getCopies() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "AVAILABLE COPIES \"" + item.getAvCopies() + "\"");
				writer.newLine();
				writer.write("\t" + "}");
			}
			catch (IOException e) {
				System.err.println("Write error!");
			}			
		}
		
		try	{
			writer.newLine();
			writer.write("}");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}
		
		try {
			writer.close();
		}
		catch (IOException e) {	
			System.err.println("Error closing file.");
		}
	}
	
	public void saveRental(String path, ArrayList<Rental> rentals){//rentals are saved in the same way/format in the rentals' txt file
		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(path);
		}
		catch (NullPointerException e) {
			System.err.println ("File not found.");
		}

		try	{
			writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(f)));
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file for writing!");
		}
		
		try	{
			writer.write("RENTAL_LIST");
			writer.newLine();
			writer.write("{");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}
		
		for(Rental rent : rentals){
			try{
				writer.newLine();
				writer.write("\t" + "RENTAL");
				writer.newLine();
				writer.write("\t" + "{");
				writer.newLine();
				writer.write("\t" + "\t" + "CODE \"" + rent.getCode_of_rental() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "ITEM_TYPE \"" + rent.getItemType() + "\"");
				writer.newLine();
				if(rent.getItemType().equalsIgnoreCase("movie")){
					writer.write("\t" + "\t" + "SUB_TYPE \"" + rent.getType() + "\"");
					writer.newLine();
				}
				else{
					writer.write("\t" + "\t" + "PLATFORM \"" + rent.getType() + "\"");
					writer.newLine();
				}
				writer.write("\t" + "\t" + "TITLE \"" + rent.getTitle() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "NAME \"" + rent.getName() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "DATE \"" + rent.getDate() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "DAYS \"" + rent.getDays_of_rental() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "OVERDUE DAYS \"" + rent.getOverdueDays() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "PHONE \"" + rent.getPhone() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "COST \"" + rent.getCost() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "OVERDUE COST \"" + rent.getOverdueCost() + "\"");
				writer.newLine();
				writer.write("\t" + "\t" + "RETURNED \"" + rent.getReturned() + "\"");
				writer.newLine();
				writer.write("\t" + "}");
			}
			catch (IOException e) {
				System.err.println("Write error!");
			}
		}
		
		try	{
			writer.newLine();
			writer.write("}");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}
		
		try {
			writer.close();
		}
		catch (IOException e) {	
			System.err.println("Error closing file.");
		}
	}
	
	public void checkCopies(){//Checks if available copies + rentals don't equal copies of all copies. In other words, that we haven't rented more copies than we have
		for(int i=0; i<=items.size()-1; i++){
			int copies = items.get(i).getCopies(), avCopies = items.get(i).getAvCopies(), rentals = 0;
			
			for(int j=0; j<=rents.size()-1; j++){
				if(i==rents.get(j).getCatAvIndex() && !rents.get(j).getReturned()){
					rentals++;
				}
			}
			
			int new_copies = avCopies + rentals;
			if(new_copies>copies){
				items.get(i).setNumAll(new_copies);
				System.out.println("Error! Rents+Available Copies>Copies for product " + items.get(i).getTitle() + " " + items.get(i).getType());
				System.out.println("New copies: " + new_copies);
			}
			else if(new_copies<copies){
				items.get(i).setNumAll(new_copies);
				System.out.println("Error! You are trying to rent a number of copies that is higher than the number of copies we have! Error found at: " + items.get(i).getTitle() + " " + items.get(i).getType());
				System.out.println("New copies: " + new_copies);
			}
		}
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public ArrayList<Rental> getRents() {
		return rents;
	}
}
