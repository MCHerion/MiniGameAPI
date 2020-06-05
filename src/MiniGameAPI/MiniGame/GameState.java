package MiniGameAPI.MiniGame;

import MiniGameAPI.CustomPlayer.CustomPlayer;
import PluginUtils.Flags.Flag;

public interface GameState<P extends CustomPlayer, M extends MiniGame<P>> extends Flag<M>
{
	@Override
	public GameWorker<P, M> getFlagWorker();
	
	@Override
	public GameWorker<P, M> createFlagWorker();
	
	@Override
	public void subscribe(M miniGame);
	
	@Override
	public void unsubscribe(M miniGame);
}
