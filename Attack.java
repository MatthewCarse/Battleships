import javax.swing.JTextArea;


public class Attack 
{
	boolean first = false;
	Grid g;
	public Attack(Grid grid)
	{
		this.g = grid;
		if (g.user)
			first = true;
	}
	
	public boolean fire(int xCoord, int yCoord, JTextArea console, JTextArea header)
	{
		boolean hit = true;
		if (g.inBounds(xCoord) && g.inBounds(yCoord))
		{
			// give message that shot is being fired at set coordinates
			console.append(String.format("\nFiring at coordinates %dX %dY", xCoord, yCoord));
			if (g.available(xCoord, yCoord)) // if coordinates are available (i.e. not a ship)
			{
				if (first)
				{
					console.setText(String.format("\nFiring at coordinates %dX %dY", xCoord, yCoord));
					first = false;
				}
				console.append("\n...Miss!\n");
				g.sink(xCoord, yCoord, false);
				g.printGrid("Attack");
				header.append(g.getHeader());
				console.append(g.getGrid());
				hit = false;
			}	
			else
			{
				if (first)
				{
					console.setText(String.format("\nFiring at coordinates %dX %dY", xCoord, yCoord));
					first = false;
				}
				console.append("\n...Hit!");
				// hit remains true
				g.sink(xCoord, yCoord, true);
				header.append(g.getHeader());
				console.append(g.getGrid());
			}
		}
		else
			console.append(String.format("\nCoordinates %dX %dY out of bounds.", xCoord, yCoord));
		
		return hit;
	}
}