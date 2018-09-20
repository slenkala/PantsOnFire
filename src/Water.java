/**
 * The Water class is responsible for everything that deals with the object Water
 * This class uses the Water method to initialize Water, update method to update
 * Water and the getGraphic method to get the graphic of Water to main
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Graphic graphic;</li>
 * <li>private float speed;</li>
 * <li>private float distanceTraveled;</li>
 * </ul></tt>
 * @author Jacob Sigmund and Shriyans Lenkala
 *
 */
public class Water {
	// Creating variables for Water class
	private Graphic graphic;
	private float speed;
	private float distanceTraveled;
	
	/**
	 * This method initializes the Water for the game
	 * @param x coordinate for the Water
	 * @param y coordinate for the Water
	 * @param direction the Water shoots from the hero
	 */
	public Water(float x, float y, float direction) {
		// initializes all parts of Water
		graphic = new Graphic("WATER");
		speed = .7f; 						// sets speed
		graphic.setDirection(direction); 	// sets direction Water comes from hero
		graphic.setX(x);					// sets x coordinate
		graphic.setY(y);					// sets y coordinate
		distanceTraveled = 0;				// sets initial distance from hero
	}
	
	/** 
	 * This method will update the coordinates and speed of the Water
	 * @param time determines how coordinates and speed are changed
	 * @return the water back to the game
	 */
	public Water update(int time) {
		// turns Water to null if to far away from hero
		if (distanceTraveled <= 200){
			graphic.setX(graphic.getX() + (graphic.getDirectionX()*(speed*time))); // update x coordinate
			graphic.setY(graphic.getY() + (graphic.getDirectionY()*(speed*time))); // update y coordinate
			distanceTraveled += (speed*time); // update speed
			graphic.draw(); // draw Water
			return this;
		}
		return null;
	}
	/**
	 * Allows graphic to be called to main in level class
	 * @return the graphic of Water
	 */
	public Graphic getGraphic(){
		return graphic;
	}
}