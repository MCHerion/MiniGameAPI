package MiniGameAPI.MiniGamePlayer.Compass;

import org.bukkit.entity.Player;

/**
 * Class that represents a CompassSelector that will automatically return the desired Location using the defined method {@link #getTarget(Player)}
 * 
 * @author Elytes
 */
public abstract class CompassSelector
{
	/**
	 * Method used to get a CompassTarget using a Player
	 * @param player Player to get target to
	 * @return CompassTarget associated to specified Player
	 */
	public abstract CompassTarget getTarget(Player player);
}
