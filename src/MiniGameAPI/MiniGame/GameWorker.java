package MiniGameAPI.MiniGame;

import org.bukkit.Bukkit;

import MiniGameAPI.CustomEvents.GameStateChangedEvent;
import MiniGameAPI.CustomPlayer.CustomPlayer;
import PluginUtils.Flags.FlagWorker;

public interface GameWorker<P extends CustomPlayer, M extends MiniGame<P>> extends FlagWorker<M, GameStateChangedEvent>
{
	public default void changeState(M subscriber, GameState<P, MiniGame<P>> newGameState)
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
