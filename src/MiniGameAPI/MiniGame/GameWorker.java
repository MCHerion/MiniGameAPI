package MiniGameAPI.MiniGame;

import org.bukkit.Bukkit;

import MiniGameAPI.CustomEvents.GameStateChangedEvent;
import PluginUtils.Flags.FlagWorker;

public interface GameWorker extends FlagWorker<MiniGame, GameStateChangedEvent>
{
	public default void changeState(MiniGame subscriber, GameState newGameState)
	{
		subscriber.setGameState(newGameState);
		Bukkit.getPluginManager().callEvent(new GameStateChangedEvent(newGameState));
	}
	
	@Override
	public void activate(MiniGame subscriber);
}
