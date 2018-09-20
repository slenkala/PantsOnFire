//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            Pants on Fire
// Files:            Level.java , Hero.java , Pant.java , Water.java , Fire.java , Fireball.java , 
//						level01.pof , level02.pof , level03.pof
// Semester:         CS302 Fall 2016
//
// Author:           Shriyans Lenkala	
// Email:            lenkala@wisc.edu	
// CS Login:         shriyans
// Lecturer's Name:  Jim Williams
// Lab Section:      324
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     Shriyans Lenkala
// Partner Email:    jsigmund2@wisc.edu
// Partner CS Login: sigmund
// Lecturer's Name:  Gary Dahl
// Lab Section:      345
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _X_ Write-up states that Pair Programming is allowed for this assignment.
//    _X_ We have both read the CS302 Pair Programming policy.
//    _X_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do.
//
// Persons:          (identify each person and describe their help in detail)
// Online Sources:   (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 */
public class Level
{
	// Creating the field variables for level class
	private Hero hero;
	private Water[] water;
	private ArrayList<Pant> pants;
	private ArrayList<Fire>	fires;
	private ArrayList<Fireball> fireballs;
	private Random randGen;
	private int controlType;
	private float pi = 3.141592653f;
	
	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their beginning states.
	 * @param randGen is the only Random number generator that should be used
	 * throughout this level, by the Level itself and all of the Objects within.
	 * @param level is a string that either contains the word "RANDOM", or the 
	 * contents of a level file that should be loaded and played. 
	 */
	public Level(Random randGen, String level) 
	{ 
		this.randGen = randGen;
		this.controlType = randGen.nextInt(3) + 1; //Random number 1 2 or 3 for controlType

		// Decided rather to load a level or create random level
		if(level.equals("RANDOM")){
			createRandomLevel();
		}
		else{
			loadLevel(level);
		}
	}
	
	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short
	 * 3 second pause and a message indicating that the player has lost.  When
	 * this method returns "ADVANCE", a short pause and win message will be 
	 * followed by the creation of a new level which replaces this one.  When
	 * this method returns anything else (including "CONTINUE"), the GameEngine
	 * will simply continue to call this update() method as usual. 
	 */
	public String update(int time) 
	{
		hero.update(time, water); // Updates Hero
		// Updates water if not null
		for (int i = 0 ; i < water.length; i++){
			if (water[i] != null){
				water[i] = water[i].update(time);
			}
		}
		// Updates pants if not null
		for (int j = 0; j < pants.size(); j++){
			if (!pants.get(j).equals(null)){
				pants.get(j).update(time);
			}
		}
		// Updates fires by throwing a fireball if not null
		for (int k = 0; k < fires.size(); ++k) {
			if (!fires.get(k).equals(null)) {
				Fireball ball = fires.get(k).update(time);
				if ( ball != null) {
					fireballs.add(ball);
				}
			}
		}
		// Updates fireballs if not null
		for (int l = 0; l < fireballs.size(); l++){
			if (!( fireballs.get(l) == null)){
				fireballs.get(l).update(time);		
			}
		}
		// Checks to see if Hero is hit by fireball
		if ((hero.handleFireballCollisions(fireballs)) == true){
			return "QUIT";
		}
		// Checks to see if a fireball is hit by water
		for (int i = 0; i < fireballs.size(); i++){
			fireballs.get(i).handleWaterCollisions(water);
		}
		// Checks if a fire is being hit by water
		for (int i = 0; i < fires.size(); i++){
			fires.get(i).handleWaterCollisions(water);
		}
		// Removes pant and creates fire if fireball hits a pant
		for (int i = 0; i < pants.size(); ++i) {
			Fire firenew = pants.get(i).handleFireballCollisions(fireballs);
			if (firenew != null) {
				pants.remove(i);
				fires.add(firenew);
			}
		}
		// Checks if a pant should be removed from it's arrayList
		for( int i = 0; i <pants.size(); ++i){
			if(pants.get(i).shouldRemove() == true){ 
				pants.remove(i);
				--i;
			}
		}
		// Checks if a fire should be removed from it's arrayList
		for(int i = 0; i <fires.size(); ++i){
			if(fires.get(i).shouldRemove() == true){
				fires.remove(i);
				--i;
			}
		}
		// Checks if a fireball should be removed from it's arrayList
		for(int i = 0; i <fireballs.size(); ++i){
			if(fireballs.get(i).shouldRemove() == true){
				fireballs.remove(i);
				--i;
			}
		}
		// Determines if you should move on to next level or if all the pants have been turned to fire,meaning you lost
		if(pants.size() == 0)
			return "QUIT";
		if(fires.size() == 0)
			return "ADVANCE";
		// If there are still pants and fires game continues
		return "CONTINUE";
	}	
	
