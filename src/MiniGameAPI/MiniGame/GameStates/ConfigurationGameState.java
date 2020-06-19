package MiniGameAPI.MiniGame.GameStates;

import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public abstract class ConfigurationGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public ConfigurationGameState(MG miniGame)
	{
		super(miniGame, "");
	}

	@Override
	public boolean canJoin() 
	{
		return true;
	}

}
