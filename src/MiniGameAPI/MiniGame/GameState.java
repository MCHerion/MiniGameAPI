package MiniGameAPI.MiniGame;

import org.bukkit.event.Event;

import PluginUtils.Flags.FlagWorker;

public interface GameState<E extends Event> extends FlagWorker<Game, E>
{
	public default void changeState(Game subscriber, GameState<?> newGameState)
	{
		subscriber.setGameState(newGameState);
	}
}