	/**
	 * This method returns a string of text that will be displayed in the
	 * upper left hand corner of the game window.  Ultimately this text should 
	 * convey the number of unburned pants and fires remaining in the level.  
	 * However, this may also be useful for temporarily displaying messages that 
	 * help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() 
	{
		// Displayed number of pants and fires left
		return "Pants Left: " + pants.size() + "\nFires Left: " + fires.size();
	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires,
	 * and 20 randomly positioned Pants.
	 */
	public void createRandomLevel() 
	{ 
		// Starts hero in the middle of the game board
		hero = new Hero(GameEngine.getWidth()/2, GameEngine.getHeight()/2 , controlType);
		// Creating arrayLists for variables
		water = new Water[8];
		pants = new ArrayList<Pant>();
		fires = new ArrayList<Fire>();
		fireballs = new ArrayList<Fireball>();
		// Randomizes 20 pant locations
		for (int i = 0; i < 20; i++){
			float x = GameEngine.getWidth()* this.randGen.nextFloat();
			float y = GameEngine.getHeight()* this.randGen.nextFloat();
			pants.add(new Pant(x, y, this.randGen));
		}
		// Randomizes 6 fire locations
		for (int j = 0; j < 6; j++){
			float x = GameEngine.getWidth()* this.randGen.nextFloat();
			float y = GameEngine.getHeight()* this.randGen.nextFloat();
			fires.add(new Fire(x, y, this.randGen));
		}		 
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * @param level is a string containing the contents of a custom level file 
	 * that is read in by the GameEngine.  The contents of this file are then 
	 * passed to Level through its Constructor, and then passed from there to 
	 * here when a custom level is loaded.  You can see the text within these 
	 * level files by dragging them onto the code editing view in Eclipse, or 
	 * by printing out the contents of this level parameter.  Try looking 
	 * through a few of the provided level files to see how they are formatted.
	 * The first line is always the "ControlType: #" where # is either 1, 2, or
	 * 3.  Subsequent lines describe an object TYPE, along with an X and Y 
	 * position, formatted as: "TYPE @ X, Y".  This method should instantiate 
	 * and initialize a new object of the correct type and at the correct 
	 * position for each such line in the level String.
	 */
	public void loadLevel(String level) 
	{ 
		// Creating arrayLists for variables
		water = new Water[8];
		fires = new ArrayList<Fire>();
		pants = new ArrayList<Pant>();
		fireballs = new ArrayList<Fireball>();
		// splits up input data for the already created level
		String[] line = level.split("\n");
		// Finds what controlType the level uses
		controlType = Integer.parseInt(line[0].substring(13, 14));
		// Goes through input data to initialize the level
		for(int i = 1; i < line.length; ++i){
			// initializes of input is for fire
			if(line[i].substring(0, 4).equals("FIRE")){
				String[] lineSectOne = line[i].split("@"); // splits name from coordinates
				String[] lineSectTwo = lineSectOne[1].split(","); // splits up x and y coordinates
				float x = Float.parseFloat(lineSectTwo[0].trim()); // changes x input from string to float
				float y = Float.parseFloat(lineSectTwo[1].substring(1).trim()); // changes y input from string to float
				fires.add(new Fire(x, y, this.randGen)); // creates fire
			}
			// initializes of input is for pant
			else if(line[i].substring(0, 4).equals("PANT")){
				String[] lineSectOne = line[i].split("@"); // splits name from coordinates
				String[] lineSectTwo = lineSectOne[1].split(","); // splits up x and y coordinates
				float x = Float.parseFloat(lineSectTwo[0].trim()); // changes x input from string to float
				float y = Float.parseFloat(lineSectTwo[1].substring(1).trim()); // changes y input from string to float
				pants.add(new Pant(x, y, this.randGen)); // creates pant
			}
			// initializes of input is for hero
			else if(line[i].substring(0, 4).equals("HERO")){
				String[] lineSectOne = line[i].split("@"); // splits name from coordinates
				String[] lineSectTwo = lineSectOne[1].split(","); // splits up x and y coordinates
				float x = Float.parseFloat(lineSectTwo[0].trim()); // changes x input from string to float
				float y = Float.parseFloat(lineSectTwo[1].substring(1).trim()); //changes y input from string to float
				hero = new Hero(x, y ,controlType); // creates hero
			}
		}
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level.  Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * @param args is the sequence of custom level files to play through.
	 */
	public static void main(String[] args)
	{
		// Creates and runs game
		GameEngine.start(null,args);
	}
}