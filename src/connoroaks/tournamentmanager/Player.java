package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

public class Player
{
	private Integer rank, score, wins, losses;
	private final String name;
	private final char gender; 
	private final PlayerDisplay display;
	
	public Player(String name, char gender) 
	{
		// Initialize fields.
		this.rank = 1;
		this.name = name;
		this.gender = gender;
		this.score = 0; 
		this.wins = 0;
		this.losses = 0; 
		this.display = new PlayerDisplay(name, gender);
	}

	/**
	 * @return The current ranking of the player.
	 */
	public int getRank() 
	{
		return rank;
	}
	
	/**
	 * @param rank - The new rank of the player.
	 */
	public void setRank(int rank) 
	{
		this.rank = rank;
	}
	
	/**
	 * @return The number of wins the team currently has.
	 */
	public Integer getWins()
	{
		return wins;
	}
	
	/**
	 * Increment wins.
	 */
	public void win()
	{
		wins++;
	}
	
	/**
	 * @return The number of wins the team currently has.
	 */
	public Integer getLosses() 
	{
		return losses;
	}
	
	/**
	 * @return The win/loss difference. 
	 */
	public Integer getRecord()
	{
		return wins - losses;
	}
	/**
	 * Increment losses.
	 */
	public void lose()
	{
		losses++; 
	}
	
	/**
	 * @param score - The point differential from the last match.
	 */
	public void addDifferential(int diff) 
	{
		this.score += diff;
	}
	
	/**
	 * @return score - The player's current score. 
	 */
	public Integer getScore() 
	{
		return score;
	}
	
	/**
	 * @return the name of the player. 
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * @return the gender of the player.
	 */
	public char getGender() 
	{
		return gender;
	}
	
	/**
	 * @return The GUI display for the player.
	 */
	public PlayerDisplay getDisplay() 
	{
		display.refresh(rank, score, wins, losses);
		return display;
	}
	
	/**
	 * Reset the players record and score.
	 */
	public void reset()
	{
		this.wins = 0;
		this.losses = 0;
		this.score = 0;
		this.rank = 1;
	}
}
