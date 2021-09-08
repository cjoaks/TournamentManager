
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
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class RoundManager extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private PlayerManager playerManager;
	private Integer playersPerTeam, numRounds;
	private JComboBox<Integer> playerSelector, roundSelector; 
	private JButton regenerateButton; 
	private JTabbedPane roundsDisplay;
	
	public RoundManager()
	{
		// Initialize fields
		playerManager = TournamentManager.getPlayerManager(); 
		playersPerTeam = 0;
		numRounds = 0;
		// Initialize rounds display.
		roundsDisplay = new JTabbedPane();
		roundsDisplay.setPreferredSize(new Dimension(950, 530));	// Set the preferred size of the rounds display.
		this.add(roundsDisplay);	// Add rounds display to tab.
		// Initialize selectors.
		playerSelector = new JComboBox<Integer>();
		roundSelector = new JComboBox<Integer>();
		for(int i = 2; i < 10; i++) 
		{
			playerSelector.addItem(i);
			roundSelector.addItem(i);
		}
		regenerateButton = new JButton("Generate new"); 
		// Add listener for when a round number is selected.
		regenerateButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				generateNew();
			}
		});
		
		// Add selectors to tab.
		this.add(new JLabel("Number of players per team: "));
		this.add(playerSelector);
		this.add(new JLabel("Number of rounds: "));
		this.add(roundSelector); 
		this.add(regenerateButton);
		// Tab attributes.
		this.setPreferredSize(new Dimension(1000, 630));
	}
	
	public void generateNew()
	{ 
		playersPerTeam = (Integer)playerSelector.getSelectedItem(); 	// Get selected number of players per team.
		numRounds = (Integer)roundSelector.getSelectedItem(); 	// Get selected number of rounds.
		if(Math.round(playerManager.getPlayerCount()) / playersPerTeam >= 2) 
		{
			roundsDisplay.removeAll();  	// Remove all current rounds from the display. 
			for(int i = 0; i < numRounds; i++) 
			{ 
				RoundDisplay round = generateRound(); 
				// Create scroll pane for teams display.
				JScrollPane scrollPane = new JScrollPane(
						round, 
						JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane.getVerticalScrollBar().setUnitIncrement(10); 	// Increase scroll speed.
				// Add round display to the rounds display panel.
				roundsDisplay.addTab("Round " + (i + 1), scrollPane);
			}
			roundsDisplay.revalidate();
			roundsDisplay.repaint();
		}
		else {
			// Tell the user there was an error. 
			TournamentManager.error("Not enough players registered for selected number of courts.", "Round Manager");
		}
	}
	
	/**
	 * @return A list of new games.
	 */
	public RoundDisplay generateRound()
	{
		ArrayList<Team> teams = generateTeams(); 		// New teams.
		int courtNumber = 0; 							// Court number.
		ArrayList<Game> games = new ArrayList<Game>(); 	// Place to store games.
		// Create game objects from the generated teams. 
		for(int i = 0; i < teams.size(); i += 2)
		{
			games.add(new Game(teams.get(i), teams.get(i + 1), ++courtNumber)); 
		}
		return new RoundDisplay(games); 	// Return a round object made from the game objects. 
	}
	
	/**
	 * @return A list of new teams.
	 */
	public ArrayList<Team> generateTeams() 
	{
		ArrayList<Player> allPlayers = TournamentManager.getPlayerManager().getPlayers(); 	// List of all players.
		ArrayList<Player> boys = new ArrayList<Player>();		// List of male players.
		ArrayList<Player> girls = new ArrayList<Player>();		// List of female players.
		// Sort players based on gender.
		for(Player p : allPlayers)
		{
			if(p.getGender() == 'M') 
				boys.add(p);
			else 
				girls.add(p); 
		}
		// Randomize players.
		Collections.shuffle(boys);
		Collections.shuffle(girls);
		ArrayList<Team> teams = new ArrayList<Team>(); 	// Create list to store new teams.
		// Create new teams. 
		while(!(boys.isEmpty() && girls.isEmpty()))
		{
			Team team = new Team();		// Create a new team.
			// Add players to the team.
			for(int i = 0; i < playersPerTeam; i++)
			{
				if(!boys.isEmpty()) {
					team.add(boys.remove(0));
				}
				else if(!girls.isEmpty()) {
					team.add(girls.remove(0));
				}
			}
			teams.add(team);		// Add the new team to the list.
		}
		int lastTeamIndex = teams.size() - 1; 	// Get the index of the last team created.
		// Check that there are an even number of teams and redistribute players as needed.
		if(teams.size() % 2 != 0)
		{
			ArrayList<Player> leftoverPlayers = teams.remove(lastTeamIndex).getPlayers(); // Get a list of leftover players.
			int teamIndex = 0;		// Index in the team list.
			// Redistribute leftover players.
			while(!leftoverPlayers.isEmpty())
			{
				teams.get(teamIndex++).add(leftoverPlayers.remove(0));
				if(teamIndex >= teams.size()) {
					teamIndex = 0;
				}
			}
		}
		else if(teams.get(lastTeamIndex).getPlayers().size() < playersPerTeam - 1)	// Make sure player distribution isn't too uneven.
		{
			Team smallTeam = teams.get(lastTeamIndex);	// Get the team with too few players. 
			Team largeTeam = teams.get(lastTeamIndex - 1);	// Get the team with too many players.
			// Redistribute players evenly.
			while(largeTeam.size() - smallTeam.size() > 1) {
				smallTeam.add(largeTeam.remove());
			}
		}
		return teams;
	}
	
	/**
	 * Save current round and game data to a text file.
	 * @param rounds - A list of rounds to be saved.
	 */
	public void writeRoundData(ArrayList<RoundDisplay> rounds)
	{
		try(PrintWriter writer = new PrintWriter(new FileWriter("game_data.txt", false)))
		{
			// Iterate through current rounds.  
			for(RoundDisplay round : rounds)
			{
				// Iterate through each game in a given round. 
				for(Game game : round.getGames())
				{
					for(Player player : game.getTeam1().getPlayers())
					{
						writer.append(player.getName() + System.getProperty("line.separator")); 
					}
				}
			}
			writer.append(";");
		} 
		catch (IOException e) 
		{
			TournamentManager.error("Error saving round data.", "Round Manager");
		}
	}
}
