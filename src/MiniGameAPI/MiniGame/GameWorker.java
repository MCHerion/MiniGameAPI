package MiniGameAPI.MiniGame;

import org.bukkit.event.Event;

import MiniGameAPI.CustomPlayer.CustomPlayer;
import PluginUtils.Flags.FlagWorker;

public interface GameWorker<P extends CustomPlayer, M extends MiniGame<P, ?>, E extends Event> extends FlagWorker<M, E>
{
	@Override
	public void activate(M subscriber);
	
	@Override
	public void desactivate(M subscriber);
}
