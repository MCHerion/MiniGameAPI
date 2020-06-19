package MiniGameAPI.MiniGame.GameFlags.GameRules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;

@GameFlagInfos
(
	name = "Interdire de placer des blocs",
	description = { "Permet d'interdire à tous les joueurs de placer des blocs" }
)
public class AntiPlaceBlockGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public AntiPlaceBlockGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(getMiniGame().getWorld() == event.getBlock().getWorld())
		{
			event.setCancelled(true);
		}
	}
}
