package MiniGameAPI.GUI.FieldCases;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.GUI.BuilderCategorie;
import MiniGameAPI.GUI.FieldCase;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import net.md_5.bungee.api.ChatColor;

public abstract class IntegerFieldCase extends FieldCase
{
	public IntegerFieldCase(BuilderCategorie pere, MiniGamePlayer<?> MiniGamePlayer, ItemStack item) 
	{
		super(pere, MiniGamePlayer, item);
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
