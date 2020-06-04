package MiniGameAPI.MiniGame;

import org.bukkit.Bukkit;

import MiniGameAPI.CustomEvents.GameStateChangedEvent;
import PluginUtils.Flags.FlagWorker;

public interface GameWorker<M extends MiniGame> extends FlagWorker<M, GameStateChangedEvent>
{
	public default void changeState(M subscriber, GameState newGameState)
	{
		subscriber.setGameState(newGameState);
		Bukkit.getPluginManager().callEvent(new GameStateChangedEvent(newGameState));
	}
	
	@Override
	public void activate(M subscriber);
	
	@Override
	public void desactivate(M subscriber);
	
	@Override
	public default Class<GameStateChangedEvent> getEventType()
	{
		return GameStateChangedEvent.class;
	}
}
