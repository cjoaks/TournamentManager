package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamDisplay extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private JComboBox<Integer> scoreSelector;
	private ArrayList<JLabel> playerLabels;
	
	public TeamDisplay(Team team) 
	{
		// Toggle layout.
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		Font labelFont = new Font("Arial", Font.BOLD, 20); 	// Create font of team and score. 
		// Create score label.
		JLabel scoreLabel = new JLabel("Score: ");
		scoreLabel.setFont(labelFont);
		// Toggle layout constraints for label.  
		gbc.gridy = 0;
		gbc.gridx = 0;
		this.add(scoreLabel, gbc); 
		// Create score selector.
		scoreSelector = new JComboBox<Integer>(); 
		for(int i = 0; i <= 40; i++)
		{
			scoreSelector.addItem(i);
		}
		gbc.gridx = 1; 
		// Action listener for when the score is selected.
		scoreSelector.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				team.setScore((Integer)scoreSelector.getSelectedItem());		// Set team score.
			}
		});
		this.add(scoreSelector, gbc);
		// Create players label.
		JLabel playersLabel = new JLabel("Players");
		playersLabel.setFont(labelFont);
		gbc.gridx = 0; 
		gbc.gridy = 1;
		gbc.gridwidth = 2; 
		this.add(playersLabel, gbc);
		// Create list to store player labels.
		playerLabels = new ArrayList<JLabel>(); 
		// Tab attributes. 
		Dimension size = new Dimension(200, 225); 
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setBackground(new Color(255, 255, 102));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * @param name - The name of the player to be added to the display.
	 */
	public void add(String name) 
	{
		JLabel playerLabel = new JLabel(name);
		playerLabels.add(playerLabel);
		gbc.gridy++; 
		this.add(playerLabel, gbc); 
	}
	
	/**
	 * @param name - The name of the player to be removed from the display.
	 */
	public void remove(String name)
	{
		JLabel toBeRemoved = null;	// Create place to store label we want to remove.
		// Find the label we want to remove.
		for(JLabel label : playerLabels)
		{
			if(label.getText().equals(name)) {
				toBeRemoved = label;
				break; 
			}
		}
		if(toBeRemoved != null) {		// Make sure a label was found. 
			playerLabels.remove(toBeRemoved);	// Remove label from list.
			this.remove(toBeRemoved);		// Remove label from display.
			// Reset display.
			this.revalidate();
			this.repaint();
		}
	}
}
