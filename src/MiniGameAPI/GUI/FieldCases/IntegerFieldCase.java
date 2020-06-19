package MiniGameAPI.GUI.FieldCases;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import MiniGameAPI.GUI.FieldCase;
import MiniGameAPI.GUI.GameFlagBuilder;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import net.md_5.bungee.api.ChatColor;

public class IntegerFieldCase extends FieldCase
{
	public IntegerFieldCase(GameFlagBuilder pere, MiniGamePlayer<?> MiniGamePlayer, Field field, GameFlagInfos gameFlagInfos) 
	{
		super(pere, MiniGamePlayer, field, Material.IRON_INGOT, gameFlagInfos);
		_player.sendMessage(ChatColor.YELLOW + "Veuillez saisir un nombre entier");
	}
	
	@Override
	public void setObject(Player player, String message)
	{
		try
		{
			_object = Integer.parseInt(message);
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "Veuillez saisir un nombre entier (exemple : 10)");
		}
	}
}
