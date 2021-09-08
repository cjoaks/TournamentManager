package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

import java.util.ArrayList;

public class Team 
{
	private ArrayList<Player> players;
	private int score;
	private TeamDisplay display;
	
	public Team() {
		players = new ArrayList<Player>();  
		score = 0; 
		display = new TeamDisplay(this);
	}

	/**
	 * @param p - The player to be added to the team. 
	 */
	public void add(Player p)
	{
		players.add(p); 
		display.add(p.getName());
	}
	
	/**
	 * @return The player to be removed from the team. 
	 */
	public Player remove()
	{
		Player p = players.remove(0);
		display.remove(p.getName());
		return p;
	}
	
	/**
	 * @return The list of players on the team.
	 */
	public ArrayList<Player> getPlayers()
	{
		return players; 
	}
	
	/**
	 * @return The number of players on the team. 
	 */
	public int size()
	{
		return players.size(); 
	}
	
	/**
	 * @return The team's score.
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Increment the teams wins when the team wins.
	 */
	public void setScore(int score)
	{
		this.score = score;
	}
	
	
	/**
	 * Add the wins of all players on the team.
	 */
	public void win()
	{
		for(Player player : players) {
			player.win();		
		}
	}
	
	/**
	 * Add to the losses of all players of the team.
	 */
	public void lose()
	{
		for(Player player : players) {
			player.lose();			
		}
	}

	/**
	 * Add the differential to all players scores.
	 */
	public void addDifferential(int diff)
	{
		for(Player player : players) {
			player.addDifferential(diff);
		}
	}
	
	/**
	 * Reset the score to 0.
	 */
	public void reset()
	{
		score = 0; 
	}
	
	/** 
	 * @return The teams display 
	 */
	public TeamDisplay getDisplay()
	{
		return display;
	}
}
