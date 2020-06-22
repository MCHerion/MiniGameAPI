package MiniGameAPI.MiniGame.GameStates;

import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public abstract class GameGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public GameGameState(MG miniGame, String name)
	{
		super(miniGame, name);
	}
}
