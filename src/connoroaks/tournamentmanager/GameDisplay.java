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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameDisplay extends JPanel 
{
	private static final long serialVersionUID = 1L;

	public GameDisplay(Game game)
	{
		// Set layout for the display.
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(); 
		// Create label for the court. 
		JLabel courtLabel = new JLabel("Court " + game.getCourtNumber());
		courtLabel.setFont(new Font("Arial", Font.BOLD, 30));
		// Toggle layout constraints for label. 
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 0;
		gbc.gridy = 0; 
		gbc.gridwidth = 3;
		this.add(courtLabel, gbc);					// Add label to frame.
		// Add team1 display to the game display.
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(game.getTeam1().getDisplay(), gbc);
		// Add gap between the team display and the submit button. 
		gbc.gridx = 1; 
		this.add(Box.createHorizontalStrut(20), gbc);
		// Create submit button. 
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				game.play();
			}
		});
		// Add submit button to display. 
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(submitButton, gbc);
		gbc.anchor = GridBagConstraints.NORTH;
		// Add gap between the button and the team display.   
		gbc.gridx = 3;
		this.add(Box.createHorizontalStrut(20), gbc); 
		// Add team2 display to the game display.
		gbc.gridx = 4; 
		this.add(game.getTeam2().getDisplay(), gbc);
		// Add space at the bottom.
		gbc.gridy = 2;
		this.add(Box.createVerticalStrut(15), gbc);
		// Panel attributes. 
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		// Toggle size. 
		Dimension size = new Dimension(930, 300);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
	}
}
