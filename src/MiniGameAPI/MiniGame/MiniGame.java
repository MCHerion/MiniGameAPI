package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class MiniGame
{
	protected GameState _gameState;
	protected ArrayList<Team<CustomPlayer>> _teams;
	
	public MiniGame(GameState gameState)
	{
		setGameState(gameState);
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
