package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TournamentManager extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static PlayerManager playerManager = new PlayerManager();
	private static RoundManager roundManager = new RoundManager();
	private static PlayerRegistry playerRegistry = new PlayerRegistry(); 
	
	public TournamentManager() {
		// Create tabbed layout for main window. 
		JTabbedPane tabbedLayout = new JTabbedPane();
		// Add tabs to layout.  
		tabbedLayout.addTab("Round Manager", roundManager);
		tabbedLayout.addTab("Player Rankings", playerManager);
		tabbedLayout.addTab("Player Registry", playerRegistry);
		this.add(tabbedLayout);
		// Frame attributes. 
		this.setTitle("Volleyball Tournament Manager by Connor Oaks");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * @return The round manager instance.
	 */
	public static RoundManager getRoundManager() 
	{
		return roundManager;
	}
	
	/**
	 * @return The player manager instance. 
	 */
	public static PlayerManager getPlayerManager() 
	{
		return playerManager;
	}
	
	/**
	 * @param message - The message to be shown to the user. 
	 */
	public static void alert(String message, String caption)
	{
		JOptionPane.showMessageDialog(
				null,
				message,
				caption,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * @param message - The error message to be shown to the user. 
	 */
	public static void error(String message, String caption) 
	{
		JOptionPane.showMessageDialog(
				null,
				message,
				caption, 
				JOptionPane.ERROR_MESSAGE);
	}
}
