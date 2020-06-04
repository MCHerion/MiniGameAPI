package MiniGameAPI.Player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface CustomPlayer
{
	public String getName();
	
	public default Player getPlayer()
	{
		return Bukkit.getPlayer(getName());
	}
}
