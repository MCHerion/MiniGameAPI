package MiniGameAPI.MiniGame;

import java.util.ArrayList;

public class Game
{
	protected GameState<?> _gameState;
	protected ArrayList<Team> _teams;
	
	public Game(GameState<?> gameState)
	{
		_gameState = gameState;
	}
	
	public void setGameState(GameState<?> gameState)
	{
		_gameState = gameState;
	}
	
	public GameState<?> getGameState()
	{
		return _gameState;
	}
}
