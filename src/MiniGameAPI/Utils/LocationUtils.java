package MiniGameAPI.Utils;

import org.bukkit.Location;

public class LocationUtils 
{
	public static int get2DDistance(Location position1, Location position2)
	{
		return (int) Math.sqrt(Math.pow(position1.getBlockX() - position2.getBlockX(), 2) + Math.pow(position1.getBlockZ(), position2.getBlockZ()));
	}
}
