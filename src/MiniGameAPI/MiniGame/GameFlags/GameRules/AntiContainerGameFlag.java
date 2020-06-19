package MiniGameAPI.MiniGame.GameFlags.GameRules;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;

@GameFlagInfos
(
	name = "Interdire les Containers",
	description = { "Permet d'interdire aux joueurs d'utiliser :", "- Les coffres", "- Les fours", "- Les enderchests" }
)
public class AntiContainerGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public AntiContainerGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(event.getClickedBlock().getWorld() == getMiniGame().getWorld())
			{
				Material type = event.getClickedBlock().getType();
				if(type == Material.CHEST || type == Material.ENDER_CHEST || type == Material.FURNACE || type == Material.BURNING_FURNACE || type == Material.TRAPPED_CHEST)
				{
					event.setCancelled(true);
				}
			}
		}
	}
}