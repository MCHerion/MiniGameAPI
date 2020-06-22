package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;

/**
 * Class that represents a RequestManager
 * Players will be able to request for an object of O type, and if a majority of players (over or equals to {@link #getNeededVotes()}) then
 * {@link #onAccept(Object)} will get called to treat the accepted request
 * 
 * @author Elytes
 *
 * @param <O> Object players will vote for
 */
public abstract class RequestManager<O>
{
	/**
	 * Variable used to store requests and amount of votes
	 */
	protected HashMap<O, ArrayList<Player>> _requests = new HashMap<O, ArrayList<Player>>();
	
	/**
	 * Method used to get player list that will be used to get how much players can vote
	 * Like that we will be able to know if amount of votes of a specified request is over or equals to this list's size
	 * 
	 * @return Players that can vote
	 */
	public abstract ArrayList<Player> getInvolvedPlayers();
	
	/**
	 * Method that will get called when a request have been accepted cause a majority of players (over or equals to {@link #getNeededVotes()}) voted for it
	 * 
	 * @param request Request that have been accepted
	 */
	public abstract void onAccept(O request);
	
	/**
	 * Method used to request for a specific object to be accepted
	 * This will add a vote for the specified object if this player haven't already voted for it
	 * 
	 * @param request Object requested to be accepted
	 * @param player Player that request this Object to be accepted
	 * @return true if the vote have been added, false else
	 */
	public boolean request(O request, Player player)
	{
		boolean returnValue = false;
		if(!_requests.containsKey(request))
		{
			_requests.put(request, new ArrayList<Player>());
		}
		if(!_requests.get(request).contains(player))
		{
			_requests.get(request).add(player);
			if(getAcceptedAmount(request) > getNeededVotes())
			{
				onAccept(request);
			}
			returnValue = true;
		}
		return returnValue;
	}
	
	/**
	 * Method used to get amount of votes on a specific request
	 * 
	 * @param request Request to get votes
	 * @return Amount of votes of the specified request
	 */
	public int getAcceptedAmount(O request)
	{
		if(_requests.containsKey(request))
		{
			return _requests.get(request).size();
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * Method used to get the amount of votes needed to accept a request
	 * 
	 * @return Amount of votes needed to accept a request
	 */
	public int getNeededVotes()
	{
		return getInvolvedPlayers().size() / 2;
	}
}
