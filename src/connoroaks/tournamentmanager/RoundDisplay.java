package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.Box;
import javax.swing.JPanel;

public class RoundDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Game> games;
	public RoundDisplay(ArrayList<Game> games) 
	{
		this.games = games; 
		// Toggle layout.
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(); 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL; 
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		// Add game displays. 
		for(Game game : games) 
		{
			this.add(Box.createVerticalStrut(20), gbc); 
			gbc.gridy++;
			this.add(game.getDisplay(), gbc);
			gbc.gridy++; 
		}
		// Add filler panel.
		JPanel filler = new JPanel();
		filler.setOpaque(false);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0; 
		this.add(filler, gbc); 
		this.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * @return a list of games in the round being displayed.
	 */
	public ArrayList<Game> getGames() 
	{
		return games;
	}
}
