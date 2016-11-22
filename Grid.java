import java.util.Arrays;

public class Grid 
{
	Grid g_template;
	boolean user;
	char[][] grid; // 2D character grid
	int gridSize; // grid length
	boolean shipPlacement = true; // ship placement phase, default: true
	boolean shipAttack = false; // attack phase, default: false
	boolean gameOver = false;
	StringBuilder header = new StringBuilder(); // header to be displayed in player's GUI
	StringBuilder feedback = new StringBuilder(); // feedback message (e.g. place already occupied) or grid
	
	public Grid(int gridSize)
	{
		this.grid = new char[gridSize][gridSize];
		this.gridSize = gridSize;
	}
	
	/**
	 * Create a square grid for placing battleships
	 * @param gridSize (An integer of grid length)
	 */
	public Grid(int gridSize, boolean user)
	{
		// a 2-d string array
		 this.grid = new char[gridSize][gridSize];
		 this.gridSize = gridSize;
		 this.user = user;
		 // if the grid belongs to AI then create a template grid on just showing hits/misses
		 if (!user)
		 {
			 g_template = new Grid(12);
			 g_template.user = false;
		 }
		 fillGrid();
	}
	
	/**
	 * Function to add a ship to the grid
	 * @param type (2 patrol boats (1 x 2), 2 battleships (1 x 3), 1 submarine (1 x 3),
	 *  1 destroyer (1 x 4) and 1 carrier (1 x 5))
	 * @param xCoord (X-coordinate of placement (starts at 0 - top-left))
	 * @param yCoord (Y-coordinate of placement (starts at 0 - top-left))
	 * @param direction (String form of direction for ship to be placed e.g. South)
	 * @param user (True if user is placing ship)
	 */
	public boolean addShip(String type, int xCoord, int yCoord, String direction)
	{	
		int xLength = 0;
		int yLength = 0;
		switch (type)
		{
			case ("Patrol Boat"):
				PatrolBoat pb = new PatrolBoat();
				xLength = pb.getXLength(direction);
				yLength = pb.getYLength(direction);
				break;
			case ("Battleship"):
				Battleship b = new Battleship();
				xLength = b.getXLength(direction);
				yLength = b.getYLength(direction);
				break;
			case ("Submarine"):
				Submarine s = new Submarine();
				xLength = s.getXLength(direction);
				yLength = s.getYLength(direction);
				break;
			case ("Destroyer"):
				Destroyer d = new Destroyer();
				xLength = d.getXLength(direction);
				yLength = d.getYLength(direction);
				break;
			case ("Carrier"):
				Carrier c = new Carrier();
				xLength = c.getXLength(direction);
				yLength = c.getYLength(direction);
				break;	
		}
		
		if (checkPlacement(xCoord, yCoord, xLength, yLength))
		{
			placeShip(type, xCoord, yCoord, xLength, yLength);
			if (user)
				printGrid("Placement");	
			return true;
		}
		else
			return false;
		
	}
	
	/**
	 * Checks the ship placement for validity (in bounds and space available)
	 * @param xCoord
	 * @param yCoord
	 * @param xLength
	 * @param yLength
	 * @param user
	 * @return Validity
	 */
	public boolean checkPlacement(int xCoord, int yCoord, int xLength, int yLength)
	{
		boolean valid = true;
		// if placing South or East
		if (xLength > 0 && yLength > 0)
		{
			for (int i = xCoord; i < (xCoord + xLength); i++)
			{
				if (!inBounds(i))
				{
					if (user)
						feedback.append("\nThe location "+i+"X is out of bounds");
					valid = false;
					break;	
				}
				for (int j = yCoord; j < (yCoord + yLength); j++)
				{
					if (!inBounds(j))
					{
						if (user)
							feedback.append("\nThe location "+j+"Y is out of bounds");
						valid = false;
						break;	
					}
					if (!available(i, j))
					{
						if (user)
							feedback.append("\nThe space "+i+"X "+j+"Y is already occupied");
						valid = false;
						break;
					}
				}
			}	
		}
		
		// if placing North or West
		if (xLength < 0 && yLength < 0)
		{
			for (int i = xCoord; i > (xCoord + xLength); i--)
			{
				if (!inBounds(i))
				{
					if (user)
						feedback.append("\nThe location "+i+"X is out of bounds");
					valid = false;
					break;	
				}
				
				for (int j = yCoord; j > (yCoord + yLength); j--)
				{
					if (!inBounds(j))
					{
						if (user)
							feedback.append("\nThe location "+j+"Y is out of bounds");
						valid = false;
						break;	
					}

					if (!available(i, j))
					{
						
						if (user)
							feedback.append("\nThe space ("+i+"X "+j+"Y) is already occupied");
						valid = false;
						break;
					}
				}
			}
		}
		
		
		return valid;
	}
	
	/**
	 * Places the ship on the grid if the placement is valid
	 * @param type
	 * @param xCoord
	 * @param yCoord
	 * @param xLength
	 * @param yLength
	 */
	public void placeShip(String type, int xCoord, int yCoord, int xLength, int yLength)
	{
		// if placing South or East
		if (xLength > 0 && yLength > 0)		
			for (int i = xCoord; i < (xCoord + xLength); i++)
				for (int j = yCoord; j < (yCoord + yLength); j++)								
					this.grid[i][j] = type.charAt(0);			
				
							
		// if placing North or West
		if (xLength < 0 && yLength < 0)		
			for (int i = xCoord; i > (xCoord + xLength); i--)			
				for (int j = yCoord; j > (yCoord + yLength); j--)				
					this.grid[i][j] = type.charAt(0);
	}
	
