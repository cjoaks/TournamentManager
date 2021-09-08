package connoroaks.tournamentmanager;

/**
 * @author Connor Oaks
 */

public class Game 
{ 
	private Team team1, team2; 
	private int courtNumber;
	private GameDisplay display;
	
	public Game(Team team1, Team team2, int courtNumber) 
	{
		this.team1 = team1;
		this.team2 = team2;
		this.courtNumber = courtNumber; 
		display = new GameDisplay(this);
	}
	
	/**
	 * @return The first team.
	 */
	public Team getTeam1() 
	{
		return team1;
	}
	
	/**
	 * @return The second team.
	 */
	public Team getTeam2() 
	{
		return team2;
	}
	
	/**
	 * @return The court number.
	 */
	public int getCourtNumber() 
	{
		return courtNumber;
	}

	/**
	 * @return The game display.
	 */
	public GameDisplay getDisplay()
	{
		return display;
	}
	
	/**
	 * @return Factor the scores and point differential into the player rankings.
	 */
	public void play()
	{
		// Get scores.
		int team1Score = team1.getScore();
		int team2Score = team2.getScore();
		// Add to each teams win/loss record.
		if(team1Score > team2Score)
		{
			team1.win();
			team2.lose();
		}
		else {
			team1.lose();
			team2.win();
		}
		// Add point differential. 
		int diff = team1.getScore() - team2.getScore();
		team1.addDifferential(diff);
		team2.addDifferential(diff * -1);
		// Reset team scores and refresh the player display.
		team1.reset();
		team2.reset();
		TournamentManager.getPlayerManager().sortPlayers();
	}
}
