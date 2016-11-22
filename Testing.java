import java.util.*;


public class Testing 
{
	// task 1
	private void printLines()
	{
		for (int i = 1; i < 8; i++)
		{
			for (int j = 0; j < i; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}	
	}
	
	// task 2
	public void getInput()
	{
		int lines;
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a number:");
		lines = Integer.parseInt(scan.next());
		scan.close();
		
		for (int i = 0; i < lines; i++)
			this.printLines();
	}
	
	
	// task 3
	public String rev1(String orig)
	{
		String rev = "";
		for (int i = orig.length()-1; i >= 0; i--)
			rev += orig.charAt(i);
			
		return rev;
	}
	
	// task 4
	public String rev2(String orig)
	{
		String rev = "";
		ArrayList<Character> cList = new ArrayList<Character>();
		for (char c : orig.toCharArray())
			cList.add(c);
		Collections.reverse(cList);
		for (char c : cList)
			rev += c;
		return rev;
	}
	
	// task 5
	public void openGarage()
	{
		@SuppressWarnings("unused")
		Garage g = new Garage();
	}
	
	// task 6
	public void startPaintWiz()
	{
		@SuppressWarnings("unused")
		PaintWiz pw = new PaintWiz();
	}
	
	// task 7
	public void startPrimeFind()
	{
		@SuppressWarnings("unused")
		Prime p = new Prime();
	}
	
	// task 9
	public void startStrings()
	{
		@SuppressWarnings("unused")
		Strings s = new Strings();
	}
	
	// task 11
	public void openLibrary()
	{
		// add the items to the library
		Library l = new Library();
		l.addItem("Star Wars: The Last Command", "Book", 0012, "Science Fiction", "Hardbound", "9783641078317");
		l.addItem("Star Wars: The Old Republic: Revan", "Book", 1508, "Science Fiction", "Softbound", "9788863551815");
		l.addItem("OS Road 5: East Midlands", "Maps", 854, "East Midlands", "OS");
		l.addItem("Financial Times", "Newspaper", 99, "Broadsheet");
		l.listItems(); // list all the items in the library
		
		// add members to the library
		l.addMember("Max", "Power", 34);
		l.addMember("Book", "Reader", 1);
		l.addMember("Keyser", "Soze", 4);
		l.listMembers(); // list all members
		l.updateMember(1, "id", 2); // change membership id of member #1
		l.listMembers();
		l.delMember(2);
		l.listMembers();
		
		// update the book with id 0012 from hardbound to softbound
		l.updateItem(0012, "Book", "binding", "Softbound");
		// relist the items in the library, showing change
		l.listItems();
		l.checkOut(0012, 4); // member 4 checks out item 0012
		l.listItems();
	}
	
	// task 12
	public void playBattleships()
	{
		Battleships b = new Battleships();
	}
}
