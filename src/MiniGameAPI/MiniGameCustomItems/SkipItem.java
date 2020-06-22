package MiniGameAPI.MiniGameCustomItems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.CustomItems.InteractableItem;
import net.md_5.bungee.api.ChatColor;

public class SkipItem extends MiniGameInteractableItem
{
	public SkipItem()
	{
		super
		(
			MiniGameCustomItem.GameFlagSelectorItem.name(),
			Material.WATCH,
			ChatColor.GRAY + "Passez cette période de jeu", 
			ChatColor.AQUA + "Cliquez pour " + ChatColor.DARK_AQUA + "voter" + ChatColor.AQUA + " pour passer cette période de jeu"
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
		miniGamePlayer.getMiniGame().getGameState().getSkipRequestVoteManager().request(true, player);
	}
}