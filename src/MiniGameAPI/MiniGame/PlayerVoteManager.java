package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

/**
 * Class that represents a vote manager, a player can vote for another player and can change his vote but can't vote multiple players at the same time
 * 
 * @author Elytes
 *
 * @param <P> Generic Player type
 */
public class PlayerVoteManager<P extends MiniGamePlayer<?>>
{
	/**
	 * Variable used to store players' votes
	 * Key is the player that is voting for
	 * Value is the player that is getting voted by the Key
	 */
	protected HashMap<P, P> _votes = new HashMap<P, P>();
	
	/**
	 * Method used to get which player is getting voted by the specified player
	 * 
	 * @param voter Player we want to get the player he voted for
	 * @return Player that have been voted by specified player
	 */
	public P getVoted(P voter)
	{
		for(P voted : _votes.values())
		{
			if(_votes.get(voter) == voted)
			{
				return voted;
			}
		}
		return null;
	}
	
	/**
	 * Method used to vote for a specific player
	 * 
	 * @param player Player that is voting
	 * @param target Player that is getting voted
	 */
	public void vote(P player, P target)
	{
		_votes.put(player, target);
	}
	
	/**
	 * Method used to get amount of votes on a specific player
	 * 
	 * @param target Player we want to get amount of votes
	 * @return Amount of votes against the specified player
	 */
	public int getVotes(P target)
	{
		int amount = 0;
		for(P loupGarouPlayer : _votes.keySet())
		{
			if(_votes.get(loupGarouPlayer) == target)
			{
				++amount;
			}
		}
		return amount;
	}
	
	/**
	 * Method used to reset every votes of this PlayerVoteManager
	 */
	public void resetVotes()
	{
		_votes.clear();
	}
	
	/**
	 * Method used to get all top voted players
	 * 
	 * @return List of top voted players
	 */
	public ArrayList<P> getTopVotedPlayers()
	{
		ArrayList<P> returnValue = new ArrayList<P>();
		P topPlayer = null;
		for(P player : _votes.keySet())
		{
			if(topPlayer == null || getVotes(player) > getVotes(topPlayer))
			{
				topPlayer = player;
				returnValue.clear();
				returnValue.add(player);
			}
			else if(getVotes(player) == getVotes(topPlayer))
			{
				returnValue.add(player);
			}
		}
		return returnValue;
	}
}
