package MiniGameAPI.MiniGame;

import org.bukkit.event.Event;

import PluginUtils.Flags.FlagWorker;

public interface GameWorker<E extends Event> extends FlagWorker<MiniGame, E>
{
	public default void changeState(MiniGame subscriber, GameWorker<?> newGameState)
	{
		subscriber.setGameState(newGameState);
	}
}
