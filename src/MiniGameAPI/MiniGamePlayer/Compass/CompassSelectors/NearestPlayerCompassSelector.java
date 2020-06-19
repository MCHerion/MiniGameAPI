package MiniGameAPI.MiniGamePlayer.Compass.CompassSelectors;

import java.util.ArrayList;
import org.bukkit.entity.Player;

import MiniGameAPI.MiniGamePlayer.Compass.CompassSelector;
import MiniGameAPI.MiniGamePlayer.Compass.CompassTarget;
import MiniGameAPI.Utils.LocationUtils;

/**
 * Class that represents a CompassSelector that'll automatically select the nearest player in a specified list of players using a player location
 * 
 * @author Elytes
 */
public abstract class NearestPlayerCompassSelector extends CompassSelector
{
	/**
	 * Method used to get players that'll be able to get targeted by this CompassSelector
	 * 
	 * @return List of Players that'll be able to get targeted
	 */
	public abstract ArrayList<Player> getInvolvedPlayers();

	@Override
	public CompassTarget getTarget(Player player) 
	{
		ArrayList<Player> involvedPlayers = new ArrayList<Player>();
		involvedPlayers.addAll(getInvolvedPlayers());
		// If involvedPlayers isn't empty
		if(!involvedPlayers.isEmpty())
		{
			Player nearestPlayer = null;
			// Variable that'll be used to store the distance between the player and the latest nearest player
			int distanceToNearest = 0;
			// For each player in players that can get targeted
			for(Player otherPlayer : getInvolvedPlayers())
			{
				// If nearest player is null or distance between player and otherPlayer is over the distance between the player and the latest nearest player
				if(nearestPlayer == null || LocationUtils.get2DDistance(player.getLocation(), otherPlayer.getLocation()) > distanceToNearest)
				{
					// Changing the nearestPlayer
					nearestPlayer = otherPlayer;
					// Calculating the distance between the player and the nearest player
					distanceToNearest = LocationUtils.get2DDistance(player.getLocation(), nearestPlayer.getLocation());
				}
			}
			// Returning a CompassTarget that'll use the nearestPlayer location as Location and the nearestPlayer's name as name
			return new CompassTarget(nearestPlayer.getLocation(), nearestPlayer.getName());
		}
		// Else if involvedPlayers is empty
		else
		{
			// Returning null
			return null;
		}
	}
}
