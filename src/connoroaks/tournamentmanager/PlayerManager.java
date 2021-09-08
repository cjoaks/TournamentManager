package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlayerManager extends JPanel
{
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private JPanel listContainer;
	private ArrayList<Player> players;
	
	public PlayerManager() 
	{
		// Initialize players.
		players = new ArrayList<Player>(); 
		// Create container for player list. 
		listContainer = new JPanel(); 
		// Initialize layout for list.
		listContainer.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;  
		gbc.gridwidth = 1;
		gbc.gridx = 0; 
		// Set list area background. 
		listContainer.setBackground(Color.DARK_GRAY);
		// Create scroll area for the list. 
		JScrollPane scrollPane = new JScrollPane(
				listContainer, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);		// Increase scroll speed.
		scrollPane.setPreferredSize(new Dimension(970, 570));	// Set the preferred size of the scroll pane. 
		// Main tab attributes. 
		this.add(scrollPane); 					// Add scroll pane to main panel. 
		this.setPreferredSize(new Dimension(1000, 630));	// Set preferred size for the tab.
		// Create reset button.
		JButton resetButton = new JButton("Reset player rankings"); 
		resetButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for(Player player : players)
				{
					player.reset();
				}
				refreshDisplay(); 
			}
		});
		this.add(resetButton); 
		
		if(readPlayerData()) {
			refreshDisplay();
		}
	}
	
	/**
	 * @return A list of all registered players. 
	 */
	public ArrayList<Player> getPlayers() 
	{
		return players;
	}

	/**
	 * @return The number of players.
	 */
	public int getPlayerCount()
	{
		return players.size();
	}
	
	/**
	 * Read the saved data for players that have already been registered. 
	 */
	public boolean readPlayerData() 
	{ 
		// Try statement with reader resource. 
		try(BufferedReader reader = new BufferedReader(new FileReader("player_data.txt")))
		{
			String line = reader.readLine();	// Read the first line of the file.
			while(line != null && !line.isEmpty())
			{
				int separator = line.indexOf(','); 		// Get the index of the separator. 
				String name = line.substring(0, separator);	// Read name.
				char gender = line.charAt(++separator);		// Read gender. 
				players.add(new Player(name, gender)); 	// Add new player to the list. 
				line = reader.readLine();				// Read the next line in the file. 
			}
			refreshDisplay();			// Refresh player ranking display. 
			return true;
		}
		catch(IOException ex) 
		{
			TournamentManager.error("Error loading player data.", "Player Manager");		// Tell user there was an error. 
		}
		return false;
	}
	
	/**
	 * @param player - The player to be added to the list.
	 */
	public void addPlayer(Player player)
	{
		players.add(player); 
		refreshDisplay(); 
	}
	
	/** 
	 * Sort the players by record, then by point differential.
	 */
	public void sortPlayers()
	{
		// Sort players based on their records.
		players.sort((player1, player2) -> player1.getRecord().compareTo(player2.getRecord()));
		// Create list of lists of players with the same number of wins. 
		ArrayList<ArrayList<Player>> similarities = new ArrayList<ArrayList<Player>>();
		// Sort players into lists based on their win counts.
		for(int i = players.get(players.size() - 1).getWins(); i >= 0; i--)
		{
			ArrayList<Player> similarWins = new ArrayList<Player>(); 	// Create player list.
			// Add players to list if their win count is equal to i.
			for(Player player : players)
			{
				if(player.getWins() == i)
					similarWins.add(player);
			}
			similarities.add(similarWins); 	// Add player list to list of player lists. 
		}
		// Sort each group of players by their point differentials.
		for(ArrayList<Player> similarWins : similarities)
		{
			similarWins.sort((player1, player2) -> player1.getScore().compareTo(player2.getScore()));
		}
		players.clear();	// Clear current player list.
		// Add players back into the list in the correct order.
		for(ArrayList<Player> similarWins : similarities)
		{
			for(int i = similarWins.size() - 1; i >= 0; i--)
			{
				players.add(similarWins.get(i));
			}
		}
		// Set player rankings.
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).setRank(i + 1);
		}
		refreshDisplay(); 	// Refresh player display.
		TournamentManager.alert("Rankings updated successfully.", "Player Rankings");	// Let user know the rankings have been updated.
	}
	
	/**
	 * Display the players in a list.
	 */
	public void refreshDisplay() 
	{ 
		listContainer.removeAll(); 		// Remove all current displays from the list. 
		// Set grid bag constraints. 
		gbc.gridy = 0; 
		gbc.weighty = 0.0;
		gbc.weightx = 0.0; 
		for(Player player : players)
		{
			listContainer.add(Box.createVerticalStrut(5), gbc);		// Add space between displays.
			gbc.gridy++;
			listContainer.add(player.getDisplay(), gbc);			// Add display to list. 
			gbc.gridy++; 
		}
		// Add filler panel. 
		JPanel filler = new JPanel();
		filler.setOpaque(false);
		gbc.weighty = 1.0;
		gbc.weightx = 1.0; 
		listContainer.add(filler, gbc);
		// Repaint list to show any updates. 
		listContainer.revalidate();
		listContainer.repaint();
	}
}
