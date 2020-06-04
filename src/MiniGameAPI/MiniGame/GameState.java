package MiniGameAPI.MiniGame;

import PluginUtils.Flags.Flag;

public interface GameState<M extends MiniGame<?>> extends Flag<M>
{
	@Override
	public GameWorker<M> getFlagWorker();
	
	@Override
	public GameWorker<M> createFlagWorker();
}
