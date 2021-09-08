package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerDisplay extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private JLabel rank, name, gender, score, record; 
	 
	public PlayerDisplay(String name, char gender) 
	{	
		Font f = new Font("Arial", Font.BOLD, 16);	// Create font for labels. 
		// Create label for rank
		rank = new JLabel();
		rank.setFont(f);
		// Create label for name. 
		this.name = new JLabel(name);
		this.name.setFont(f);
		// Create label for gender.
		this.gender = new JLabel("Gender:  " + gender);
		this.gender.setFont(f);
		// Create label for record.
		record = new JLabel("Record: 0 - 0"); 
		record.setFont(f);
		// Create label for score.
		score = new JLabel();
		score.setFont(f);
		// Set layout and add labels to main display.
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createHorizontalStrut(10));
		this.add(rank);
		this.add(Box.createHorizontalStrut(30));
		this.add(this.name);
		this.add(Box.createHorizontalStrut(60));
		this.add(this.gender);
		this.add(Box.createHorizontalStrut(60));
		this.add(record);
		this.add(Box.createHorizontalStrut(60));
		this.add(score); 
		// Other attributes. 
		this.setPreferredSize(new Dimension(950, 40));
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
	}

	/**
	 * @param rank - The new rank.
	 * @param score - The new score.
	 */
	public void refresh(int rank, int score, int wins, int losses)
	{
		this.rank.setText(Integer.toString(rank));
		record.setText("Record: " + wins + " - " + losses);
		this.score.setText("Score:  " + score);
		
	}
}
