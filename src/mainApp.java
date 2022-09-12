import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/*BITZIS DIMITRIOS 2nd SEMESTER 3140134
 *CHINIS VASILEIOS 2nd SEMESTER 3140223
 *ERGASIA 3
 */

public class mainApp extends JPanel implements ActionListener{
	Scanner keyb = new Scanner(System.in);
	private static CatalogueAvailable item_cat;
	private static CatalogueRented rental_cat;
	private int code_of_rental, index, days_of_rental, cost, overdue_cost;
	private boolean allClicked = true, moviesClicked = false, gamesClicked = false, okClicked = false, cancelClicked = false;
	private String name=null , phone=null;
	private static String path_of_Items = "";
	private static String path_of_Rentals = "";
	private StoreFileR store;
	private JList gamesList = new JList(), moviesList = new JList(), rentsList = new JList(); //lists of tab
	private JButton showAll = new JButton("Show All"),
					showMovies = new JButton("Show Movies"),
					showGames = new JButton("Show Games"),
					saveRentals = new JButton("Save Rents"),
					saveItems = new JButton("Save Items"),
					addMovie = new JButton("Add Movie"),
					addGame = new JButton("Add Game");//buttons
	
	private String title = null, publisher = null, dateOfProduction = null,  director = null, script_writer = null, time = null, sub_type = null;
	private ArrayList<String> category = new ArrayList<String>();
	private ArrayList<String> actors = new ArrayList<String>();
	private boolean latest_release = false;
	private int numAll = 0, numAvailable = 0;
	
	private static final int dvdCost1=3; //cost per week for non-latest releases
	private static final int dvdCost2=2; //cost per day for latest releases
	private static final int blueRayCost=3;//per day
	private static final int gameCost=4;//per week
	private static final int overdueCost=1;//per day for every product
	

	//MouseListener for MoviesList
	MouseListener mouseListenerMovie = new MouseAdapter() {
		public void mouseClicked(MouseEvent e){
			if (e.getClickCount() == 2) {
				String selectedItem = (String) moviesList.getSelectedValue();
				
				itemOverview("Movie", selectedItem);
 			}
		}
	};
	
