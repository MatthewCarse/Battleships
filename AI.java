import java.util.Random;


public class AI 
{
	Grid g;
	public AI(Grid g)
	{
		this.g = g;
		place();
	}
	
	// array of ship names
	String[] fleet = {"Patrol Boat", "Patrol Boat", 
			"Battleship", "Battleship", "Submarine",
			"Destroyer", "Carrier"};
	// array of cardinal directions
	String[] directions = {"North", "South", "East", "West"};
	
	// iterate through ship names, finding a location for current and a direction
	// try again if location is not available or ship crosses another ship
	
	public void place()
	{
		int xc; int yc;
		String direction;
		Random generator = new Random();
		
		for (int n = 0; n < fleet.length; n++)
		{
			boolean placed = false;
			while (!placed)
			{
				do
				{
					xc = generator.nextInt(12);
					yc = generator.nextInt(12);
				}
				while(!g.available(xc, yc));
				direction = directions[generator.nextInt(4)];
				
				placed = g.addShip(fleet[n], xc, yc, direction);
			}
		}	
	}
}
