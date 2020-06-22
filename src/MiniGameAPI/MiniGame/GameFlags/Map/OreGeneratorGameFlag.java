package MiniGameAPI.MiniGame.GameFlags.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldInitEvent;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGame.WorldGenerator.OreInfo;
import MiniGameAPI.MiniGame.WorldGenerator.OrePopulator;

@GameFlagInfos
(
	name = "Modifier les minerais",
	description = { "Permet de changer les taux de minerais" }
)
public class OreGeneratorGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	protected OrePopulator _orePopulator = new OrePopulator(new OreInfo(null, 0, 0, 0, 0));
	
	public OreGeneratorGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
	
	@EventHandler
	public void onWorldInit(WorldInitEvent event)
	{
		event.getWorld().getPopulators().add(getOrePopulator());
	}
	
	public OrePopulator getOrePopulator()
	{
		return new OrePopulator();
	}
}
