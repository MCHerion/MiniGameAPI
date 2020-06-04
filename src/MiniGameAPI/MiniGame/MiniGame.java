package MiniGameAPI.MiniGame;

import java.util.ArrayList;

public class MiniGame
{
	protected GameWorker<?> _gameState;
	protected ArrayList<Team> _teams;
	
	public MiniGame(GameWorker<?> gameState)
	{
		_gameState = gameState;
	}
	
	public void setGameState(GameWorker<?> gameState)
	{
		_gameState = gameState;
	}
	
	public GameWorker<?> getGameState()
	{
		return _gameState;
	}
}
