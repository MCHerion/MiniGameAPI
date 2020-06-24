package MiniGameAPI.MiniGame.GameStates;

import org.bukkit.event.EventHandler;

import MiniGameAPI.CustomEvents.GameStateActivatedEvent;
import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public abstract class ConfigurationGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public ConfigurationGameState()
	{
		super("Configuration");
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
