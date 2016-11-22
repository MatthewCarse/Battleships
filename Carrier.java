
public class Carrier extends Ship
{
	public Carrier () {}
	
	public int getXLength(String direction) 
	{
		switch (direction)
		{
			case ("South"):
				return 1;
			case ("North"):
				return -1;
			case ("West"):
				return -5;
			case ("East"):
				return 5;
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
				return 5;
			case ("North"):
				return -5;
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