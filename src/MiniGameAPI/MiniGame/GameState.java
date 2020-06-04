package MiniGameAPI.MiniGame;

import PluginUtils.Flags.Flag;

public interface GameState extends Flag<MiniGame>
{
	@Override
	public GameWorker<?> getFlagWorker();
	
	@Override
	public GameWorker<?> createFlagWorker();
}
