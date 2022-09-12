import java.util.ArrayList;

public class Movie extends Item{

	private ArrayList<String> actor = new ArrayList<String>(1);//Arraylist with movie's actors/actresses 
	 
	private String director, script_writer, time;
	private boolean latest_release;
	private int numAll, numAvailable;
	 
	//Constructor
	Movie(String title, String director, ArrayList<String> category,String script_writer,ArrayList<String> actor, String publisher,
	 String date_of_production, String time, String type, boolean latest_release, int numAvailable, int numAll, String path){
		super(title, publisher, date_of_production, category, numAvailable, numAll, type, path);
		this.actor = actor;
		this.director = director;
		this.script_writer = script_writer;
		this.latest_release = latest_release;
		this.time = time;
		this.numAll = numAll;
		this.numAvailable = numAvailable;
	}
	 
	//toString method needed to return additional information about the movie
    public String toString () {
		return super.toString() + 
				"<br>" + "Time: " + time +
				"<br>" + "Actors: " + actor +
				"<br>" + "Director: " + director +
				"<br>" + "Script writer: " + script_writer +
				"<br>" + "Latest Release: " + Boolean.toString(latest_release) +
				"<br>" + "Number of all copies: "+ numAll + 
				"<br>" + "Number of available copies: "+ numAvailable +"</html>";
    }
    
	//true4yes false4no
    public boolean getLatestRelease(){
		return latest_release;
	}
    
    public String getTime(){
    	return time;
    }
    
    public ArrayList<String> getCast(){
    	return actor;
    }
    
    public String getDirector(){
    	return director;
    }
    
    public String getScriptWriter(){
    	return script_writer;
    }
}
