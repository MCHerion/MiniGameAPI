package MiniGameAPI.GUI.FieldCases;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.GUI.FieldCase;
import MiniGameAPI.GUI.GameFlagBuilder;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Categorie;
import net.md_5.bungee.api.ChatColor;

public class DoubleFieldCase extends FieldCase
{
	public DoubleFieldCase(GameFlagBuilder pere, MiniGamePlayer<?> MiniGamePlayer, Field field, GameFlagInfos gameFlagInfos) 
	{
		super(pere, MiniGamePlayer, field, Material.GOLD_INGOT, gameFlagInfos);
		_player.sendMessage(ChatColor.YELLOW + "Veuillez saisir un nombre");
	}

	@Override
	public void setObject(Player player, String message)
	{
		try
		{
			_object = Double.parseDouble(message);
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "Veuillez saisir un nombre (exemple :  10.15)");
		}
	}
}
