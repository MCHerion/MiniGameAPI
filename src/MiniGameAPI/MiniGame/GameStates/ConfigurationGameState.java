package MiniGameAPI.MiniGame.GameStates;

import org.bukkit.event.EventHandler;

import MiniGameAPI.CustomEvents.GameStateActivatedEvent;
import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;
import net.md_5.bungee.api.ChatColor;

public abstract class ConfigurationGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public ConfigurationGameState(MG miniGame)
	{
		super(miniGame, "Configuration");
	}
	
	@EventHandler
	public void onGameStateActivated(GameStateActivatedEvent event)
	{
		startNextGameStateCountDown();
	}

	@Override
	public boolean canJoin() 
	{
		return true;
	}
	
	@Override
	public boolean isSkippable()
	{
		return true;
	}
}
