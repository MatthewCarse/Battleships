
public class Battleship extends Ship
{
	public Battleship () {}
	
	public int getXLength(String direction) 
	{
		switch (direction)
		{
			case ("South"):
				return 1;
			case ("North"):
				return -1;
			case ("West"):
				return -3;
			case ("East"):
				return 3;
			default:
				System.out.println("Please enter a valid direction");
		}
		return 0;
	}

	public int getYLength(String direction) 
	{
		switch (direction)
		{
			case ("South"):
				return 3;
			case ("North"):
				return -3;
			case ("West"):
				return -1;
			case ("East"):
				return 1;
			default:
				System.out.println("Please enter a valid direction");
		}
		return 0;
	}	
}
