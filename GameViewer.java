import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class GameViewer extends JFrame implements ActionListener
{
	
	// GUI components
	private JComboBox<Object> shipCombo, xCoordCombo, yCoordCombo, directionCombo, xAttackCombo, yAttackCombo;
	public JTextArea console, header;
	public JButton bConfirm, bAttackConfirm;
	private String ship, direction;
	private int xc, yc, xAttack, yAttack;
	public JPanel topTop, topBottom;
	
	public boolean placed = false;
	public HashMap<String, Integer> fleet;
	
	private boolean hit = false;
	private Attack a1;
	private Attack a2;
	
	Listener listener = new Listener(); // for JComboBox
	
	
	
	Grid g1, g2;
	public GameViewer(Grid g1, Grid g2)
	{
		this.g1 = g1;
		this.g2 = g2;
		
		a1 = new Attack(g2); // player 1
		a2 = new Attack(g1); // AI
		
		setLayout(new BorderLayout(10, 10));
		setTitle("Battleships");
		setSize(640, 360);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setup();
		pack();
		setVisible(true);
	}
	
	public void setup()
	{
		// one panel for top area - divided in two
		// first panel of two = drop-down boxes for user to pick ships to add + text label + confirm button
		// second panel of two = attack coordinates + text label + confirm button
		JPanel top = new JPanel(); 
		GridLayout gridTop = new GridLayout(2, 1);
		top.setLayout(gridTop); add("North", top);
		
		// set layouts of top panels and add
		topTop = new JPanel(); // first panel
		GridLayout gridtTop = new GridLayout(1, 6);
		topTop.setLayout(gridtTop); top.add(topTop);
		topBottom = new JPanel();
		GridLayout gridtBottom = new GridLayout(1,3);
		topBottom.setLayout(gridtBottom); top.add(topBottom);
		
		// create labels and add to panels
		JLabel phase1 = new JLabel("Placement Phase   ");
		JLabel phase2 = new JLabel("Attack Phase   ");
		topTop.add(phase1); topBottom.add(phase2);
		phase1.setToolTipText("In the ship placement phase, the player must place 2x Patrol Boat,"
				+ " 2x Battleship, 1x Submarine, 1x Destroyer and 1x Carrier. Ships cannot overlap"
				+ " each other.");
		phase2.setToolTipText("In each attack phase, the player chooses a location to fire at."
				+ " If the shot hits, another shot can be taken.");
		
		/* ----- top panel ----- */
		
		// create and populate combo boxes
		shipCombo = new JComboBox<>(); populateShipCombo();
		xCoordCombo = new JComboBox<>(); populateCoordCombo("xCoordCombo");
		yCoordCombo = new JComboBox<>(); populateCoordCombo("yCoordCombo");
		directionCombo = new JComboBox<>(); populateDirectionCombo();
		shipCombo.setToolTipText("Choose a ship to place on the board.");
		xCoordCombo.setToolTipText("Choose an x-coordinate");
		yCoordCombo.setToolTipText("Choose a y-coordinate");
		directionCombo.setToolTipText("Choose a direction");
		
		// add combo boxes to panel
		topTop.add(shipCombo); topTop.add(xCoordCombo);
		topTop.add(yCoordCombo); topTop.add(directionCombo);
		
		// create and add confirm button
		bConfirm = new JButton("Deploy");
		topTop.add(bConfirm);
		
		topTop.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		/* ----- bottom panel ----- */
		
		// create and populate combo boxes
		xAttackCombo = new JComboBox<>(); populateCoordCombo("xAttackCombo");
		yAttackCombo = new JComboBox<>(); populateCoordCombo("yAttackCombo");
		xAttackCombo.setToolTipText("Choose an x-coordinate");
		yAttackCombo.setToolTipText("Choose a y-coordinate");
		
		// add combo boxes to panel
		topBottom.add(xAttackCombo); topBottom.add(yAttackCombo);
		
		// create and add confirm button
		bAttackConfirm = new JButton("Fire!");
		topBottom.add(bAttackConfirm);
		
		topBottom.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		
		JPanel bottom = new JPanel();
		add("Center", bottom);
		header = new JTextArea(3, 60);
		console = new JTextArea(40, 60);
		console.setMargin(new Insets(10, 2, 2, 2));
		JScrollPane dataPane = new JScrollPane(console);
		dataPane.setColumnHeaderView(header);
		bottom.add(dataPane);
		
		/*------ add action/item listeners -----*/
		xCoordCombo.addItemListener(listener);
		yCoordCombo.addItemListener(listener);
		xAttackCombo.addItemListener(listener);
		yAttackCombo.addItemListener(listener);
		shipCombo.addItemListener(listener);
		directionCombo.addItemListener(listener);
		bConfirm.addActionListener(this);
		bAttackConfirm.addActionListener(this);
		
		bAttackConfirm.setEnabled(false); // cannot attack before placing ships
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == bConfirm)
		{
			clear(); // clear the feedback and header areas
			if(!g1.addShip(ship, xc, yc, direction)) // if ship not successfully placed then display error message
			{
				clear();
				// will display out-of-bounds or space-already-occupied message and number of ships left to deploy
				console.setText(g1.getGrid() + "\n" + displayFleet());
			}			
			else // if ship successfully placed
			{
				// get quantity of ship left to place
				int quantity = ((int) fleet.get(ship).intValue());
				if (quantity == 1) // if last ship
				{
					clear();
					// remove ship from fleet and combo box
					fleet.remove(ship);
					shipCombo.removeItem(ship);
					
					// will display "current grid" (user's playing board)
					header.setText(g1.getHeader());
					// and user's grid and fleets left to deploy
					console.setText(g1.getGrid() + "\n" + displayFleet());
					setShipChoice();
				}
				// if first placement of patrol boat / battleship
				else
				{
					clear();
					// decrement quantity
					fleet.put(ship, new Integer(quantity-1));
					// display "current grid" and current grid + ships left
					header.setText(g1.getHeader());
					console.setText(g1.getGrid() + "\n" + displayFleet());
					setShipChoice();
				}
			}	
			
			// once fleet has been deployed
			if (fleet.isEmpty())
			{
				placed = true;
				bConfirm.setEnabled(false); // disable deployment confirm button
				header.setText("\nAttack!\n");
				console.setText("");
				bAttackConfirm.setEnabled(true); // enable fire button
				g1.shipAttack = true;
			}		
		}
		
		// if attack phase
		if (event.getSource() == bAttackConfirm)
		{
			// if shot hits
			if(a1.fire(xAttack, yAttack, console, header))
			{
				g1.shipAttack = true; // player 1 gets another turn
			}
			else
			{
				bAttackConfirm.setEnabled(false);
				g1.shipAttack = false; // player 1's turn is over
				g2.shipAttack = true; // AI's turn begins
			}
			
			int xCoAI = 0; int yCoAI = 0;
			int prevX = 0; int prevY = 0; // previous coordinates for AI
			int prevXCopy = 0; int prevYCopy = 0; // copy of previous coordinates for AI
			
			// while AI is attacking
			while(g2.shipAttack)
			{
				console.append("\nAI!\n\n");
				Random generator = new Random();

				// if last shot landed
				if (hit)
				{
					boolean valid = false;
					// array of cardinal directions
					String[] directions = {"N", "S", "E", "W"};

					// repeat until a valid direction is chosen
					while (!valid)
					{
						// pick a direction at random and increment or decrement x or y-coordinate accordingly
						String d = directions[generator.nextInt(4)];
						switch (d)
						{
							case ("N"):
								prevXCopy--;
								break;
							case ("S"):
								prevXCopy++;
								break;
							case ("E"):
								prevYCopy++;
								break;
							case ("W"):
								prevYCopy--;
								break;
						}
						// if the choice is valid, update prevX and prevY to new choice
						if (g2.inBounds(prevXCopy) && g2.inBounds(prevYCopy))
						{
							if (g2.available(prevXCopy, prevYCopy))
							{
								prevX = prevXCopy;
								prevY = prevYCopy;
								xCoAI = prevXCopy;
								yCoAI = prevYCopy;
								valid = true;
							}
							// if choice is not valid, revert prevXCopy and prevYCopy to previous
							else
							{
								prevXCopy = prevX;
								prevYCopy = prevY;
							}
						}
					}


				}
				// if previous shot did not land, randomly pick coordinates
				else
				{
					do
					{
						xCoAI = generator.nextInt(12);
						yCoAI = generator.nextInt(12);
					}
					while(!g2.available(xCoAI, yCoAI));
					prevX = xCoAI; prevY = yCoAI; // save last coordinates
					prevXCopy = xCoAI; prevYCopy = yCoAI; // save last coordinates (copy)
				}



				if(a2.fire(xCoAI, yCoAI, console, header))
				{
					g2.shipAttack = true; // AI gets another turn
					hit = true;
				}
				else
				{
					hit = false;
					g2.shipAttack = false; // AI's turn is over
					g1.shipAttack = true; // player 1's turn begins
					bAttackConfirm.setEnabled(true);
					g1.feedback.delete(0, g1.feedback.length());
					g1.header.delete(0, g1.header.length());
					g2.feedback.delete(0, g2.feedback.length());
					g2.header.delete(0, g2.header.length());
				}
					}
		}

		if (!g1.gameOver && !g2.gameOver)
		{
			g2.printGrid("Victory");
		}
	}

	

	// set initial choices after populating combo boxes
	private void setShipChoice() { ship = (String) shipCombo.getSelectedItem(); }
	private void setXCChoice() { xc = (int) xCoordCombo.getSelectedItem(); }
	private void setYCChoice() { yc = (int) yCoordCombo.getSelectedItem(); }
	private void setDirectionChoice() { direction = (String) directionCombo.getSelectedItem(); }
	private void setXAttackChoice() { xAttack = (int) xAttackCombo.getSelectedItem(); }
	private void setYAttackChoice() { yAttack = (int) yAttackCombo.getSelectedItem(); }
	
	private void populateShipCombo()
	{
		shipCombo.addItem("Patrol Boat");
		shipCombo.addItem("Battleship");
		shipCombo.addItem("Submarine");
		shipCombo.addItem("Destroyer");
		shipCombo.addItem("Carrier");
		setShipChoice();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateCoordCombo(String name)
	{
		JComboBox jcb = null;
		switch (name)
		{
			case ("xCoordCombo"):
				jcb = xCoordCombo;
				break;
			case ("yCoordCombo"):
				jcb = yCoordCombo;
				break;
			case ("xAttackCombo"):
				jcb = xAttackCombo;
				break;
			case ("yAttackCombo"):
				jcb = yAttackCombo;
				break;
		}
		
		jcb.addItem(0); jcb.addItem(1);
		jcb.addItem(2); jcb.addItem(3);
		jcb.addItem(4); jcb.addItem(5);
		jcb.addItem(6); jcb.addItem(7);
		jcb.addItem(8); jcb.addItem(9);
		jcb.addItem(10); jcb.addItem(11);
		
		switch (name)
		{
			case ("xCoordCombo"):
				setXCChoice();
				break;
			case ("yCoordCombo"):
				setYCChoice();
				break;
			case ("xAttackCombo"):
				setXAttackChoice();
				break;
			case ("yAttackCombo"):
				setYAttackChoice();
				break;
		}
	}
	
	private void populateDirectionCombo()
	{
		directionCombo.addItem("North");
		directionCombo.addItem("South");
		directionCombo.addItem("East");
		directionCombo.addItem("West");	
		setDirectionChoice();
	}
	
	public void clear()
	{
		header.setText("");
		console.setText("");
	}

	/**
	 * Inner class for ItemListener
	 */
	class Listener implements ItemListener
	{
		/**
		 * Reset choice when JComboBox is changed
		 */
		public void itemStateChanged(ItemEvent event)
		{
			if (shipCombo.hasFocus())
				ship = (String) shipCombo.getSelectedItem();
			if (xCoordCombo.hasFocus())
				xc = (int) xCoordCombo.getSelectedItem();
			if (yCoordCombo.hasFocus())
				yc = (int) yCoordCombo.getSelectedItem();
			if (directionCombo.hasFocus())
				direction = (String) directionCombo.getSelectedItem();
			if (xAttackCombo.hasFocus())
				xAttack = (int) xAttackCombo.getSelectedItem();
			if (yAttackCombo.hasFocus())
				yAttack = (int) yAttackCombo.getSelectedItem();
		}
	}

	// method to display the remaining fleet to be deployed
		@SuppressWarnings("rawtypes")
		public String displayFleet()
		{
			StringBuilder sb = new StringBuilder();
			// get a set of the entries
			Set set = fleet.entrySet();

			// get an iterator
			Iterator i = set.iterator();

			// display elements
			sb.append("\nRemaining fleet to be deployed:");
			while(i.hasNext()) {
				Map.Entry me = (Map.Entry)i.next();
				sb.append("\n"+me.getKey() + ": ");
				sb.append(me.getValue());
			}
			sb.append("\n");
			
			return sb.toString();
		}
	
}
