import java.util.ArrayList;
import java.util.Random;

/**
 * The Pant class is responsible for everything that deals with the object Pant
 * This class uses the Pant method to initialize Pant, update method to update
 * Pant, the getGraphic method to get the graphic of Pant to main, 
 * handleFireballCollisions method to handle collisions with Pants and Fireballs,
 * and a shouldRemove method to check if a Pant should be removed
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Graphic graphic</li>
 * <li>private Random randGen;</li>
 * <li>private boolean isAlive;</li>
 * </ul></tt>
 * @author Jacob Sigmund and Shriyans Lenkala
 *
 */
public class Pant {
	// Creating variables for Pant class
	private Graphic graphic;
	private Random randGen;
	private boolean isAlive;

	/**
	 * This method initializes the Pant for the game
	 * @param x coordinate for the Pant
	 * @param y coordinate for the Pant
	 * @param randGen brings the random generator to the method
	 */
	public Pant(float x, float y, Random randGen) {
		graphic = new Graphic("PANT"); // creates the Pant graphic
		graphic.setPosition(x, y); // sets x,y coordinates of the Pant
		isAlive = true; 		// Makes the Pant appear
		this.randGen = randGen; // makes the class random generator to the loaded one from level
	}
	
	/** 
	 * This method will check to see if the pant should still be drawn
	 * @param time of game
	 */
	public void update(int time) {
		// checks to see if Pant should still be drawn
		if (isAlive) {
			graphic.draw();
		}
	}
	
	/**
	 * Allows graphic to be called to main in level class
	 * @return the graphic of Pant
	 */
	public Graphic getGraphic(){
		return graphic;
	}
	/**
	 * This method handles what is done when a fireball hits a pant
	 * @param fireballs all the fireballs that are on the screen
	 * @return either a new Fire or null
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		// checks to see if any fireball is hitting any Pant
		for (int i = 0; i <  fireballs.size(); ++i){
			// makes sure the fireball is not null
			if (fireballs.get(i) != null){
				// check if there is a collision
				if (graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
					fireballs.get(i).destroy(); // destroys fireball if collision
					isAlive = false;			//makes Pant not appear
					return new Fire(graphic.getX(), graphic.getY(), this.randGen); // Returns a new Fire object
				}
			}
		}
		return null; // if no collision
	}
	
	/**
	 * Finds out if a Pant should be removed or left
	 * @return true or false
	 */
	public boolean shouldRemove() {
		// returns if Pant should be removed or not
		if(isAlive == false)
			return true;
		else
			return false;
	}
}