import java.util.HashMap;

public class Battleships 
{
	HashMap<String, Integer> fleet;	
	// true = user, false = AI
	Grid g2 = new Grid(12, false);
	Grid g1 = new Grid(12, true); // create grid of size 12
	GameViewer gv;
	@SuppressWarnings("unused")
	public Battleships()
	{
		constructFleet();
		gv = new GameViewer(g1, g2);
		createGrids();
		AI ai = new AI(g2);
	}
	
	public void createGrids()
	{
		// true = user, false = AI
		g2 = new Grid(12, false);
		g1 = new Grid(12, true); // create grid of size 12
		gv.fleet = fleet;				
	}
		
	// add the fleet of ships to a HashMap, with the value being the quantity left to place
	public void constructFleet()
	{
		fleet = new HashMap<String, Integer>();
		fleet.put("Patrol Boat", 2);
		fleet.put("Battleship", 2);
		fleet.put("Submarine", 1);
		fleet.put("Destroyer", 1);
		fleet.put("Carrier", 1);
	}
}
