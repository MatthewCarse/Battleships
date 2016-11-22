
public class Destroyer extends Ship

{
	public Destroyer () {}

	public int getXLength(String direction) 
	{
		switch (direction)
		{
		case ("South"):
			return 1;
		case ("North"):
			return -1;
		case ("West"):
			return -4;
		case ("East"):
			return 4;
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
			return 4;
		case ("North"):
			return -4;
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

