
public class PatrolBoat extends Ship
{
	public PatrolBoat() {}

	public int getXLength(String direction) 
	{
		switch (direction)
		{
			case ("South"):
				return 1;
			case ("North"):
				return -1;
			case ("West"):
				return -2;
			case ("East"):
				return 2;
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
				return 2;
			case ("North"):
				return -2;
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