	/**
	 * Check whether the specified space is available (. /)
	 * @param xCoord
	 * @param yCoord
	 * @return True or False
	 */
	public boolean available(int xCoord, int yCoord)
	{
		boolean available = true;
		if (!(this.grid[xCoord][yCoord] == '.' || this.grid[xCoord][yCoord] == '/' || this.grid[xCoord][yCoord] == 'X'))
				available = false;
		return available;
	}
	
	/**
	 * Check whether the coordinate is within bounds
	 * @param coord
	 * @return True or False
	 */
	public boolean inBounds(int coord)
	{
		if (coord < 0 || coord >= gridSize)
			return false;
		return true;
	}
	
	/**
	 * Change square to X when attack is successful 
	 * @param xCoord
	 * @param yCoord
	 */
	public void sink(int xCoord, int yCoord, boolean hit)
	{
		// if user's grid (AI firing) then display user's grid and AI's grid
		if (user)
		{
			if (hit)
			{
				grid[xCoord][yCoord] = 'X';
				printGrid("Placement");
				if (victory())
				{
					feedback.append("\nAI wins! All your ships have been sunk!");
					gameOver = true;
				}	
			}
			else if (grid[xCoord][yCoord] == 'X')
				grid[xCoord][yCoord] = 'X';
			else
				grid[xCoord][yCoord] = '/';
		}
		// if AI's grid (user firing) then display AI's template grid
		else
		{
			if (hit)
			{
				grid[xCoord][yCoord] = 'X';
				g_template.grid[xCoord][yCoord] = 'X';
				printGrid("Attack");
				if (victory())
				{
					feedback.append("\nVictory! All opposition ships have been sunk!");
					gameOver = true;
				}	
			}
			else if (grid[xCoord][yCoord] == 'X')
			{
				grid[xCoord][yCoord] = 'X';
				g_template.grid[xCoord][yCoord] = 'X';
				printGrid("Attack");
			}				
			else
			{
				grid[xCoord][yCoord] = '/';
				g_template.grid[xCoord][yCoord] = '/';
				printGrid("Attack");
			}		
		}		
	}
	
	/**
	 * Check whether all ships have been sunk
	 * @return True or False
	 */
	public boolean victory()
	{
		boolean victory = true;
		for (char[] row : grid)
			for (char c : row)
				if (!(c == 'X' || c == '.' || c == '/'))
				{
					victory = false;
					break;
				}
		return victory;
	}
	
	/**
	 * Prints the grid to the console
	 */
	public void printGrid(String mode)
	{
		// if user is attacking (AI's grid - user = false) - display opponent's grid
		if (!user && mode.equals("Attack"))
		{
			if (!shipPlacement)
			{
				header = new StringBuilder();
				header.append("\nOpponent's Grid:\n");
				feedback.append("\n\n    ");
				for (int k = 0; k < gridSize; k++)
					feedback.append(String.format("%6s", k));
				feedback.append(String.format("\n"));
				for (int i = 0; i < gridSize; i++)
				{
					feedback.append(String.format("%5s",i));
					for (int j = 0; j < gridSize; j++)			
						feedback.append("     "+g_template.grid[j][i] + " ");
					feedback.append(String.format("\n"));
				}
			}	
		}
		
	
		// if user is being attacked and is hit (user = true) then display own grid
		// if user is placing ships (own grid - user = true) - display own grid
		if (user && mode.equals("Placement"))
		{
			header = new StringBuilder();
			header.append("\nCurrent Grid:\n");
			
			feedback = new StringBuilder();
			feedback.append("\n\n    ");
			for (int k = 0; k < gridSize; k++)
				feedback.append(String.format("%6s", k));
			feedback.append(String.format("\n"));
			for (int i = 0; i < gridSize; i++)
			{
				feedback.append(String.format("%5s",i));
				for (int j = 0; j < gridSize; j++)			
					feedback.append("      "+grid[j][i] + " ");				
				feedback.append(String.format("\n"));
			}
		}
		
		// if user is victorious (AI's grid - user = false) - display AI full grid
		if (!user && mode.equals("Victory"))
		{
			header = new StringBuilder();
			header.append("\nAI's Grid:\n");
			feedback.append("\n\n   ");
			for (int k = 0; k < gridSize; k++)
				feedback.append(String.format("%6s", k));
			feedback.append(String.format("\n"));
			for (int i = 0; i < gridSize; i++)
			{
				feedback.append(String.format("%5s",i));
				for (int j = 0; j < gridSize; j++)				
					feedback.append("      "+grid[j][i] + " ");				
				feedback.append(String.format("\n"));
			}
		}	
	}
	
	/**
	 * Fill each space in the grid with an . for available
	 */
	public void fillGrid()
	{
		// if player's grid
		if (user)
		{
			for (char[] row : this.grid)
				Arrays.fill(row, '.');
		}
		// if AI's grid
		else
		{
			// fill own grid
			for (char[] row : this.grid)
				Arrays.fill(row, '.');
			// fill template grid
			for (char[] row : this.g_template.grid)
				Arrays.fill(row, '.');
		}	
	}
	
	public String getGrid() { return feedback.toString(); }
	public String getHeader() { return header.toString(); }
	
}
