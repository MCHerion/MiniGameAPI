package MiniGameAPI.MiniGame.Reasons;

import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.Reason;

/**
 * Class that represents a reason for a player to lose
 * 
 * @see {@link MiniGame#lose(Player, LoseReason)}
 * 
 * @author Elytes
 */
public class LoseReason extends Reason
{
	public LoseReason(String reason)
	{
		super(reason);
	}
}