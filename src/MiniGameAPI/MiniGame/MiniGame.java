package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class MiniGame<P extends CustomPlayer>
{
	protected GameState<?> _gameState;
	protected ArrayList<Team<P>> _teams;
	
	public MiniGame(GameState<?> gameState)
	{
		setGameState(gameState);
	}
	
	public ArrayList<P> getAllPlayers()
	{
		return null;
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
