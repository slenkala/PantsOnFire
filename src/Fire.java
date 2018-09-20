import java.util.Random;

public class Fire {
	
	//Field variables
	private Graphic graphic;	//declare memory variable of an instance of Graphic
	private Random randGen;	//declare memory variable of an instance of Random
	private int fireballCountdown;	//variable to keep track of time of when fireball is thrown
	private final float pi = 3.14159f;	//variable for pi so we don't have to use Math class
	private int heat;	//"health" for the fire
	
	//This is the constructor for a instance of Fire
	public Fire(float x, float y, Random randGen) {
		
		graphic = new Graphic("FIRE");	//assign new instance of Graphic to graphic of argument FIRE
		graphic.setPosition(x, y);	//use the parameters sent to set position of the new graphic
		fireballCountdown = randGen.nextInt(3000) + 3001;	//assigns the variable count a number form 3000-6000
		heat = 40; //assigns "health" to 40
		this.randGen = randGen; //uses the parameter to assign randGen an instance

	}
	
	//This method updates the instance of Fire and returns a new instance of Fireball if the conditions are met
	public Fireball update(int time) {
		
		//This if statement checks if the heat or "health" is above 0 to check if the Fire is still active
		if (heat > 0){
			graphic.draw();
			this.fireballCountdown -= time;
			
			//This if statement checks if the counter has expired. If it has, it tells Fire to create a 
			//fireball and throw it from the appropriate instance of Fire and reset the counter variable.
			if (fireballCountdown <= 0){
				fireballCountdown = this.randGen.nextInt(3000) + 3001;
				Fireball fireballs = new Fireball(graphic.getX(), graphic.getY(), this.randGen.nextFloat()*2* pi);
				return fireballs;
			}
			else{
				return null;
			}
		}
		else {
			return null;
		}
	}

	//This method returns the information retaining the the respective graphic
	public Graphic getGraphic(){
		return graphic;
	}

	//This method checks to see if water comes in contact with fire, and if it does, each water contact will reduce
	//the heat or "health" of the fire.
	public void handleWaterCollisions(Water[] water) {
		
		for (int i = 0; i < water.length; i++){
			if (water[i] != null){
				if (graphic.isCollidingWith(water[i].getGraphic())){
					heat --;
					water[i] = null;
				}
			}
		}
	}
	
	//This method is implemented to make sure that the Fire is removed, not just stop drawing it, when it has been
	//extinguished by the water
	public boolean shouldRemove() {
		if(heat < 1)
			return true;
		else
			return false;
	}
}