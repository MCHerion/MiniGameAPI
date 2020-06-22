package MiniGameAPI.MiniGame;

/**
 * Enum used to get how players will have a team
 * 
 * @author Elytes
 *
 */
public enum TeamSelector 
{
	/**
	 * Players won't have any team
	 */
	NO_TEAM,
	/**
	 * Player will be automatically put in a random team between available teams, but will be able to join a team that isn't full
	 */
	CHOICE,
	/**
	 * Player will be automatically put in a random team and won't be able to change it
	 */
	FORCED
}
