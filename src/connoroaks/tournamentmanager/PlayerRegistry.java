package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerRegistry extends JPanel
{
	private static final long serialVersionUID = 1L;
	private PlayerManager playerManager;
	
	public PlayerRegistry()
	{
		playerManager = TournamentManager.getPlayerManager(); 	// Get player manager instance.
		JLabel nameLabel = new JLabel("Name: "); 		// Create label for name box.
		this.add(nameLabel);							// Add name label to the tab.
		JTextField nameBox = new JTextField(); 			// Create name box.
		nameBox.setPreferredSize(new Dimension(100, 25));	// Set box size. 
		this.add(nameBox);								// Add name box to the tab.
		JLabel genderLabel = new JLabel("Gender: "); 	// Create label for gender selector.
		this.add(genderLabel);							// Add gender label to the tab.
		JComboBox<Character> genderSelector = new JComboBox<Character>(); // Create gender selector.
		// Add options to gender selector.
		genderSelector.addItem('M');
		genderSelector.addItem('F');
		genderSelector.addItem('O');
		this.add(genderSelector); 						// Add gender selector to the tab. 
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String name = nameBox.getText(); 	// Get the name of the player.
				Character gender = genderSelector.getItemAt(genderSelector.getSelectedIndex());		// Get the gender of the player.
				if(writePlayerData(name + ',' + gender))	
				{
					playerManager.addPlayer(new Player(name, gender));
					TournamentManager.alert("Player " + name + " registered successfully.", "Player Registry");
				}
			}
			
		});
		this.add(submitButton); 
		// Tab attributes. 
		this.setPreferredSize(new Dimension(1000, 630));	// Set the preferred tab size. 
	}
	
	/**
	 * @param data - The formatted string containing the player's name and gender.
	 */
	public boolean writePlayerData(String data)
	{
		// Open file writer in try statement. 
		try(PrintWriter writer = new PrintWriter(new FileWriter("player_data.txt", true))) 
		{
			writer.print(data + System.getProperty("line.separator"));	// Write line to file.
			return true; 
		}
		catch(IOException ex) 
		{
			TournamentManager.error("Error writing player data.", "Player Registry");	// Tell the user there was an error. 
		}
		return false;
	}
}