	//MouseListener for GamesList
	MouseListener mouseListenerGame = new MouseAdapter() {
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				String selectedItem = (String) gamesList.getSelectedValue();
				
				itemOverview("Game", selectedItem);
			}
		}
	};
	
	//MouseListener for RentalsList
	MouseListener mouseListenerRents = new MouseAdapter() {
		public void mouseClicked(MouseEvent e){
			if (e.getClickCount() == 2) {				
				String selectedItem = (String) rentsList.getSelectedValue();
				String num;
				
				int i = 0;
				
				while(!(selectedItem.charAt(i)=='.')){
					i++;
				}
				
				num = selectedItem.substring(0, i);
				index = Integer.parseInt(num);
				
				if(allClicked){
					index--;
				}
				else if(moviesClicked){
					int k=0;
					
					for(i=0; i<=rental_cat.getCatalogueRented().size()-1; i++){
						if(rental_cat.getItemType(i).equals("movie")){
							k++;
							if(k==index){
								break;
							}
						}
					}

					index = i;
				}
				else if(gamesClicked){
					int k=0;
					
					for(i=0; i<=rental_cat.getCatalogueRented().size()-1; i++){
						if(rental_cat.getItemType(i).equals("game")){
							k++;
							if(k==index){
								break;
							}
						}
					}

					index = i;
				}
				Rental rent = rental_cat.getRental(index);				
				
				//Everytime someone opens a rental it will set the costs of the current time!
				setOverdueCosts(index);
				
				LabelGUI rentalOverview;
				
				if(rent.getReturned()){
					rentalOverview = new LabelGUI(rent.toString(), rent.getName());
				}
				else{
					String[] buttons = {"Return"};
					
					rentalOverview = new LabelGUI(rent.toString(), rent.getName(), buttons);
				}
				
				int choose = rentalOverview.getChoose();
				
				switch (choose) {
					case 0:
						Return();
					break;
				}				
			}
		}
	};

	public void itemOverview(String type, String selectedItem){
		String num;
		
		int i = 0;
		
		while(!(selectedItem.charAt(i)=='.')){
			i++;
		}
		
		num = selectedItem.substring(0, i);
		index = Integer.parseInt(num);
		
		int k=0;
		for(i=0; i<=item_cat.getSize()-1; i++){
			if(item_cat.getItem(i).getClass().toString().replaceAll("\\s","").substring(5).equals(type)){
				k++;
				if(k==index) break;
			}
		}
		index = i;
		Item item;
		
		if(type.equals("Movie")){
			item = (Movie) item_cat.getItem(index);
		}
		else{
			item = (Game) item_cat.getItem(index);
		}
		
		boolean avCopies = true;
		
		if(item.getAvCopies()<=0){
			avCopies = false;
		}
		
		JLabel label = new JLabel(item.toString());
		
		ImageGUI overview = new ImageGUI(label, item.getTitle(), item.getPath(), avCopies);
		
		int choose = overview.getChoose();
		
		switch (choose) {
			case 0:
				ReadName();
			break;
		}
	}
	
	mainApp(){
		store = new StoreFileR(path_of_Items, path_of_Rentals);
		code_of_rental = store.getRents().size()+1;
		item_cat = new CatalogueAvailable(store.getItems());
		rental_cat = new CatalogueRented(store.getRents());
		
		//Add action listeners for our buttons
		showAll.addActionListener(this);
		showMovies.addActionListener(this);
		showGames.addActionListener(this);
		saveItems.addActionListener(this);
		saveRentals.addActionListener(this);
		addMovie.addActionListener(this);
		addGame.addActionListener(this);
		
		//Create images of tabs
		ImageIcon games = new ImageIcon("images/tabs/games.png");
		Image img = games.getImage();
		Image newimg = img.getScaledInstance(35, 28,  java.awt.Image.SCALE_SMOOTH);
		games = new ImageIcon(newimg);
		
		ImageIcon movies = new ImageIcon("images/tabs/movies.png");
		img = movies.getImage();
		newimg = img.getScaledInstance(30, 28,  java.awt.Image.SCALE_SMOOTH);
		movies = new ImageIcon(newimg);
		
		ImageIcon rents = new ImageIcon("images/tabs/rental.png");
		img = rents.getImage();
		newimg = img.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH);
		rents = new ImageIcon(newimg);
		
		//Create a tab panel for our program
		JTabbedPane jtb = new JTabbedPane();
		
		//Create tab of Movies
		JPanel moviesPanel = new JPanel();
		
		GridBagLayout moviesLayout = new GridBagLayout();

		GridBagConstraints moviesGBC = new GridBagConstraints();
		
		moviesPanel.setLayout(moviesLayout);
		
		createLists("Movie");
		
		JScrollPane scrollPaneMovies = new JScrollPane();
		scrollPaneMovies.setViewportView(moviesList);
		moviesList.addMouseListener(mouseListenerMovie);
		
		moviesGBC.fill = GridBagConstraints.BOTH;//gbc is used to specialize every part of the frame in a way that every object fits well
		moviesGBC.anchor = GridBagConstraints.NORTHWEST;//anchor is used to determine the place
		moviesGBC.weightx = 0.0;//percentage of the window's extra horizontal space that is used by the object
		moviesGBC.weighty = 1.0;//percentage of the window's extra vertical space that is used by the object
		moviesGBC.gridwidth = 2;//this frame will use 2 parts of the grid extending from top left and moving right 2 places
		moviesGBC.gridx = 0;//coordinates of the part of the grid the item will use
		moviesGBC.gridy = 0;//coordinates of the part of the grid the item will use
		moviesPanel.add( scrollPaneMovies, moviesGBC );
		
		moviesGBC = new GridBagConstraints();//reset
		
		moviesGBC.anchor = GridBagConstraints.LAST_LINE_END;
		moviesGBC.weightx = 0.5;
		moviesGBC.gridx = 0;
		moviesGBC.gridy = 1;
		moviesPanel.add(addMovie, moviesGBC);
		
		jtb.addTab("Movies", movies, moviesPanel);
		jtb.setSelectedIndex(0);
		
		//Create tab of Games
		JPanel gamesPanel = new JPanel();
		
		GridBagLayout gamesLayout = new GridBagLayout();

		GridBagConstraints gamesGBC = new GridBagConstraints();
		
		gamesPanel.setLayout(gamesLayout);
		
		createLists("Game");
		
		JScrollPane scrollPaneGames = new JScrollPane();
		scrollPaneGames.setViewportView(gamesList);
		gamesList.addMouseListener(mouseListenerGame);
		
		gamesGBC.fill = GridBagConstraints.BOTH;
		gamesGBC.anchor = GridBagConstraints.NORTHWEST;
		gamesGBC.weightx = 0.0;
		gamesGBC.weighty = 1.0;
		gamesGBC.gridwidth = 2;
		gamesGBC.gridx = 0;
		gamesGBC.gridy = 0;
		gamesPanel.add( scrollPaneGames, gamesGBC );
		
		gamesGBC = new GridBagConstraints();
		
		gamesGBC.anchor = GridBagConstraints.LAST_LINE_END;
		gamesGBC.weightx = 0.5;
		gamesGBC.gridx = 0;
		gamesGBC.gridy = 1;
		gamesPanel.add(addGame, gamesGBC);
		
		jtb.addTab("Games", games, gamesPanel);
		
		//Create tab of Rentals
		JPanel rentsPanel = new JPanel();
		createLists("Rental");
		
		JScrollPane scrollPaneRents = new JScrollPane();
		scrollPaneRents.setViewportView(rentsList);
		rentsList.addMouseListener(mouseListenerRents);
		
		GridBagLayout layout = new GridBagLayout();

		GridBagConstraints gbc = new GridBagConstraints();

		rentsPanel.setLayout(layout);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		rentsPanel.add(scrollPaneRents, gbc);
		
		gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		rentsPanel.add(showAll, gbc);
		
		gbc.weightx = 0.5;		
		gbc.gridx = 1;
		gbc.gridy = 1;
		rentsPanel.add(showMovies, gbc);
		
		gbc.weightx = 0.5;		
		gbc.gridx = 2;
		gbc.gridy = 1;
		rentsPanel.add(showGames, gbc);
		
		jtb.addTab("Rentals", rents, rentsPanel);
		
		GridBagLayout layout2 = new GridBagLayout();
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		
		setLayout(layout2);
		
		gbc2.anchor = GridBagConstraints.NORTHEAST;
		gbc2.weightx = 1;		
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.insets = new Insets(10, 10, 0, 10);
		add(saveRentals, gbc2);
		
		gbc2 = new GridBagConstraints();
		
		gbc2.anchor = GridBagConstraints.NORTHEAST;
		gbc2.weightx = 0.0;	
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.insets = new Insets(10, 0, 0, 10);
		add(saveItems, gbc2);
		
		gbc2 = new GridBagConstraints();
		
		gbc2.fill = GridBagConstraints.BOTH;
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		gbc2.weighty = 1.0;
		gbc2.weightx = 0.0;
		gbc2.gridwidth = 2;
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		add(jtb, gbc2);	
	}
	
	//creates the lists
	public void createLists(String type){
		DefaultListModel model = new DefaultListModel();
		
		int i = 1;
		if(type.equals("Movie")){
			for(Item item : item_cat.getCatalogueAvailable()){
				if(item.getClass().toString().replaceAll("\\s","").substring(5).equals(type)){
					model.addElement(i + ". " + item.getTitle() + ", " + item.getType());
					i++;
				}
			}	
			moviesList.setModel(model);
		}
		else if(type.equals("Game")){
			for(Item item : item_cat.getCatalogueAvailable()){
				if(item.getClass().toString().replaceAll("\\s","").substring(5).equals(type)){
					model.addElement(i + ". " + item.getTitle() + ", " + item.getType());
					i++;
				}
			}
			gamesList.setModel(model);
		}
		else{
			for(Rental rent : rental_cat.getCatalogueRented()){
				if(allClicked){
					model.addElement(i + ". " + rent.getName() + ", " + rent.getTitle() + ", " + rent.getType() + ", Returned: " + rent.getReturned());
					i++;
				}
				else if(moviesClicked){
					if(rent.getItemType().equals("movie")){
						model.addElement(i + ". " + rent.getName() + ", " + rent.getTitle() + ", " + rent.getType() + ", Returned: " + rent.getReturned());
						i++;
					}
				}
				else if(gamesClicked){
					if(rent.getItemType().equals("game")){
						model.addElement(i + ". " + rent.getName() + ", " + rent.getTitle() + ", " + rent.getType() + ", Returned: " + rent.getReturned());
						i++;
					}
				}
			}
			rentsList.setModel(model);
		}
	}
	
	//it does something when a button is pressed
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == showAll){
			allClicked = true;
			moviesClicked = false;
			gamesClicked = false;
			
			createLists("Rental");
		}
		if(e.getSource() == showMovies){
			allClicked = false;
			moviesClicked = true;
			gamesClicked = false;
			
			createLists("Rental");
		}
		if(e.getSource() == showGames){
			allClicked = false;
			moviesClicked = false;
			gamesClicked = true;
			
			createLists("Rental");
		}
		if(e.getSource() == saveItems){
			store.saveItems(path_of_Items, item_cat.getCatalogueAvailable());
		}
		if(e.getSource() == saveRentals){
			for(int i=0; i<=rental_cat.getSize()-1; i++){//we have to calculate overdue cost up until this moment and save it (through setCosts(int i) method)
				if(!rental_cat.getReturned(i)){
					setOverdueCosts(i);
				}
			}
			store.saveRental(path_of_Rentals, rental_cat.getCatalogueRented());
		}
		if(e.getSource() == addGame){
			ReadTitle("Game");
		}
		if(e.getSource() == addMovie){
			ReadTitle("Movie");
		}
	}
	
	public void ReadTitle(String type){
		title = null;
		publisher = null;
		dateOfProduction = null;
		sub_type = null;
		director = null;
		script_writer = null;
		time = null;
		
		latest_release = false;
		
		numAll = 0;
		numAvailable = 0;
		
		category = new ArrayList<String>();
		actors = new ArrayList<String>();
		
		TextFieldGUI readTitle = new TextFieldGUI("Title of item: ", "Read title!");
		
		title = readTitle.getText();
		
		if(!title.equals("")){
			ReadSubType(type);
		}
	}
	
	public void ReadSubType(String type){
		Object[] possibilitiesGame = {"Playstation", "Nintendo", "XBOX"};
		Object[] possibilitiesMovie = {"DVD", "blue ray"};
		
		ComboBoxGUI readSubType;
		if(type.equals("Game")){
			readSubType = new ComboBoxGUI("Subtype of item: ", "Readsubtype!", possibilitiesGame);
		}
		else{
			readSubType = new ComboBoxGUI("Subtype of item: ", "Readsubtype!", possibilitiesMovie);
		}
		
		sub_type = readSubType.getText();
		
		if(!sub_type.equals("")){
			ReadCategory(type);
		}
	}

	public void ReadCategory(String type){
		ArrayListGUI readCategory = new ArrayListGUI("Categories of the item: ", "Read Category!");
		
		category = readCategory.getList();
		
		if(category != null){
			ReadPublisher(type);
		}
	}
	
	public void ReadPublisher(String type){
		TextFieldGUI readPublisher = new TextFieldGUI("Publisher of the item: ", "Read Publisher!");
		
		publisher = readPublisher.getText();
		
		if(!publisher.equals("")){
			ReadDateOfProduction(type);
		}
	}
	
	public void ReadDateOfProduction(String type){
		TextFieldGUI readDateOfProduction = new TextFieldGUI("Date of production: ", "Read date of production!");
		
		dateOfProduction = readDateOfProduction.getText();
		
		if(!dateOfProduction.equals("")){
			if(type.equals("Game")){
				ReadNumAvailable(type);
			}
			else{
				ReadActor(type);
			}
		}
	}
	
	public void ReadActor(String type){
		ArrayListGUI readActor = new ArrayListGUI("Actors of the movie: ", "Read actors!");
		actors = readActor.getList();
		
		if(actors!=null){
			ReadDirector(type);
		}
	}
	
	public void ReadDirector(String type){
		TextFieldGUI readDirector = new TextFieldGUI("Director of the movie: ", "Read director!");
		director = readDirector.getText();
		
		if(!director.equals("")){
			ReadScriptWriter(type);
		}
	}
	
	public void ReadScriptWriter(String type){
		TextFieldGUI readScriptWriter = new TextFieldGUI("Script writer of the movie: ", "Read script writer!");
		script_writer = readScriptWriter.getText();
		
		if(!script_writer.equals("")){
			ReadLatestRelease(type);
		}
	}
	
	public void ReadLatestRelease(String type){
		String[] buttons = {"Yes", "No", "Cancel"};
		
		LabelGUI readLatestRelease = new LabelGUI("Is the movie a latest release?", "Read latest release!", buttons);
		int choose = readLatestRelease.getChoose();
		
		if(choose == 0){
			latest_release = true;
			ReadTime(type);
		}
		else if(choose == 1){
			latest_release = false;
			ReadTime(type);
		}		
	}
	
	public void ReadTime(String type){
		TextFieldGUI readTime = new TextFieldGUI("Time of the movie: ", "Read time!");
		time = readTime.getText();
		
		if(!time.equals("")){
			ReadNumAvailable(type);
		}
	}
	
	public void ReadNumAvailable(String type){
		TextFieldGUI readNumAvailable = new TextFieldGUI("Number of available copies: ", "Read number of available copies!");
		String numAvailableText = readNumAvailable.getText();
		
		if(!numAvailableText.equals("")){
			try {
				numAvailable = Integer.parseInt(numAvailableText);
			} catch (NumberFormatException ex) {
				ReadNumAvailable(type);
			}
			
			numAll = numAvailable;
			createItem(type);
		}
	}
	
	public void createItem(String type){
		Item item = null;
		  
		if(type.equals("Game")){
			item = new Game(title, sub_type, category, publisher, dateOfProduction, numAll, numAvailable, "");
		}
		else{
			item = new Movie(title, director, category, script_writer, actors, publisher, dateOfProduction, time, sub_type, latest_release, numAvailable, numAll, "");
		}
		
		String[] buttons = {"Yes", "No"};
		
		LabelGUI createItem = new LabelGUI(item.toString(), "Create Item", buttons);
		int choose = createItem.getChoose();
		
		if(choose == 0){			  
			if(item!=null){
				  item_cat.add(item);
			}
			
			createLists(type);
		}
	}
	
	/*  
	 * method used to rent a product, name, phone number and time of rental are given by the user and then the cost of the rental is calculated depending on the product and our prices :P
	 */
	public void Rent(){
		cost = 0;
				
		if(item_cat.MovieOrGame(index) && item_cat.getType(index)=="DVD"){ //DVDmovie
			if (item_cat.getLatestRelease(index)) cost=dvdCost2*days_of_rental;//latest release
			else cost=dvdCost1*days_of_rental*7;//old release
		}
		else if (item_cat.MovieOrGame(index) && item_cat.getType(index)=="blue ray") cost=blueRayCost*days_of_rental;
		else if (!item_cat.MovieOrGame(index)) cost=gameCost*days_of_rental*7;
		
		Date date = new Date();
		try{
			DateFormat formatter;
			formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
			String d = formatter.format(date);
			date = formatter.parse(d);
		}catch (Exception ex) {
			
		}
		
		Rental rental = new Rental(code_of_rental, item_cat.getTitle(index), item_cat.getItemType(index).toLowerCase(), item_cat.getType(index), name, phone, date, days_of_rental, cost, 0, false, index);
		
		String[] buttons = {"Yes", "No"};
		
		LabelGUI rent = new LabelGUI(rental.toString(), "Rental", buttons);
		int choose = rent.getChoose();
		
		if(choose == 0){				
			  rental_cat.add(rental);
			  item_cat.setNumAvailable(index, -1);
				
			  code_of_rental++;
				
			  createLists("Rental");
		}
	}
	
	public void ReadName(){
		name = null;
		phone = null;
		days_of_rental = 0;
		cost = 0;
		overdue_cost = 0;
		
		TextFieldGUI readName = new TextFieldGUI("Give me your name: ", "Read name!");
		name = readName.getText();
		
		if(!name.equals("")){
			ReadPhone();
		}
		
	}
	
	public void ReadPhone(){
		TextFieldGUI readPhone = new TextFieldGUI("Give me your phone: ", "Read mobile phone!");
		phone = readPhone.getText();
		
		if(!phone.equals("")){
			ReadDaysOfRental();
		}
	}
	
	public void ReadDaysOfRental(){
		String label = null;
		//per day (that includes movies that are blue rays and latest release DVDs)
		if(item_cat.MovieOrGame(index) && item_cat.getType(index)=="blue ray" || item_cat.MovieOrGame(index) &&  item_cat.getType(index)=="DVD" && item_cat.getLatestRelease(index)){
			label = ("<html>" + "This product is rented per day." + "<br>" + 
								"How many days will the rental last?" + "</html>");
		}
		//per week (games and non latest release DVDs)
		else if(!item_cat.MovieOrGame(index) || item_cat.MovieOrGame(index) &&  item_cat.getType(index)=="DVD" && !item_cat.getLatestRelease(index)){
			label = ("<html>" + "This product is rented per week." + "<br>" +
							   "How many weeks will the rental last?" + "</html>");
		}
			
		TextFieldGUI readDaysOfRental = new TextFieldGUI(label, title);
		String daysOfRentalText = readDaysOfRental.getText();
		
		if(!daysOfRentalText.equals("")){
			int weeks_of_rental = 0;
		    
			//per day
		    if(item_cat.MovieOrGame(index) && item_cat.getType(index)=="blue ray" || item_cat.MovieOrGame(index) &&  item_cat.getType(index)=="DVD" && item_cat.getLatestRelease(index)){
		    	try {
		    		days_of_rental = Integer.parseInt(daysOfRentalText);
		    	} catch (NumberFormatException ex) {
		    	    days_of_rental = 0;
		    	}
		    }
			//per week (games and non latest release DVDs)
			else if(!item_cat.MovieOrGame(index) || item_cat.MovieOrGame(index) &&  item_cat.getType(index)=="DVD" && !item_cat.getLatestRelease(index)){
				try {
					weeks_of_rental = Integer.parseInt(daysOfRentalText);
					days_of_rental=weeks_of_rental*7;
		    	} catch (NumberFormatException ex) {
		    	    days_of_rental = 0;
		    	}
			}
		    
		    if(days_of_rental == 0){
		    	ReadDaysOfRental();
		    }
		    else{
			    Rent();
		    }
		}
	}
	
	public void Return(){
	    rental_cat.setReturned(index, true);
	    
	    item_cat.setNumAvailable(rental_cat.getCatAvIndex(index), 1);
	    
	    createLists("Rental");
	}
	
	public void setOverdueCosts(int i){
		if(!rental_cat.getReturned(i)){
			int diff = DateDifference(i)-rental_cat.getDays_of_rental(i); //date difference in days between the time the product was rented and current time (when the user tries to return it)
			if ( diff < 0 ){ //if the product is returned before the time margin expires
				diff=0;
			}
			else{
				rental_cat.setOverdueDays(i, diff);
			}
			
			int ov_cost = diff*overdueCost;//overdue cost = days overdue * overdue cost
			rental_cat.setOverdueCost(i, ov_cost);
		}
	}
	
	public int DateDifference(int i){
		Date date = new Date();
		try{
			DateFormat formatter;
			formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
			String d = formatter.format(date);
			date = formatter.parse(d);
		}catch (Exception ex) {
			
		}	
		long diff = date.getTime() - rental_cat.getDate(i).getTime();
		if (diff%(1000 * 60 * 60 * 24)==0){ //if the product is late exactly 1,2 ... days
			return (int) diff/(1000 * 60 * 60 * 24);
		}
		else if (diff%(1000 * 60 * 60 * 24)>0){
			return (int) diff/(1000 * 60 * 60 * 24) + 1; //if the product is late 1,2 .. days plus some milliseconds
		}
		else return 0;		
	}
	
	public static void mainFrame(){
		JFrame frame = new JFrame("VideoClub");
		
		frame.setSize(550, 400);
		
		frame.getContentPane().add(new mainApp(),
				BorderLayout.CENTER);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		frame.setVisible(true);
		
		//When program closes
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	System.exit(0);
		    }
		});
	}
	
	//main method
	public static void main (String[] args){
		SelectListFileGUI item_list = new SelectListFileGUI("Select ITEM_LIST.txt");
		path_of_Items = item_list.getPath();
		SelectListFileGUI rental_list = new SelectListFileGUI("Select RENTAL_LIST.txt");
		path_of_Rentals = rental_list.getPath();
		
		mainFrame();
	}
}