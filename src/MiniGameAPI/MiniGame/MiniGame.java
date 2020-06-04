package MiniGameAPI.MiniGame;

import java.util.ArrayList;

public class MiniGame
{
	protected GameState _gameState;
	protected ArrayList<Team> _teams;
	
	public MiniGame(GameState gameState)
	{
		_gameState = gameState;
	}
	
	public void setGameState(GameState gameState)
	{
		_gameState = gameState;
	}
	
	public GameState getGameState()
	{
		return _gameState;
	}
}
