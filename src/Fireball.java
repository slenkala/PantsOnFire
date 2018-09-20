public class Fireball {
	
	//Field Variables
	private Graphic graphic;	//declare memory variable of an instance of Graphic
	private float speed;	//variable that hold the pace in which the fireball moves
	private boolean isAlive;	//variable that shows whether the fireball exists or not

	//This is the constructor for a new instance of Fireball
	public Fireball(float x, float y, float directionAngle) {
		graphic = new Graphic("FIREBALL");	//assign new instance of Graphic to graphic of argument FIREBALL
		graphic.setPosition(x, y);	//uses the parameters sent to set position of the new graphic
		graphic.setDirection(directionAngle);	//assign an angle in which the fireball shoots 
		speed = .2f;	//speed is .2f according to documentation
		isAlive = true;	//variable that states whether the Fireball is active or should be terminated
	}
	
	//This method is used to update the instance of Fireball to make sure it is within a certain distance away from
	//the screen, otherwise the fireball should be terminated.
	public void update (int time ) {
		if ((graphic.getX() >= (GameEngine.getWidth() + 100))||(graphic.getX() <= (0 - 100))||
				(graphic.getY() >= (GameEngine.getWidth() + 100))||(graphic.getY() <= (0 - 100))){
			isAlive = false;
		}

		//this while loop checks if the fireball is still "alive", and if it is, it respectively draws it to the screen
		while (isAlive == true) {
			graphic.setX(graphic.getX() + graphic.getDirectionX()*(speed * time));
			graphic.setY(graphic.getY() + graphic.getDirectionY()*(speed * time));
			graphic.draw();
			return;
			
		}

	}
	
	//This method returns the information retaining the the respective graphic
	public Graphic getGraphic(){
		return graphic;
	}

	//This method checks to see if water comes in contact with fireball, and if it does, each water contact 
	//terminate the fireball
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++){
			if (water[i] != null){
				if (graphic.isCollidingWith(water[i].getGraphic())){
					isAlive = false;
					water[i] = null;
				}
			}
		}
	}

	//This method is called when the fireball comes with water and pants so it is terminated
	public void destroy() {
		isAlive = false;
	}
	
	//This method is implemented to make sure that the Fireball is removed, not just stop drawing it, when it has been
	//extinguished by the water or comes in contact with pants
	public boolean shouldRemove() {
		if(isAlive == false)
			return true;
		else
			return false;
	}
}