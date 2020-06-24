package MiniGameAPI.GUI.FieldCases;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.GUI.BuilderCategorie;
import MiniGameAPI.GUI.FieldCase;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import net.md_5.bungee.api.ChatColor;

public abstract class StringFieldCase extends FieldCase
{
	public StringFieldCase(BuilderCategorie pere, MiniGamePlayer<?> MiniGamePlayer, ItemStack item) 
	{
		super(pere, MiniGamePlayer, item);
		_player.sendMessage(ChatColor.YELLOW + "Veuillez saisir du texte");
	}

	@Override
	public void setObject(Player player, String message)
	{
		_object = message;
	}
}
