import java.util.ArrayList;

/**
 * The Hero class is responsible for everything that deals with the object Hero
 * This class uses the Hero method to initialize the Hero, update method to update
 * the Hero and its controlType, the getGraphic method to get the graphic of the Hero
 *  to main, and the handleFireballCollisions method to handle collisions with the 
 *  Hero and Fireballs
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Graphic graphic</li>
 * <li>private float speed</li>
 * <li>private int controlType;</li>
 * </ul></tt>
 * @author Jacob Sigmund and Shriyans Lenkala
 *
 */
public class Hero {
	// Creating variables for Hero class
	private Graphic graphic;
	private float speed;
	private int controlType;

	/**
	 * This constructor initializes a new Hero object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their beginning states.
	 * @param randGen is the only Random number generator that should be used
	 * throughout this level, by the Level itself and all of the Objects within.
	 * @param level is a string that either contains the word "RANDOM", or the 
	 * contents of a level file that should be loaded and played. 
	 */
	public Hero(float x, float y, int controlType){
		graphic = new Graphic("HERO");  // creates the hero graphic
		speed = 0.12f;					// sets the speed of the hero
		this.controlType = controlType; // sets the controlType for moving the hero
		graphic.setPosition(x,y);		// sets the coordinates of the hero
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
	public void update(int time, Water[] water){
		// makes Water appear if user hits spacebar or clicks
		if (GameEngine.isKeyHeld("SPACE") || GameEngine.isKeyHeld("MOUSE")) {
			for (int i = 0; i < water.length; ++i) {
				// if Water is null then creates a new Water object
				if (water[i] == null) {
					water[i] = new Water(graphic.getX(), graphic.getY(), graphic.getDirection());
					break; // once Water is created then loop is done
				}
			}
		}
		float pi = 3.141592653589793238462f; // value of pi
		// for controlType 1 which uses 'd', 'w', 'a', and 's' for moving the hero
		// direction of hero is determined by keys as well
		if (controlType == 1){
			// moves hero to the right if 'd' is pressed by user
			if (GameEngine.isKeyHeld("D") == true){
				graphic.setX(graphic.getX() + (speed * time));
				graphic.setDirection(0);
			}
			// moves hero upward if 'w' is pressed by user
			if (GameEngine.isKeyHeld("W") == true){
				graphic.setY(graphic.getY() - (speed * time));
				graphic.setDirection(3*(pi/2));
			}
			// moves hero to the left if 'a' is pressed by user
			if (GameEngine.isKeyHeld("A") == true){
				graphic.setX(graphic.getX() - (speed * time));
				graphic.setDirection(pi);
			}
			// moves hero downward if 's' is pressed by user
			if (GameEngine.isKeyHeld("S") == true){
				graphic.setY(graphic.getY() + (speed * time));
				graphic.setDirection(pi/2);
			}
		}
		// for controlType 2 hero is still moved with 'd', 'w', 'a', 's'
		// however direction of hero is determined by where on the screen the mouse is located
		else if (controlType == 2) {
			graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY()); // makes hero face the mouse
			// moves hero to the right if 'd' is pressed by user
			if (GameEngine.isKeyHeld("D") == true){
				graphic.setX(graphic.getX() + (speed * time));
			}
			// moves hero upward if 'w' is pressed by user
			if (GameEngine.isKeyHeld("W") == true){
				graphic.setY(graphic.getY() - (speed * time));
			}
			// moves hero to the left if 'a' is pressed by user
			if (GameEngine.isKeyHeld("A") == true){
				graphic.setX(graphic.getX() - (speed * time));
			}
			// moves hero downward if 's' is pressed by user
			if (GameEngine.isKeyHeld("S") == true){
				graphic.setY(graphic.getY() + (speed * time));
			}
		}
		// controlType 3 uses the mouse to move the hero, the hero will move follow the mouse around the screen
		else if (controlType == 3){
			graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());	// makes hero face the mouse
			double distance = Math.sqrt(Math.pow(graphic.getX() - GameEngine.getMouseX(), 2) + 
					Math.pow(graphic.getY() - GameEngine.getMouseY(), 2)); // distance between hero and mouse
			// only moves hero toward mouse until hero is 20 pixels away from the mouse
			if (distance > 20){
				graphic.setX((graphic.getDirectionX() * (speed*time)) + graphic.getX()); // move x coordinate of hero
				graphic.setY((graphic.getDirectionY() * (speed*time)) + graphic.getY()); // move y coordinate of hero
			}
		}
		graphic.draw(); // draw the hero
	}

	/**
	 * Allows graphic to be called to main in level class
	 * @return the graphic of Pant
	 */
	public Graphic getGraphic(){
		return graphic;
	}

	/**
	 * This method checks rather or not the any of the fireballs is hitting the hero
	 * @param fireballs arrayList so every fireball can get checked
	 * @return true or false
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++){
			// checks if any of the fireballs is hitting the hero
			if (graphic.isCollidingWith(fireballs.get(i).getGraphic())){
				return true; // returns there is a collision		
			}
		}
		return false; // returns there was no collision
	}
}