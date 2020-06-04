package MiniGameAPI.CustomPlayer;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerManager
{
	public static HashMap<String, CustomPlayer> _customPlayers = new HashMap<String, CustomPlayer>();
	
	public static CustomPlayer getCustomPlayer(Player player)
	{
		return _customPlayers.get(player.getName());
	}
	
	public static CustomPlayer getCustomPlayer(String playerName)
	{
		return _customPlayers.get(playerName);
	}
	
	public static void registerCustomPlayer(String playerName, CustomPlayer customPlayer)
	{
		_customPlayers.put(playerName, customPlayer);
	}
}
