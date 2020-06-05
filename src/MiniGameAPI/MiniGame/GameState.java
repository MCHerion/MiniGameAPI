package MiniGameAPI.MiniGame;

import MiniGameAPI.CustomPlayer.CustomPlayer;
import PluginUtils.Flags.Flag;

public interface GameState<M extends MiniGame<? extends CustomPlayer>> extends Flag<M>
{
	@Override
	public GameWorker<M, ?> getFlagWorker();
	
	@Override
	public GameWorker<M, ?> createFlagWorker();
}
