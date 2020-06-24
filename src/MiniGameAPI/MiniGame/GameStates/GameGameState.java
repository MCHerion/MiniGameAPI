package MiniGameAPI.MiniGame.GameStates;

import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public abstract class GameGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public GameGameState(String name)
	{
		super(name);
	}
}
