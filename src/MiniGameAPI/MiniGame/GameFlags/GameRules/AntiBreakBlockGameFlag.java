package MiniGameAPI.MiniGame.GameFlags.GameRules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;

@GameFlagInfos
(
	name = "Interdire de casser des blocs",
	description = { "Permet d'interdire à tous les joueurs de détruire des blocs" }
)
public class AntiBreakBlockGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public AntiBreakBlockGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(getMiniGame().getWorld() == event.getBlock().getWorld())
		{
			event.setCancelled(true);
		}
	}
}
