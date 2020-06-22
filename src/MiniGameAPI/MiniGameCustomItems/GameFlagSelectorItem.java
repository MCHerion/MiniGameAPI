package MiniGameAPI.MiniGameCustomItems;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import MiniGameAPI.GUI.GameFlagSelector;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.CustomItems.InteractableItem;
import net.md_5.bungee.api.ChatColor;

public class GameFlagSelectorItem extends MiniGameInteractableItem
{
	public GameFlagSelectorItem()
	{
		super
		(
			MiniGameCustomItem.GameFlagSelectorItem.name(),
			Material.PAPER,
			ChatColor.GRAY + "Ajoutez des règles", 
			ChatColor.AQUA + "Clic " + ChatColor.DARK_AQUA + "droit" + ChatColor.AQUA + " pour ajouter des règles"
		);
	}

	@Override
	public void onLeftClick(Player player)
	{
		
	}

	@Override
	public void onRightClick(Player player)
	{
		MiniGamePlayer<?> miniGamePlayer = MiniGamePlayer.getMiniGamePlayer(player);
		miniGamePlayer.getGUI().open(new GameFlagSelector(miniGamePlayer));
	}
}
